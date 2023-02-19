package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.controller.TrackNetwork;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Alignment;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;

/**
 * Handles the 'add track &lt;startPoint&gt; -&gtM; &lt;endPoint&gt;' command which adds a track to the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class AddTrackCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public AddTrackCommand(TrainSimulationCLI cli) {
        super(cli, "add track", 3);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Point startPoint = ParsingUtility.parsePoint(args[0]);
        ParsingUtility.checkIsArrow(args[1]);
        Point endPoint = ParsingUtility.parsePoint(args[2]);

        int numTracksSP = getTrackNetwork().getTracksWithEndpointAtPoint(startPoint).size();
        int numTracksEP = getTrackNetwork().getTracksWithEndpointAtPoint(endPoint).size();

        if (!Alignment.isAligned(startPoint, endPoint)) {
            Terminal.printError("track must be horizontal or vertical.");

        } else if (startPoint.equals(endPoint)) {
            Terminal.printError("start- and endpoint can not be equal.");

        } else if (numTracksEP > 1 || numTracksSP > 1) {
            Terminal.printError("start or end point is already occupied.");

        } else if (getTrackNetwork().getTrackAmount() != 0
                && numTracksEP == 0 && numTracksSP == 0) {
            // must be connected, except there is nothing to connect to
            Terminal.printError("track must be connected.");

        } else {
            Track track = new Track(startPoint, endPoint);
            getTrackNetwork().registerTrack(track);
            Terminal.printLine(track.getId());
        }
    }

    /**
     * Helper method for getting the track network of the simulation.
     * @return the track network of the simulation.
     */
    private TrackNetwork getTrackNetwork() {
        return getCli().getSimulation().getTrackNetwork();
    }
}
