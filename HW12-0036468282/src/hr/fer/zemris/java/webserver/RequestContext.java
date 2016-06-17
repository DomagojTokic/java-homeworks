package hr.fer.zemris.java.webserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * Class for sending data to web browsers. Main function is writing header when
 * user writes for the first time. Beside setting encoding, statusCode,
 * statusText and mimeType, user can add cookies which hold user data.
 * 
 * @author Domagoj
 *
 */
public class RequestContext {

	/**
	 * Class containing all necessary information to store user's personal
	 * information. It's primary use is for web-servers to adjust it's view to
	 * user by using data stored in this type of object.
	 * 
	 * @author Domagoj
	 *
	 */
	public static class RCCookie {

		/**
		 * User name key.
		 */
		private String name;

		/**
		 * User name value.
		 */
		private String value;

		/**
		 * User's domain.
		 */
		private String domain;

		/**
		 * Cookie's path.
		 */
		private String path;

		/**
		 * Maximum age of user.
		 */
		private Integer maxAge;

		/**
		 * Indicates that cookie should work only with http protocol
		 */
		private boolean httpOnly;

		/**
		 * Creates new instance of {@link RCCookie}.
		 * 
		 * @param name User name key.
		 * @param value User name value.
		 * @param maxAge Maximum age of user.
		 * @param domain Users domain.
		 * @param path Cookies path.
		 * @param httpOnly Indicates that cookie should work only with http protocol
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path,
				boolean httpOnly) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns user name key.
		 * 
		 * @return user name key.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns user name value.
		 * 
		 * @return user name value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns users domain.
		 * 
		 * @return users domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns users maximum age.
		 * 
		 * @return users maximum age.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Checks if cookie is httpOnly
		 * 
		 * @return httpOnly state
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}
	}

	/**
	 * Output stream
	 */
	private OutputStream outputStream;

	/**
	 * {@link Charset} used for writing to output.
	 */
	private Charset charset;

	/**
	 * Encoding
	 */
	private String encoding;

	/**
	 * Status code
	 */
	private int statusCode;

	/**
	 * Status text
	 */
	private String statusText;

	/**
	 * Mime type
	 */
	private String mimeType;

	/**
	 * Size of output data
	 */
	private Integer contentLength;

	/**
	 * Map of parameters. Key is the name of parameter.
	 */
	private Map<String, String> parameters;

	/**
	 * Map of temporary parameters. Key is the name of parameter.
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Map of persistent parameters. Key is the name of parameter.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * List of output cookies
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Status of header generation.
	 */
	boolean headerGenerated;

	/**
	 * Creates new instance of {@link RequestContext}.
	 * 
	 * @param outputStream Output stream
	 * @param parameters Map containing parameters
	 * @param persistentParameters Map containing persistent parameters
	 * @param outputCookies List of output cookies.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new NullPointerException("Output stream must not be null");
		}
		this.outputStream = outputStream;
		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if (outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
		temporaryParameters = new HashMap<>();
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
	}

	/**
	 * Sets encoding.
	 * 
	 * @param encoding Encoding
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated("encoding");
		this.encoding = encoding;
	}

	/**
	 * Sets status code.
	 * 
	 * @param statusCode Status code
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated("status code");
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text.
	 * 
	 * @param statusText Status text
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated("status text");
		this.statusText = statusText;
	}

	/**
	 * Sets mime type.
	 * 
	 * @param mimeType Mime type.
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated("mime type");
		this.mimeType = mimeType;
	}

	/**
	 * Number of bytes in output data
	 * 
	 * @param contentLength Size of output data.
	 */
	public void setContentLength(int contentLength) {
		checkHeaderGenerated("content length");
		this.contentLength = contentLength;
	}

	/**
	 * Checks if header is generated to output.
	 * 
	 * @param name Name of attribute for which is header generation status
	 *            checked.
	 * @throws RuntimeException if header is already generated.
	 */
	private void checkHeaderGenerated(String name) {
		if (headerGenerated) {
			throw new RuntimeException("Unable to change " + name + " after header is generated");
		}
	}

	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists).
	 * 
	 * @param name Name of parameter
	 * @return value from parameters map.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map (note,
	 * this set is read-only).
	 * 
	 * @return names of all parameters in parameters map.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 * 
	 * @param name Name of persistent parameter.
	 * @return value from persistentParameters map.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters
	 * map (note, this set is read-only).
	 * 
	 * @return names of all parameters in persistent parameters map.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name Name of persistent parameter.
	 * @param value Value of persistent parameter.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * method that removes a value from persistentParameters map:
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {

	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name Name of temporary parameter.
	 * @return value from temporaryParameters map.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map
	 * (note, this set is read-only).
	 * 
	 * @return names of all parameters in temporary parameters map.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name Name of temporary parameter.
	 * @param value Value of temporary parameter.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name Name of temporary parameter.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds cookie to output cookie list.
	 * 
	 * @param cookie Cookie for adding.
	 */
	public void addRCCookie(RCCookie cookie) {
		checkHeaderGenerated("cookies");
		outputCookies.add(cookie);
	}

	/**
	 * Removes cookie from output cookies list.
	 * 
	 * @param cookie Cookie for removal
	 */
	public void removeRCCookie(RCCookie cookie) {
		checkHeaderGenerated("cookies");
		outputCookies.remove(cookie);
	}

	/**
	 * Writes byte data to defined output. If it's first writing to output,
	 * header will be generated and written to output before writing of byte
	 * data occurs.
	 * 
	 * @param data Data for writing.
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes text to defined output. If it's first writing to output, header
	 * will be generated and written to output before writing of text occurs.
	 * 
	 * @param text Text for writing to output.
	 * @return this
	 * @throws IOException if method is unable to write to output.
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Writes header to output.
	 * 
	 * @throws IOException if method is unable to write to output.
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		String header = "";
		header += "HTTP/1.1 " + statusCode + " " + statusText + "\r\n";
		header += "Content-Type: " + mimeType;
		if (mimeType.startsWith("text/")) {
			header += "; charset=" + encoding;
		}
		header += "\r\n";
		if (contentLength != null) {
			header += "Content-Length: " + contentLength + "\r\n";
		}
		for (RCCookie cookie : outputCookies) {
			header += "Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"";
			if (cookie.domain != null)
				header += "; Domain=" + cookie.domain;
			if (cookie.path != null)
				header += "; Path=" + cookie.path;
			if (cookie.maxAge != null)
				header += "; Max-Age=" + cookie.maxAge;
			if (cookie.httpOnly) {
				header += "; HttpOnly";
			}
			header += "\r\n";
		}
		header += "\r\n";

		if (!headerGenerated) {
			outputStream.write(header.getBytes(charset));
		}
		headerGenerated = true;
	}

}
