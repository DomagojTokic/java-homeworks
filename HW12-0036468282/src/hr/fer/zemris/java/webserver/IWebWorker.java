package hr.fer.zemris.java.webserver;

/**
 * Interface toward classes which create and write custom data for browsers.
 * 
 * @author Domagoj
 *
 */
public interface IWebWorker {

	/**
	 * Processes request by creating data and writing it by using
	 * {@link RequestContext}
	 * 
	 * @param context
	 */
	public void processRequest(RequestContext context);

}
