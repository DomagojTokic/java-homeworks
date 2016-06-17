package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Third {@link SmartScriptEngine} test and {@link RequestContext} test.
 * 
 * @author Domagoj
 *
 */
public class Test3 {

	/**
	 * Entrance into test.
	 * 
	 * @param args No arguments.
	 */
	public static void main(String[] args) {
		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("brojPoziva.smscr")),
					StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
			persistentParameters.put("brojPoziva", "3");
			RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
					cookies);
			new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), rc).execute();
			System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

		} catch (IOException e) {
			System.err.println("Invalid file path argument!");
			System.exit(1);
		}
	}

}
