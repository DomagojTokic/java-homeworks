package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception thrown when parser fails to parse lexer elements by given rules.
 * @author Domagoj Tokić
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new SmartScriptParserException.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructor for SmartScriptParserException which carries message.
	 * @param message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructor for SmartScriptParserException which carries message and
	 * exception which caused it.
	 * @param message
	 * @param cause
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for SmartScriptParserException which carries exception which
	 * caused it.
	 * @param cause
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

}
