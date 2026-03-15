package org.nintynine.problems;

import java.util.stream.Stream;

/**
 * Interface for streamable collections.
 *
 * @param <T> the type of elements
 */
public interface Streamable<T> extends Iterable<T> {
  /**
   * Returns a sequential Stream with this collection as its source.
   *
   * @return a sequential Stream over the elements in this collection
   */
  Stream<T> stream();
}
