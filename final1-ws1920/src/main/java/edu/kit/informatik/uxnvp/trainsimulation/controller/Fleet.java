package edu.kit.informatik.uxnvp.trainsimulation.controller;

import edu.kit.informatik.uxnvp.trainsimulation.model.train.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The fleet contains and manages all data concerning trains and rolling stocks.
 * @author Max Schweikart
 * @version 1.0
 */
public class Fleet {
    private final Set<Engine> engines;
    private final Set<Coach> coaches;
    private final Set<TrainSet> trainSets;
    private final Set<Train> trains;

    /**
     * Constructs an empty fleet.
     */
    public Fleet() {
        this.engines = new HashSet<>();
        this.coaches = new HashSet<>();
        this.trainSets = new HashSet<>();
        this.trains = new HashSet<>();
    }

    /**
     * Adds an engine to this fleet.
     * @param engine the engine to add. Should have been checked for validity before.
     */
    public void addEngine(Engine engine) {
        engines.add(engine);
    }

    /**
     * Finds an engine based on an identifier.
     * @param id the identifier to find an engine with.
     * @return the engine with the given identifier or null if no such engine is registered in this fleet.
     */
    public Engine getEngineById(String id) {
        return engines.stream()
                .filter(engine -> engine.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    /**
     * Returns a read-only view on the set of all engines.
     * @return a read-only view on the set of all engines.
     */
    public Set<Engine> getAllEngines() {
        return Collections.unmodifiableSet(engines);
    }

    /**
     * Removes an engine from the fleet and does nothing if the engine is not contained.
     * @param engine the engine to remove.
     */
    public void removeEngine(Engine engine) {
        engines.remove(engine);
    }

    /**
     * Adds a coach to this fleet.
     * @param coach the coach to add.
     */
    public void addCoach(Coach coach) {
        coaches.add(coach);
    }

    /**
     * Finds the lowest available coach id.
     * @return the next available coach id.
     */
    public int getNextAvailableCoachId() {
        for (int i = 1; i <= coaches.size() + 1; i++) {
            if (getCoachById(i) == null) {
                return i;
            }
        }
        // this should never happen as n coaches can not occupy n + 1 ids
        throw new AssertionError();
    }

    /**
     * Finds a coach based on it's identifier.
     * @param id the identifier to find a coach with.
     * @return the coach with the given id or null if no such coach exists.
     */
    public Coach getCoachById(int id) {
        return coaches.stream()
                .filter(coach -> coach.getId() == id)
                .findAny()
                .orElse(null);
    }

    /**
     * Returns a read-only view on the set of all coaches.
     * @return a read-only view on the set of all coaches.
     */
    public Set<Coach> getAllCoaches() {
        return Collections.unmodifiableSet(coaches);
    }

    /**
     * Removes a coach from this fleet and does nothing if the given coach is not contained.
     * @param coach the coach to remove.
     */
    public void removeCoach(Coach coach) {
        coaches.remove(coach);
    }

    /**
     * Finds a train-set based on an identifier.
     * @param id the identifier to find a train-set with.
     * @return the train-set with the given identifier or null if no such train-set is registered in this fleet.
     */
    public TrainSet getTrainSetById(String id) {
        return trainSets.stream()
                .filter(trainSet -> trainSet.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    /**
     * Adds a train-set to this fleet.
     * @param trainSet the train-set to add.
     */
    public void addTrainSet(TrainSet trainSet) {
        trainSets.add(trainSet);
    }

    /**
     * Returns a read-only view on the set of all train-sets.
     * @return a read-only view on the set of all train-sets.
     */
    public Set<TrainSet> getAllTrainSets() {
        return Collections.unmodifiableSet(trainSets);
    }

    /**
     * Removes a train-set from this fleet and does nothing if the train-set is not contained.
     * @param trainSet the train-set to remove.
     */
    public void removeTrainSet(TrainSet trainSet) {
        trainSets.remove(trainSet);
    }

    /**
     * Finds a train with the given id or creates it if it does not exist and if the id is the next available one.
     * @param id the id of the train to get. If the train does not exist yet, this must be the next available one.
     * @return the found or created train or null if the train does not exist and the id is not the next available one.
     */
    public Train getOrCreateTrain(int id) {
        Train train = getTrainById(id);
        if (train == null && id == getNextAvailableTrainId()) {
            train = new Train(id);
            trains.add(train);
        }
        return train;
    }

    /**
     * Finds a train based on it's identifier.
     * @param id the identifier of the train to find.
     * @return the train with the given id or null
     */
    public Train getTrainById(int id) {
        return trains.stream()
                .filter(train -> train.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds the next available train id. The next available id is the lowest id that is not registered with a train.
     * @return the next available train id.
     */
    private int getNextAvailableTrainId() {
        for (int i = 1; i <= trains.size() + 1; i++) {
            if (getTrainById(i) == null) {
                return i;
            }
        }
        // this should never happen as n trains can not occupy n + 1 ids
        throw new AssertionError();
    }

    /**
     * Removes a train from this fleet and does nothing if the train is not contained.
     * @param train the train to remove.
     */
    public void removeTrain(Train train) {
        train.removeAllRollingStocks();
        trains.remove(train);
    }

    /**
     * Returns a read-only view on the set of all trains.
     * @return a read-only view on the set of all trains.
     */
    public Set<Train> getAllTrains() {
        return Collections.unmodifiableSet(trains);
    }

    /**
     * Returns a set of all trains that are currently on a rail.
     * @return a set of all trains that are currently on a rail.
     */
    public Set<Train> getTrainsOnRails() {
        return trains.stream()
                .filter(train -> train.getCurrentLocation() != null)
                .collect(Collectors.toSet());
    }

    /**
     * Finds a rolling stock based on it's string identifier.
     * @param rollingStockID the string identifier to find a rolling stock with.
     * @return the rolling stock with the given id or null of no registered rolling stock has that id.
     */
    public RollingStock getRollingStockById(String rollingStockID) {
        if (rollingStockID.startsWith("W")) {
            int coachId = Integer.parseInt(rollingStockID.substring(1));
            return getCoachById(coachId);
        } else {
            Engine engine = getEngineById(rollingStockID);
            if (engine != null) {
                return engine;
            } else {
                return getTrainSetById(rollingStockID);
            }
        }
    }
}
