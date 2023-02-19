package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.Inventory;

/**
 * This requirement checks that the inventory already contains a fireplace.
 * @author Max Schweikart
 * @version 1.0
 */
public class FireplaceRequirement implements ItemRequirement {
    @Override
    public boolean isFulfilled(Inventory inventory, Item item) {
        return inventory.getBuiltItems().contains(new Fireplace());
    }

    @Override
    public void deductResources(Inventory inventory) {
        // the fireplace is not consumed when building an item
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            return true;
        }
    }
}
