package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class for geometric shapes. Meant for drawing shapes onto raster
 * class.
 * 
 * @author Domagoj Tokić
 *
 */
public abstract class GeometricShape {

	/**
	 * Draws geometric shape onto raster. Recommended for overriding if user
	 * wants more efficient method - this method tests every pixel on raster.
	 * 
	 * @param r Raster where shape will be drawn
	 */
	public void draw(BWRaster r) {
		for (int i = 0, ymax = r.getHeight(); i < ymax; i++) {
			for (int j = 0, xmax = r.getWidth(); j < xmax; j++) {
				if (this.containsPoint(j, i)) {
					r.turnOn(j, i);
				}

			}
		}
	}

	/**
	 * Returns true if geometric shape contains point with coordinates x, y
	 * 
	 * @param x Horizontal coordinate
	 * @param y Vertical coordinate
	 * @return True if geometric shape contains given point
	 */
	public abstract boolean containsPoint(int x, int y);

}
