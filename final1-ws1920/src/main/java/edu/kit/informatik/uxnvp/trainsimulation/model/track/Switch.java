package edu.kit.informatik.uxnvp.trainsimulation.model.track;

import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A switch is a track that can be toggled between to endpoints.
 * @author Max Schweikart
 * @version 1.0
 */
public class Switch extends Track {
    private final Point endPoint1;
    private final Point endPoint2;
    private Point currentEndPoint;

    /**
     * Constructs a new switch based on it's start- and endpoints.
     * @param startPoint the start-point of this switch.
     * @param endPoint1 the first end-point of this switch.
     * @param endPoint2 the second end-point of this switch.
     */
    public Switch(Point startPoint, Point endPoint1, Point endPoint2) {
        super(startPoint, null); // track position is undefined at the beginning
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
    }

    /**
     * Switches this track to an endpoint.
     * @param point the endpoint to switch to. Must be one of the endpoints of this switch.
     */
    public void switchTo(Point point) {
        if (!endPoint1.equals(point) && !endPoint2.equals(point)) {
            throw new IllegalArgumentException("point is not an endpoint of this switch.");
        }
        this.currentEndPoint = point;
    }

    @Override
    public Point getEndPoint() {
        return currentEndPoint;
    }

    /**
     * Returns the first endpoint of this switch.
     * @return the first endpoint of this switch.
     */
    public Point getEndPoint1() {
        return endPoint1;
    }

    /**
     * Returns the second endpoint of this switch.
     * @return the second endpoint of this switch.
     */
    public Point getEndPoint2() {
        return endPoint2;
    }

    @Override
    public Set<Point> getConnectionPoints() {
        return new HashSet<>(Arrays.asList(getStartPoint(), endPoint1, endPoint2));
    }

    @Override
    public String toString() {
        String stringWithoutLength = String.format("s %s %s -> %s,%s", getId(), getStartPoint(), endPoint1, endPoint2);

        if (currentEndPoint == null) {
            return stringWithoutLength;
        } else {
            return stringWithoutLength + " " + getLength();
        }
    }

    @Override
    public boolean containsPoint(Point point) {
        return currentEndPoint != null && super.containsPoint(point);
    }
}
