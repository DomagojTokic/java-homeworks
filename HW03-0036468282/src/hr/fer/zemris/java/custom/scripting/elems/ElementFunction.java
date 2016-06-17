package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;

/**
 * Class for storing function names.
 * @author Domagoj Tokić
 */
public class ElementFunction extends Element {

	/**
	 * Name of function.
	 */
	String name;

	/**
	 * Creates instance of ElementFunction.
	 * @param name Name of function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return SmartScriptLexer.functionSymbol +  name;
	}
}
