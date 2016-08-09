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
 * Geometric shape line (empty) drawn on {@link JDrawingCanvas}.
 * 
 * @author Domagoj Tokić
 *
 */
public class LineGeomObject extends GeometricalObject {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** Start X coordiante */
	private int x1;
	
	/** Start Y coordiante */
	private int y1;
	
	/** End X coordiante */
	private int x2;
	
	/** End Y coordiante */
	private int y2;
	
	/** Number of times that this object has been instantiated */
	protected static int timesInstantiated;
	
	/**
	 * Creates new {@link LineGeomObject}.
	 * @param x1 Start X coordinate
	 * @param y1 Start Y coordinate
	 * @param x2 End X coordinate
	 * @param y2 End Y coordinate
	 * @param color Line color
	 */
	public LineGeomObject(int x1, int y1, int x2, int y2, Color color) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;		
		this.foregroundColor = color;
		timesInstantiated++;
		name = "Line " + timesInstantiated;
	}

	@Override
	public BoundingBox getBoundingBox() {
		if(boundingBox == null) {
			int minX = x1 < x2 ? x1 : x2;
			int maxX = x1 > x2 ? x1 : x2;
			int minY = y1 < y2 ? y1 : y2;
			int maxY = y1 > y2 ? y1 : y2;
			boundingBox = new BoundingBox(minX, maxX, minY, maxY);
		}
		return boundingBox;
	}

	@Override
	public void showChangeDialog(JFrame parent) {
		JPanel changePanel = new JPanel(new GridLayout(5, 2));
		JTextField x1Field = new JTextField(Integer.toString(x1));
		JTextField y1Field = new JTextField(Integer.toString(y1));
		JTextField x2Field = new JTextField(Integer.toString(x2));
		JTextField y2Field = new JTextField(Integer.toString(y2));

		JColorArea color = new JColorArea(this.foregroundColor, parent,
				"color");


		changePanel.add(new JLabel("Start x-coordinate"));
		changePanel.add(x1Field);
		changePanel.add(new JLabel("Start y-coordinate"));
		changePanel.add(y1Field);
		changePanel.add(new JLabel("End x-coordinate"));
		changePanel.add(x2Field);
		changePanel.add(new JLabel("End y-coordinate"));
		changePanel.add(y2Field);
		changePanel.add(new JLabel("Color"));
		changePanel.add(color);

		if (JOptionPane.showConfirmDialog(parent, changePanel,
				"Set new " + name + " values",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				int x1 = Integer.parseInt(x1Field.getText());
				int y1 = Integer.parseInt(y1Field.getText());
				int x2 = Integer.parseInt(x2Field.getText());
				int y2 = Integer.parseInt(y2Field.getText());

				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				this.foregroundColor = color.getCurrentColor();

			} catch (NumberFormatException invalidNumber) {
				JOptionPane.showMessageDialog(parent, "Invalid number",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void changeEndPoint(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(foregroundColor);
		g.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public String getJVDString() {
		StringBuilder builder = new StringBuilder("LINE ");
		builder.append(x1);
		builder.append(" ");
		builder.append(y1);
		builder.append(" ");
		builder.append(x2);
		builder.append(" ");
		builder.append(y2);
		builder.append(" ");
		builder.append(foregroundColor.getRed());
		builder.append(" ");
		builder.append(foregroundColor.getGreen());
		builder.append(" ");
		builder.append(foregroundColor.getBlue());
		
		return builder.toString();
	}

	@Override
	public GeometricalObject clone() {
		timesInstantiated--;
		return new LineGeomObject(x1, y1, x2, y2, foregroundColor);
	}
}
