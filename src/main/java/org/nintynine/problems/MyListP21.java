package org.nintynine.problems;

/**
 * A generic list class that provides functionality to insert an element at a specified position.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP21<T> extends MyListP20<T> {

    /**
     * Constructs a new MyListP21 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP21(T... elements) {
        super(elements);
    }

    /**
     * Inserts an element at the specified position (1-based indexing).
     *
     * <p>Examples:
     * <pre>
     * [a, b, c] insertAt("x", 2) → [a, x, b, c]
     * [] insertAt("x", 1) → [x]
     * [a] insertAt("x", 1) → [x, a]
     * </pre>
     *
     * @param element  the element to insert
     * @param position the position where to insert (1-based)
     * @return a new MyListP21 with the element inserted
     * @throws IllegalArgumentException if the position is invalid
     */
    public MyListP21<T> insertAt(T element, int position) {
        if (position < 1 || position > length() + 1) {
            throw new IllegalArgumentException(
                    "Position must be between 1 and " + (length() + 1)
            );
        }

        // Convert to 0-based index
        int index = position - 1;

        // Create a new array with space for a new element
        @SuppressWarnings("unchecked")
        T[] newItems = (T[]) new Object[Math.toIntExact(length() + 1)];

        // Copy elements before the insertion point
        System.arraycopy(items, 0, newItems, 0, index);

        // Insert new element
        newItems[index] = element;

        // Copy elements after the insertion point
        for (int i = index; i < length(); i++) {
            newItems[i + 1] = items[i];
        }

        return new MyListP21<>(newItems);
    }
}
