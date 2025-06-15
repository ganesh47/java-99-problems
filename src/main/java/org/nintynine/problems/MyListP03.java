package org.nintynine.problems;

import java.util.Arrays;

/**
 * Provides a method to select the Kth element of a list.
 *
 * @param <T> element type
 */
public class MyListP03<T> extends MyListP02<T> {
  @SafeVarargs
  public MyListP03(T... elements) {
    super(elements);
  }

  /**
   * Returns the element at the given 1-based position.
   *
   * @param k position (1-based)
   * @return element at that position
   */
  public T elementAt(long k) {
    if (k < 1) {
      throw new IllegalArgumentException("Position must be greater than 0");
    }
    return Arrays.stream(items)
        .skip(k - 1)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Position " + k + " is out of bounds"));
  }
}
