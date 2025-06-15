package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.stream.LongStream;

/**
 * A generic list class that provides functionality to drop every N'th element.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP16<T> extends MyListP15<T> {

  /**
   * Constructs a new MyListP16 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP16(T... elements) {
    super(elements);
  }

  /**
   * Drops every N'th element from the list.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c, d, e, f] drop 3 → [a, b, d, e]
   * [a, b, c] drop 1 → []
   * [a, b, c] drop 4 → [a, b, c]
   * [] drop 2 → []
   * [a] drop 2 → [a]
   * </pre>
   *
   * @param n the position of elements to drop (every n'th element)
   * @return a new MyListP16 with elements removed
   * @throws IllegalArgumentException if n is less than 1
   */
  public MyListP16<T> drop(int n) {
    if (n < 1) {
      throw new IllegalArgumentException("N must be greater than 0");
    }

    if (length() == 0) {
      return new MyListP16<>();
    }

    @SuppressWarnings("unchecked")
    T[] filtered =
        LongStream.range(0, length())
            .filter(i -> (i + 1) % n != 0)
            .mapToObj(i -> items[Math.toIntExact(i)])
            .toArray(size -> (T[]) Array.newInstance(items[0].getClass(), size));

    return new MyListP16<>(filtered);
  }
}
