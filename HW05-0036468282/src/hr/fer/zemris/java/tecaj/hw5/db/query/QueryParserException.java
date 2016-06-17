package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Exception for handling invalid stream of tokens query parser.
 * 
 * @author Domagoj Tokić
 *
 */
public class QueryParserException extends RuntimeException {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new QueryParserException.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructor for QueryParserException which carries message.
	 * @param message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructor for QueryParserException which carries message and exception which caused it.
	 * @param message
	 * @param cause
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor for QueryParserException which carries exception which caused it.
	 * @param cause
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
	
}
