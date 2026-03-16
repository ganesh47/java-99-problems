package org.nintynine.problems;

/**
 * A generic list class that provides functionality to split a list into two parts.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP17<T> extends MyListP16<T> {

  /**
   * Constructs a new MyListP17 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP17(T... elements) {
    super(elements);
  }

  /**
   * Splits the list into two parts at the specified position.
   *
   * @param position the position at which to split the list
   * @return a pair of MyListP17 instances containing the split parts
   */
  public Pair<MyListP17<T>, MyListP17<T>> split(int position) {
    if (position < 0 || position > length()) {
      throw new IllegalArgumentException("Position must be between 0 and list length (inclusive)");
    }
    return new Pair<>(
        new MyListP17<>(sliceItems(0, position)),
        new MyListP17<>(sliceItems(position, (int) length() - position)));
  }

  /**
   * A simple pair class to hold two values.
   */
  public record Pair<A, B>(A first, B second) {
    @Override
    public String toString() {
      return "(" + first + ", " + second + ")";
    }
  }
}
