package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Engine;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.List;
import java.util.Set;

/**
 * Handles the 'list engines' command which prints a list of all engines to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListEnginesCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListEnginesCommand(TrainSimulationCLI cli) {
        super(cli, "list engines", 0);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Set<Engine> engines = getCli().getSimulation().getFleet().getAllEngines();
        List<Engine> sortedEngines = CollectionsUtility.toSortedList(engines);

        if (sortedEngines.isEmpty()) {
            Terminal.printLine("No engine exists");
        } else {
            for (Engine engine : sortedEngines) {
                Terminal.printLine(engine.toString());
            }
        }

    }
}
