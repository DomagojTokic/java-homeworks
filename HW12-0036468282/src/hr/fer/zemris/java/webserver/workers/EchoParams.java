package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class which sends all {@link RequestContext} parameter values to
 * {@link RequestContext} output.
 * 
 * @author Domagoj
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		try {
			context.setMimeType("text/html");
			context.write("<html><body>");
			context.write("<table border=\"1\">");

			Set<String> params = context.getParameterNames();
			context.write("<tr><td>Parameter name</td><td>Parameter value</td></tr>");

			for (String name : params) {
				String value = context.getParameter(name);
				context.write("<tr><td><b>" + name + "</b></td><td><b>" + value + "</b></td></tr>");
			}
			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
