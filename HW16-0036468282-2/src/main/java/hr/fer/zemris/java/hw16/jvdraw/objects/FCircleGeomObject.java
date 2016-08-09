package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.draw.JDrawingCanvas;

/**
 * Geometric shape filled circle drawn of {@link JDrawingCanvas}.
 * 
 * @author Domagoj Tokić
 *
 */
public class FCircleGeomObject extends CircleGeomObject {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** Number of times that this object has been instantiated */
	protected static int timesInstantiated;

	/**
	 * Creates new instance of {@link FCircleGeomObject}.
	 * 
	 * @param x Center X coordinate
	 * @param y Center X coordinate
	 * @param radius Circle
	 * @param foregroundColor Circle border color
	 * @param backgroundColor Circle fill color.
	 */
	public FCircleGeomObject(int x, int y, int radius, Color foregroundColor,
			Color backgroundColor) {
		super(x, y, radius, foregroundColor);
		this.backgroundColor = backgroundColor;
		timesInstantiated++;
		name = "Filled circle " + timesInstantiated;
	}

	@Override
	public void showChangeDialog(JFrame parent) {
		JPanel changePanel = new JPanel(new GridLayout(5, 2));
		JTextField xField = new JTextField(Integer.toString(x));
		JTextField yField = new JTextField(Integer.toString(y));
		JTextField radiusField = new JTextField(Integer.toString(radius));
		JColorArea fillColor = new JColorArea(this.backgroundColor, parent,
				"fill color");
		JColorArea outlineColor = new JColorArea(this.foregroundColor,
				parent, "outline color");

		changePanel.add(new JLabel("Center x-coordinate"));
		changePanel.add(xField);
		changePanel.add(new JLabel("Center y-coordinate"));
		changePanel.add(yField);
		changePanel.add(new JLabel("Radius"));
		changePanel.add(radiusField);
		changePanel.add(new JLabel("Fill color"));
		changePanel.add(fillColor);
		changePanel.add(new JLabel("Outline color"));
		changePanel.add(outlineColor);

		if (JOptionPane.showConfirmDialog(parent, changePanel,
				"Set new " + name + " values",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				int x = Integer.parseInt(xField.getText());
				int y = Integer.parseInt(yField.getText());
				int radius = Integer.parseInt(radiusField.getText());

				this.x = x;
				this.y = y;
				this.radius = radius;
				this.backgroundColor = fillColor.getCurrentColor();
				this.foregroundColor = outlineColor.getCurrentColor();

			} catch (NumberFormatException invalidNumber) {
				JOptionPane.showMessageDialog(parent, "Invalid number",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(backgroundColor);
		g.fillOval(x - (radius / 2), y - (radius / 2), radius, radius);

		g.setColor(foregroundColor);
		g.drawOval(x - (radius / 2), y - (radius / 2), radius, radius);
	}
	
	@Override
	public String getJVDString() {
		StringBuilder builder = new StringBuilder("FCIRCLE ");
		builder.append(x);
		builder.append(" ");
		builder.append(y);
		builder.append(" ");
		builder.append(radius);
		builder.append(" ");
		builder.append(foregroundColor.getRed());
		builder.append(" ");
		builder.append(foregroundColor.getGreen());
		builder.append(" ");
		builder.append(foregroundColor.getBlue());
		builder.append(" ");
		builder.append(backgroundColor.getRed());
		builder.append(" ");
		builder.append(backgroundColor.getGreen());
		builder.append(" ");
		builder.append(backgroundColor.getBlue());
		
		return builder.toString();
	}

	@Override
	public GeometricalObject clone() {
		timesInstantiated--;
		return new FCircleGeomObject(x, y, radius, foregroundColor, backgroundColor);
	}
}
