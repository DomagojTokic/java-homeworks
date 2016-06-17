package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Class for storing value and describing type of value for parsing.
 * 
 * @author Domagoj Tokić
 *
 */
public class Token {

	/**
	 * Token value
	 */
	private String value;

	/**
	 * Token type
	 */
	private TokenType type;

	/**
	 * Creates instance of Token
	 * @param value Token value
	 * @param type Token type
	 */
	public Token(String value, TokenType type) {
		super();
		this.value = value;
		this.type = type;
	}

	/**
	 * Returns token value.
	 * @return token value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns token type.
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}

}
