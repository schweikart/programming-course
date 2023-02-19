package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Direction;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.TrainLocation;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;

import java.util.Set;

/**
 * Handles the 'put train &lt;trainId&gt; at &lt;point&gt; in direction &lt;x&gt;,&lt;y&gt;' command which puts a train
 * on the tracks in the simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class PutTrainCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public PutTrainCommand(TrainSimulationCLI cli) {
        super(cli, "put train", 6);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        // parse and validate arguments
        int trainId = ParsingUtility.parseInteger(args[0]);
        ParsingUtility.checkIsString("at", args[1]);
        Point point = ParsingUtility.parsePoint(args[2]);
        ParsingUtility.checkIsString("in", args[3]);
        ParsingUtility.checkIsString("direction", args[4]);
        Direction direction = ParsingUtility.parseDirection(args[5]);

        // construct and retrieve relevant objects
        Train train = getCli().getSimulation().getFleet().getTrainById(trainId);
        Set<Track> tracksAtPoint = getCli().getSimulation().getTrackNetwork().getTracksAt(point);
        Direction normalizedDirection = getCli().getSimulation().getTrackNetwork()
                .getNormalizedDirection(point, direction);
        TrainLocation location = new TrainLocation(point, normalizedDirection);

        // check circumstances
        if (train == null) {
            Terminal.printError(String.format("'%s' is not a registered train id.", trainId));
        } else if (!train.isValid()) {
            Terminal.printError("the train is not valid yet.");
        } else if (train.getCurrentLocation() != null) {
            Terminal.printError("this train is already on the rails.");
        } else if (direction == null) {
            Terminal.printError(String.format("'%s' is not a valid direction.", args[5]));
        } else if (tracksAtPoint.size() == 0) {
            Terminal.printError("there is no track at this point.");
        } else if (tracksAtPoint.stream().noneMatch(track -> track.getAlignment() == direction.getAlignment())) {
            Terminal.printError("the train can not be placed in this direction at the given point.");
        } else if (!getCli().getSimulation().fitsOnTrackPut(train, location)) {
            Terminal.printError("the train does not fit on the track.");
        } else {
            train.setLocation(location);
            Terminal.printLine("OK");
        }
    }
}
