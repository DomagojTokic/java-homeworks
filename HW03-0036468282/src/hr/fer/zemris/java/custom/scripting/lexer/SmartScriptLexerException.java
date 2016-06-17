package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception thrown when lexer was unable to generate token.
 * @author Domagoj
 *
 */
public class SmartScriptLexerException extends RuntimeException {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new SmartScriptLexerException.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructor for SmartScriptLexerException which carries message.
	 * @param message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

	/**
	 * Constructor for SmartScriptLexerException which carries message and exception which caused it.
	 * @param message
	 * @param cause
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor for SmartScriptLexerException which carries exception which caused it.
	 * @param cause
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}
}
