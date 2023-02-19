package edu.kit.informatik.uxnvp.cardgame.model;

/**
 * Different types of dice.
 * @author Max Schweikart
 * @version 1.0
 */
public enum Die {
    /**
     * A die with four sides (tetrahedron die).
     */
    FOUR(4),
    /**
     * A die with four sides (cube die).
     */
    SIX(6),
    /**
     * A die with eight sides (octahedron die).
     */
    EIGHT(8);

    /**
     * The minimal value that can be rolled with a die.
     */
    public static final int MIN_VALUE = 1;

    /**
     * The maximal value that can be rolled with this die.
     */
    private final int maxValue;

    /**
     * Constructs a new die with all of its attributes.
     * @param maxValue the maximal value that can be rolled with this die.
     */
    Die(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Finds a die based on its maximal value.
     * @param maxValue the maximal value of the die to find.
     * @return the die with the given max value or null of no die has that maximal value.
     */
    public static Die getByMaxValue(int maxValue) {
        for (Die die : values()) {
            if (die.getMaxValue() == maxValue) {
                return die;
            }
        }
        return null;
    }

    /**
     * Returns the maximal value that can be rolled with this die.
     * @return the maximal value that can be rolled with this die.
     */
    public int getMaxValue() {
        return maxValue;
    }
}
