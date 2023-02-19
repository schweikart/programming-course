package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;

/**
 * The shack item protects a number of the latest resources.
 * @author Max Schweikart
 * @version 1.0
 */
public class Shack extends Item {
    /**
     * The amount of resources that can be stored in the shack.
     */
    private static final byte SHACK_RESOURCE_CAPACITY = 5;

    /**
     * Constructs the shack item.
     */
    public Shack() {
        super("shack",
                new UniquenessRequirement(),
                new ResourceRequirement(2, 1, 2));
    }

    @Override
    public void onBuild(CardGame game) {
        game.getInventory().increaseProtectedResourcesAmount(SHACK_RESOURCE_CAPACITY);
    }
}
