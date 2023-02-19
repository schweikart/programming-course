package edu.kit.informatik.uxnvp.trainsimulation.model.train;

import edu.kit.informatik.uxnvp.trainsimulation.model.AsciiImage;

/**
 * A rolling stock is a wagon that can be used in a train.
 * @author Max Schweikart
 * @version 1.0
 */
public abstract class RollingStock implements Couplable {
    /**
     * A graphical representation of this rolling stock.
     */
    private final AsciiImage graphicalRepresentation;
    /**
     * The id string is a unique identifier among all rolling stocks (including sub-classes).
     */
    private final String idString;
    /**
     * The type string is a short description of the type of this rolling stock.
     */
    private final String typeString;
    /**
     * The length of this rolling stock.
     */
    private final int length;
    /**
     * Whether this rolling stock has a motor and can move a train.
     */
    private final boolean isMotorized;
    /**
     * Whether this rolling stock is couplable in the front.
     */
    private final boolean hasFrontCoupling;
    /**
     * Whether this rolling stock is couplable in the back.
     */
    private final boolean hasBackCoupling;
    /**
     * The train this rolling stock is used in.
     */
    private Train train = null;

    /**
     * Constructs a new rolling stock that is not used in a train yet.
     * @param graphicalRepresentation the graphical representation of this rolling stock
     * @param idString a unique identifier among all rolling stocks.
     * @param typeString a short description of the type of this rolling stock.
     * @param length the length of this rolling stock.
     * @param isMotorized whether this rolling stock has a motor and can move a train.
     * @param hasFrontCoupling whether this rolling stock is couplable in the front.
     * @param hasBackCoupling whether this rolling stock is couplable in the back.
     */
    public RollingStock(AsciiImage graphicalRepresentation, String idString, String typeString, int length,
                        boolean isMotorized, boolean hasFrontCoupling, boolean hasBackCoupling) {
        this.graphicalRepresentation = graphicalRepresentation;
        this.idString = idString;
        this.typeString = typeString;
        this.length = length;
        this.isMotorized = isMotorized;
        this.hasFrontCoupling = hasFrontCoupling;
        this.hasBackCoupling = hasBackCoupling;
    }

    /**
     * Returns the graphical representation of this rolling stock.
     * @return the graphical representation of this rolling stock.
     */
    public final AsciiImage getGraphicalRepresentation() {
        return graphicalRepresentation;
    }

    /**
     * The id string is unique among all rolling stocks and it's sub-classes.
     * @return the unique identifier of this rolling stock.
     */
    public final String getIdString() {
        return idString;
    }

    /**
     * The type string is a short description of the type of this rolling stock.
     * @return the type string of this rolling stock.
     */
    public final String getTypeString() {
        return typeString;
    }

    /**
     * Assigns a train to this rolling stock. Make sure to also assign this rolling stock to the train!
     * @param train the train to assign this rolling stock to.
     */
    public final void setTrain(Train train) {
        this.train = train;
    }

    /**
     * Returns the train this rolling stock is currently assigned to.
     * @return the train this rolling stock is currently assigned to or null if this rolling stock is not assigned to a
     *         train.
     */
    public final Train getTrain() {
        return train;
    }

    /**
     * Returns the length of this rolling stock.
     * @return the length of this rolling stock.
     */
    public final int getLength() {
        return length;
    }

    /**
     * Returns whether this rolling stock has a motor and can move a train.
     * @return whether this rolling stock has a motor and can move a train.
     */
    public final boolean isMotorized() {
        return isMotorized;
    }

    @Override
    public final boolean hasFrontCoupling() {
        return hasFrontCoupling;
    }

    @Override
    public boolean hasBackCoupling() {
        return hasBackCoupling;
    }
}
