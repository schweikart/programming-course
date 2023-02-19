package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.MoveType;
import edu.kit.informatik.uxnvp.cardgame.model.item.Item;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handles the 'build?' command which prints a list of all currently buildable items to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListBuildableItemsCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListBuildableItemsCommand(CardGameCLI cli) {
        super(cli, "build?", Pattern.compile("build\\?"),
                new GameInitializedCheck());
    }

    @Override
    public void execute(String[] args) {
        if (!getCli().getCardGame().getCurrentPhase().isMoveAllowed(MoveType.BUILD)) {
            Terminal.printError("you can not check for buildable items right now.");
            return;
        }

        // find and sort buildable items
        List<Item> buildableItems = getCli().getCardGame().getItemStore().getItems().stream()
                .filter(item -> item.canBeBuilt(getCli().getCardGame().getInventory()))
                .sorted(Comparator.comparing(Item::getCodeName))
                .collect(Collectors.toList());

        // print list / EMPTY
        if (buildableItems.isEmpty()) {
            Terminal.printLine("EMPTY");
        } else {
            for (Item item : buildableItems) {
                Terminal.printLine(item.getCodeName());
            }
        }
    }
}
