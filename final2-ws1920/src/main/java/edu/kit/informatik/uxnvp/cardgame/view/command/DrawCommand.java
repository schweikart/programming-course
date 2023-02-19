package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.MoveType;
import edu.kit.informatik.uxnvp.cardgame.controller.Phase;
import edu.kit.informatik.uxnvp.cardgame.model.card.Card;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;
import edu.kit.informatik.uxnvp.cardgame.view.check.MoveAllowedCheck;

import java.util.regex.Pattern;

/**
 * Handles the 'draw' command which draws a card from the stack.
 * @author Max Schweikart
 * @version 1.0
 */
public class DrawCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public DrawCommand(CardGameCLI cli) {
        super(cli, "draw", Pattern.compile("draw"),
                new GameInitializedCheck(),
                new MoveAllowedCheck(MoveType.DRAW));
    }

    @Override
    public void execute(String[] args) {
        if (getCli().getCardGame().isCardStackEmpty()) {
            Terminal.printError("the card stack is empty.");
            return;
        }

        Card card = getCli().getCardGame().draw();
        Terminal.printLine(card.getCodeName());

        if (getCli().getCardGame().getCurrentPhase() == Phase.END) {
            // drawing a card can only end a game as lost
            Terminal.printLine("lost");
        }
    }
}
