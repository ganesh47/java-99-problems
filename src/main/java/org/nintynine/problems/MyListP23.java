package org.nintynine.problems;

import java.util.Random;

/**
 * A generic list class that provides functionality to randomly select elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP23<T> extends MyListP22<T> {

  private static final Random random = new Random();

  /**
   * Constructs a new MyListP23 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP23(T... elements) {
    super(elements);
  }

  /**
   * Randomly selects N elements from the list. If N is greater than list size, returns all elements
   * in random order.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c, d] rndSelect(2) → might return [c, a]
   * [a, b] rndSelect(3) → might return [b, a]
   * [a, b, c] rndSelect(0) → []
   * </pre>
   *
   * @param n number of elements to select
   * @return a new MyListP23 containing randomly selected elements
   * @throws IllegalArgumentException if n is negative
   */
  public MyListP23<T> rndSelect(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Number of elements to select must be non-negative");
    }

    // If n is larger than list size, return all elements in random order
    n = Math.toIntExact(Math.min(n, length()));

    // Create a working copy to avoid modifying an original list
    MyListP23<T> workingCopy = new MyListP23<>(items);

    @SuppressWarnings("unchecked")
    T[] selected = (T[]) new Object[n];

    // Select n elements
    for (int i = 0; i < n; i++) {
      // Get random position (1-based for removeAt)
      int position = random.nextInt(Math.toIntExact(workingCopy.length())) + 1;

      // Remove and store the selected element
      Pair<T, MyListP20<T>> result = workingCopy.removeAt(position);
      selected[i] = result.first();

      // Update working copy
      workingCopy = new MyListP23<>(result.second().items);
    }

    return new MyListP23<>(selected);
  }
}
