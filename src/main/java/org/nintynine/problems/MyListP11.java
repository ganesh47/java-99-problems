package org.nintynine.problems;

/**
 * A generic list class that provides modified run-length encoding functionality. Single elements
 * are kept as is, while only repeated elements are encoded as (N E) pairs.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP11<T> extends MyListP10<T> {

  /**
   * Constructs a new MyListP11 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP11(T... elements) {
    super(elements);
  }

  /**
   * Modified run-length encoding. Only elements with duplicates are transferred
   * as EncodedElement terms.
   *
   * <p>E.g. {@code [a,a,b,c,c,a]} results in {@code [(2 a), b, (2 c), a]}.</p>
   *
   * @return encoded sequence
   */
  public MyListP11<Object> encodeModified() {
    MyListP09<MyListP09<T>> packed = pack();

    Object[] encoded =
        packed.stream()
            .map(
                sublist -> {
                  if (sublist.length() == 1) {
                    return sublist.elementAt(1);
                  } else {
                    return new EncodedElement<>(sublist.length(), sublist.elementAt(1));
                  }
                })
            .toArray();

    return new MyListP11<>(encoded);
  }
}
