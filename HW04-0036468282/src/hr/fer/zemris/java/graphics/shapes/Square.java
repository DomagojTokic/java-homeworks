package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Geometric shape square. Contains basic parameters for position and
 * dimensions, method for checking if point is contained in shape
 * and a method drawing onto raster.
 * 
 * @author Domagoj Tokić
 *
 */
public class Square extends GeometricShape {

	/**
	 * Horizontal coordinate of top left point
	 */
	int x;

	/**
	 * Vertical coordinate of top left point
	 */
	int y;

	/**
	 * Width and height
	 */
	int size;

	/**
	 * Creates instance of {@link Square}
	 * 
	 * @param x Horizontal coordinate of top left point
	 * @param y Vertical coordinate of top left point
	 * @param size Width and height
	 */
	public Square(int x, int y, int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Size must be greater than 0");
		}
		this.x = x;
		this.y = y;
		this.size = size;
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
	 * Returns rectangle size
	 * 
	 * @return rectangle size
	 */
	public int getSize() {
		if (size <= 0) {
			throw new IllegalArgumentException();
		}
		return size;
	}

	/**
	 * Sets rectangle size
	 * 
	 * @param size Rectangle size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x)
			return false;
		if (y < this.y)
			return false;
		if (x >= this.x + size)
			return false;
		if (y >= this.y + size)
			return false;
		return true;
	}

	/**
	 * Draws square by turning on all raster points from point (x, y) to width-1
	 * (horizontally) and height-1 (vertically).
	 */
	@Override
	public void draw(BWRaster r) {
		for (int i = 0; i < y + size; i++) {
			for (int j = 0; j < x + size; j++) {
				if (this.containsPoint(i, j)) {
					r.turnOn(i, j);
				}

			}
		}
	}

}
