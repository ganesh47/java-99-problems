package org.nintynine.problems;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MyListP07<T> extends MyListP06<T> {
    @SafeVarargs
    public MyListP07(T... elements) {
        super(elements);
    }

    @SuppressWarnings("unchecked")
    public MyListP07<T> flatten() {
        T[] flattenedArray = (T[]) Arrays.stream(items)
                .flatMap(this::flattenHelper)
                .toArray();
        return new MyListP07<>(flattenedArray);
    }

    protected Stream<?> flattenHelper(Object item) {
        return switch (item) {
            case MyListP07<?> myListP07 -> Arrays.stream(myListP07.items).flatMap(this::flattenHelper);
            case List<?> objects -> objects.stream().flatMap(this::flattenHelper);
            case Object[] objects -> Arrays.stream(objects).flatMap(this::flattenHelper);
            case null, default -> Stream.of(item);
        };
    }
}
