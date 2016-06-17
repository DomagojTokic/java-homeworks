package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Token types adjusted for {@link QueryParser} and {@link IndexedQueryParser}
 * 
 * @author Domagoj Tokić
 *
 */
public enum TokenType {

	/**
	 * Type for attributes and logic operators
	 */
	ATTRIBUTE,

	/**
	 * Type for string values
	 */
	STRING,

	/**
	 * Type for conditions
	 */
	CONDITION,

	/**
	 * Type for query end
	 */
	END

}
