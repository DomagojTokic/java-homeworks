package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class for storing constant double number.
 * @author Domagoj Tokić
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value of double element
	 */
	double value;

	/**
	 * Creates instance ElementConstantDouble
	 * @param value Value of double element.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
		type = TokenType.NUMBER;
	}
	
	/**
	 * Returns value of element
	 * @return value of element
	 */
	public double getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
