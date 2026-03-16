package org.nintynine.problems;

import java.lang.reflect.Array;

/**
 * A generic list class that provides functionality to remove an element at a specified position.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP20<T> extends MyListP19<T> {

  /**
   * Constructs a new MyListP20 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP20(T... elements) {
    super(elements);
  }

  /**
   * Removes the element at the specified position (1-based indexing).
   *
   * @param position the position of the element to remove (1-based)
   * @return a pair containing the removed element and the new list
   */
  @SuppressWarnings("unchecked")
  public Pair<T, MyListP20<T>> removeAt(int position) {
    if (position < 1 || position > length()) {
      throw new IllegalArgumentException("Position must be between 1 and " + length());
    }

    T removedElement = items[position - 1];
    T[] newItems = (T[]) Array.newInstance(items.getClass().getComponentType(), (int) length() - 1);

    if (position > 1) {
      System.arraycopy(items, 0, newItems, 0, position - 1);
    }
    if (position < length()) {
      System.arraycopy(items, position, newItems, position - 1, (int) length() - position);
    }

    return new Pair<>(removedElement, new MyListP20<>(newItems));
  }
}
