package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JFrame;

import hr.fer.zemris.java.hw16.jvdraw.draw.JDrawingCanvas;
import javafx.scene.shape.Circle;

/**
 * Abstract class for all shapes drawn on {@link JDrawingCanvas}.
 *
 * @author Domagoj Tokić
 */
public abstract class GeometricalObject extends JComponent {

    /** UID */
    private static final long serialVersionUID = 1L;

    /** Background (fill) color */
    protected Color backgroundColor;

    /** Foreground (border) color */
    protected Color foregroundColor;

    /** Object name */
    protected String name;

    /** Bounding Box */
    protected BoundingBox boundingBox;

    /**
     * Displays dialog for changing geometrical object parameters.
     *
     * @param parent Parent window
     */
    public abstract void showChangeDialog(JFrame parent);

    /**
     * Changes end point.
     *
     * @param x2 Ending X coordinate
     * @param y2 Ending Y coordinate
     */
    public abstract void changeEndPoint(int x2, int y2);

    /**
     * Returns geometrical objects {@link BoundingBox}
     */
    public abstract BoundingBox getBoundingBox();

    /**
     * Returns string used to create JVD file.
     *
     * @return string used to create JVD file.
     */
    public abstract String getJVDString();

    /**
     * Clones object
     *
     * @return cloned object
     */
    public abstract GeometricalObject clone();

    @Override
    public abstract void paint(Graphics g);


    /**
     * Creates new {@link GeometricalObject} with given parameters based ond
     * given {@link GeometricalObjectType}.
     *
     * @param x1              Start X coordinate
     * @param y1              Start Y coordinate
     * @param x2              End X coordinate
     * @param y2              End Y coordinate
     * @param backgroundColor Background color
     * @param foregroundColor Foreground color
     * @param type            Geometric object type
     * @return Concrete implementation of {@link GeometricalObject}.
     */
    public static GeometricalObject createGeometricalObject(int x1, int y1, int x2, int y2,
                                                            Color backgroundColor, Color foregroundColor, GeometricalObjectType type) {
        switch (type) {
            case LINE:
                return new LineGeomObject(x1, y1, x2, y2, foregroundColor);
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                return new CircleGeomObject(x1, y1, radius, foregroundColor);
            case FCIRCLE:
                int radiusF = (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                return new FCircleGeomObject(x1, y1, radiusF, foregroundColor, backgroundColor);
            default:
                return null;
        }
    }

    /**
     * Returns {@link GeometricalObject} from string.
     *
     * @param string String containing information about {@link GeometricalObject}
     * @return {@link GeometricalObject}
     */
    public static GeometricalObject fromString(String string) {
        String[] split = string.split(" ");
        switch (split[0]) {
            case "LINE":
                int x1 = Integer.valueOf(split[1]);
                int y1 = Integer.valueOf(split[2]);
                int x2 = Integer.valueOf(split[3]);
                int y2 = Integer.valueOf(split[4]);
                int r = Integer.valueOf(split[5]);
                int g = Integer.valueOf(split[6]);
                int b = Integer.valueOf(split[7]);
                return new LineGeomObject(x1, y1, x2, y2, new Color(r, g, b));
            case "CIRCLE":
                int x = Integer.valueOf(split[1]);
                int y = Integer.valueOf(split[2]);
                int radius = Integer.valueOf(split[3]);
                int rc = Integer.valueOf(split[4]);
                int gc = Integer.valueOf(split[5]);
                int bc = Integer.valueOf(split[6]);
                return new CircleGeomObject(x, y, radius, new Color(rc, gc, bc));
            case "FCIRCLE":
                int xfc = Integer.valueOf(split[1]);
                int yfc = Integer.valueOf(split[2]);
                int radiusfc = Integer.valueOf(split[3]);
                int rfc = Integer.valueOf(split[4]);
                int gfc = Integer.valueOf(split[5]);
                int bfc = Integer.valueOf(split[6]);
                int rfc1 = Integer.valueOf(split[7]);
                int gfc1 = Integer.valueOf(split[8]);
                int bfc1 = Integer.valueOf(split[9]);
                return new FCircleGeomObject(xfc, yfc, radiusfc, new Color(rfc, gfc, bfc),
                        new Color(rfc1, gfc1, bfc1));
            default:
                return null;
        }
    }

    public static void restartCounters() {
        CircleGeomObject.timesInstantiated = 0;
        FCircleGeomObject.timesInstantiated = 0;
        LineGeomObject.timesInstantiated = 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
