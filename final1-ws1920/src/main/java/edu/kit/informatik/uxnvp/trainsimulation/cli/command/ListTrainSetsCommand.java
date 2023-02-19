package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.TrainSet;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.List;
import java.util.Set;

/**
 * Handles the 'list train-sets' command which prints a list of all train-sets in the simulation to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListTrainSetsCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListTrainSetsCommand(TrainSimulationCLI cli) {
        super(cli, "list train-sets", 0);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Set<TrainSet> trainSets = getCli().getSimulation().getFleet().getAllTrainSets();
        List<TrainSet> sortedTrainSets = CollectionsUtility.toSortedList(trainSets);

        if (sortedTrainSets.isEmpty()) {
            Terminal.printLine("No train-set exists");
        } else {
            for (TrainSet trainSet : sortedTrainSets) {
                Terminal.printLine(trainSet.toString());
            }
        }
    }
}
