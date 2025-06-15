package org.nintynine.problems;

import java.util.stream.IntStream;

/**
 * A generic list class that provides functionality to split a list into two parts.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP17<T> extends MyListP16<T> {

  /**
   * Constructs a new MyListP17 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP17(T... elements) {
    super(elements);
  }

  /**
   * Splits the list into two parts at the specified position.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c, d, e] split 3 → ([a, b, c], [d, e])
   * [a, b] split 0 → ([], [a, b])
   * [a, b] split 2 → ([a, b], [])
   * [] split 0 → ([], [])
   * </pre>
   *
   * @param position the position at which to split the list
   * @return a pair of MyListP17 instances containing the split parts
   * @throws IllegalArgumentException if position is negative or greater than list length
   */
  public Pair<MyListP17<T>, MyListP17<T>> split(int position) {
    if (position < 0 || position > length()) {
      throw new IllegalArgumentException("Position must be between 0 and list length (inclusive)");
    }

    // Create arrays for both parts
    @SuppressWarnings("unchecked")
    T[] firstPart = (T[]) new Object[position];
    @SuppressWarnings("unchecked")
    T[] secondPart = (T[]) new Object[Math.toIntExact(length() - position)];

    // Copy elements to first part
    System.arraycopy(items, 0, firstPart, 0, position);

    // Copy elements to second part
    IntStream.iterate(position, i -> i < length(), i -> i + 1)
        .forEachOrdered(i -> secondPart[i - position] = items[i]);

    return new Pair<>(new MyListP17<>(firstPart), new MyListP17<>(secondPart));
  }

  /**
   * A simple pair class to hold two values.
   *
   * @param <A> type of the first value
   * @param <B> type of the second value
   */
  public record Pair<A, B>(A first, B second) {

    @Override
    public String toString() {
      return "(" + first + ", " + second + ")";
    }
  }
}
