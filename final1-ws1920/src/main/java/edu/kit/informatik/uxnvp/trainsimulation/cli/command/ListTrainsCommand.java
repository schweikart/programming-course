package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.List;
import java.util.Set;

/**
 * Handles the 'list trains' command which prints a list of all trains from the simulation to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListTrainsCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListTrainsCommand(TrainSimulationCLI cli) {
        super(cli, "list trains", 0);
    }

    @Override
    public void execute(String[] args) {
        Set<Train> trains = getCli().getSimulation().getFleet().getAllTrains();
        List<Train> sortedTrains = CollectionsUtility.toSortedList(trains);

        if (sortedTrains.isEmpty()) {
            Terminal.printLine("No train exists");
        } else {
            for (Train train : sortedTrains) {
                Terminal.printLine(train.toString());
            }
        }
    }
}
