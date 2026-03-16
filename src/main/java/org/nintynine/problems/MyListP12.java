package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.List;

/**
 * A generic list class that provides functionality to decode run-length encoded lists.
 */
public class MyListP12<T> extends MyListP11<T> {

  /**
   * Constructs a new MyListP12 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP12(T... elements) {
    super(elements);
  }

  /**
   * Decodes a run-length encoded list.
   *
   * @param <U> output type
   * @return decoded list
   */
  @SuppressWarnings("unchecked")
  public <U> MyListP12<U> decode() {
    List<U> decoded = decodeItems();
    return new MyListP12<>(
        decoded.toArray(
            size ->
                (U[])
                    Array.newInstance(
                        decoded.isEmpty() ? Object.class : decoded.getFirst().getClass(), size)));
  }
}
