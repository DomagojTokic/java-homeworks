package hr.fer.zemris.java.gui.charts;

/**
 * Class used to store bar x and y values used in {@link BarChart}.
 * 
 * @author Domagoj
 *
 */
public class XYValue {

	/** Domain value */
	private int x;

	/** Codomain value */
	private int y;

	/**
	 * Creates new instance of <code>XYValue</code>
	 * @param x Domain value
	 * @param y Codomain value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns domain value.
	 * @return domain value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns codomain value.
	 * @return codomain value.
	 */
	public int getY() {
		return y;
	}
}
