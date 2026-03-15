package org.nintynine.problems;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A custom list implementation for the 99 Problems.
 *
 * @param <T> the type of elements
 */
public class MyList<T> implements Streamable<T> {
  protected T[] items;

  /**
   * Constructs a MyList from the provided elements.
   *
   * @param elements the elements to include in the list
   */
  @SafeVarargs
  public MyList(T... elements) {
    this.items = Arrays.copyOf(elements, elements.length);
  }

  /**
   * Find the last box of a list.
   *
   * @return the last element
   */
  public T last() {
    return Arrays.stream(items)
        .reduce((first, second) -> second)
        .orElseThrow(() -> new IllegalStateException("Empty list has no last element"));
  }

  @Override
  public String toString() {
    return "(" + String.join(" ", Arrays.stream(items).map(Objects::toString).toList()) + ")";
  }

  @Override
  public Stream<T> stream() {
    return Arrays.stream(items);
  }

  @Override
  public Iterator<T> iterator() {
    return Arrays.stream(items).iterator();
  }
}
