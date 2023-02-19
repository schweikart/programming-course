package edu.kit.informatik.uxnvp.trainsimulation.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods for working with collections.
 * @author Max Schweikart
 * @version 1.0
 */
public final class CollectionsUtility {
    /**
     * Utility classes should never be instantiated.
     */
    private CollectionsUtility() {
        throw new AssertionError("utility classes should never be instantiated.");
    }

    /**
     * Creates a sorted {@link List} from a {@link Collection}.
     * @param source the collection whose elements should be used.
     * @param <T> the content type of the source and the list.
     * @return a sorted list that contains all elements from the source collection.
     */
    public static <T extends Comparable<T>> List<T> toSortedList(Collection<T> source) {
        List<T> list = new ArrayList<>(source);
        Collections.sort(list);
        return list;
    }

    /**
     * Gets the first element from a collection. "First" means that we will use the first element of the iterator of
     * the collection.
     * @param source the collection from which to get the first element.
     * @param <T> the content type of the the source collection.
     * @return the first element of the source collection or null if the source collection is empty.
     */
    public static <T> T getFirstOrNull(Collection<T> source) {
        return source.iterator().hasNext() ? source.iterator().next() : null;
    }

    /**
     * Gets the first element from a collection. "First" means that we will use the first element of the iterator of
     * the collection.
     * @param source the collection from which to get the first element.
     * @param <T> the content type of the source collection.
     * @return the first element of the source collection.
     * @throws java.util.NoSuchElementException if the source collection is empty.
     */
    public static <T> T getFirst(Collection<T> source) {
        return source.iterator().next();
    }
}
