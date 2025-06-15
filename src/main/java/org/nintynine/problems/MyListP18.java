package org.nintynine.problems;

import static java.util.stream.IntStream.range;

/**
 * A generic list class that provides functionality to extract a slice.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP18<T> extends MyListP17<T> {

  /**
   * Constructs a new MyListP18 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP18(T... elements) {
    super(elements);
  }

  /**
   * Extracts a slice from the list between startIndex and endIndex (inclusive, 1-based indices).
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c, d, e] slice(2, 4) → [b, c, d]
   * [a, b, c] slice(1, 3) → [a, b, c]
   * [a, b, c, d] slice(2, 2) → [b]
   * </pre>
   *
   * @param startIndex the starting index (1-based)
   * @param endIndex the ending index (1-based)
   * @return a new MyListP18 containing the slice
   * @throws IllegalArgumentException if indices are invalid
   */
  public MyListP18<T> slice(int startIndex, int endIndex) {
    // Convert to 0-based indices for internal use
    int start = startIndex - 1;
    int end = endIndex - 1;

    // Validate indices
    if (startIndex < 1) {
      throw new IllegalArgumentException("Start index must be at least 1");
    }
    if (endIndex > length()) {
      throw new IllegalArgumentException("End index cannot exceed list length (" + length() + ")");
    }
    if (startIndex > endIndex) {
      throw new IllegalArgumentException("Start index cannot be greater than end index");
    }

    // Calculate slice length
    int sliceLength = end - start + 1;

    // Create array for the slice
    @SuppressWarnings("unchecked")
    T[] slicedItems = (T[]) new Object[sliceLength];

    // Copy elements to the slice
    range(0, sliceLength).forEach(i -> slicedItems[i] = items[start + i]);

    return new MyListP18<>(slicedItems);
  }
}
