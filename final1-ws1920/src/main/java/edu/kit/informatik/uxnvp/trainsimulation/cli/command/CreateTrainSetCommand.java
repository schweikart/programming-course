package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.TrainSet;

/**
 * Handles the 'create train-set &lt;class&gt; &lt;series&gt; &lt;length&gt; &lt;couplingFront&gt; &lt;couplingBack&gt;'
 * command which adds a new coach to the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class CreateTrainSetCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public CreateTrainSetCommand(TrainSimulationCLI cli) {
        super(cli, "create train-set", 5);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        String series = ParsingUtility.parseSeries(args[0]);
        String name = ParsingUtility.parseName(args[1]);
        int length = ParsingUtility.parseInteger(args[2]);
        boolean hasFrontCoupling = ParsingUtility.parseBoolean(args[3]);
        boolean hasBackCoupling = ParsingUtility.parseBoolean(args[4]);

        TrainSet trainSet = new TrainSet(series, name, length, hasFrontCoupling, hasBackCoupling);
        if (length <= 0) {
            Terminal.printError("the length of a coach must be positive.");
        } else if (!hasFrontCoupling && !hasBackCoupling) {
            Terminal.printError("train-set must have at least one coupling.");
        } else if (getCli().getSimulation().getFleet().getRollingStockById(trainSet.getId()) != null) {
            Terminal.printError("there already is a train-set with this id.");
        } else {
            getCli().getSimulation().getFleet().addTrainSet(trainSet);
            Terminal.printLine(trainSet.getId());
        }
    }
}
