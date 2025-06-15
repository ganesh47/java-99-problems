package org.nintynine.problems;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Utility list that provides a method to get the penultimate element.
 *
 * @param <T> element type
 */
public class MyListP02<T> extends MyList<T> {
  @SafeVarargs
  public MyListP02(T... elements) {
    super(elements);
  }

  /**
   * Returns the element just before the last one in the list.
   *
   * @return second-to-last element
   */
  public T lastButOne() {
    return Arrays.stream(items)
        .reduce(Pair.<T>empty(), Pair::shift, (ignore, b) -> b)
        .secondLastOrThrow();
  }

  private record Pair<T>(T first, T second) {
    static <T> Pair<T> empty() {
      return new Pair<>(null, null);
    }

    static <T> Pair<T> shift(Pair<T> prev, T next) {
      return new Pair<>(prev.second, next);
    }

    T secondLastOrThrow() {
      if (first == null) {
        throw new NoSuchElementException("List has fewer than two elements");
      }
      return first;
    }
  }
}
