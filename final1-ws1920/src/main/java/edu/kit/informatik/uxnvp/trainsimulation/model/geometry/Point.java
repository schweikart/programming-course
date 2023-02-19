package edu.kit.informatik.uxnvp.trainsimulation.model.geometry;

/**
 * A two-dimensional point with integer coordinates.
 * @author Max Schweikart
 * @version 1.0
 */
public class Point {
    /**
     * The origin of the cartesian coordinate system.
     */
    public static final Point ORIGIN = new Point(0, 0);

    /**
     * The x-axis coordinate of this point.
     */
    private final int x;
    /**
     * The y-axis coordinate of this point.
     */
    private final int y;

    /**
     * Constructs a new point with it's coordinates.
     * @param x the x-axis coordinate of this point.
     * @param y the y-axis coordinate of this point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-axis coordinate of this point.
     * @return the x-axis coordinate of this point.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-axis coordinate of this point.
     * @return the y-axis coordinate of this point.
     */
    public int getY() {
        return y;
    }

    /**
     * Adds another point onto this one without modifying either one.
     * @param other the point to add to this one. May not be null.
     * @return a new Point whose coordinates are the sum of this and the other Point's coordinates.
     */
    public Point add(Point other) {
        return new Point(getX() + other.getX(), getY() + other.getY());
    }

    /**
     * Subtracts another point from this one without modifying either one.
     * @param other the point to subtract from this one. May not be null.
     * @return a new Point whose coordinates are the difference of this and the other Point's coordinates.
     */
    public Point subtract(Point other) {
        return new Point(getX() - other.getX(), getY() - other.getY());
    }

    /**
     * Inverts this Point without modifying it.
     * @return a new Point whose coordinates are the additive inverse of this Point's coordinates.
     */
    public Point invert() {
        return new Point(-getX(), -getY());
    }

    /**
     * Calculates the distance to another point, if the line between the points is
     * {@link Alignment#isAligned(Point, Point) aligned}.<br/>
     * Note: We are using long because the distance between very high and very low integer values can exceed the integer
     * size limit (e.g. {@code Integer.MAX_VALUE - Integer.MIN_VALUE > Integer.MAX_VALUE}).
     * @param other the point to calculate the distance to.
     * @return the length of the line between both points.
     * @throws IllegalArgumentException if the other point is not aligned to this point.
     */
    public long distanceTo(Point other) {
        Alignment alignment = Alignment.getAlignment(this, other);
        if (alignment == Alignment.HORIZONTAL) {
            return Math.abs((long) this.getX() - (long) other.getX());
        } else if (alignment == Alignment.VERTICAL) {
            return Math.abs((long) this.getY() - (long) other.getY());
        } else {
            throw new IllegalArgumentException("can not distance between unaligned points.");
        }
    }

    /**
     * Checks if this point is equal to another object.
     * @param obj the other obj to check for equality.
     * @return true if both objects are instances of the {@link Point} class and if their coordinates are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() != getClass()) {
            return false;
        } else {
            Point other = (Point) obj;
            return getX() == other.getX() && getY() == other.getY();
        }
    }

    @Override
    public int hashCode() {
        return x + y;
    }

    /**
     * The String representation of a Point is in the following format: "(&lt;x&gt;,&lt;y&gt;)"
     * @return the string representation of this point.
     */
    @Override
    public String toString() {
        return String.format("(%s,%s)", x, y);
    }
}
