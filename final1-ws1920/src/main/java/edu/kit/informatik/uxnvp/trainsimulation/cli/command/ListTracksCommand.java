package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.*;

/**
 * Handles the 'list tracks' command which prints a list of all tracks in the simulation to the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public class ListTracksCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public ListTracksCommand(TrainSimulationCLI cli) {
        super(cli, "list tracks", 0);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Set<Track> trackSet = getCli().getSimulation().getTrackNetwork().getAllTracks();
        List<Track> sortedTracks = CollectionsUtility.toSortedList(trackSet);

        if (sortedTracks.isEmpty()) {
            Terminal.printLine("No track exists");
        } else {
            for (Track track : sortedTracks) {
                Terminal.printLine(track.toString());
            }
        }
    }
}
