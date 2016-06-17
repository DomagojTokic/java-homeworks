package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class for storing elements with string value.
 * @author Domagoj Tokić
 *
 */
public class ElementString extends Element {

	/**
	 * Value of element string
	 */
	String value;

	/**
	 * Returns instance of ElementString
	 * @param value string value of element.
	 * @param type Type of ElementString
	 * @throws IllegalArgumentException if type isn't string or text.
	 */
	public ElementString(String value, TokenType type) {
		this.value = value;
		if (type == TokenType.STRING || type == TokenType.TEXT) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("Invalid state of ElementString");
		}
	}

	/**
	 * Returns value of element.
	 * @return value of element.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value;
	}
}
