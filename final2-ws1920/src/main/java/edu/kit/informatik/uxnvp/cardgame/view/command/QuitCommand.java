package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;

import java.util.regex.Pattern;

/**
 * Handles the 'quit' command which stops the cli prompt loop.
 * @author Max Schweikart
 * @version 1.0
 */
public class QuitCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public QuitCommand(CardGameCLI cli) {
        super(cli, "quit", Pattern.compile("quit"));
    }

    @Override
    public void execute(String[] args) {
        getCli().stopPromptLoop();
    }
}
