package org.nintynine.problems;

import java.util.Arrays;

/**
 * P04: Find the number of elements in a list.
 *
 * @param <T> element type
 */
public class MyListP04<T> extends MyListP03<T> {
  /**
   * Constructs a MyListP04 from the provided elements.
   *
   * @param elements the elements to include in the list
   */
  @SafeVarargs
  public MyListP04(T... elements) {
    super(elements);
  }

  /**
   * Returns the number of elements in the list.
   *
   * @return list length
   */
  public long length() {
    return Arrays.stream(items).count();
  }
}
