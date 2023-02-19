package edu.kit.informatik.uxnvp.trainsimulation.cli.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.RollingStock;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.TrainSet;
import edu.kit.informatik.uxnvp.trainsimulation.util.ParsingUtility;
import edu.kit.informatik.uxnvp.trainsimulation.cli.Command;
import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;
import edu.kit.informatik.uxnvp.trainsimulation.controller.Fleet;

/**
 * Handles the 'add train &lt;trainId&gt; &lt;rollingStockId&gt;' command which adds a rolling stock to a train in the
 * simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public class AddTrainCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public AddTrainCommand(TrainSimulationCLI cli) {
        super(cli, "add train", 2);
    }

    @Override
    public void execute(String[] args) throws SyntaxException {
        int trainId = ParsingUtility.parseInteger(args[0]);
        String rollingStockID = ParsingUtility.parseRollingStockId(args[1]);

        RollingStock rollingStock = getFleet().getRollingStockById(rollingStockID);
        if (rollingStock == null) {
            Terminal.printError(String.format("'%s' is not a registered rolling stock id.", rollingStockID));
        } else if (rollingStock.getTrain() != null) {
            Terminal.printError("this rolling stock is already used in a train.");
        } else {
            Train train = getFleet().getTrainById(trainId);
            if (train == null) {
                train = getFleet().getOrCreateTrain(trainId);
                if (train == null) {
                    Terminal.printError(String.format("'%s' is not a registered a train id and a train with that id "
                            + "could not be created because there are lower available train ids.", trainId));
                } else {
                    // we do not need to check couplings if there are no other rolling stocks
                    train.addRollingStock(rollingStock);
                    Terminal.printLine(String.format("%s %s added to train %s",
                            rollingStock.getTypeString(), rollingStockID, trainId));
                }
            } else if (train.getCurrentLocation() != null) {
                Terminal.printError("this train is already on the rails.");
            } else if (!rollingStock.hasFrontCoupling()) {
                Terminal.printError("this rolling stock can not be coupled in the front.");
            } else if (!train.hasBackCoupling()) {
                // new trains always "have a back coupling" so we can not mistakenly create a train.
                Terminal.printError("this train does not have a back coupling.");
            } else if (!checkTrainSetCouplings(rollingStock, train.getLastRollingStock())) {
                Terminal.printError("train-sets can only be coupled with train-sets of the same class.");
            } else {
                train.addRollingStock(rollingStock);
                Terminal.printLine(String.format("%s %s added to train %s",
                        rollingStock.getTypeString(), rollingStockID, trainId));
            }
        }
    }

    //todo
    private boolean checkTrainSetCouplings(RollingStock stockOne, RollingStock stockTwo) {
        // Note about instanceof usage: Using instanceof in this case is justified because we need to check whether
        // certain rolling stocks are of the TrainSet type. Train-sets are a special case and trying to
        // implement this in a polymorphic model (just to avoid using instanceof) would result in a another way to
        // check the type of a rolling stock, i.e. by having a isTrainSet() or getRollingStockType() method which both
        // are essentially just other approaches to an instanceof check
        if ((stockOne instanceof TrainSet && !(stockTwo instanceof TrainSet))
                || (!(stockOne instanceof TrainSet) && stockTwo instanceof TrainSet)) {
            return false;
        } else if (stockOne instanceof TrainSet) { // stockTwo must be a train-set as well because of the case above
            return ((TrainSet) stockOne).getSeries().equals(((TrainSet) stockTwo).getSeries());
        } else { // neither of the rolling stocks is a train-set
            return true;
        }
    }

    /**
     * Helper method for accessing the fleet of the simulation.
     * @return the fleet of the simulation.
     */
    private Fleet getFleet() {
        return getCli().getSimulation().getFleet();
    }
}
