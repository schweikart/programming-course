package edu.kit.informatik.uxnvp.cardgame.controller;

/**
 * Different types of moves that the player can do in the game.
 * @author Max Schweikart
 * @version 1.0
 */
public enum MoveType {
    /**
     * Building items or finding out which items can be built.
     */
    BUILD,
    /**
     * Drawing cards.
     */
    DRAW,
    /**
     * Rolling a die.
     */
    ROLL
}
