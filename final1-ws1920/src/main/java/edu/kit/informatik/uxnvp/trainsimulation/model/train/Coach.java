package edu.kit.informatik.uxnvp.trainsimulation.model.train;

/**
 * A coach is a rolling stock used for storing passengers, freight and more.
 * @author Max Schweikart
 * @version 1.0
 */
public class Coach extends RollingStock implements Comparable<Coach> {
    private final CoachType type;
    private final int id;

    /**
     * Constructs a new coach with all of it's attributes.
     * @param type the type of this coach.
     * @param id the unique identifier of this coach.
     * @param length the length of this coach.
     * @param hasFrontCoupling whether this coach has a coupling in the front.
     * @param hasBackCoupling whether this coach has a coupling in the back.
     */
    public Coach(CoachType type, int id, int length, boolean hasFrontCoupling, boolean hasBackCoupling) {
        super(type.getGraphicalRepresentation(),
                String.format("W%s", id),
                String.format("%s coach", type.getCodeName()),
                length,
                false,
                hasFrontCoupling,
                hasBackCoupling);
        this.type = type;
        this.id = id;
    }

    /**
     * Returns the unique numeric identifier of this coach.
     * @return the unique numeric identifier of this coach.
     */
    public int getId() {
        return id;
    }

    /**
     * Compares this coach with another one, based on their id.
     * @param o the coach to compare this coach with.
     * @return 0 if both coaches are equal, a value greater than 0 if this coach's id is greater than the id of the
     * other coach and a value less than 0 if this coach's id is less than the id of the other coach.
     */
    @Override
    public int compareTo(Coach o) {
        return Integer.compare(getId(), o.getId());
    }

    /**
     * Returns the string representation of this coach.
     * @return the string representation of this coach.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s", id, getTrain() == null ? "none" : getTrain().getId(),
                type.getAbbreviatedCodeName(), getLength(), hasFrontCoupling(), hasBackCoupling());
    }
}
