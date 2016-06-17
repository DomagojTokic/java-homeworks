package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Graphical component which draws {@link BarChart}
 * 
 * @author Domagoj
 *
 */
public class BarChartComponent extends JComponent {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** Default text font. */
	private static final Font defaultFont = new Font("TimesRoman", Font.PLAIN, 14);
	
	/** Bar color. */
	private static final Color barColor = Color.ORANGE;
	
	/** Grid color. */
	private static final Color gridColor = Color.LIGHT_GRAY;

	/** Spacing of BarChart elements. */
	private static final int spacing = 10;

	/** Free space on top and right side of BarChart. */
	private static final int reservedEndOfGraphSpace = 50;

	/** Bar chart for drawing. */
	private BarChart chart;

	/** Y coordinate step height. */
	private int stepHeight;

	/** Chart bar Width. */
	private int barWidth;

	/** Maximum number of steps for display. */
	private int numOfSteps;

	/** Origination of bar chart */
	Point chartCorner;

	/**
	 * Creates instance of <code>BarChartComponent</code>
	 * 
	 * @param chart Bar chart for drawing.
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x = getInsets().left;
		int y = getInsets().top;
		int width = getWidth() - getInsets().left - getInsets().right;
		int height = getHeight() - getInsets().top - getInsets().bottom;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(defaultFont);

		Point labelCorner = drawNames(g2d, x, y, width, height);
		drawLabels(g2d, labelCorner, x, y, width, height);
		drawChart(g2d, x, y);

	}

	/**
	 * Writes names of x and y coordinates. Names are written parallel with it's
	 * coordinate direction.
	 * 
	 * @param g2d Graphics object for drawing.
	 * @param x Starting x coordinate of chart.
	 * @param y Starting y coordinate of chart.
	 * @param width Component width.
	 * @param height Component height.
	 * @return Bottom left point from where drawing chart labels begin.
	 */
	private Point drawNames(Graphics2D g2d, int x, int y, int width, int height) {
		int fontHeight = g2d.getFontMetrics(defaultFont).getAscent();

		int xCorner;
		int yCorner;

		g2d.setColor(Color.BLACK);

		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		g2d.drawString(chart.getyName(),
				(int) (-height / 2) - y
						- g2d.getFontMetrics(defaultFont).stringWidth(chart.getyName()) / 2,
				(int) spacing + x + fontHeight);

		at = AffineTransform.getQuadrantRotateInstance(0);
		g2d.setTransform(at);
		g2d.drawString(chart.getxName(),
				(int) (width / 2) + x
						- g2d.getFontMetrics(defaultFont).stringWidth(chart.getxName()) / 2,
				(int) (height * (1 - 0.03) + y));

		xCorner = x + spacing * 2 + fontHeight;
		yCorner = y + height - spacing * 2 - fontHeight;

		return new Point(xCorner, yCorner);
	}

	/**
	 * Draws bar chart x and y value labels.
	 * 
	 * @param g2d Graphics object for drawing.
	 * @param labelCorner Bottom left point from where drawing of labels begins.
	 * @param x Starting x coordinate of chart.
	 * @param y Starting y coordinate of chart.
	 * @param width Component width.
	 * @param height Component height.
	 */
	private void drawLabels(Graphics2D g2d, Point labelCorner, int x, int y, int width,
			int height) {
		int fontHeight = g2d.getFontMetrics(defaultFont).getAscent();
		Integer highestValue = Integer.MIN_VALUE;
		for (XYValue bar : chart.getBars()) {
			if (bar.getY() > highestValue) {
				highestValue = bar.getY();
			}
		}

		highestValue += (highestValue - chart.getyMin()) % chart.getStep();
		if (highestValue > chart.getyMax()) {
			highestValue = chart.getyMax();
		}

		int xGridCorner = (int) (labelCorner.getX() + 2 * spacing + g2d.getFontMetrics(defaultFont)
				.stringWidth("1" + highestValue.toString().replaceAll(".", "0")));
		int yGridCorner = (int) (labelCorner.getY() - 2 * spacing
				- g2d.getFontMetrics(defaultFont).getMaxAscent());

		chartCorner = new Point(xGridCorner, yGridCorner);

		int xc;
		int yc;
		int count = 0;
		numOfSteps = (highestValue - chart.getyMin()) / chart.getStep();
		stepHeight = (int) (yGridCorner - y - reservedEndOfGraphSpace) / numOfSteps;

		for (int i = chart.getyMin(), step = chart.getStep(); i <= highestValue; i += step) {
			yc = yGridCorner - stepHeight * count;
			xc = xGridCorner - g2d.getFontMetrics(defaultFont).stringWidth(Integer.toString(i))
					- spacing;
			g2d.drawString(Integer.toString(i), xc, yc);
			count++;
		}

		count = 0;
		barWidth = (width - xGridCorner + x - spacing - reservedEndOfGraphSpace)
				/ chart.getBars().size();
		for (XYValue bar : chart.getBars()) {
			xc = xGridCorner + barWidth * count;
			g2d.drawString(Integer.toString(bar.getX()), xc + barWidth / 2,
					yGridCorner + spacing + fontHeight);
			count++;
		}
	}

	/**
	 * Draws x and y coordinate, grid and bars.
	 * 
	 * @param g2d Graphics object for drawing.
	 * @param x Starting x coordinate of chart.
	 * @param y Starting y coordinate of chart.
	 */
	private void drawChart(Graphics2D g2d, int x, int y) {
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine((int) chartCorner.getX(), (int) chartCorner.getY(), (int) chartCorner.getX(),
				(int) chartCorner.getY() - numOfSteps * stepHeight - spacing);
		g2d.drawLine((int) chartCorner.getX(), (int) chartCorner.getY(),
				getWidth() - getInsets().right - reservedEndOfGraphSpace, (int) chartCorner.getY());

		g2d.setPaint(gridColor);
		// Drawing horizontal lines
		for (int i = 1; i <= numOfSteps; i++) {
			g2d.drawLine((int) chartCorner.getX() - spacing / 2,
					(int) chartCorner.getY() - i * stepHeight,
					getWidth() - getInsets().right - reservedEndOfGraphSpace,
					(int) chartCorner.getY() - i * stepHeight);
		}

		// Drawing vertical lines
		for (int i = 1, barNum = chart.getBars().size(); i <= barNum; i++) {
			g2d.drawLine((int) chartCorner.getX() + i * barWidth, (int) chartCorner.getY(),
					(int) chartCorner.getX() + i * barWidth,
					(int) chartCorner.getY() - numOfSteps * stepHeight - spacing);
		}

		// Drawing bars
		g2d.setColor(barColor);
		for (int i = 0, barNum = chart.getBars().size(); i < barNum; i++) {
			double barHeight = ((double) (chart.getBars().get(i).getY() - chart.getyMin())
					/ chart.getStep()) * stepHeight;
			g2d.fillRect((int) chartCorner.getX() + 2 + i * barWidth,
					(int) (chartCorner.getY() - barHeight), barWidth - 4, (int) barHeight - 2);
		}
	}

}
