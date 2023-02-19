package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Coach;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.CoachType;

/**
 * Handles the 'create coach &lt;coachType&gt; &lt;length&gt; &lt;couplingFront&gt; &lt;couplingBack&gt;' command which
 * adds a coach to the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class CreateCoachCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public CreateCoachCommand(TrainSimulationCLI cli) {
        super(cli, "create coach", 4);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        CoachType type = ParsingUtility.parseCoachType(args[0]);
        int length = ParsingUtility.parseInteger(args[1]);
        boolean hasFrontCoupling = ParsingUtility.parseBoolean(args[2]);
        boolean hasBackCoupling = ParsingUtility.parseBoolean(args[3]);

        if (length <= 0) {
            Terminal.printError("the length of a coach must be positive.");
        } else if (!hasFrontCoupling && !hasBackCoupling) {
            Terminal.printError("coaches must have at least one coupling.");
        } else {
            int coachId = getCli().getSimulation().getFleet().getNextAvailableCoachId();
            Coach coach = new Coach(type, coachId, length, hasFrontCoupling, hasBackCoupling);
            getCli().getSimulation().getFleet().addCoach(coach);
            Terminal.printLine(coach.getId());
        }
    }
}
