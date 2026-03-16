package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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

  /**
   * Replicates each element in the list a specified number of times.
   *
   * @param times the number of times to replicate each element
   * @return a new array with replicated elements
   * @throws IllegalArgumentException if times is negative
   */
  @SuppressWarnings("unchecked")
  protected T[] replicateItems(int times) {
    if (times < 0) {
      throw new IllegalArgumentException("Number of replications cannot be negative");
    }
    if (times == 0 || items.length == 0) {
      return (T[]) Array.newInstance(items.getClass().getComponentType(), 0);
    }
    return stream()
        .flatMap(item -> Stream.generate(() -> item).limit(times))
        .toArray(size -> (T[]) Array.newInstance(items.getClass().getComponentType(), size));
  }

  /**
   * Helper to decode run-length encoded items.
   *
   * @return list of decoded items
   */
  @SuppressWarnings("unchecked")
  protected <U> List<U> decodeItems() {
    List<U> decoded = new ArrayList<>();
    for (T item : items) {
      if (item instanceof MyListP10.EncodedElement<?> encoded) {
        U element = (U) encoded.element;
        for (long i = 0; i < encoded.count; i++) {
          decoded.add(element);
        }
      } else {
        decoded.add((U) item);
      }
    }
    return decoded;
  }

  /**
   * Returns a new array containing a slice of the items.
   *
   * @param offset starting index
   * @param size number of elements
   * @return new array with sliced items
   */
  @SuppressWarnings("unchecked")
  protected T[] sliceItems(int offset, int size) {
    T[] slice = (T[]) java.lang.reflect.Array.newInstance(items.getClass().getComponentType(), size);
    System.arraycopy(items, offset, slice, 0, size);
    return slice;
  }
}
