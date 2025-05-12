package org.nintynine.problems;

/**
 * A generic list class that provides lotto number generation functionality.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP24<T> extends MyListP23<T> {

    /**
     * Constructs a new MyListP24 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP24(T... elements) {
        super(elements);
    }

    /**
     * Generates N different random numbers from the range 1…M.
     *
     * <p>Examples:
     * <pre>
     * lottoSelect(6, 49) → might return [23, 1, 17, 33, 21, 37]
     * lottoSelect(3, 10) → might return [7, 2, 9]
     * lottoSelect(1, 1) → [1]
     * </pre>
     *
     * @param n number of numbers to draw (N)
     * @param m maximum number in range (M)
     * @return a new MyListP24 containing N random numbers from 1…M
     * @throws IllegalArgumentException if n < 0 or m < 1 or n > m
     */
    public static MyListP24<Integer> lottoSelect(int n, int m) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of selections must be non-negative");
        }
        if (m < 1) {
            throw new IllegalArgumentException("Maximum number must be positive");
        }
        if (n > m) {
            throw new IllegalArgumentException(
                    "Cannot select " + n + " numbers from range_ of size " + m
            );
        }

        // Generate range_ 1…M using P22's range_ method
        MyListP22<Integer> range_ = range(1, m);

        // Convert to P23 to use random selection
        MyListP23<Integer> pool = new MyListP23<>(range_.stream().toList().toArray(new Integer[0]));

        // Select N random numbers using P23's rndSelect
        return new MyListP24<>(pool.rndSelect(n).stream().toList().toArray(new Integer[0]));

    }
}
