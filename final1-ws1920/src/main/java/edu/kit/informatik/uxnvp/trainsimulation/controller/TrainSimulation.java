package edu.kit.informatik.uxnvp.trainsimulation.controller;

import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.TrainLocation;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Switch;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.Train;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simulation for model trains.
 * @author Max Schweikart
 * @version 1.0
 */
public class TrainSimulation {
    private final TrackNetwork trackNetwork;
    private final Fleet fleet;

    /**
     * Constructs a new empty train simulation.
     */
    public TrainSimulation() {
        this.trackNetwork = new TrackNetwork();
        this.fleet = new Fleet();
    }

    /**
     * Checks if a track is occupied by a {@link Train}.
     * @param track the track to check.
     * @return true of the track is occupied and false otherwise.
     */
    public boolean isTrackOccupied(Track track) {
        for (Train train : getFleet().getTrainsOnRails()) {
            Set<Point> occupiedPoints = findOccupiedPoints(train);
            for (Point point : occupiedPoints) {
                if (track.containsPoint(point) && !track.isEndOfTrack(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds all points occupied by a train.s
     * @param train the train to check for occupation.
     * @return a set of all points occupied by the train.
     */
    private Set<Point> findOccupiedPoints(Train train) {
        Set<Point> occupiedPoints = new HashSet<>();

        TrainLocation currentLocation = train.getCurrentLocation();
        occupiedPoints.add(currentLocation.getPoint());
        for (int i = 0; i < train.getLength(); i++) {
            currentLocation = trackNetwork.walkStepBackwards(currentLocation);
            occupiedPoints.add(currentLocation.getPoint());
        }
        return occupiedPoints;
    }

    /**
     * Runs a simulation step.
     * @param speed the speed to run the simulation with.
     * @return a set of all crashes that occurred while running the simulation. A crash is represented by a set of
     * trains involved in the crash.
     */
    public Set<Set<Train>> runStep(int speed) {
        Set<Train> trainsOnRails = getFleet().getAllTrains().stream()
                .filter(train -> train.getCurrentLocation() != null)
                .collect(Collectors.toSet());

        Set<Set<Train>> crashes = new HashSet<>();

        int stepAmount = Math.abs(speed);
        boolean backwards = speed < 0;

        for (int i = 0; i < stepAmount; i++) {
            Set<Train> derailedTrains = new HashSet<>();
            for (Train train : trainsOnRails) {
                TrainLocation nextLocation = backwards
                        ? getTrackNetwork().walkStepBackwards(train.getCurrentLocation())
                        : getTrackNetwork().walkStep(train.getCurrentLocation());

                if (nextLocation == null || !fitsOnTrack(train, nextLocation)) {
                    derailedTrains.add(train);
                    train.setLocation(null);
                } else {
                    train.setLocation(nextLocation);
                }
            }
            Set<Set<Train>> recentCrashes = findCrashes();
            for (Train train : derailedTrains) {
                findOrCreateSetWithTrain(recentCrashes, train);
            }
            crashes.addAll(recentCrashes);

            // de-rail crashed trains
            for (Set<Train> crash : recentCrashes) {
                for (Train train : crash) {
                    train.setLocation(null);
                    trainsOnRails.remove(train);
                }
            }
        }
        return crashes;
    }

    /**
     * todo rename
     * Checks whether a train with a would fit on the track at a specified location.
     * @param train the train to check.
     * @param location the location to check.
     * @return true if there is enough TODO rail for the train.
     */
    public boolean fitsOnTrack(Train train, TrainLocation location) {
        Set<Point> uniquePoints = new HashSet<>();
        TrainLocation currentLocation = location;
        for (int i = 0; i < train.getLength(); i++) {
            currentLocation = getTrackNetwork().walkStepBackwards(currentLocation);
            if (currentLocation == null) {
                return false;
            } else {
                uniquePoints.add(currentLocation.getPoint());
            }
        }

        // If the tracks combine to a circle, the train might crash into itself. If that's the case, different parts of
        // it are on the same point. Thus, there are less unique points occupied by this train.
        return uniquePoints.size() == train.getLength();
    }

    /**
     * todo rename
     * Checks whether a train with a would fit on the track at a specified location.
     * @param train the train to check.
     * @param location the location to check.
     * @return true if there is enough TODO rail for the train.
     */
    public boolean fitsOnTrackPut(Train train, TrainLocation location) {
        Set<Point> uniquePoints = new HashSet<>();
        Set<Point> alreadyOccupiedPoints = getFleet().getTrainsOnRails().stream()
                .map(this::findOccupiedPoints)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        TrainLocation currentLocation = location;
        for (int i = 0; i < train.getLength(); i++) {
            currentLocation = getTrackNetwork().walkStepBackwards(currentLocation);
            if (currentLocation == null) {
                return false;
            } else {
                uniquePoints.add(currentLocation.getPoint());

                // check for track occupations
                Set<Track> tracksAtCurrentPoint = getTrackNetwork().getTracksAt(currentLocation.getPoint());
                if (tracksAtCurrentPoint.stream().anyMatch(this::isTrackOccupied)) {
                    return false;
                }

                // check for point occupations
                if (alreadyOccupiedPoints.contains(currentLocation.getPoint())) {
                    return false;
                }
            }
        }

        // If the tracks combine to a circle, the train might crash into itself. If that's the case, different parts of
        // it are on the same point. Thus, there are less unique points occupied by this train.
        return uniquePoints.size() == train.getLength();
    }

    private Set<Set<Train>> findCrashes() {
        Set<Set<Train>> crashes = new HashSet<>();

        // check if there are any two trains that (a) are on the same track or (b) have a point in common
        Map<Track, Train> trackTrainMap = new HashMap<>();
        Map<Point, Train> pointTrainMap = new HashMap<>();

        for (Train train : getFleet().getTrainsOnRails()) {
            for (Point point : findOccupiedPoints(train)) {
                // point occupation
                if (pointTrainMap.containsKey(point) && pointTrainMap.get(point) != train) {
                    Set<Train> crash = findOrCreateSetWithTrain(crashes, pointTrainMap.get(point));
                    crash.add(train);
                } else {
                    pointTrainMap.put(point, train);
                }

                // track occupation
                Set<Track> tracksAtPoint = getTrackNetwork().getTracksAt(point);
                // If there are 0 tracks, we don't need to check. If there are 2 tracks this is an endpoint.
                // There can not be more than two tracks at a point. -> only check if there is exactly one track
                if (tracksAtPoint.size() == 1) {
                    Track track = CollectionsUtility.getFirst(tracksAtPoint);
                    if (!track.isEndOfTrack(point)) {
                        if (trackTrainMap.containsKey(track) && trackTrainMap.get(track) != train) {
                            Set<Train> crash = findOrCreateSetWithTrain(crashes, trackTrainMap.get(track));
                            crash.add(train);
                        } else {
                            trackTrainMap.put(track, train);
                        }
                    }
                }
            }
        }
        return crashes;
    }

    private Set<Train> findOrCreateSetWithTrain(Set<Set<Train>> crashes, Train train) {
        for (Set<Train> crash : crashes) {
            if (crash.contains(train)) {
                return crash;
            }
        }
        Set<Train> newSet =  new HashSet<>(Collections.singletonList(train));
        crashes.add(newSet);
        return newSet;
    }

    /**
     * Returns the track network of this simulation.
     * @return the track network of this simulation.
     */
    public TrackNetwork getTrackNetwork() {
        return trackNetwork;
    }

    /**
     * Returns the fleet of this simulation.
     * @return the fleet of this simulation.s
     */
    public Fleet getFleet() {
        return fleet;
    }

    /**
     * Changes a switch position and de-rails affected trains.
     * @param switchTrack the switch track to switch.
     * @param newEndPoint the new endpoint of the switch.
     */
    public void setSwitch(Switch switchTrack, Point newEndPoint) {
        for (Train train : getFleet().getTrainsOnRails()) {
            for (Point occupiedPoint : findOccupiedPoints(train)) {
                if (switchTrack.containsPoint(occupiedPoint)) {
                    // de-rail
                    train.setLocation(null);
                    // todo we could stop the point iteration here but break; is bad practise
                }
            }
        }
        switchTrack.switchTo(newEndPoint);
    }
}
