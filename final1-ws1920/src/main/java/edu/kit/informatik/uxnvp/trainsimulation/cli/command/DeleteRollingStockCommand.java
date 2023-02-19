package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.controller.Fleet;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Coach;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Engine;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.TrainSet;

/**
 * Handles the 'delete rolling stock &lt;id&gt;' command which removes a rolling stock from the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class DeleteRollingStockCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public DeleteRollingStockCommand(TrainSimulationCLI cli) {
        super(cli, "delete rolling stock", 1);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        String idString = ParsingUtility.parseRollingStockId(args[0]);
        if (idString.startsWith("W")) {
            int coachId = ParsingUtility.parseInteger(idString.substring(1));
            Coach coach = getFleet().getCoachById(coachId);

            if (coach == null) {
                Terminal.printError(String.format("'%s' is not a registered coach id.", coachId));
            } else if (coach.getTrain() != null) {
                Terminal.printError("this coach is currently used in a train.");
            } else {
                getFleet().removeCoach(coach);
                Terminal.printLine("OK");
            }
        } else {
            Engine engine = getFleet().getEngineById(idString);
            TrainSet trainSet = getFleet().getTrainSetById(idString);

            if (engine != null) {
                if (engine.getTrain() != null) {
                    Terminal.printError("this engine is currently used in a train.");
                } else {
                    getFleet().removeEngine(engine);
                    Terminal.printLine("OK");
                }
            } else if (trainSet != null) {
                if (trainSet.getTrain() != null) {
                    Terminal.printError("this train-set is currently used in a train.");
                } else {
                    getFleet().removeTrainSet(trainSet);
                    Terminal.printLine("OK");
                }
            } else {
                Terminal.printError(String.format("'%s' is not a registered rolling stock id.", idString));
            }
        }
    }

    /**
     * Helper method for accessing the fleet of the simulation.
     * @return the fleet of the simulation.
     */
    private Fleet getFleet() {
        return getCli().getSimulation().getFleet();
    }
}
