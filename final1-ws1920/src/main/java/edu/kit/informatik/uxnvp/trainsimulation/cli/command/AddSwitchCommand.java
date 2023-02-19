package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.model.*;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Alignment;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Switch;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.controller.TrackNetwork;

/**
 * Handles the 'add switch &lt;startPoint&gt; -&gt; &lt;endPoint1&gt;,&lt;endPoint2&gt;' command which adds a switch
 * track to the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class AddSwitchCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public AddSwitchCommand(TrainSimulationCLI cli) {
        super(cli, "add switch", 3);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        Point startPoint = ParsingUtility.parsePoint(args[0]);
        ParsingUtility.checkIsArrow(args[1]);
        Pair<Point> endPoints = ParsingUtility.parsePointPair(args[2]);

        int numTracksSP = getTrackNetwork().getTracksWithEndpointAtPoint(startPoint).size();
        int numTracksEP1 = getTrackNetwork().getTracksWithEndpointAtPoint(endPoints.getFirstElement()).size();
        int numTracksEP2 = getTrackNetwork().getTracksWithEndpointAtPoint(endPoints.getSecondElement()).size();

        if (!Alignment.isAligned(startPoint, endPoints.getFirstElement())
                || !Alignment.isAligned(startPoint, endPoints.getSecondElement())) {
            Terminal.printError("switch is not aligned vertically or horizontally.");

        } else if (startPoint.equals(endPoints.getFirstElement()) || startPoint.equals(endPoints.getSecondElement())) {
            Terminal.printError("the endpoints can not be equal to the start-point.");

        } else if (numTracksSP > 1 || numTracksEP1 > 1 || numTracksEP2 > 1) {
            Terminal.printError("a start or end point is already occupied.");

        } else if (getTrackNetwork().getTrackAmount() != 0
                && numTracksSP == 0 && numTracksEP1 == 0 && numTracksEP2 == 0) {
            Terminal.printError("switch is not connected.");

        } else {
            Switch switchTrack = new Switch(startPoint, endPoints.getFirstElement(), endPoints.getSecondElement());
            getTrackNetwork().registerTrack(switchTrack);
            Terminal.printLine(switchTrack.getId());
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
