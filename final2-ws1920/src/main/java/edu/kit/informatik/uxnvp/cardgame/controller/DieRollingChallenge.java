package edu.kit.informatik.uxnvp.cardgame.controller;

import edu.kit.informatik.uxnvp.cardgame.model.Die;

/**
 * A die rolling challenge occurs when the player has to decide the success of an action by rolling a die.
 * @see AnimalEncounterChallenge
 * @see EndeavourChallenge
 * @author Max Schweikart
 * @version 1.0
 */
public abstract class DieRollingChallenge {
    /**
     * The player must roll a value truly greater than the max. value of the die divided by this value.
     */
    private static final int ROLLING_WIN_THRESHOLD_DENOMINATOR = 2;

    private final CardGame game;
    private final Die die;
    private final int threshold;

    /**
     * Constructs a die rolling challenge in a game with an certain die.
     * @param game the game this challenge takes place in.
     * @param die the die to roll in this challenge.
     */
    public DieRollingChallenge(CardGame game, Die die) {
        this.game = game;
        this.die = die;
        this.threshold = die.getMaxValue() / ROLLING_WIN_THRESHOLD_DENOMINATOR;
    }

    /**
     * Returns the game this challenge has occurred in.
     * @return the game this challenge has occurred in.
     */
    public CardGame getGame() {
        return game;
    }

    /**
     * Returns the die that should be used in this challenge.
     * @return the die that should be used in this challenge.
     */
    public Die getDie() {
        return die;
    }

    /**
     * Rolls the die to decide whether the player has succeeded in this challenge.
     * @param rolledNumber the number that the player rolled.
     * @return true if the player succeeded and false otherwise.
     */
    public boolean roll(int rolledNumber) {
        if (rolledNumber > threshold) {
            onSucceed();
            return true;
        } else {
            onFail();
            return false;
        }
    }

    /**
     * Method that is executed when the player succeeds in this challenge.
     */
    public abstract void onSucceed();

    /**
     * Method that is executed when the player fails in this challenge.
     */
    public abstract void onFail();
}
