package org.nintynine.problems;

import java.util.Arrays;
import java.util.Objects;

public class MyList<T> {
    protected final T[] items;

    @SafeVarargs
    public MyList(T... elements) {
        this.items = Arrays.copyOf(elements, elements.length);
    }
    //P01 : Find the last box of a list
    public T last() {
        return Arrays.stream(items)
                .reduce((_, b) -> b)
                .orElseThrow(() -> new IllegalStateException("Empty list has no last element"));
    }

    @Override
    public String toString() {
        return "(" + String.join(" ", Arrays.stream(items).map(Objects::toString).toList()) + ")";
    }
}
