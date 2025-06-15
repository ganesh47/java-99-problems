package org.nintynine.problems;

import java.util.Arrays;
import java.util.LinkedList;

public class MyListP05<T> extends MyListP04<T> {
  @SafeVarargs
  public MyListP05(T... elements) {
    super(elements);
  }

  public MyListP05<T> reverse() {
    return new MyListP05<>(
        Arrays.stream(items)
            .reduce(
                new LinkedList<T>(),
                (list, item) -> {
                  list.addFirst(item);
                  return list;
                },
                (list1, list2) -> {
                  list2.addAll(list1);
                  return list2;
                })
            .toArray(Arrays.copyOf(items, 0)));
  }
}
