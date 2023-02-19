package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;

/**
 * The fireplace item allows building certain rescue items.
 * @author Max Schweikart
 * @version 1.0
 */
public class Fireplace extends Item {
    /**
     * Constructs the fireplace item.
     */
    public Fireplace() {
        super("fireplace",
                new UniquenessRequirement(),
                new ResourceRequirement(3, 1, 0));
    }

    @Override
    public void onBuild(CardGame game) {
        // building this item will not trigger any action immediately
    }
}
