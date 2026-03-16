package org.nintynine.problems;

/**
 * A generic list class that provides functionality to replicate elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP15<T> extends MyListP14<T> {

  /**
   * Constructs a new MyListP15 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP15(T... elements) {
    super(elements);
  }

  /**
   * Replicates each element in the list a specified number of times.
   *
   * @param times the number of times to replicate each element
   * @return a new MyListP15 with replicated elements
   */
  public MyListP15<T> replicate(int times) {
    return new MyListP15<>(replicateItems(times));
  }
}
