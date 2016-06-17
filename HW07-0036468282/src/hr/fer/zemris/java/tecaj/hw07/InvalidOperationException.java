package hr.fer.zemris.java.tecaj.hw07;

/**
 * Exception thrown if command doesn't exist, arguments are invalid or program
 * is unable to communicate with input or output of environment.
 * 
 * @author Domagoj Tokić
 *
 */
public class InvalidOperationException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new InvalidOperationException.
	 */
	public InvalidOperationException() {
		super();
	}

	/**
	 * Constructor for InvalidOperationException which carries message.
	 * 
	 * @param message
	 */
	public InvalidOperationException(String message) {
		super(message);
	}

	/**
	 * Constructor for InvalidOperationException which carries message and
	 * exception which caused it.
	 * 
	 * @param message
	 * @param cause
	 */
	public InvalidOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for InvalidOperationException which carries exception which
	 * caused it.
	 * 
	 * @param cause
	 */
	public InvalidOperationException(Throwable cause) {
		super(cause);
	}

}
