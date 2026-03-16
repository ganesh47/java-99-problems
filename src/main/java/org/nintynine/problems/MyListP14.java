package org.nintynine.problems;

/**
 * A generic list class that provides functionality to duplicate elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP14<T> extends MyListP13<T> {

  /**
   * Constructs a new MyListP14 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP14(T... elements) {
    super(elements);
  }

  /**
   * Duplicates each element in the list.
   *
   * @return new list with duplicated elements
   */
  public MyListP14<T> duplicate() {
    return new MyListP14<>(replicateItems(2));
  }
}
