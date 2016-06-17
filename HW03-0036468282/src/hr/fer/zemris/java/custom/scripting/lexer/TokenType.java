package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration class representing types of tokens.
 * @author Domagoj Tokić
 */
public enum TokenType {

	/**
	 * End of file token
	 */
	EOF,

	/**
	 * Text token outside tags
	 */
	TEXT,

	/**
	 * String value token inside tags
	 */
	STRING,

	/**
	 * Number token
	 */
	NUMBER,

	/**
	 * Echo tag token
	 */
	TAG_NAME,

	/**
	 * Variable name token
	 */
	VARIABLE;

}
