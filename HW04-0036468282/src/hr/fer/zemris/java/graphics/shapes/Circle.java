package hr.fer.zemris.java.graphics.shapes;

/**
 * Geometric shape circle. Contains basic parameters for position and
 * dimensions and method for checking if point is contained in shape.
 * 
 * @author Domagoj Tokić
 *
 */
public class Circle extends GeometricShape {

	/**
	 * Horizontal coordinate of center point
	 */
	int x;

	/**
	 * Vertical coordinate of center point
	 */
	int y;

	/**
	 * Circle radius
	 */
	int radius;

	/**
	 * Returns instance of {@link Circle}
	 * 
	 * @param x Horizontal coordinate of center point
	 * @param y Vertical coordinate of center point
	 * @param radius Circle radius
	 */
	public Circle(int x, int y, int radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("Radius must be greater than 0");
		}
		this.x = x;
		this.y = y;
		this.radius = radius;
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
	 * Returns circle radius
	 * 
	 * @return circle radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets circle radius
	 * 
	 * @param radius
	 * @throws IllegalArgumentException if new radius is less or equals 0
	 */
	public void setRadius(int radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException();
		}
		this.radius = radius;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		x = x - this.x;
		y = y - this.y;
		double squaredRadius = Math.pow(radius, 2);
		double equation = Math.pow(x, 2) + Math.pow(y, 2);
		if (equation < squaredRadius) {
			return true;
		}
		return false;
	}

}
