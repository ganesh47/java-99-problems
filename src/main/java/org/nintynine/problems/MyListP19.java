package org.nintynine.problems;

import java.lang.reflect.Array;

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
   * @param n number of places to rotate
   * @return a new MyListP19 containing the rotated elements
   */
  @SuppressWarnings("unchecked")
  public MyListP19<T> rotate(int n) {
    if (length() <= 1) {
      return new MyListP19<>(items);
    }

    int len = (int) length();
    int k = n % len;
    if (k < 0) {
      k += len;
    }

    if (k == 0) {
      return new MyListP19<>(items);
    }

    T[] rotated = (T[]) Array.newInstance(items.getClass().getComponentType(), len);
    System.arraycopy(items, k, rotated, 0, len - k);
    System.arraycopy(items, 0, rotated, len - k, k);

    return new MyListP19<>(rotated);
  }
}
