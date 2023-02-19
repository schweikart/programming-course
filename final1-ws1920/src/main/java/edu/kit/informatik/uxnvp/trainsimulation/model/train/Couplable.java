package edu.kit.informatik.uxnvp.trainsimulation.model.train;

/**
 * Objects of this type can be coupled with other couplables.
 * @author Max Schweikart
 * @version 1.0
 */
public interface Couplable {
    /**
     * Returns whether this couplable has a coupling in the front.
     * @return whether this couplable has a coupling in the front.
     */
    boolean hasFrontCoupling();

    /**
     * Returns whether this couplable has a coupling in the back.
     * @return whether this couplable has a coupling in the back.
     */
    boolean hasBackCoupling();
}
