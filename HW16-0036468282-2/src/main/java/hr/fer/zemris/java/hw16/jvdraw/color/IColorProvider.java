package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Interface for classes which provide {@link Color} to its client.
 * 
 * @author Domagoj Tokić
 *
 */
public interface IColorProvider {

	/**
	 * Returns currently set color.
	 * 
	 * @return currently set color.
	 */
	Color getCurrentColor();

}
