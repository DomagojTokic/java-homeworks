package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class used for storing token elements with special meaning (ie. start of tag,
 * end of tag, for loop, end of for loop etc.) or for inheritance when element
 * stores data.
 * @author Domagoj Tokić
 */
public class Element {

	/**
	 * Variable for storing Element type.
	 */
	TokenType type;

	/**
	 * Creates instance of empty element
	 */
	public Element() {

	}

	/**
	 * Creates element. Used only for EOF element.
	 * @param type State of new element
	 */
	public Element(TokenType type) {
		this.type = type;
	}

	/**
	 * Returns string representation of element as written in code.
	 * @return String representation of element as written in code.
	 */
	public String asText() {
		return "";
	}

	/**
	 * Returns type of element
	 * @return type of element
	 */
	public TokenType getType() {
		return type;
	}

}
