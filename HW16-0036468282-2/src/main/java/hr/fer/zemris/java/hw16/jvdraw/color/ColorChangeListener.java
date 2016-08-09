package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Listener for selected color changes.
 * 
 * @author Domagoj Tokić
 *
 */
public interface ColorChangeListener {

	/**
	 * Action activated every time subject changes it's color property.
	 * 
	 * @param source Source/Subject of activation
	 * @param oldColor Old color
	 * @param newColor New color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
