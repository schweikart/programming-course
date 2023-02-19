package edu.kit.informatik.uxnvp.cardgame.model.card;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.model.ResourceType;

/**
 * A card that adds resources of a given type to the player's inventory when drawn.
 * @author Max Schweikart
 * @version 1.0
 */
public class ResourceCard implements Card {
    private final ResourceType resource;

    /**
     * Constructs a resource card.
     * @param resource the type of the resource to add to the player's inventory when this card is drawn.
     */
    public ResourceCard(ResourceType resource) {
        this.resource = resource;
    }

    @Override
    public void onDraw(CardGame game) {
        game.getInventory().addResource(resource);
    }

    @Override
    public String getCodeName() {
        return resource.getCodeName();
    }

    /**
     * Returns the type of resource added to the player's inventory when this card is drawn.
     * @return the type of resource added to the player's inventory when this card is drawn.
     */
    public ResourceType getResource() {
        return resource;
    }

    @Override
    public int hashCode() {
        return resource.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            return ((ResourceCard) obj).getResource() == getResource();
        }
    }
}
