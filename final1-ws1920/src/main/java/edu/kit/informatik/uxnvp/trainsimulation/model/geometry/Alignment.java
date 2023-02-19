package edu.kit.informatik.uxnvp.trainsimulation.model.geometry;

/**
 * Alignments to the axes of the coordinate system.
 * @author Max Schweikart
 * @version 1.0
 */
public enum Alignment {
    /**
     * Alignment of something that is parallel to the x axis.
     */
    HORIZONTAL,
    /**
     * Alignment of something that is parallel to the y axis.
     */
    VERTICAL;

    /**
     * Finds the alignment of a line between a given set of points.
     * @param point1 the first point of the line.
     * @param point2 the second point of the line.
     * @return {@code HORIZONTAL} if the line is parallel to the x axis,
     *         {@code VERTICAL} if the line is parallel to the y axis and
     *         {@code null} otherwise (especially if both points are identical).
     */
    public static Alignment getAlignment(Point point1, Point point2) {
        if (point1.equals(point2)) {
            return null;
        } else if (point1.getY() == point2.getY()) { // same y -> parallel to x axis
            return HORIZONTAL;
        } else if (point1.getX() == point2.getX()) { // same x -> parallel to y axis
            return VERTICAL;
        } else {
            return null;
        }
    }

    /**
     * Checks if the line between a given set of points is aligned (vertically or horizontally).
     * @param point1 the first point of the line.
     * @param point2 the second point of the line.
     * @return {@code true} if the line is vertically or horizontally aligned and {@code false} otherwise.
     */
    public static boolean isAligned(Point point1, Point point2) {
        return getAlignment(point1, point2) != null;
    }
}
