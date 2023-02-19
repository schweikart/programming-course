package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.MoveType;
import edu.kit.informatik.uxnvp.cardgame.controller.Phase;
import edu.kit.informatik.uxnvp.cardgame.model.item.Item;
import edu.kit.informatik.uxnvp.cardgame.model.item.ItemStore;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;
import edu.kit.informatik.uxnvp.cardgame.view.check.MoveAllowedCheck;

import java.util.regex.Pattern;

/**
 * Handles the 'build' command which builds an item.
 * @author Max Schweikart
 * @version 1.0
 */
public class BuildCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public BuildCommand(CardGameCLI cli) {
        super(cli, "build ", Pattern.compile("build (\\S+)"),
                new GameInitializedCheck(),
                new MoveAllowedCheck(MoveType.BUILD));
    }

    @Override
    public void execute(String[] args) {
        ItemStore store = getCli().getCardGame().getItemStore();
        String itemName = args[0];

        Item item = store.getItemByCodeName(itemName);
        if (item == null) {
            Terminal.printError(String.format("'%s' is not a valid item name.", itemName));
        } else if (!item.canBeBuilt(getCli().getCardGame().getInventory())) {
            Terminal.printError("you can not build this item right now.");
        } else {
            getCli().getCardGame().build(item);

            if (getCli().getCardGame().getCurrentPhase() == Phase.END) {
                if (getCli().getCardGame().hasWon()) {
                    // do not print OK in this case
                    Terminal.printLine("win");
                } else {
                    Terminal.printLine("OK");
                    Terminal.printLine("lost");
                }
            } else {
                // the game continues
                Terminal.printLine("OK");
            }
        }
    }
}
