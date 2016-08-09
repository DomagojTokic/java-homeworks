package hr.fer.zemris.java.hw16.jvdraw.objects;

/**
 * Bounding box of {@link GeometricalObject} implementations.
 */
public class BoundingBox {

    private int minX;

    private int maxX;

    private int minY;

    private int maxY;

    public BoundingBox(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
