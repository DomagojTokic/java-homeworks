package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Component for storing color information and notifying listeners on change of
 * color.
 * 
 * @author Domagoj Tokić
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** Currently selected color */
	private Color selectedColor;

	/** Dimension of {@link JColorArea} component */
	private static final Dimension COLOR_AREA_DIMENSION = new Dimension(15, 15);

	/** Color change listeners */
	private List<ColorChangeListener> listeners;

	/**
	 * Creates new {@link JColorArea}
	 * 
	 * @param defColor Default color
	 * @param parent Parent component of {@link JColorArea}
	 * @param name Component name
	 */
	public JColorArea(Color defColor, JFrame parent, String name) {
		selectedColor = defColor;
		listeners = new ArrayList<>();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Color changedColor = JColorChooser.showDialog(parent,
						"Please select " + name + ".", Color.BLACK);
				if (changedColor != null) {
					setCurrentColor(changedColor);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider#getCurrentColor()
	 */
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Sets new color and notifies listeners if new color is different from
	 * current.
	 * 
	 * @param color New color
	 */
	public void setCurrentColor(Color color) {
		if (!color.equals(selectedColor)) {
			for (ColorChangeListener listener : listeners) {
				listener.newColorSelected(this, selectedColor, color);
			}
			selectedColor = color;
			repaint();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return COLOR_AREA_DIMENSION;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, (int) COLOR_AREA_DIMENSION.getWidth(),
				(int) COLOR_AREA_DIMENSION.getHeight());
	}

	/**
	 * Adds new {@link ColorChangeListener} listener.
	 * 
	 * @param listener
	 */
	public void addColorChangeListener(ColorChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes listener from component.
	 * 
	 * @param listener Listener for removal
	 */
	public void removeColorChangeListener(ColorChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns color in formatted string -> (R, G, B).
	 * @return color in formatted string -> (R, G, B).
	 */
	public String getColorString() {
		return "(" + selectedColor.getRed() + ", " + selectedColor.getGreen() + ", "
				+ selectedColor.getBlue() + ")";
	}
}
