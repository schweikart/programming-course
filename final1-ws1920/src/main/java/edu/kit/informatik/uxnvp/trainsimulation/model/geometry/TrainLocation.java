package edu.kit.informatik.uxnvp.trainsimulation.model.geometry;

/**
 * A train location is a point with a direction.
 * @author Max Schweikart
 * @version 1.0
 */
public class TrainLocation {
    private final Point point;
    private final Direction direction;

    /**
     * Constructs an new train location.
     * @param point the point of this location.
     * @param direction the direction of this location, which points away from the direction the train came from.
     */
    public TrainLocation(Point point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    /**
     * Returns the point of this location.
     * @return the point of this location.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * The direction of a train location points away from the direction the train came from.
     * @return the direction of this train location.
     */
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            TrainLocation other = (TrainLocation) obj;
            return point.equals(other.getPoint()) && direction == other.getDirection();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return point.hashCode() + direction.hashCode();
    }
}
