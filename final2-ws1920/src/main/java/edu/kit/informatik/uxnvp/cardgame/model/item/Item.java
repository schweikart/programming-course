package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.controller.Inventory;

import java.util.Arrays;

/**
 * An item is an object that can be built using resources. Items can have various effects in the game.
 * @author Max Schweikart
 * @version 1.0
 */
public abstract class Item {
    private final String codeName;
    private final ItemRequirement[] itemRequirements;

    /**
     * Constructs an item with it's code name and building requirements.
     * @param codeName the unique code name of this item.
     * @param itemRequirements the requirements for building this item.
     */
    public Item(String codeName, ItemRequirement... itemRequirements) {
        this.codeName = codeName;
        this.itemRequirements = itemRequirements;
    }

    /**
     * Method that is executed when this item is built.
     * @param game the game that this item is built in.
     */
    public abstract void onBuild(CardGame game);

    /**
     * Checks whether this item can be built, considering all requirements.
     * @param inventory the inventory to check for requirements.
     * @return true if all requirements are fulfilled and false otherwise.
     */
    public boolean canBeBuilt(Inventory inventory) {
        for (ItemRequirement requirement : itemRequirements) {
            if (!requirement.isFulfilled(inventory, this)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the unique code name of this item.
     * @return the unique code name of this item.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Deducts all resources that are consumed when building this item.
     * @param inventory the inventory to deduct the resources from.
     */
    public void deductResources(Inventory inventory) {
        for (ItemRequirement requirement : itemRequirements) {
            requirement.deductResources(inventory);
        }
    }

    @Override
    public int hashCode() {
        int hash = codeName.hashCode();
        for (ItemRequirement requirement : itemRequirements) {
            hash += requirement.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            Item that = (Item) obj;
            return this.getCodeName().equals(that.getCodeName())
                    && Arrays.equals(this.itemRequirements, that.itemRequirements);
        }
    }
}
