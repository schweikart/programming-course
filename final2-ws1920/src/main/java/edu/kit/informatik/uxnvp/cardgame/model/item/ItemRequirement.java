package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.Inventory;

/**
 * A requirement for building an item that can check whether the requirement can be fulfilled and can deduct required
 * resources.
 * @author Max Schweikart
 * @version 1.0
 */
public interface ItemRequirement {
    /**
     * Checks whether this building requirement can be fulfilled by the given inventory.
     * @param inventory the inventory to check this requirement against.
     * @param item the item to check this requirement for
     * @return true if this requirement can be fulfilled by the given inventory and false otherwise.
     */
    boolean isFulfilled(Inventory inventory, Item item);

    /**
     * Deducts resources that will be consumed when building this item.
     * @param inventory the inventory to deduct the resources from.
     */
    void deductResources(Inventory inventory);
}
