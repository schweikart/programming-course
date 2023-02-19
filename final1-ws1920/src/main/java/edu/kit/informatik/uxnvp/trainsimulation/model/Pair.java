package edu.kit.informatik.uxnvp.trainsimulation.model;

/**
 * A pair of elements of the same type.
 * @param <T> the element type in this pair.
 * @author Max Schweikart
 * @version 1.0
 */
public class Pair<T> {
    /**
     * The first element in this pair.
     */
    private final T firstElement;
    /**
     * The second element in this pair.
     */
    private final T secondElement;

    /**
     * Constructs a new pair of two elements.
     * @param firstElement the first element in this pair.
     * @param secondElement the second element in this pair.
     */
    public Pair(T firstElement, T secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    /**
     * Returns the first element in this pair.
     * @return the first element in this pair.
     */
    public T getFirstElement() {
        return firstElement;
    }

    /**
     * Returns the second element in this pair.
     * @return the second element in this pair.
     */
    public T getSecondElement() {
        return secondElement;
    }
}
