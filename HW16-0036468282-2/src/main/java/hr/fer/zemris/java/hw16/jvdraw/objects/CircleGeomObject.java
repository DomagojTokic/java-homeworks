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
 * Geometric shape circle (empty) drawn on {@link JDrawingCanvas}.
 *
 * @author Domagoj Tokić
 */
public class CircleGeomObject extends GeometricalObject {

    /** UID */
    private static final long serialVersionUID = 1L;

    /** Center X coordinate */
    protected int x;

    /** Center Y coordinate */
    protected int y;

    /** Circle radius */
    protected int radius;

    /** Number of times that this object has been instantiated */
    protected static int timesInstantiated;

    /**
     * Creates new instance of {@link CircleGeomObject}
     *
     * @param x      Center X coordinate
     * @param y      Center X coordinate
     * @param radius Circle
     * @param color  Circle color
     */
    public CircleGeomObject(int x, int y, int radius, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.foregroundColor = color;
        timesInstantiated++;
        name = "Circle " + timesInstantiated;
    }

    @Override
    public BoundingBox getBoundingBox() {
        if (boundingBox == null) {
            boundingBox = new BoundingBox(x - radius, x + radius, y - radius, y + radius);
        }
        return boundingBox;
    }

    @Override
    public void showChangeDialog(JFrame parent) {
        JPanel changePanel = new JPanel(new GridLayout(4, 2));
        JTextField xField = new JTextField(Integer.toString(x));
        JTextField yField = new JTextField(Integer.toString(y));
        JTextField radiusField = new JTextField(Integer.toString(radius));

        JColorArea color = new JColorArea(this.foregroundColor,
                parent, "color");

        changePanel.add(new JLabel("Center x-coordinate"));
        changePanel.add(xField);
        changePanel.add(new JLabel("Center y-coordinate"));
        changePanel.add(yField);
        changePanel.add(new JLabel("Radius"));
        changePanel.add(radiusField);
        changePanel.add(new JLabel("color"));
        changePanel.add(color);

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
                this.foregroundColor = color.getCurrentColor();

            } catch (NumberFormatException invalidNumber) {
                JOptionPane.showMessageDialog(parent, "Invalid number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    @Override
    public void changeEndPoint(int x2, int y2) {
        radius = (int) Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(foregroundColor);
        g.drawOval(x - (radius / 2), y - (radius / 2), radius, radius);
    }

    @Override
    public String getJVDString() {
        StringBuilder builder = new StringBuilder("CIRCLE ");
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

        return builder.toString();
    }

    @Override
    public GeometricalObject clone() {
        timesInstantiated--;
        return new CircleGeomObject(x, y, radius, foregroundColor);
    }
}
