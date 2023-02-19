package edu.kit.informatik.uxnvp.cardgame.view.check;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;

/**
 * Checks whether the game is already initialized and prints an error message if it is not.
 * @author Max Schweikart
 * @version 1.0
 */
public class GameInitializedCheck implements CircumstanceCheck {
    @Override
    public boolean checkCircumstance(CardGameCLI cli) {
        if (cli.getCardGame() == null) {
            Terminal.printError("the game has not started yet.");
            return false;
        } else {
            return true;
        }
    }
}
