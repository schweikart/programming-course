package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.model.ResourceType;
import edu.kit.informatik.uxnvp.cardgame.util.StackUtility;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Handles the 'list-resources' command which prints a list of all obtained resources to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListResourcesCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListResourcesCommand(CardGameCLI cli) {
        super(cli, "list-resources", Pattern.compile("list-resources"),
                new GameInitializedCheck());
    }

    @Override
    public void execute(String[] args) {
        Stack<ResourceType> resources = getCli().getCardGame().getInventory().getObtainedResources();
        if (resources.isEmpty()) {
            Terminal.printLine("EMPTY");
        } else {
            // we want to print the stack contents in reversed order
            Stack<ResourceType> reverse = new Stack<>();
            StackUtility.moveAll(resources, reverse);

            StringBuilder builder = new StringBuilder();
            builder.append(reverse.pop().getCodeName());
            while (!reverse.isEmpty()) {
                builder.append('\n').append(reverse.pop().getCodeName());
            }
            Terminal.printLine(builder.toString());
        }
    }
}
