package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.stream.Stream;

/**
 * A generic list class that provides functionality to duplicate elements.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP14<T> extends MyListP13<T> {

    /**
     * Constructs a new org.nintynine.problems.MyListP14 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP14(T... elements) {
        super(elements);
    }

    /**
     * Duplicates each element in the list.
     * Each element appears exactly twice in the resulting list.
     * 
     * <p>Examples:
     * <pre>
     * [a, b, c, d] → [a, a, b, b, c, c, d, d]
     * [a, a] → [a, a, a, a]
     * [] → []
     * [a] → [a, a]
     * </pre>
     *
     * @return a new org.nintynine.problems.MyListP14 with duplicated elements
     */
    public MyListP14<T> duplicate() {
        if (length() == 0) {
            return new MyListP14<>();
        }

        @SuppressWarnings("unchecked")
        T[] duplicated = stream()
                .flatMap(item -> Stream.of(item, item))
                .toArray(size -> (T[]) Array.newInstance(
                        items[0].getClass(),
                        size
                ));

        return new MyListP14<>(duplicated);
    }

}
