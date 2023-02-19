package edu.kit.informatik.uxnvp.cardgame.model.card;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.model.CatastropheType;
import edu.kit.informatik.uxnvp.cardgame.model.item.Fireplace;

/**
 * A card that starts a catastrophe when drawn.
 * @author Max Schweikart
 * @version 1.0
 */
public class CatastropheCard implements Card {
    private final CatastropheType catastrophe;

    /**
     * Constructs a new catastrophe card with a given catastrophe type.
     * @param catastrophe the catastrophe type started when this card is drawn.
     */
    public CatastropheCard(CatastropheType catastrophe) {
        this.catastrophe = catastrophe;
    }

    @Override
    public void onDraw(CardGame game) {
        game.getInventory().loseResources();
        game.getInventory().removeItem(new Fireplace());
    }

    @Override
    public String getCodeName() {
        return catastrophe.getCodeName();
    }

    /**
     * Returns the catastrophe type started when this card is drawn.
     * @return the catastrophe type started when this card is drawn.
     */
    public CatastropheType getCatastrophe() {
        return catastrophe;
    }

    @Override
    public int hashCode() {
        return catastrophe.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            return ((CatastropheCard) obj).getCatastrophe() == getCatastrophe();
        }
    }
}
