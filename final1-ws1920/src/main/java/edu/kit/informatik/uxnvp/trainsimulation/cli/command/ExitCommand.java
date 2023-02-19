package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;

/**
 * Handles the 'exit' command which stops the cli prompt loop.
 * @author Max Schweikart
 * @version 1.0
 */
public class ExitCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ExitCommand(TrainSimulationCLI cli) {
        super(cli, "exit", 0);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        getCli().stopPromptLoop();
    }
}
