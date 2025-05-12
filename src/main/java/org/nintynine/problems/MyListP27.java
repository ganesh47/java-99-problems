package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A generic list class that provides group generation functionality.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP27<T> extends MyListP26<T> {

    /**
     * Constructs a new MyListP27 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP27(T... elements) {
        super(elements);
    }

    /**
     * Generates all possible ways to group 9 people into groups of 2, 3, and 4.
     *
     * @return list of all possible groupings
     * @throws IllegalStateException if the list size is not 9
     */
    public List<List<MyListP27<T>>> group3() {
        if (length() != 9) {
            throw new IllegalStateException("List must contain exactly 9 elements");
        }
        return group(Arrays.asList(2, 3, 4));
    }

    /**
     * Generates all possible ways to group elements according to specified group sizes.
     *
     * @param groupSizes list of required group sizes
     * @return list of all possible groupings
     * @throws IllegalArgumentException if group sizes don't sum to list size
     */
    public List<List<MyListP27<T>>> group(List<Integer> groupSizes) {
        // Validate input
        int sum = groupSizes.stream().mapToInt(Integer::intValue).sum();
        if (sum != length()) {
            throw new IllegalArgumentException(
                    "Sum of group sizes must equal list size"
            );
        }

        List<List<MyListP27<T>>> result = new ArrayList<>();
        generateGroups(new ArrayList<>(Set.of(items)), groupSizes, new ArrayList<>(), result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void generateGroups(List<T> remaining, List<Integer> sizes,
                                List<MyListP27<T>> current,
                                List<List<MyListP27<T>>> result) {
        // If no more sizes to process, we've found a valid grouping
        if (sizes.isEmpty()) {
            if (remaining.isEmpty()) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        int currentSize = sizes.getFirst();
        List<Integer> remainingSizes = sizes.subList(1, sizes.size());

        // Generate combinations of the current size from remaining elements
        MyListP27<T> remainingList = new MyListP27<>(
                remaining.toArray((T[]) new Object[0])
        );
        List<MyListP26<T>> combinations = remainingList.combinations(currentSize);

        for (MyListP26<T> combination : combinations) {
            // Create a new remaining list excluding chosen elements
            List<T> newRemaining = new ArrayList<>(remaining);
            for (int i = 0; i < combination.length(); i++) {
                newRemaining.remove(combination.elementAt(1+i));
            }

            // Add the current combination to groups and recurse
            current.add(new MyListP27<>(combination.items) );
            generateGroups(newRemaining, remainingSizes, current, result);
            current.removeLast();
        }
    }

    /**
     * Calculates multinomial coefficient for given group sizes.
     *
     * @param n total number of elements
     * @param k array of group sizes
     * @return multinomial coefficient
     */
    public static long multinomialCoefficient(int n, int... k) {
        // Validate input
        int sum = Arrays.stream(k).sum();
        if (sum != n) {
            throw new IllegalArgumentException(
                    "Sum of group sizes must equal total size"
            );
        }

        long result = 1;
        int denominator = 1;

        // Calculate n!/(k1!*k2!*...km!)
        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        for (int size : k) {
            for (int i = 1; i <= size; i++) {
                denominator *= i;
            }
        }

        return result / denominator;
    }
}
