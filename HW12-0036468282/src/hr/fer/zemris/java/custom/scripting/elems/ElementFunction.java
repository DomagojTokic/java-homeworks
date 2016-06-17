package hr.fer.zemris.java.custom.scripting.elems;


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

	@Override
	public String asText() {
		return name;
	}
}
