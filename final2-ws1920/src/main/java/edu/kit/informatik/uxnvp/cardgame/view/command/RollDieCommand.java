package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.model.Die;
import edu.kit.informatik.uxnvp.cardgame.controller.MoveType;
import edu.kit.informatik.uxnvp.cardgame.controller.Phase;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.GameInitializedCheck;
import edu.kit.informatik.uxnvp.cardgame.view.check.MoveAllowedCheck;

import java.util.regex.Pattern;

/**
 * Handles the 'rollDx' command which rolls one of the dice.
 * @author Max Schweikart
 * @version 1.0
 */
public class RollDieCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public RollDieCommand(CardGameCLI cli) {
        super(cli, "rollD", Pattern.compile("rollD([\\-+]?\\d+) ([\\-+]?\\d+)"),
                new GameInitializedCheck(),
                new MoveAllowedCheck(MoveType.ROLL));
    }

    @Override
    public void execute(String[] args) {
        String dieString = args[0];
        String resultString = args[1];

        Integer dieInteger = parseInteger(dieString);
        if (dieInteger == null) return;

        Die die = Die.getByMaxValue(dieInteger);
        if (die == null) {
            Terminal.printError(String.format("'%s' is not a valid die.", dieString));
            return;
        }

        if (getCli().getCardGame().getCurrentDie() != die) {
            Terminal.printError("this is the wrong die.");
            return;
        }

        // parse input
        Integer result = parseInteger(resultString);
        if (result == null) return;

        // check if throw is valid
        if (result < Die.MIN_VALUE || result > die.getMaxValue()) {
            Terminal.printError(String.format("your throw must be between %s and %s.",
                    Die.MIN_VALUE, die.getMaxValue()));
            return;
        }

        // execute roll
        Phase scenario = getCli().getCardGame().getCurrentPhase();
        boolean success = getCli().getCardGame().roll(result);
        if (success) {
            if (scenario == Phase.ENCOUNTER) {
                // the player has succeeded in an animal fight
                Terminal.printLine("survived");

                // ...but he might run out of options (it's impossible to win a game by surviving an animal fight)
                if (getCli().getCardGame().getCurrentPhase() == Phase.END) {
                    Terminal.printLine("lost");
                }
            } else if (scenario == Phase.ENDEAVOR) {
                // the player has succeeded in a rescue endeavour and wins
                Terminal.printLine("win");
            }
        } else {
            Terminal.printLine("lose");
            if (getCli().getCardGame().getCurrentPhase() == Phase.END) {
                // unsuccessfully rolling a dice can not win a game -> the player must have lost
                Terminal.printLine("lost");
            }
        }
    }

    private Integer parseInteger(String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            Terminal.printError(String.format("'%s' is not a valid integer value.", argument));
            return null;
        }
    }
}
