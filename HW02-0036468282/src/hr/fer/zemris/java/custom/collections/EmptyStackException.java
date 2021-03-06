package hr.fer.zemris.java.custom.collections;

/**
 * Used to indicate that stack is empty when method tries to remove object from
 * empty stack.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Autogenerated serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor for exception which carries message.
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructor for exception which carries message and exception which caused it.
	 * @param message
	 * @param cause
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor for exception which carries exception which caused it.
	 * @param cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
}
