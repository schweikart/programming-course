package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;

/**
 * Handles the 'delete track &lt;trackID&gt;' command which removes a track from the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class DeleteTrackCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public DeleteTrackCommand(TrainSimulationCLI cli) {
        super(cli, "delete track", 1);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        int trackId = ParsingUtility.parseInteger(args[0]);

        Track track = getCli().getSimulation().getTrackNetwork().getTrackById(trackId);

        if (track == null) {
            Terminal.printError(String.format("'%s' is not a registered track id.", trackId));

        } else if (getCli().getSimulation().isTrackOccupied(track)) {
            Terminal.printError("there is a train on the specified track.");

        } else {
            boolean success = getCli().getSimulation().getTrackNetwork().removeTrack(track);
            if (success) {
                Terminal.printLine("OK");
            } else {
                Terminal.printError("the deletion of this track would separate the track network.");
            }
        }
    }
}
