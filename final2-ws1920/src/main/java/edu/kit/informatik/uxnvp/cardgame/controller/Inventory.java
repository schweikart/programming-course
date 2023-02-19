package edu.kit.informatik.uxnvp.cardgame.controller;

import edu.kit.informatik.uxnvp.cardgame.model.item.Item;
import edu.kit.informatik.uxnvp.cardgame.model.ResourceType;
import edu.kit.informatik.uxnvp.cardgame.model.item.Shack;
import edu.kit.informatik.uxnvp.cardgame.util.StackUtility;

import java.util.Stack;

/**
 * The inventory contains obtained resources and built items.
 * @author Max Schweikart
 * @version 1.0
 */
public class Inventory {
    private final Stack<ResourceType> obtainedResources;
    private final Stack<Item> builtItems;

    private byte damageBonus;
    private byte protectedResourcesAmount;

    /**
     * Constructs a new inventory without any items or resources.
     */
    public Inventory() {
        obtainedResources = new Stack<>();
        builtItems = new Stack<>();
        damageBonus = 0;
        protectedResourcesAmount = 0;
    }

    /**
     * Removes (at max) the given amount of resources of a specific type from the inventory.
     * @param resource the resource type to remove.
     * @param amount the amount of resource cards to remove at max.
     */
    public void removeResources(ResourceType resource, int amount) {
        int removedCount = 0;
        Stack<ResourceType> temp = new Stack<>();

        while (!obtainedResources.isEmpty() && removedCount < amount) {
            ResourceType res = obtainedResources.pop();

            if (res == resource) {
                removedCount++;
            } else {
                temp.push(res);
            }
        }

        // put resources back from temp stack
        StackUtility.moveAll(temp, obtainedResources);
    }

    /**
     * Returns the current damage bonus value.
     * @return the current damage bonus value.
     */
    public int getDamageBonus() {
        return damageBonus;
    }

    /**
     * Increases the damage bonus value to the given value if it is higher than the current damage bonus value and does
     * nothing otherwise.
     * @param newDamageBonus the new damage bonus value.
     */
    public void increaseDamageBonusTo(byte newDamageBonus) {
        if (newDamageBonus > damageBonus) {
            this.damageBonus = newDamageBonus;
        }
    }

    /**
     * Increases the amount of protected resources. This amount determines how many resources will be saved in a
     * resource loss.
     * @param newProtectedResourcesAmount the new amount of protected resources. If this value is smaller than the
     *                                    current protected-resources amount, the value will not be changed.
     */
    public void increaseProtectedResourcesAmount(byte newProtectedResourcesAmount) {
        if (newProtectedResourcesAmount > protectedResourcesAmount) {
            this.protectedResourcesAmount = newProtectedResourcesAmount;
        }
    }

    /**
     * The stack of obtained resources contains resource cards that the player has drawn but not used yet. Newer
     * resources are at the top.
     * @return a copy of the obtained resources.
     */
    public Stack<ResourceType> getObtainedResources() {
        return StackUtility.clone(obtainedResources);
    }

    /**
     * Adds an item to the player's inventory.
     * @param item the item to add.
     */
    public void addItem(Item item) {
        builtItems.add(item);
    }

    /**
     * Returns the stack of built items with the newest item on top.
     * @return the stack of built items with the newest item on top.
     */
    public Stack<Item> getBuiltItems() {
        return StackUtility.clone(builtItems);
    }

    /**
     * Checks if the inventory of the player contains a given item.
     * @param item the item to check for.
     * @return true if the item is contained in the inventory.
     */
    public boolean containsItem(Item item) {
        return builtItems.contains(item);
    }

    /**
     * Adds a resource to the inventory.
     * @param type the type of resource to add.
     */
    public void addResource(ResourceType type) {
        obtainedResources.push(type);
    }

    /**
     * Removes all resources from the inventory. If the player owns a shack, the five newest resources are kept.
     */
    public void loseResources() {
        if (containsItem(new Shack())) {
            Stack<ResourceType> shackItems = new Stack<>();
            // save some items in the shack
            for (int i = 0; i < protectedResourcesAmount && !obtainedResources.isEmpty(); i++) {
                shackItems.push(obtainedResources.pop());
            }

            // remove the rest
            obtainedResources.clear();

            // restore shack items
            StackUtility.moveAll(shackItems, obtainedResources);
        } else {
            obtainedResources.clear();
        }
    }

    /**
     * Removes the given item from the inventory. Does nothing if the item was not in the inventory.
     * @param item the item to remove.
     */
    public void removeItem(Item item) {
        builtItems.remove(item);
    }
}
