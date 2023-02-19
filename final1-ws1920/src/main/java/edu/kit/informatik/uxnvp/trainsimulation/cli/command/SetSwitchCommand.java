package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Switch;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;

/**
 * Handles the 'set switch &lt;trackId&gt; position &lt;point&gt;' command which changes a switch's position.
 * @author Max Schweikart
 * @version 1.0
 */
public class SetSwitchCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public SetSwitchCommand(TrainSimulationCLI cli) {
        super(cli, "set switch", 3);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        int trackId = ParsingUtility.parseInteger(args[0]);
        ParsingUtility.checkIsString("position", args[1]);
        Point point = ParsingUtility.parsePoint(args[2]);

        Track track = getCli().getSimulation().getTrackNetwork().getTrackById(trackId);
        if (track == null || !(track instanceof Switch)) {
            //Todo they say that instanceof is the root of all evil but it seems pretty alright to me...
            Terminal.printError("there is no switch registered with the given id.");
        } else {
            Switch switchTrack = (Switch) track;
            if (!point.equals(switchTrack.getEndPoint1()) && !point.equals(switchTrack.getEndPoint2())) {
                Terminal.printError(String.format("'%s' is not a possible endpoint for the specified switch.", point));
            } else {
                getCli().getSimulation().setSwitch(switchTrack, point);
                Terminal.printLine("OK");
            }
        }
    }
}
