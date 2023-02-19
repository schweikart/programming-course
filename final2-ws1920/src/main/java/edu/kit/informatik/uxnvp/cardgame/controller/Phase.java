package edu.kit.informatik.uxnvp.cardgame.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The different phases of the game.
 * @author Max Schweikart
 * @version 1.0
 */
public enum Phase {
    /**
     * In the scavenge phase, the player can build items and draw cards.
     */
    SCAVENGE(MoveType.BUILD, MoveType.DRAW),
    /**
     * When the player encounters an animal and has to roll for winning the fight.
     */
    ENCOUNTER(MoveType.ROLL),
    /**
     * The player has built a rescue item and has to roll a die.
     */
    ENDEAVOR(MoveType.ROLL),
    /**
     * The game has ended.
     */
    END;

    private final Set<MoveType> allowedMoves;

    /**
     * Creates a phase with all of its attributes.
     * @param allowedMoves the types of moves that are allowed in the phase.
     */
    Phase(MoveType... allowedMoves) {
        this.allowedMoves = new HashSet<>(Arrays.asList(allowedMoves));
    }

    /**
     * Checks if a certain move type is allowed in this phase.
     * @param move the move to check.
     * @return true if the move type is allowed in this phase and false otherwise.
     */
    public boolean isMoveAllowed(MoveType move) {
        return allowedMoves.contains(move);
    }
}
