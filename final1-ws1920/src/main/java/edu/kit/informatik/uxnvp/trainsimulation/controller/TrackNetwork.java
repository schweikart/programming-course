package edu.kit.informatik.uxnvp.trainsimulation.controller;

import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Direction;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.track.Track;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.TrainLocation;
import edu.kit.informatik.uxnvp.trainsimulation.util.CollectionsUtility;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A network of connected tracks.
 * @author Max Schweikart
 * @version 1.0
 */
public class TrackNetwork {
    private final Set<Track> tracks;

    /**
     * Constructs an empty track network.
     */
    public TrackNetwork() {
        tracks = new HashSet<>();
    }

    /**
     * Constructs a new track network with a given set of tracks.
     * @param tracks the tracks to start with.
     */
    private TrackNetwork(Set<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * Finds all tracks that have a start-/endpoint a given point.
     * @param point the point to find tracks at.
     * @return a set of all tracks with a start-/endpoint at the given point.
     */
    public Set<Track> getTracksWithEndpointAtPoint(Point point) {
        return tracks.stream()
                .filter(track -> track.isConnectedTo(point))
                .collect(Collectors.toSet());
    }

    /**
     * Finds all tracks that contain the given point.
     * @param point the point to check for.
     * @return a set of all tracks that contain the given point.
     */
    public Set<Track> getTracksAt(Point point) {
        return tracks.stream()
                .filter(track -> track.containsPoint(point))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the total amount of tracks registered in this network.
     * @return the total amount of tracks registered in this network.
     */
    public int getTrackAmount() {
        return tracks.size();
    }

    /**
     * Registers a track in this network.
     * The validity and fitting of the track must be checked before!
     * @param track the track to register.
     */
    public void registerTrack(Track track) {
        track.setId(getNextAvailableTrackId());
        tracks.add(track);
    }

    /**
     * Finds the lowest available track id.
     * @return the next available track id.
     */
    private int getNextAvailableTrackId() {
        for (int i = 1; i <= tracks.size() + 1; i++) {
            if (getTrackById(i) == null) {
                return i;
            }
        }
        // this should never happen as n tracks can not occupy n + 1 ids
        throw new AssertionError();
    }

    /**
     * Finds a track based on an identifier.
     * @param id the identifier of the track to find.
     * @return the track with the given identifier or null if no track in this network has the given id.
     */
    public Track getTrackById(int id) {
        return tracks.stream()
                .filter(track -> track.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes a track from this network after checking whether that would break the track network. Does nothing if the
     * track not contained.
     * @param track the track to remove.
     * @return true if the track could be removed and false if the track is not contained or if removing the track would
     * split the track network.
     */
    public boolean removeTrack(Track track) {
        if (!tracks.contains(track) || !canRemoveTrack(track)) {
            return false;
        } else {
            tracks.remove(track);
            return true;
        }
    }

    /**
     * Checks whether removing the given track would split the track network.
     * @param track the track to check for removal.
     * @return false if removing the given track would split the network and true otherwise.
     */
    private boolean canRemoveTrack(Track track) {
        Set<Track> remainingTracks = new HashSet<>(tracks);
        remainingTracks.remove(track);

        TrackNetwork testTrackNetwork = new TrackNetwork(remainingTracks);
        return testTrackNetwork.isInterconnected();
    }

    /**
     * Checks whether all tracks in this network are connected.
     * @return true if all tracks in this network are connected and false otherwise.
     */
    private boolean isInterconnected() {
        if (tracks.isEmpty()) {
            return true;
        } else {
            Track anyTrack = tracks.stream().findAny().get();
            Set<Track> reachableTracks = new HashSet<>();
            Set<Track> yetToWalk = new HashSet<>();
            yetToWalk.add(anyTrack);

            while (!yetToWalk.isEmpty()) {
                Track track = yetToWalk.stream().findAny().get();
                reachableTracks.add(track);
                yetToWalk.remove(track);

                Set<Point> connectionPoints = track.getConnectionPoints();
                connectionPoints.forEach(point -> {
                    Set<Track> otherTracksAtPoint = getTracksWithEndpointAtPoint(point);
                    otherTracksAtPoint.remove(track);

                    if (!otherTracksAtPoint.isEmpty()) {
                        Track otherTrackAtPoint = otherTracksAtPoint.stream().findAny().get();
                        if (!reachableTracks.contains(otherTrackAtPoint)) {
                            yetToWalk.add(otherTrackAtPoint);
                        }
                    }
                });
            }

            return reachableTracks.equals(tracks);
        }
    }

    /**
     * Returns a read-only set of all tracks in this network.
     * @return a set of all tracks in this network.
     */
    public Set<Track> getAllTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    /**
     * Finds the train location that a train would be at after walking one step on the rails from given location.
     * @param current the current location to walk from.
     * @return the location the train would be at after walking one step or null if walking one step leads off the
     * rails.
     */
    public TrainLocation walkStep(TrainLocation current) {
        Set<Track> tracksAtCurrentPosition = getTracksAt(current.getPoint());

        if (tracksAtCurrentPosition.size() == 0) {
            // there is no track at the current position so we can't take a step along the tracks
            return null;

        } else if (tracksAtCurrentPosition.size() == 1) {
            // the current point is not on a corner
            Track trackAtCurrentPosition = CollectionsUtility.getFirst(tracksAtCurrentPosition);

            if (trackAtCurrentPosition.isEndOfTrack(current.getPoint())) {
                // walking one step will de-rail the train.
                return null;
            } else {
                // we are in the middle of a track and can continue in the same direction for at least one step
                Point nextPoint = current.getPoint().add(current.getDirection().toPoint());
                return new TrainLocation(nextPoint, current.getDirection());
            }

        } else { // tracksAtCurrentPosition.size() == 2
            // the current point is a connection point

            // ignore the track that the train came from
            Point previousPoint = current.getPoint().subtract(current.getDirection().toPoint());
            tracksAtCurrentPosition.removeIf(track -> track.containsPoint(previousPoint));

            // this leaves the connected track
            Track nextTrack = CollectionsUtility.getFirst(tracksAtCurrentPosition);
            // the current point is an endpoint of the next track so
            Direction newDirection = CollectionsUtility.getFirst(nextTrack.getAvailableDirections(current.getPoint()));
            // the new point (after going one step in the new direction) must be on the the new track, assuming that it
            // is at least one unit long
            Point newPoint = current.getPoint().add(newDirection.toPoint());

            return new TrainLocation(newPoint, newDirection);
        }
    }

    /**
     * Finds a track that includes two points.
     * @param pointOne the first point that must be included.
     * @param pointTwo the second point that must be included.
     * @return the Track that contains both points or null if there is no Track in this network containing both points.
     */
    private Track getTrackBetween(Point pointOne, Point pointTwo) {
        return tracks.stream()
                .filter(track -> track.containsPoint(pointOne) && track.containsPoint(pointTwo))
                .findFirst() // there should be one at most
                .orElse(null);
    }

    /**
     * Finds the train location that a train would be at if it moved back one step from a given location.
     * @param current the current location to walk back from.
     * @return the location that would lead to the current location after walking one step or null if walking backwards
     * leads off the rails.
     */
    public TrainLocation walkStepBackwards(TrainLocation current) {
        Point previousPoint = current.getPoint().subtract(current.getDirection().toPoint());
        Track betweenCurrentAndPrevious = getTrackBetween(previousPoint, current.getPoint());
        if (betweenCurrentAndPrevious == null) {
            return null;
        } else if (!betweenCurrentAndPrevious.isEndOfTrack(previousPoint)) {
            // the previous point is in the middle of a track
            return new TrainLocation(previousPoint, current.getDirection());

        } else {
            // the previous point is at the end of a track
            Set<Track> tracksAtPrevPoint = getTracksAt(previousPoint);
            // ignore the track we are already on - we want to find the one connected to it!
            tracksAtPrevPoint.remove(betweenCurrentAndPrevious);

            Track connectedTrack = CollectionsUtility.getFirstOrNull(tracksAtPrevPoint);
            if (connectedTrack == null) {
                // the previous point is a dead end
                return new TrainLocation(previousPoint, current.getDirection());
            } else {
                // the previous point is not a dead end -> we need to calculate the direction
                Direction prevDirection = CollectionsUtility
                        .getFirst(connectedTrack.getAvailableDirections(previousPoint)).opposite();
                return new TrainLocation(previousPoint, prevDirection);
            }

        }
    }

    /**
     * In curves, multiple directions represent the same direction for the train. This method always returns the
     * direction that points away from the track that the train came from.
     * @param point the point to find a normalized direction at.
     * @param direction the direction to normalize.
     * @return the normalized direction. todo change params to train location
     */
    public Direction getNormalizedDirection(Point point, Direction direction) {
        Set<Track> tracksAtPoint = getTracksAt(point);
        if (tracksAtPoint.size() != 2) {
            // do not change anything if there is no track connection at the point
            return direction;
        } else {
            Iterator<Track> iterator = tracksAtPoint.iterator();
            Track trackA = iterator.next();
            Track trackB = iterator.next();

            Point nextPoint = point.add(direction.toPoint());
            if (trackA.getAlignment() == trackB.getAlignment()) {
                // no need to normalize a non-curve connection
                return direction;
            } else if (trackA.containsPoint(nextPoint)) {
                // direction points towards track A -> normalized points away from track B
                return CollectionsUtility.getFirst(trackB.getAvailableDirections(point)).opposite();
            } else if (trackB.containsPoint(nextPoint)) {
                // direction points towards track B -> normalized points away from track A
                return CollectionsUtility.getFirst(trackA.getAvailableDirections(point)).opposite();
            } else {
                // direction points away from one of the tracks -> is already normalized
                return direction;
            }
        }
    }
}
