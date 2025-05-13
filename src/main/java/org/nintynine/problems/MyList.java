package org.nintynine.problems;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

interface Streamable<T> extends Iterable<T> {
    Stream<T> stream();
}

public class MyList<T> implements Streamable<T>{
    protected T[] items;

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

    @Override
    public Stream<T> stream() {
        return Arrays.stream (items);
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(items).iterator();
    }
}
