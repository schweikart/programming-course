package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Engine;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.EngineType;

/**
 * Handles the 'create engine &lt;engineType&gt; &lt;class&gt; &lt;name&gt; &lt;length&gt; &lt;couplingFront&gt;
 * &lt;couplingBack&gt;' command which adds an engine to the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class CreateEngineCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public CreateEngineCommand(TrainSimulationCLI cli) {
        super(cli, "create engine", 6);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        EngineType type = ParsingUtility.parseEngineType(args[0]);
        String series = ParsingUtility.parseSeries(args[1]);
        String name = ParsingUtility.parseName(args[2]);
        int length = ParsingUtility.parseInteger(args[3]);
        boolean hasFrontCoupling = ParsingUtility.parseBoolean(args[4]);
        boolean hasBackCoupling = ParsingUtility.parseBoolean(args[5]);

        Engine engine = new Engine(type, series, name, length, hasFrontCoupling, hasBackCoupling);
        if (length <= 0) {
            Terminal.printError("the length of an engine must be positive.");
        } else if (!hasFrontCoupling && !hasBackCoupling) {
            Terminal.printError("engines must have at least one coupling.");
        } else if (getCli().getSimulation().getFleet().getRollingStockById(engine.getId()) != null) {
            Terminal.printError("there already is an engine with this id.");
        } else {
            getCli().getSimulation().getFleet().addEngine(engine);
            Terminal.printLine(engine.getId());
        }
    }
}
