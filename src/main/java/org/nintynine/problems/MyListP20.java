package org.nintynine.problems;

import java.util.stream.IntStream;

/**
 * A generic list class that provides functionality to remove an element at a specified position.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP20<T> extends MyListP19<T> {

    /**
     * Constructs a new MyListP20 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP20(T... elements) {
        super(elements);
    }

    /**
     * Removes the element at the specified position (1-based indexing).
     * Returns a pair containing the removed element and the resulting list.
     *
     * <p>Examples:
     * <pre>
     * [a, b, c, d] removeAt(2) → (b, [a, c, d])
     * [a] removeAt(1) → (a, [])
     * </pre>
     *
     * @param position the position of the element to remove (1-based)
     * @return a pair containing the removed element and the new list
     * @throws IllegalArgumentException if position is invalid
     */
    public Pair<T, MyListP20<T>> removeAt(int position) {
        if (position < 1 || position > length()) {
            throw new IllegalArgumentException(
                    "Position must be between 1 and " + length()
            );
        }

        // Convert to 0-based index
        int index = position - 1;

        // Store element to be removed
        T removedElement = items[index];

        // Create new array without the element
        @SuppressWarnings("unchecked")
        T[] newItems = (T[]) new Object[Math.toIntExact(length() - 1)];

        // Copy elements before the removal position
        IntStream.range(0, index).forEach(i -> newItems[i] = items[i]);

        // Copy elements after the removal position
        IntStream.iterate(index + 1, i -> i < length(), i -> i + 1).forEach(i -> newItems[i - 1] = items[i]);

        return new Pair<>(removedElement, new MyListP20<>(newItems));
    }
}
