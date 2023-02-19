package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Coach;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.List;
import java.util.Set;

/**
 * Handles the 'list coaches' command which prints a list of all coaches in the simulation to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListCoachesCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListCoachesCommand(TrainSimulationCLI cli) {
        super(cli, "list coaches", 0);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Set<Coach> coaches = getCli().getSimulation().getFleet().getAllCoaches();
        List<Coach> sortedCoaches = CollectionsUtility.toSortedList(coaches);

        if (sortedCoaches.isEmpty()) {
            Terminal.printLine("No coach exists");
        } else {
            for (Coach coach : sortedCoaches) {
                Terminal.printLine(coach.toString());
            }
        }
    }
}
