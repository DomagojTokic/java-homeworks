package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Raster view which provides it's representation on standard output. User is
 * able to specify characters which will represent turned on and turned off
 * pixels.
 * 
 * @author Domagoj Tokić
 *
 */
public class SimpleRasterView implements RasterView {

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
	 * Creates instance of {@link SimpleRasterView} with default turned on and
	 * turned off characters.
	 */
	public SimpleRasterView() {
		this(DEFAULT_TURNED_ON, DEFAULT_TURNED_OFF);
	}

	/**
	 * Creates instance of {@link SimpleRasterView}
	 * 
	 * @param turnedOn Character representing pixel which is turned on
	 * @param turnedOff Character representing pixel which is turned off
	 */
	public SimpleRasterView(char turnedOn, char turnedOff) {
		this.turnedOn = turnedOn;
		this.turnedOff = turnedOff;
	}

	/**
	 * Prints out raster on standard output.
	 * 
	 * @return Null pointer
	 */
	@Override
	public Object produce(BWRaster raster) {
		int width = raster.getWidth();
		int height = raster.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (raster.isTurnedOn(i, j)) {
					System.out.print(turnedOn);
				} else {
					System.out.print(turnedOff);
				}
			}
			System.out.println();
		}
		return null;
	}

}
