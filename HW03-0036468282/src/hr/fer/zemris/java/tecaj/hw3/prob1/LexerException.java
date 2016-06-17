package hr.fer.zemris.java.tecaj.hw3.prob1;


/**
 * Exception for cases where class Lexer fails to provide tokens.
 * @author Domagoj Tokić
 *
 */
public class LexerException extends RuntimeException{

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new LexerException.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor for LexerException which carries message.
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructor for LexerException which carries message and exception which caused it.
	 * @param message
	 * @param cause
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor for LexerException which carries exception which caused it.
	 * @param cause
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
}
