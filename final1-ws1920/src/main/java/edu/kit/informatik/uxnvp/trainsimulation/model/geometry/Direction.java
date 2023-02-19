package edu.kit.informatik.uxnvp.trainsimulation.model.geometry;

/**
 * X- or y-axis-aligned directions.
 * @author Max Schweikart
 * @version 1.0
 */
public enum Direction {
    /**
     * The direction for going up (moving along the y-axis).
     */
    UP((byte) 0, (byte) 1),
    /**
     * The direction for going right (moving along the x-axis).
     */
    RIGHT((byte) 1, (byte) 0),
    /**
     * The direction for going down (moving along the y-axis negatively).
     */
    DOWN((byte) 0, (byte) -1),
    /**
     * The direction for going left (moving along the x-axis negatively).
     */
    LEFT((byte) -1, (byte) 0);

    private final byte dx;
    private final byte dy;

    /**
     * Constructs a direction with it's delta vector.
     * @param dx the x-value of this direction's delta vector.
     * @param dy the y-value of this direction's delta vector.
     */
    Direction(byte dx, byte dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the x-value of this direction's delta vector.
     * @return the x-value of this direction's delta vector.
     */
    public byte getDx() {
        return dx;
    }

    /**
     * Returns the y-value of this direction's delta vector.
     * @return the y-value of this direction's delta vector.
     */
    public byte getDy() {
        return dy;
    }

    /**
     * Finds the Direction that is needed for going from the origin (0,0) to the given point.
     * @param point the vector (point) to find a direction for.
     * @return the direction that is needed for going from the origin (0,0) to the given point or null if the vector is
     * not aligned to the x- or y-axis.
     */
    public static Direction fromPoint(Point point) {
        if (point.getX() == 0 && point.getY() == 0) {
            return null;
        } else if (point.getX() == 0) {
            return point.getY() > 0 ? UP : DOWN;
        } else if (point.getY() == 0) {
            return point.getX() > 0 ? RIGHT : LEFT;
        } else {
            return null;
        }
    }

    /**
     * Returns the normalized vector that corresponds to moving one step in this direction.
     * @return this direction as a point.
     */
    public Point toPoint() {
        return new Point(dx, dy);
    }

    /**
     * Returns the opposite direction of this direction, i.e. the opposite of {@code UP} is {@code DOWN}.
     * @return the opposite of this direction.
     */
    public Direction opposite() {
        return fromPoint(toPoint().invert());
    }

    /**
     * Computes the alignment of this direction's delta vector.
     * @return the alignment of this direction's delta vector.
     */
    public Alignment getAlignment() {
        return Alignment.getAlignment(Point.ORIGIN, toPoint());
    }
}
