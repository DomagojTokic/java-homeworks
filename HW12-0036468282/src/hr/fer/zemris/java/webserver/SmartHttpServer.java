package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implementation of http server. Supports execution of Smart Script code.
 * 
 * @author Domagoj
 *
 */
public class SmartHttpServer {

	/**
	 * Class which signifies user session and remembers session parameters. It
	 * has limited validity time. If that time has passed, it'll be ignored.
	 * 
	 * @author Domagoj
	 *
	 */
	private static class SessionMapEntry {

		/**
		 * Session id
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Time in milliseconds until which is this entry valid.
		 */
		long validUntil;
		/**
		 * Map which keeps session parameters.
		 */
		Map<String, String> map;

		/**
		 * Creates instance of {@link SessionMapEntry}
		 * 
		 * @param sid Session id
		 * @param validUntil Time in milliseconds until which is this entry
		 *            valid.
		 * @param map Map which keeps session parameters.
		 */
		public SessionMapEntry(String sid, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

	/**
	 * Server address.
	 */
	private String address;
	/**
	 * Server port.
	 */
	private int port;
	/**
	 * Maximum number of working client threads.
	 */
	private int workerThreads;
	/**
	 * Number of milliseconds after which session will become invalid.
	 */
	private int sessionTimeout;
	/**
	 * Mime types.
	 */
	private Map<String, String> mimeTypes;
	/**
	 * {@link IWebWorker} maps. Key is name of request (ea. /ext/HelloWorker),
	 * value is instance of {@link IWebWorker}.
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Server thread.
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool for executing client threads.
	 */
	private ExecutorService threadPool;
	/**
	 * Path to location on disc where server data is located.
	 */
	private Path documentRoot;
	/**
	 * All allowed session id characters.
	 */
	private String SID_CHARACTERS = "ABCDEFGHIJKLMNOPRSTUVZQXY";
	/**
	 * Flag for stopping server thread
	 */
	private boolean stop;
	/**
	 * Session map. Session id is key, {@link SessionMapEntry} is value.
	 */
	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<>();
	/**
	 * Randomizer for generation new session id.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Creates new {@link SmartHttpServer} from configuration file.
	 * 
	 * @param configFileName Path to configuration file.
	 */
	public SmartHttpServer(String configFileName) {
		try {
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(new File(configFileName));
			properties.load(fileInputStream);
			fileInputStream.close();

			address = properties.getProperty("server.address");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout")) * 1000;
			mimeTypes = getPropertyMap(properties.getProperty("server.mimeConfig"));
			workersMap = getWebWorkers(properties.getProperty("server.workers"));

		} catch (FileNotFoundException e) {
			System.err.println("Invalid server configuration properties file path!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read from server configuration properties file!");
			System.exit(1);
		}
	}

	/**
	 * Creates map from .properties file.
	 * 
	 * @param filePath path to .properties file.
	 * @return map containing keys and values according to .properties file.
	 */
	private Map<String, String> getPropertyMap(String filePath) {
		try {
			FileInputStream inputStream = new FileInputStream(
					new File(filePath));
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties.entrySet().stream().collect(
					Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
		} catch (IOException e1) {
			System.err.println("Unable to read mime configuration file");
			System.exit(1);
		}
		return null;
	}

	/**
	 * Instantiates {@link IWebWorker} workers and stores it in workers map.
	 * 
	 * @param filePath Path to .properties file containing requests as keys and
	 *            class paths as values.
	 * @return workers map.
	 */
	private Map<String, IWebWorker> getWebWorkers(String filePath) {
		Map<String, String> webWorkerMap = getPropertyMap(filePath);
		if (webWorkerMap.size() != new HashSet<>(webWorkerMap.values()).size()) {
			throw new RuntimeException("Duplicate worker classes detected!");
		}

		Map<String, IWebWorker> workersMap = new HashMap<>();
		for (Entry<String, String> entry : webWorkerMap.entrySet()) {
			try {
				Class<?> referenceToClass;
				referenceToClass = this.getClass().getClassLoader().loadClass(entry.getValue());
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(entry.getKey(), iww);
			} catch (ClassNotFoundException e) {
				System.err.println("Worker class " + entry.getValue() + " not found");
				System.exit(1);
			} catch (InstantiationException e) {
				System.err.println("Unable to instantiate " + entry.getValue() + " class");
				System.exit(1);
			} catch (IllegalAccessException e) {
				System.err.println("Unable to access " + entry.getValue() + " class");
				System.exit(1);
			}
		}
		return workersMap;
	}

	/**
	 * Starts server
	 */
	protected synchronized void start() {
		serverThread = new ServerThread();
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.run();
		cleanupThread.run();
	}

	/**
	 * Signalizes that server should stop.
	 */
	protected synchronized void stop() {
		stop = true;
		threadPool.shutdown();
	}

	/**
	 * Cleanup thread which removes invalid sessions every 5 minutes.
	 */
	private Thread cleanupThread = new Thread() {

		@Override
		public void run() {
			while (true) {
				try {
					sleep(300000);
					for (Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
						if (entry.getValue().validUntil < System.currentTimeMillis()) {
							sessions.remove(entry.getKey());
						}
					}
				} catch (InterruptedException e) {
					continue;
				}
			}
		};
	};

	/**
	 * Server thread
	 * 
	 * @author Domagoj
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while (!stop) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Client thread. Every client has it's own thread.
	 * 
	 * @author Domagoj
	 *
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Client socket.
		 */
		private Socket csocket;
		/**
		 * Client input stream (servers input).
		 */
		private PushbackInputStream istream;
		/**
		 * Client output stream (servers output).
		 */
		private OutputStream ostream;
		/**
		 * Http version.
		 */
		private String version;
		/**
		 * Clients requested method.
		 */
		private String method;
		/**
		 * Parameters sent to server through link text (part of link after '?').
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Parameters which server keeps for multiple uses by the same client.
		 */
		private Map<String, String> permPrams = new ConcurrentHashMap<>();
		/**
		 * List of {@link RCCookie} sent to user as response to request.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session id
		 */
		private String SID;

		/**
		 * Creates new instance of {@link ClientWorker}.
		 * 
		 * @param csocket Client socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> request = readRequest(istream);
				if (request == null || request.isEmpty()) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String[] firstLine = request.get(0).split(" ");

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 400, "HTTP Version Not Supported");
					return;
				}

				// Checking cookies
				permPrams = getSessionParameters(request);

				String requestedPath = firstLine[1];
				String paramString = null;
				String[] splittedRequestedPath = firstLine[1].split("\\?");
				if (splittedRequestedPath.length == 2) {
					requestedPath = splittedRequestedPath[0];
					paramString = splittedRequestedPath[1];
				}
				// Setting parameters if they exist
				if (paramString != null) {
					setParameters(paramString);
				}

				RequestContext requestContext = new RequestContext(ostream, params, permPrams,
						outputCookies);

				if (workersMap.containsKey(requestedPath)) {
					workersMap.get(requestedPath).processRequest(requestContext);
					ostream.flush();
					ostream.close();
					return;
				}

				if (Paths.get(requestedPath).isAbsolute()
						&& !Paths.get(requestedPath).startsWith(documentRoot)) {
					sendError(ostream, 403, "Forbidden access");
					return;
				}

				if (!Paths.get(requestedPath).isAbsolute()) {
					requestedPath = documentRoot + requestedPath;
				}

				File file = new File(requestedPath);
				if (!file.exists() || !file.canRead()) {
					sendError(ostream, 404, "Requested file does not exist");
					return;
				}

				String extension = null;
				String[] splittedPath = requestedPath.split("\\.");
				if (splittedPath.length == 2) {
					extension = splittedPath[1];
				}

				if (extension.equals("smscr")) {
					executeSmartScript(file, requestContext);
					ostream.flush();
					ostream.close();
					return;
				}

				byte[] outputData = Files.readAllBytes(file.toPath());

				String mime = mimeTypes.get(extension);
				if (mime == null) {
					mime = "application/octet-stream";
				}

				int statusCode = 200;

				requestContext.setContentLength(outputData.length);
				requestContext.setMimeType(mime);
				requestContext.setStatusCode(statusCode);

				requestContext.write(outputData);
				ostream.flush();
				ostream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Returns session parameters stored on server or new parameters map is
		 * current is invalid or it doesn't exit.
		 * 
		 * @param request List of request lines.
		 * @return Map containing session parameters.
		 */
		private Map<String, String> getSessionParameters(List<String> request) {
			SessionMapEntry entry = null;
			SID = checkSessionID(request);

			if (SID == null) {
				SID = generateSID();
			}

			if (!sessions.containsKey(SID)) {
				entry = new SessionMapEntry(SID,
						sessionTimeout + System.currentTimeMillis(), permPrams);
				sessions.put(SID, entry);
				outputCookies.add(new RCCookie("sid", SID, null, address, "/", true));
			} else {
				entry = sessions.get(SID);
				if (entry.validUntil - System.currentTimeMillis() < 0) {
					sessions.remove(SID);
					SID = null;
				} else {
					entry.validUntil = System.currentTimeMillis() + sessionTimeout;
				}
			}
			return entry.map;
		}

		/**
		 * Returns session id if from request lines.
		 * 
		 * @param request Request text lines.
		 * @return Session id or null if it doesn't exist.
		 */
		private String checkSessionID(List<String> request) {
			for (String line : request) {
				if (line.startsWith("Cookie: ")) {
					line = line.substring(8, line.length());
					String[] cookies = line.split(";");
					for (String cookie : cookies) {
						String[] pair = cookie.split("=");
						if (pair[0].equals("sid")) {
							return pair[1].replaceAll("[^A-Z]", "");
						}
					}
				}
			}
			return null;
		}

		/**
		 * Generates new session id.
		 * 
		 * @return new session id.
		 */
		private String generateSID() {
			String sid = "";
			for (int i = 0; i < 20; i++) {
				sid += SID_CHARACTERS.charAt(sessionRandom.nextInt(SID_CHARACTERS.length()));
			}
			return sid;
		}

		/**
		 * Executes SmartScript code and sends result to client.
		 * 
		 * @param file File containing SmartScript code.
		 * @param requestContext Result output.
		 */
		private void executeSmartScript(File file, RequestContext requestContext) {
			try {
				String docBody = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())),
						StandardCharsets.UTF_8);
				SmartScriptParser parser = new SmartScriptParser(docBody);
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(),
						requestContext);
				engine.execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Sets parameters map.
		 * 
		 * @param paramString String containing parameters.
		 */
		private void setParameters(String paramString) {
			String[] parameters = paramString.split("&");
			String[] pair;
			for (String parameter : parameters) {
				pair = parameter.split("=");
				params.put(pair[0], pair[1]);
			}
		}

