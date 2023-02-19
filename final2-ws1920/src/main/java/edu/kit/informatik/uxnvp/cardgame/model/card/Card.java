package edu.kit.informatik.uxnvp.cardgame.model.card;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;

/**
 * A drawable piece of paper that triggers an action in the game.
 * @author Max Schweikart
 * @version 1.0
 */
public interface Card {
    /**
     * Should be called once when a card of this type is drawn.
     * @param game the game this card is drawn in.
     */
    void onDraw(CardGame game);

    /**
     * Returns the unique code name of this card type.
     * @return the unique code name of this card type.
     */
    String getCodeName();
}
