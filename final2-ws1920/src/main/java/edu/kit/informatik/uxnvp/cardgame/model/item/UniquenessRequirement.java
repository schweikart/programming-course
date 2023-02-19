package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.Inventory;

/**
 * This requirement makes sure that the same item can not be built twice.
 * @author Max Schweikart
 * @version 1.0
 */
public class UniquenessRequirement implements ItemRequirement {
    /**
     * Constructs a uniqueness requirement.
     */
    public UniquenessRequirement() {
    }

    @Override
    public boolean isFulfilled(Inventory inventory, Item item) {
        return !inventory.containsItem(item);
    }

    @Override
    public void deductResources(Inventory inventory) {
        // this requirement does not consume any resources
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }
}
