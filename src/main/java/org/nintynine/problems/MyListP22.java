package org.nintynine.problems;

/**
 * A generic list class that provides functionality to create lists with integer ranges.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP22<T> extends MyListP21<T> {

  /**
   * Constructs a new MyListP22 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP22(T... elements) {
    super(elements);
  }

  /**
   * Creates a list containing all integers within the given range (inclusive). If start > end,
   * creates list in decreasing order.
   *
   * <p>Examples:
   *
   * <pre>
   * range(4, 9) → [4, 5, 6, 7, 8, 9]
   * range(9, 4) → [9, 8, 7, 6, 5, 4]
   * range(3, 3) → [3]
   * </pre>
   *
   * @param start the starting value
   * @param end the ending value
   * @return a new MyListP22 containing the range of integers
   */
  public static MyListP22<Integer> range(int start, int end) {
    int size = Math.abs(end - start) + 1;
    Integer[] numbers = new Integer[size];

    if (start <= end) {
      // Ascending order
      for (int i = 0; i < size; i++) {
        numbers[i] = start + i;
      }
    } else {
      // Descending order
      for (int i = 0; i < size; i++) {
        numbers[i] = start - i;
      }
    }

    return new MyListP22<>(numbers);
  }
}
