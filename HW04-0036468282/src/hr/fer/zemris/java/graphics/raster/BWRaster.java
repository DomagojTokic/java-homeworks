package hr.fer.zemris.java.graphics.raster;

/**
 * Interface for rasters providing only two options for storing pixel value -
 * turned on and turned off (black and white). Raster provides flip mode. If
 * flipping mode of raster is disabled, then the call of the turnOn method turns
 * on the pixel at specified location (if location is valid). However, if
 * flipping mode is enabled, then the call of the turnOn method flips the pixel
 * at the specified location (if it was turned on, it must be turned off, and if
 * it was turned off, it must be turned on).
 * 
 * @author Domagoj Tokić
 *
 */
public interface BWRaster {

	/**
	 * Returns raster width
	 * @return raster width
	 */
	int getWidth();

	/**
	 * Returns raster height
	 * @return raster height
	 */
	int getHeight();

	/**
	 * Turns off all pixels on raster
	 */
	void clear();

	/**
	 * Turns on pixel at given coordinate if it exists on raster.
	 * @param x Horizontal coordinate
	 * @param y Vertical coordinate
	 */
	void turnOn(int x, int y);

	/**
	 * Turns off pixel at given coordinate if it exists on raster.
	 * @param x Horizontal coordinate
	 * @param y Vertical coordinate
	 */
	void turnOff(int x, int y);

	/**
	 * Enables flip mode of raster
	 */
	void enableFlipMode();

	/**
	 * Disables flip mode of raster
	 */
	void disableFlipMode();

	/**
	 * Checks if pixel is turned on
	 * @param x Horizontal coordinate
	 * @param y Vertical coordinate
	 * @return True if pixel is turned on, else false
	 */
	boolean isTurnedOn(int x, int y);

}
