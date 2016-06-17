package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * A bar chart uses vertical bars to show comparisons in various categories. One
 * axis (domain axis) of the chart shows the specific domain being compared, and
 * the other axis (range axis) represents discrete values. In current
 * implementation, domain values are whole numbers. Bar values are instances of
 * {@link XYValue}. User also specifies domain (x) and value (y) names and step
 * in values.
 * 
 * @author Domagoj
 *
 */
public class BarChart {

	/**
	 * Bar values
	 */
	private List<XYValue> bars;

	/**
	 * Domain name
	 */
	private String xName;

	/**
	 * Value name
	 */
	private String yName;

	/**
	 * Minimum value
	 */
	private int yMin;

	/**
	 * Maximum value
	 */
	private int yMax;

	/**
	 * Step in values
	 */
	private int step;

	/**
	 * Creates instance of <code>BarChart</code>
	 * 
	 * @param bars Bar values
	 * @param xName Domain name
	 * @param yName Value name
	 * @param yMin Minimum value
	 * @param yMax Maximum value
	 * @param step Value step
	 */
	public BarChart(List<XYValue> bars, String xName, String yName, int yMin, int yMax, int step) {
		super();
		this.bars = bars;
		this.xName = xName;
		this.yName = yName;
		this.yMin = yMin;
		this.yMax = yMax;
		this.step = step;
	}

	/**
	 * Returns bar values.
	 * 
	 * @return bar values.
	 */
	public List<XYValue> getBars() {
		return bars;
	}

	/**
	 * Returns domain name.
	 * 
	 * @return domain name.
	 */
	public String getxName() {
		return xName;
	}

	/**
	 * Returns value name.
	 * 
	 * @return value name.
	 */
	public String getyName() {
		return yName;
	}

	/**
	 * Returns minimum value.
	 * 
	 * @return minimum value.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns maximum value.
	 * 
	 * @return maximum value.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns step value.
	 * 
	 * @return step value.
	 */
	public int getStep() {
		return step;
	}
}
