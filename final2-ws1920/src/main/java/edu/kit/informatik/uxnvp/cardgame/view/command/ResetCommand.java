package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;

import java.util.regex.Pattern;

/**
 * Handles the 'reset' command which resets the current game.
 * @author Max Schweikart
 * @version 1.0
 */
public class ResetCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ResetCommand(CardGameCLI cli) {
        super(cli, "reset", Pattern.compile("reset"),
                new GameInitializedCheck());
    }

    @Override
    public void execute(String[] args) {
        getCli().getCardGame().reset();
        Terminal.printLine("OK");
    }
}
