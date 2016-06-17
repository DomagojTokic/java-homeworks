package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class for storing variable or tag name.
 * @author Domagoj Tokić
 */
public class ElementVariable extends Element {

	/**
	 * Variable or tag name
	 */
	String name;

	/**
	 * Creates instance of ElementVariable
	 * @param name Name of variable or tag name
	 * @param type Type of ElementVariable
	 * @throws IllegalArgumentException if given type isn't variable or tag
	 *             name.
	 */
	public ElementVariable(String name, TokenType type) {
		this.name = name;
		if (type == TokenType.VARIABLE || type == TokenType.TAG_NAME) {
			this.type = type;
		} else {
			throw new IllegalArgumentException(
					"Illegal Element Variable state!");
		}
	}

	/**
	 * Returns name of variable
	 * @return name of variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return name;
	}

}
