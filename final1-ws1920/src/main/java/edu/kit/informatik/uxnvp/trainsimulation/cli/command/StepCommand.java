package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles the 'step &lt;speed&gt;' command which runs a simulation step.
 * @author Max Schweikart
 * @version 1.0
 */
public class StepCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public StepCommand(TrainSimulationCLI cli) {
        super(cli, "step", 1);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        int speed = ParsingUtility.parseInteger(args[0]);

        if (getCli().getSimulation().getTrackNetwork().getAllTracks().stream()
                .anyMatch(track -> track.getEndPoint() == null)) {
            Terminal.printError("all switches must be set before running the simulation.");
        } else {
            Set<Set<Train>> crashes = getCli().getSimulation().runStep(speed);
            Set<Train> trainsOnRails = getCli().getSimulation().getFleet().getTrainsOnRails();

            List<StepResult> stepResults = new ArrayList<>();
            stepResults.addAll(crashes.stream()
                    .map(CrashStepResult::new)
                    .collect(Collectors.toList()));
            stepResults.addAll(trainsOnRails.stream()
                    .map(TrainPositionStepResult::new)
                    .collect(Collectors.toList()));

            stepResults.sort(StepResult::compareTo);

            if (stepResults.isEmpty()) {
                Terminal.printLine("OK");
            } else {
                for (StepResult info : stepResults) {
                    Terminal.printLine(info.toString());
                }
            }
        }
    }

    /**
     * An interface that allows sorting and printing crashes and train positions in the same list.
     * @author Max Schweikart
     * @version 1.0
     */
    private interface StepResult extends Comparable<StepResult> {
        /**
         * Returns the lowest id involved in this info.
         * @return the lowest id involved in this info.
         */
        int getLowestId();

        @Override
        default int compareTo(StepResult o) {
            return Integer.compare(getLowestId(), o.getLowestId());
        }
    }

    /**
     * Crash implementation of the {@link StepResult} interface.
     * @author Max Schweikart
     * @version 1.0
     */
    private static final class CrashStepResult implements StepResult {
        private final List<Integer> involvedTrainIds;

        private CrashStepResult(Set<Train> involvedTrains) {
            this.involvedTrainIds = involvedTrains.stream()
                    .map(Train::getId)
                    .sorted(Integer::compareTo)
                    .collect(Collectors.toList());
        }

        @Override
        public int getLowestId() {
            return involvedTrainIds.get(0);
        }

        @Override
        public String toString() {
            return String.format("Crash of train %s", involvedTrainIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
    }

    /**
     * Train position implementation of the {@link StepResult} interface.
     * @author Max Schweikart
     * @version 1.0
     */
    private static class TrainPositionStepResult implements StepResult {
        private final Train train;

        public TrainPositionStepResult(Train t) {
            this.train = t;
        }

        @Override
        public int getLowestId() {
            return train.getId();
        }

        @Override
        public String toString() {
            return String.format("Train %s at %s", train.getId(), train.getCurrentLocation().getPoint());
        }
    }
}
