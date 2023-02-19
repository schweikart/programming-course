package edu.kit.informatik.uxnvp.trainsimulation.util;

/**
 * Mathematical utility methods.
 * @author Max Schweikart
 * @version 1.0
 */
public final class MathUtility {
    /**
     * Utility classes should never be instantiated.
     */
    private MathUtility() {
        throw new AssertionError("utility classes should never be instantiated.");
    }

    /**
     * Checks whether a value is in between (inclusive) bounds. The bounds must not be in a specific order.
     * @param a one bound.
     * @param b another bound.
     * @param valueToCheck the value to check.
     * @return whether the value is in between a and b.
     */
    public static boolean isBetween(int a, int b, int valueToCheck) {
        if (a <= b) {
            return a <= valueToCheck && valueToCheck <= b;
        } else {
            return isBetween(b, a, valueToCheck);
        }
    }

}
