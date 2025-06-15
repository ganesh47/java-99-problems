package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.stream.LongStream;

/**
 * Removes consecutive duplicates from the list.
 *
 * @param <T> element type
 */
public class MyListP08<T> extends MyListP07<T> {
  @SafeVarargs
  public MyListP08(T... elements) {
    super(elements);
  }

  /**
   * Compresses consecutive duplicate elements.
   *
   * @return a new list without consecutive duplicates
   */
  public MyListP08<T> compress() {
    if (length() == 0) {
      return new MyListP08<>();
    }

    @SuppressWarnings("unchecked")
    T[] compressed =
        LongStream.range(0, length())
            .filter(i -> i == 0 || !Objects.equals(elementAt(i + 1), elementAt(i)))
            .mapToObj(k -> elementAt(k + 1))
            .toArray(size -> (T[]) Array.newInstance(items.getClass().getComponentType(), size));

    return new MyListP08<>(compressed);
  }
}
