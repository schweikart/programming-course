package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.model.item.Item;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Handles the 'list-buildings' command which prints a list of all built items to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListBuildingsCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListBuildingsCommand(CardGameCLI cli) {
        super(cli, "list-buildings", Pattern.compile("list-buildings"),
                new GameInitializedCheck());
    }

    @Override
    public void execute(String[] args) {
        Stack<Item> items = getCli().getCardGame().getInventory().getBuiltItems();
        if (items.isEmpty()) {
            Terminal.printLine("EMPTY");
        } else {
            while (!items.isEmpty()) {
                Terminal.printLine(items.pop().getCodeName());
            }
        }
    }
}
