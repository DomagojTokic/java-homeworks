package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Exception for handling invalid stream of characters query lexer.
 * 
 * @author Domagoj Tokić
 *
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new QueryLexerException.
	 */
	public QueryLexerException() {
		super();
	}

	/**
	 * Constructor for QueryLexerException which carries message.
	 * 
	 * @param message
	 */
	public QueryLexerException(String message) {
		super(message);
	}

	/**
	 * Constructor for QueryLexerException which carries message and exception
	 * which caused it.
	 * 
	 * @param message
	 * @param cause
	 */
	public QueryLexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for QueryLexerException which carries exception which caused
	 * it.
	 * 
	 * @param cause
	 */
	public QueryLexerException(Throwable cause) {
		super(cause);
	}

}
