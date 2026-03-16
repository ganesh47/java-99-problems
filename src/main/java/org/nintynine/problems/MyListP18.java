package org.nintynine.problems;

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
   * @param startIndex the starting index (1-based)
   * @param endIndex the ending index (1-based)
   * @return a new MyListP18 containing the slice
   */
  public MyListP18<T> slice(int startIndex, int endIndex) {
    if (startIndex < 1 || endIndex > length() || startIndex > endIndex) {
      throw new IllegalArgumentException("Invalid slice range: " + startIndex + " to " + endIndex);
    }
    return new MyListP18<>(sliceItems(startIndex - 1, endIndex - startIndex + 1));
  }
}
