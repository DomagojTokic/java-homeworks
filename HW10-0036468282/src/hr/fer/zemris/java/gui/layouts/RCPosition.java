package hr.fer.zemris.java.gui.layouts;

/**
 * The <code>RCPosition</code> class specifies constraints for components that
 * are laid out using the <code>CalcLayout</code> class. <code>CalcLayout</code>
 * has a form of table so every cell has its row and column.
 * 
 * @author Domagoj
 *
 */
public class RCPosition {

	/**
	 * Specifies vertical position of cell which is bounded by upper and
	 * lower boundary.
	 */
	private int row;

	/**
	 * Specifies horizontal position of cell which is bounded by left and
	 * right boundary.
	 */
	private int column;

	/**
	 * Creates a <code>RCPosition</code> object with
     * all of its fields set to the passed-in arguments.
     * 
	 * @param row	The initial row value.
	 * @param column	The initial column value.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Method for reading row number.
	 * @return row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Method for reading column number.
	 * @return column number.
	 */
	public int getColumn() {
		return column;
	}

}
