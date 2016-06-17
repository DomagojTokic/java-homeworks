package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class for storing constant integer number.
 * @author Domagoj Tokić
 */
public class ElementConstantInteger extends Element {

	/**
	 * Value of integer element
	 */
	int value;

	/**
	 * Creates instance of ElementConstantInteger
	 * @param value Value of integer element.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
		type = TokenType.NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
