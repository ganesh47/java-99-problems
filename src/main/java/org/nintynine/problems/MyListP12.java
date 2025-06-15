package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic list class that provides functionality to decode run-length encoded lists. Can decode
 * both standard (P10) and modified (P11) run-length encodings.
 *
 * @param <T> the type of elements in the list
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
   * Decodes a run-length encoded list, supporting both standard (P10) and modified (P11) encodings.
   * For modified encoding, single elements are represented directly, while repeated elements are
   * encoded as EncodedElement instances.
   *
   * <p>Examples:
   *
   * <pre>
   * [(4 a), b, (2 c), (2 a), d, (4 e)] → [a, a, a, a, b, c, c, a, a, d, e, e, e, e]
   * [a, b, c] → [a, b, c]
   * [(2 a), (2 b)] → [a, a, b, b]
   * [] → []
   * [a] → [a]
   * </pre>
   *
   * @param <U> the type of elements in the decoded list
   * @return a new MyListP12 containing the decoded sequence
   * @throws IllegalArgumentException if the encoded list contains invalid elements
   */
  @SuppressWarnings("unchecked")
  public <U> MyListP12<U> decode() {
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

    return new MyListP12<>(
        decoded.toArray(
            size ->
                (U[])
                    Array.newInstance(
                        decoded.isEmpty() ? Object.class : decoded.getFirst().getClass(), size)));
  }
}