		/**
		 * Reads request from client input stream and returns request text in
		 * form of String lines.
		 * 
		 * @param inputStream Client input stream.
		 * @return request text in form of String lines.
		 */
		private List<String> readRequest(PushbackInputStream inputStream) {
			try {
				byte[] requestBytes = readRequestBytes(inputStream);
				if (requestBytes == null) {
					sendError(ostream, 400, "Bad request");
					return null;
				}
				String requestString = new String(requestBytes, StandardCharsets.US_ASCII);
				return extractHeaders(requestString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Sends error message to client.
	 * 
	 * @param cos Client output stream
	 * @param statusCode Error status code
	 * @param statusText Error status text
	 */
	private static void sendError(OutputStream cos, int statusCode, String statusText) {
		try {
			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
					+ "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n"
					+ "Content-Length: 0\r\n"
					+ "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.write(("Error " + statusCode).getBytes(StandardCharsets.US_ASCII));
			cos.flush();
		} catch (SocketException ignore) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Extracts headers from request string
	 * 
	 * @param requestHeader Request header string
	 * @return List of header request lines.
	 */
	private static List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String attributes = null;
		for (String s : requestHeader.split("\n")) {
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == '\t' || c == ' ') {
				attributes += s;
			} else {
				if (attributes != null) {
					headers.add(attributes);
				}
				attributes = s;
			}
		}
		if (!attributes.isEmpty()) {
			headers.add(attributes);
		}
		return headers;
	}

	/**
	 * Transforms request data into byte array.
	 * 
	 * @param is Input stream
	 * @return byte array
	 * @throws IOException
	 */
	private static byte[] readRequestBytes(InputStream is) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		l: while (true) {
			int b = is.read();
			if (b == -1)
				return null;
			if (b != 13) {
				bos.write(b);
			}
			switch (state) {
			case 0:
				if (b == 13) {
					state = 1;
				} else if (b == 10)
					state = 4;
				break;
			case 1:
				if (b == 10) {
					state = 2;
				} else
					state = 0;
				break;
			case 2:
				if (b == 13) {
					state = 3;
				} else
					state = 0;
				break;
			case 3:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			case 4:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			}
		}
		return bos.toByteArray();
	}

	/**
	 * Entrance into {@link SmartHttpServer}.
	 * 
	 * @param args Server configuration file path
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Server must accept configuration file path!");
			System.exit(1);
		}
		new SmartHttpServer(args[0]).start();
	}
}
