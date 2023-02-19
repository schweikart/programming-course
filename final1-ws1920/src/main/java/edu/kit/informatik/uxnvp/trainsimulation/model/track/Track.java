package edu.kit.informatik.uxnvp.trainsimulation.model.track;

import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Alignment;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Direction;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.util.MathUtility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A track that {@link Train}s can run on.
 * @author Max Schweikart
 * @version 1.0
 */
public class Track implements Comparable<Track> {
    private final Point startPoint;
    private final Point endPoint;

    private int id = -1;

    /**
     * Construct a new track based on a start- and endpoint.
     * @param startPoint the starting point of this track.
     * @param endPoint the ending point of this track.
     */
    public Track(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Returns the numeric unique identifier for this track.
     * @return the numeric unique identifier for this track.
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the identifier of this track. TODO identifiers should be final
     * @param id the new identifier for this track.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Calculates the distance between the start- and endpoint of this track.
     * @return the length of this track.
     * @throws UnsupportedOperationException if this track is not aligned.
     */
    public long getLength() {
        return getStartPoint().distanceTo(getEndPoint());
    }

    /**
     * Returns the ending point of this track.
     * @return the ending point of this track.
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Returns the starting point of this track.
     * @return the starting point of this track.
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Returns the alignment of this track.
     * @return the alignment of this track or null if this track is not aligned.
     */
    public Alignment getAlignment() {
        return Alignment.getAlignment(getStartPoint(), getEndPoint());
    }

    /**
     * Returns a set of Points this track is connected to.
     * @return a set of Points this track is connected to.
     */
    public Set<Point> getConnectionPoints() {
        return new HashSet<>(Arrays.asList(startPoint, endPoint));
    }

    /**
     * Compares this track with another one based on their identifiers.
     * @param o the track to compare this track with.
     * @return 0 if the identifiers are identical, a value greater than 0 if this track's id is greater and a value less
     * than 0 if this track's id is smaller than the other track's id.
     */
    @Override
    public int compareTo(Track o) {
        return Integer.compare(getId(), o.getId());
    }

    /**
     * Returns the string representation of this track.
     * @return the string representation of this track.
     */
    @Override
    public String toString() {
        return String.format("t %s %s -> %s %s", getId(), getStartPoint(), getEndPoint(), getLength());
    }

    /**
     * Checks whether a point on the active part of this track.
     * @param point the point to check.
     * @return true if the point is in between the start- and the end-point of this track.
     */
    public boolean containsPoint(Point point) {
        return MathUtility.isBetween(getStartPoint().getX(), getEndPoint().getX(), point.getX())
                && MathUtility.isBetween(getStartPoint().getY(), getEndPoint().getY(), point.getY());
    }

    /**
     * Finds all directions one could walk in from a point on this track.
     * @param point the point to check for available directions.
     * @return a set of directions that are available from the given point. Is empty if the point is not on the track.
     */
    public Set<Direction> getAvailableDirections(Point point) {
        return Arrays.stream(Direction.values())
                .filter(direction -> {
                    Point pointInDirection = point.add(direction.toPoint());
                    return containsPoint(point) && containsPoint(pointInDirection);
                })
                .collect(Collectors.toSet());
    }

    /**
     * Checks whether this track is connected to a given point. Being connected to a point does not mean that the given
     * point is on the active part of this track!
     * @see #isEndOfTrack(Point) for checking whether a point is the active part of this track.
     * @param point the point to check.
     * @return true if the point is equal to one of the connection points and false otherwise.
     */
    public boolean isConnectedTo(Point point) {
        return getConnectionPoints().contains(point);
    }

    /**
     * Checks whether a given point is at one of this track's endings.
     * @param point the point to check.
     * @return true if the given point is at one of this track's connection points and false otherwise.
     */
    public boolean isEndOfTrack(Point point) {
        return point.equals(getStartPoint()) || point.equals(getEndPoint());
    }
}
