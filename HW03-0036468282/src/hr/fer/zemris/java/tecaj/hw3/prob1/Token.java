package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Class defining token.
 * @author Domagoj Tokić
 *
 */
public class Token {

	/**
	 * Token type
	 */
	private TokenType type;

	/**
	 * Token value
	 */
	Object value;

	/**
	 * Creates token of given type and initializes appropriate class variable.
	 * Token type must match value: value of word is string, number is long,
	 * symbol is character, eof accepts anything because it doesn't store value.
	 * @param type Type of new token
	 * @param value Value of token
	 * @throws NullPointerException if given type is null.
	 */
	public Token(TokenType type, Object value) {
		if(type == null) {
			throw new NullPointerException("Type cannot be null");
		}

		this.type = type;
	}

	/**
	 * Returns token value
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns token type
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}
}
