package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Raster view which provides it's representation as String. User is
 * able to specify characters which will represent turned on and turned off
 * pixels.
 * 
 * @author Domagoj Tokić
 *
 */
public class StringRasterView implements RasterView {

	/**
	 * Character representing pixel which is turned on
	 */
	private char turnedOn;
	
	/**
	 * Character representing pixel which is turned off
	 */
	private char turnedOff;

	/**
	 * Default turned on character
	 */
	private static char DEFAULT_TURNED_ON = '*';
	
	/**
	 * Default turned off character
	 */
	private static char DEFAULT_TURNED_OFF = '.';

	/**
	 * Creates instance of {@link StringRasterView} with default turned on and
	 * turned off characters.
	 */
	public StringRasterView() {
		this(DEFAULT_TURNED_ON, DEFAULT_TURNED_OFF);
	}

	/**
	 * Creates instance of {@link StringRasterView}
	 * 
	 * @param turnedOn Character representing pixel which is turned on
	 * @param turnedOff Character representing pixel which is turned off
	 */
	public StringRasterView(char turnedOn, char turnedOff) {
		this.turnedOn = turnedOn;
		this.turnedOff = turnedOff;
	}

	/**
	 * Returns String representation of raster
	 * @return String representation of raster
	 */
	@Override
	public Object produce(BWRaster raster) {
		int width = raster.getWidth();
		int height = raster.getHeight();
		String string = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (raster.isTurnedOn(i, j)) {
					string += turnedOn;
				} else {
					string += turnedOff;
				}
			}
			if (i != height - 1) {
				string += "\n";
			}

		}
		return string;
	}

}
