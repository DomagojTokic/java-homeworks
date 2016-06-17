package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class for storing operators.
 * @author Domagoj Tokić
 */
public class ElementOperator extends Element {

	/**
	 * Symbol of operation
	 */
	char symbol;

	/**
	 * Creates instance of ElementOperator.
	 * @param symbol Symbol of operation.
	 */
	public ElementOperator(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Character.toString(symbol);
	}
}
