package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Geometric shape rectangle. Contains basic parameters for position and
 * dimensions, method for checking if point is contained in shape
 * and a method for drawing onto raster.
 * 
 * @author Domagoj Tokić
 *
 */
public class Rectangle extends GeometricShape {

	/**
	 * Horizontal coordinate of top left point
	 */
	int x;

	/**
	 * Vertical coordinate of top left point
	 */
	int y;

	/**
	 * Width of rectangle
	 */
	int width;

	/**
	 * Height of rectangle
	 */
	int height;

	/**
	 * Creates instance of {@link Rectangle}
	 * 
	 * @param x Horizontal coordinate of top left point
	 * @param y Vertical coordinate of top left point
	 * @param width Width of rectangle
	 * @param height Height of rectangle
	 */
	public Rectangle(int x, int y, int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException(
					"Width and height must be greater than 0");
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns horizontal coordinate of top left point
	 * 
	 * @return Horizontal coordinate of top left point
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets horizontal coordinate of top left point
	 * 
	 * @param x Horizontal coordinate of top left point
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns vertical coordinate of top left point
	 * 
	 * @return Vertical coordinate of top left point
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets vertical coordinate of top left point
	 * 
	 * @param y Vertical coordinate of top left point
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns width of rectangle
	 * 
	 * @return Width of rectangle
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets width of rectangle
	 * 
	 * @param width Width of rectangle
	 */
	public void setWidth(int width) {
		if (width <= 0) {
			throw new IllegalArgumentException();
		}
		this.width = width;
	}

	/**
	 * Returns height of rectangle
	 * 
	 * @return Height of rectangle
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets height of rectangle
	 * 
	 * @param height Height of rectangle
	 */
	public void setHeight(int height) {
		if (height <= 0) {
			throw new IllegalArgumentException();
		}
		this.height = height;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x)
			return false;
		if (y < this.y)
			return false;
		if (x >= this.x + width)
			return false;
		if (y >= this.y + height)
			return false;
		return true;
	}

	/**
	 * Draws rectangle by turning on all raster points from point (x, y) to
	 * width-1 (horizontally) and height-1 (vertically).
	 */
	@Override
	public void draw(BWRaster r) {
		for (int i = y; i < y + height; i++) {
			for (int j = x; j < x + width; j++) {
				r.turnOn(i, j);
			}
		}
	}

}
