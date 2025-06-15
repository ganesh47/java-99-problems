package org.nintynine.problems;

import java.util.Objects;
import java.util.stream.LongStream;

public class MyListP06<T> extends MyListP05<T> {
  @SafeVarargs
  public MyListP06(T... elements) {
    super(elements);
  }

  public boolean isPalindrome() {
    final long len = length();
    return LongStream.range(0, len / 2)
        .allMatch(i -> Objects.equals(elementAt(i + 1), elementAt(len + 1 - 1 - i)));
  }
}
