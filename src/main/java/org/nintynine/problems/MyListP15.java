package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A generic list class that provides functionality to replicate elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP15<T> extends MyListP14<T> {

  /**
   * Constructs a new MyListP15 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP15(T... elements) {
    super(elements);
  }

  /**
   * Replicates each element in the list a specified number of times.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c] replicate 3 → [a, a, a, b, b, b, c, c, c]
   * [a, b] replicate 2 → [a, a, b, b]
   * [] replicate 5 → []
   * [a] replicate 1 → [a]
   * [a, b] replicate 0 → []
   * </pre>
   *
   * @param times the number of times to replicate each element
   * @return a new MyListP15 with replicated elements
   * @throws IllegalArgumentException if times is negative
   */
  public MyListP15<T> replicate(int times) {
    if (times < 0) {
      throw new IllegalArgumentException("Number of replications cannot be negative");
    }

    if (times == 0 || length() == 0) {
      return new MyListP15<>();
    }

    @SuppressWarnings("unchecked")
    T[] replicated =
        Arrays.stream(items)
            .flatMap(item -> Stream.generate(() -> item).limit(times))
            .toArray(size -> (T[]) Array.newInstance(items[0].getClass(), size));

    return new MyListP15<>(replicated);
  }
}
