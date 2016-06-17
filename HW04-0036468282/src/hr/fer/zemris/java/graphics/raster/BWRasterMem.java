package hr.fer.zemris.java.graphics.raster;

/**
 * Implementation of {@link BWRaster}. Gives additional option for getting flip mode
 * value.
 * 
 * @author Domagoj Tokić
 *
 */
public class BWRasterMem implements BWRaster {

	/**
	 * Raster width
	 */
	int width;
	
	/**
	 * Raster height
	 */
	int height;
	
	/**
	 * Array which stores pixel value
	 */
	boolean raster[][];
	
	/**
	 * State of flip mode
	 */
	boolean flipMode;

	/**
	 * Returns instance of {@link BWRasterMem}
	 * @param width Raster width
	 * @param height Raster height
	 */
	public BWRasterMem(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException(
					"Raster must have dimensions higher than 0");
		}

		this.width = width;
		this.height = height;
		raster = new boolean[height][width];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Returns true if flip mode is turned on, else false
	 * 
	 * @return true if flip mode is turned on, else false
	 */
	public boolean isFlipMode() {
		return flipMode;
	}

	/**
	 * Sets flip mode to provided boolean value (on if true, off if false)
	 * 
	 * @param flipMode Wanted value of flip mode (true/on, false/off)
	 */
	public void setFlipMode(boolean flipMode) {
		this.flipMode = flipMode;
	}

	@Override
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				turnOff(i, j);
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			if (flipMode) {
				raster[x][y] = raster[x][y] ^ true;
			} else {
				raster[x][y] = true;
			}
		}
	}

	@Override
	public void turnOff(int x, int y) {
		if (x > 0 && y > 0 && x < width && y < height) {
			raster[x][y] = false;
		}
	}

	@Override
	public void enableFlipMode() {
		flipMode = true;

	}

	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		if (x >= 0 && y >= 0 && x < height && y < width) {
			return raster[x][y];
		}
		throw new IllegalArgumentException();
	}

}
