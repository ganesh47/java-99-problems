package org.nintynine.problems;


import java.util.Arrays;

public class MyListP04<T> extends MyListP03<T> {
    @SafeVarargs
    public MyListP04(T... elements) {
        super(elements);
    }

    public long length() {
        return Arrays.stream(items)
                .count();
    }
}
