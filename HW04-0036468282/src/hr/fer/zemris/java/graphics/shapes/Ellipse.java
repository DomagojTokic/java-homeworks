package hr.fer.zemris.java.graphics.shapes;

/**
 * Geometric shape ellipse. Contains basic parameters for position and
 * dimensions and method for checking if point is contained in shape.
 * 
 * @author Domagoj Tokić
 *
 */
public class Ellipse extends GeometricShape {

	/**
	 * Horizontal coordinate of center point
	 */
	int x;

	/**
	 * Vertical coordinate of center point
	 */
	int y;

	/**
	 * Horizontal radius
	 */
	int horizontalRadius;

	/**
	 * Vertical radius
	 */
	int verticalRadius;

	/**
	 * Creates instance of {@link Ellipse}
	 * 
	 * @param x horizontal coordinate of center point
	 * @param y vertical coordinate of center point
	 * @param horizontalRadius Horizontal radius
	 * @param verticalRadius Vertical radius
	 */
	public Ellipse(int x, int y, int horizontalRadius, int verticalRadius) {
		if (horizontalRadius <= 0 || verticalRadius <= 0) {
			throw new IllegalArgumentException(
					"Horisontal and vertical radius must be greater than 0");
		}
		this.x = x;
		this.y = y;
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
	}

	/**
	 * Returns horizontal coordinate of center point
	 * 
	 * @return horizontal coordinate of center point
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets horizontal coordinate of center point
	 * 
	 * @param x Horizontal coordinate of center point
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns vertical coordinate of center point
	 * 
	 * @return vertical coordinate of center point
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets vertical coordinate of center point
	 * 
	 * @param y Vertical coordinate of center point
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns horizontal radius
	 * 
	 * @return horizontal radius
	 */
	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	/**
	 * Sets horizontal radius
	 * 
	 * @param horizontalRadius Horizontal radius
	 * @throws IllegalArgumentException if new radius is less or equals 0
	 */
	public void setHorizontalRadius(int horizontalRadius) {
		if (horizontalRadius <= 0) {
			throw new IllegalArgumentException();
		}
		this.horizontalRadius = horizontalRadius;
	}

	/**
	 * Returns vertical radius
	 * 
	 * @return vertical radius
	 */
	public int getVerticalRadius() {
		return verticalRadius;
	}

	/**
	 * Sets vertical radius
	 * 
	 * @param verticalRadius Vertical radius
	 * @throws IllegalArgumentException if new radius is less or equals 0
	 */
	public void setVerticalRadius(int verticalRadius) {
		if (verticalRadius <= 0) {
			throw new IllegalArgumentException();
		}
		this.verticalRadius = verticalRadius;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		x = x - this.x;
		y = y - this.y;
		double equation = (double) Math.pow(x, 2)
				/ Math.pow(horizontalRadius, 2)
				+ (double) Math.pow(y, 2) / Math.pow(verticalRadius, 2);
		if (equation < 1) {
			return true;
		}
		return false;
	}

}
