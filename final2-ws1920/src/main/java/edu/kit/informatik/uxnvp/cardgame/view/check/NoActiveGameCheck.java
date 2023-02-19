package edu.kit.informatik.uxnvp.cardgame.view.check;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;

/**
 * Checks whether there is no active game.
 * @author Max Schweikart
 * @version 1.0
 */
public class NoActiveGameCheck implements CircumstanceCheck {
    @Override
    public boolean checkCircumstance(CardGameCLI cli) {
        if (cli.getCardGame() != null && cli.getCardGame().isActive()) {
            Terminal.printError("the game is already active.");
            return false;
        } else {
            return true;
        }
    }
}
