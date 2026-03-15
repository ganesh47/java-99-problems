package org.nintynine.problems;

import java.util.Objects;

/**
 * P06: Find out whether a list is a palindrome.
 *
 * @param <T> element type
 */
public class MyListP06<T> extends MyListP05<T> {
  /**
   * Constructs a MyListP06 from the provided elements.
   *
   * @param elements the elements to include in the list
   */
  @SafeVarargs
  public MyListP06(T... elements) {
    super(elements);
  }

  /**
   * Checks if the list is a palindrome.
   *
   * @return true if palindrome, false otherwise
   */
  public boolean isPalindrome() {
    if (items == null || items.length == 0) {
      return true;
    }
    final int len = items.length;
    for (int i = 0; i < len / 2; i++) {
      if (!Objects.equals(items[i], items[len - 1 - i])) {
        return false;
      }
    }
    return true;
  }
}
