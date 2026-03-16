package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A generic list class that provides direct run-length encoding functionality.
 */
public class MyListP13<T> extends MyListP12<T> {

  /**
   * Constructs a new org.nintynine.problems.MyListP13 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP13(T... elements) {
    super(elements);
  }

  /**
   * Direct run-length encoding.
   *
   * @return encoded result
   */
  public MyListP13<Object> encodeDirect() {
    if (length() == 0) {
      return new MyListP13<>();
    }

    List<Object> result = new ArrayList<>();
    T currentElement = elementAt(1);
    long count = 1;

    for (int i = 1; i < length(); i++) {
      T element = elementAt(i + 1);
      if (Objects.equals(currentElement, element)) {
        count++;
      } else {
        addEncodedElement(result, count, currentElement);
        currentElement = element;
        count = 1;
      }
    }

    addEncodedElement(result, count, currentElement);
    return new MyListP13<>(result.toArray());
  }

  private void addEncodedElement(List<Object> result, long count, T element) {
    if (count == 1) {
      result.add(element);
    } else {
      result.add(new MyListP10.EncodedElement<>(count, element));
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <U> MyListP13<U> decode() {
    List<U> decoded = decodeItems();
    return new MyListP13<>(
        decoded.toArray(
            size ->
                (U[])
                    Array.newInstance(
                        decoded.isEmpty() ? Object.class : decoded.getFirst().getClass(), size)));
  }
}
