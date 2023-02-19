package edu.kit.informatik.uxnvp.trainsimulation.cli;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.AddSwitchCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.AddTrackCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.AddTrainCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.CreateCoachCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.CreateEngineCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.CreateTrainSetCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.DeleteRollingStockCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.DeleteTrackCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.DeleteTrainCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ExitCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ListCoachesCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ListEnginesCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ListTracksCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ListTrainSetsCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ListTrainsCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.PutTrainCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.SetSwitchCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.ShowTrainCommand;
import edu.kit.informatik.uxnvp.trainsimulation.cli.command.StepCommand;
import edu.kit.informatik.uxnvp.trainsimulation.controller.TrainSimulation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A command-line-interface for interacting with the train simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class TrainSimulationCLI {
    private final Set<Command> commands;
    private final TrainSimulation simulation;

    private boolean promptLoopRunning = false;

    /**
     * Constructs a new CLI for a train simulation.
     */
    public TrainSimulationCLI() {
        commands = new HashSet<>(Arrays.asList(
                new AddTrackCommand(this),
                new AddSwitchCommand(this),
                new DeleteTrackCommand(this),
                new ListTracksCommand(this),
                new SetSwitchCommand(this),
                new ExitCommand(this),
                new CreateEngineCommand(this),
                new ListEnginesCommand(this),
                new CreateCoachCommand(this),
                new ListCoachesCommand(this),
                new CreateTrainSetCommand(this),
                new ListTrainSetsCommand(this),
                new DeleteRollingStockCommand(this),
                new AddTrainCommand(this),
                new DeleteTrainCommand(this),
                new ListTrainsCommand(this),
                new ShowTrainCommand(this),
                new PutTrainCommand(this),
                new StepCommand(this)
        ));
        simulation = new TrainSimulation();
    }

    /**
     * Starts the prompt loop if it is not already running.
     */
    public void startPromptLoop() {
        if (!promptLoopRunning) {
            promptLoopRunning = true;
            while (promptLoopRunning) {
                String input = Terminal.readLine();
                handleInput(input);
            }
        }
    }

    /**
     * Stops the prompt loop after the current iteration.
     */
    public void stopPromptLoop() {
        promptLoopRunning = false;
    }

    /**
     * Finds a command based on the prefix of the given input.
     * @param input the input to find the correct command for. Must be not null.
     * @return the command that fits to the input or null if no command fits to the input.
     */
    private Command findCommand(String input) {
        return commands.stream()
                .filter(command -> input.startsWith(command.getPrefix()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Tries to parse and execute a command from the given input.
     * @param input the command string. Should contain a command.
     */
    private void handleInput(String input) {
        if (input == null) {
            Terminal.printError("please enter a command.");
            return;
        }

        Command command = findCommand(input);
        if (command == null) {
            Terminal.printError("can not find a matching command.");
            return;
        }

        try {
            command.invoke(input);
        } catch (SyntaxException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Returns the simulation this CLI is working with.
     * @return the simulation this CLI is working with.
     */
    public TrainSimulation getSimulation() {
        return simulation;
    }
}
