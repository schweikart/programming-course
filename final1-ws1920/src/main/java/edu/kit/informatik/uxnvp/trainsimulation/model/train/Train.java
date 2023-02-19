package edu.kit.informatik.uxnvp.trainsimulation.model.train;

import edu.kit.informatik.uxnvp.trainsimulation.model.AsciiImage;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.TrainLocation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A train is a composition of rolling stocks.
 * @author Max Schweikart
 * @version 1.0
 */
public class Train implements Comparable<Train>, Couplable {
    private final int id;
    private final List<RollingStock> rollingStocks;
    private TrainLocation currentLocation;

    /**
     * Constructs a new train without any rolling stocks.
     * @param id the unique identifier of the rolling stock.
     */
    public Train(int id) {
        this.id = id;
        rollingStocks = new LinkedList<>();
    }

    /**
     * Returns the unique identifier of this train.
     * @return the unique identifier of this train.
     */
    public int getId() {
        return id;
    }

    @Override
    public boolean hasFrontCoupling() {
        return rollingStocks.size() == 0 || rollingStocks.get(0).hasFrontCoupling();
    }

    @Override
    public boolean hasBackCoupling() {
        return rollingStocks.size() == 0 || rollingStocks.get(rollingStocks.size() - 1).hasBackCoupling();
    }

    /**
     * Appends a rolling stock to the back of this train.
     * @param rollingStock the coach to append.
     */
    public void addRollingStock(RollingStock rollingStock) {
        rollingStocks.add(rollingStock);
        rollingStock.setTrain(this);
    }

    /**
     * Removes all rolling stocks from this train (and un-assigns this train from them).
     */
    public void removeAllRollingStocks() {
        Iterator<RollingStock> iterator = rollingStocks.iterator();
        while (iterator.hasNext()) {
            iterator.next().setTrain(null);
            iterator.remove();
        }
    }

    /**
     * Concatenates the graphical representations of all rolling stocks.
     * @return the graphical representation of this train.
     */
    public AsciiImage getGraphicalRepresentation() {
        if (rollingStocks.isEmpty()) {
            return new AsciiImage();
        } else {
            AsciiImage concatenated = rollingStocks.get(0).getGraphicalRepresentation();
            for (int i = 1; i < rollingStocks.size(); i++) {
                AsciiImage imageToAppend = rollingStocks.get(i).getGraphicalRepresentation();
                concatenated = concatenated.concat(imageToAppend);
            }
            return concatenated;
        }
    }

    /**
     * Updates the location of this train.
     * @param location the new location of this train.
     */
    public void setLocation(TrainLocation location) {
        this.currentLocation = location;
    }

    /**
     * Returns the current location of this train.
     * @return the current location of this train. Is null if the train is not placed on a track at the moment.
     */
    public TrainLocation getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Calculates the length of this train.<br/>
     * Note: We are using long instead of int because the sum the lengths of two very long trains can exceed the integer
     * size limit. TODO update usages
     * @return the length of this train.
     */
    public long getLength() {
        long length = 0;
        for (RollingStock rollingStock : rollingStocks) {
            length += rollingStock.getLength();
        }
        return length;
    }

    /**
     * Checks whether this train is valid. As couplings are already checked before adding rolling stocks, this check
     * tests if either the fist or last rolling stock is motorized. todo add to put train
     * @return whether this train is valid.
     */
    public boolean isValid() {
        return rollingStocks.size() > 0
                && (getFirstRollingStock().isMotorized() || getLastRollingStock().isMotorized());
    }

    @Override
    public int compareTo(Train o) {
        return Integer.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getId());
        for (RollingStock rollingStock : rollingStocks) {
            builder.append(' ').append(rollingStock.getIdString());
        }
        return builder.toString();
    }

    /**
     * Returns the first rolling stock in this train.
     * @return the first rolling stock in this train or null if there is no rolling stock in this train.
     */
    public RollingStock getFirstRollingStock() {
        return rollingStocks.size() == 0 ? null : rollingStocks.get(0);
    }

    /**
     * Returns the last rolling stock in this train.
     * @return the last rolling stock in this train or null if there is no rolling stock in this train.
     */
    public RollingStock getLastRollingStock() {
        return rollingStocks.size() == 0 ? null : rollingStocks.get(rollingStocks.size() - 1);
    }
}
