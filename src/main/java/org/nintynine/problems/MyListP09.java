package org.nintynine.problems;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A generic list class that extends MyListP08 to provide functionality for packing
 * consecutive duplicate elements into sublists.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP09<T> extends MyListP08<T> {

    /**
     * Constructs a new MyListP09 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP09(T... elements) {
        super(elements);
    }

    /**
     * Packs consecutive duplicate elements into sublists.
     * Each sublist contains elements that are equal according to {@link Object#equals(Object)}.
     *
     * <p>Examples:
     * <pre>
     * [a, a, a, b, c, c] → [[a, a, a], [b], [c, c]]
     * [a, b, a, b] → [[a], [b], [a], [b]]
     * [] → []
     * [a] → [[a]]
     * </pre>
     *
     * @return a new MyListP09 instance where each element is a MyListP09 containing
     * consecutive equal elements from the original list
     * @throws NullPointerException if the list contains null elements
     */
    @SuppressWarnings("unchecked")
    public MyListP09<MyListP09<T>> pack() {
        if (length() == 0) {
            return new MyListP09<>();
        }

        List<List<T>> packed = Arrays.stream(items)
                .sequential()
                .collect(ArrayList::new,
                        (lists, item) -> {
                            if (lists.isEmpty() || !Objects.equals(item, lists.getLast().getFirst())) {
                                lists.add(new ArrayList<>(List.of(item)));
                            } else {
                                lists.getLast().add(item);
                            }
                        },
                        ArrayList::addAll);

        //noinspection rawtypes
        return new MyListP09<>(packed.stream()
                .map(group -> new MyListP09<>(group.toArray((T[]) Array.newInstance(items.getClass().getComponentType(), 0))))
                .toArray(MyListP09[]::new));
    }
}
