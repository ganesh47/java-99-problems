package org.nintynine.problems;

import java.util.Objects;

/**
 * A generic list class that provides run-length encoding functionality for consecutive duplicate
 * elements. Uses MyListP09's packing functionality to implement run-length encoding compression.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP10<T> extends MyListP09<T> {

  /**
   * Constructs a new MyListP10 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP10(T... elements) {
    super(elements);
  }

  /**
   * Run-length encoding of a list. Consecutive duplicates of elements are encoded
   * as EncodedElement instances.
   *
   * <p>Example: {@code [a,a,a,a,b,c,c,a,a,d,e,e,e,e]} becomes
   * {@code [(4 a), (1 b), (2 c), (2 a), (1 d), (4 e)]}.</p>
   *
   * @return list of run-length encoded elements
   */
  public MyListP10<EncodedElement<T>> encode() {
    MyListP09<MyListP09<T>> packed = pack();

    @SuppressWarnings("unchecked")
    EncodedElement<T>[] encoded =
        packed.stream()
            .map(sublist -> new EncodedElement<>(sublist.length(), sublist.elementAt(1)))
            .toArray(EncodedElement[]::new);

    return new MyListP10<>(encoded);
  }

  /**
   * Represents a run-length encoded element.
   *
   * @param <T> the type of the element
   */
  public static class EncodedElement<T> {
    final long count;
    final T element;

    /**
     * Creates a new EncodedElement with the specified count and element.
     *
     * @param count the number of consecutive occurrences
     * @param element the element that was repeated
     */
    public EncodedElement(long count, T element) {
      this.count = count;
      this.element = element;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      EncodedElement<?> that = (EncodedElement<?>) o;
      return count == that.count && Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
      return Objects.hash(count, element);
    }

    @Override
    public String toString() {
      return "(" + count + " " + element + ")";
    }
  }
}
