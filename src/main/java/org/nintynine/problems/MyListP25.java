package org.nintynine.problems;

import java.lang.reflect.Array;

/**
 * A generic list class that provides random permutation functionality.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP25<T> extends MyListP24<T> {

    /**
     * Constructs a new MyListP25 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP25(T... elements) {
        super(elements);
    }

    /**
     * Generates a random permutation of the list elements.
     *
     * <p>Examples:
     * <pre>
     * [a, b, c] → might return [b, c, a]
     * [1, 2] → might return [2, 1]
     * [x] → [x]
     * [] → []
     * </pre>
     *
     * @return a new MyListP25 containing a random permutation of elements`
     */
    @SuppressWarnings("unchecked")
    public MyListP25<T> randomPermutation() {
        // Using P23's rndSelect to select all elements randomly
        return new MyListP25<>(rndSelect(Math.toIntExact(length())).stream().toArray(size -> (T[]) Array.newInstance(items.getClass().getComponentType(), size)));
    }
}
