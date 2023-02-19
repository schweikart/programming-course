package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;

/**
 * Handles the 'show train' command which prints the graphical representation of a train from the simulation to the
 * terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ShowTrainCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ShowTrainCommand(TrainSimulationCLI cli) {
        super(cli, "show train", 1);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        int trainId = ParsingUtility.parseInteger(args[0]);

        Train train = getCli().getSimulation().getFleet().getTrainById(trainId);
        if (train == null) {
            Terminal.printError(String.format("'%s' is not a registered train id.", trainId));
        } else {
            Terminal.printLine(train.getGraphicalRepresentation().toString());
        }
    }
}
