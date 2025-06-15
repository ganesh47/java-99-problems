package org.nintynine.problems;

import java.util.stream.LongStream;

/**
 * A generic list class that provides functionality to rotate elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP19<T> extends MyListP18<T> {

  /**
   * Constructs a new MyListP19 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP19(T... elements) {
    super(elements);
  }

  /**
   * Rotates the list N places to the left. Negative values rotate to the right.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c, d] rotate 1 → [b, c, d, a]
   * [a, b, c, d] rotate -1 → [d, a, b, c]
   * [a, b, c, d] rotate 4 → [a, b, c, d]
   * [a, b, c, d] rotate 5 → [b, c, d, a]
   * </pre>
   *
   * @param n number of places to rotate (positive for left, negative for right)
   * @return a new MyListP19 containing the rotated elements
   */
  public MyListP19<T> rotate(int n) {
    if (length() <= 1) {
      return new MyListP19<>(items);
    }

    // Normalize n to be within list length
    n = Math.toIntExact(n % length());
    if (n < 0) {
      n += Math.toIntExact(length()); // Convert right rotation to the equivalent left rotation
    }

    if (n == 0) {
      return new MyListP19<>(items);
    }

    // Split the list at a rotation point
    Pair<MyListP17<T>, MyListP17<T>> parts = split(n);

    // Create an array for rotated elements
    @SuppressWarnings("unchecked")
    T[] rotated = (T[]) new Object[Math.toIntExact(length())];

    // Copy the second part
    LongStream.iterate(0, i -> i < parts.second().length(), i -> i + 1)
        .forEachOrdered(i -> rotated[Math.toIntExact(i)] = parts.second().elementAt(i + 1));

    // Copy the first part
    LongStream.iterate(0, i -> i < parts.first().length(), i -> i + 1)
        .forEachOrdered(
            i ->
                rotated[Math.toIntExact(parts.second().length() + i)] =
                    parts.first().elementAt(i + 1));

    return new MyListP19<>(rotated);
  }
}
