package edu.kit.informatik.uxnvp.cardgame.view.check;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.MoveType;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;

/**
 * Checks whether a certain move is currently allowed and prints an error message if it is not.
 * @author Max Schweikart
 * @version 1.0
 */
public class MoveAllowedCheck implements CircumstanceCheck {
    private final MoveType move;

    /**
     * Constructs a check for a certain move.
     * @param move the move to check for.
     */
    public MoveAllowedCheck(MoveType move) {
        this.move = move;
    }

    @Override
    public boolean checkCircumstance(CardGameCLI cli) {
        if (cli.getCardGame().getCurrentPhase().isMoveAllowed(move)) {
            return true;
        } else {
            Terminal.printError("this move is not allowed right now.");
            return false;
        }
    }
}
