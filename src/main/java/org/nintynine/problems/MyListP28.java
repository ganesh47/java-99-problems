package org.nintynine.problems;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A generic list class that provides list sorting functionality based on sublist properties.
 *
 * @param <T> the type of elements in the sublists
 */
public class MyListP28<T> extends MyListP27<MyListP28<T>> {


    @SafeVarargs
    public MyListP28(T... elements) {
        super();
        this.items = createItemsArray(elements);
    }

    @SuppressWarnings("unchecked")
    private MyListP28<T>[] createItemsArray(T[] elements) {
        MyListP28<T>[] result = (MyListP28<T>[]) new MyListP28[elements.length];
        for (int i = 0; i < elements.length; i++) {
            T[] singleElement = (T[]) new Object[]{elements[i]};
            result[i] = new MyListP28<>(singleElement, true);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private MyListP28(T[] elements, boolean isSublist) {
        super();
        if (isSublist && elements.length == 1) {
            // Base case: single element sublist
            this.items = (MyListP28<T>[]) new MyListP28[1];
            // Store the single element as a leaf node
            this.items[0] = new MyListP28<>() {
                @Override
                public String toString() {
                    return elements[0].toString();
                }
            };
        }
    }
    /**
     * Creates a list of lists from the given sublists.
     *
     * @param sublists array of sublists to initialize with
     * @param <T> the type of elements in the sublists
     * @return new MyListP28 containing the sublists
     */
    @SafeVarargs
    public static <T> MyListP28<T> of(MyListP28<T>... sublists) {
        MyListP28<T> result = new MyListP28<>();
        result.items = sublists;
        return result;
    }

    /**
     * Sorts sublists by their length.
     *
     * @return new list with sublists sorted by length
     */
    public MyListP28<T> lsort() {
        MyListP28<T>[] sorted = Arrays.copyOf(items, items.length);
        Arrays.sort(sorted, Comparator.comparingInt(sublist -> Math.toIntExact(sublist.length())));
        return new MyListP28<T>().withItems(sorted);
    }

    /**
     * Sorts sublists by frequency of their lengths.
     * Lists with rare lengths appear first.
     *
     * @return new list with sublists sorted by length frequency
     */
    public MyListP28<T> lfsort() {
        // Count frequency of each length
        Map<Integer, Integer> lengthFreq = new HashMap<>();
        for (MyListP28<T> sublist : items) {
            lengthFreq.merge(Math.toIntExact(sublist.length()), 1, Integer::sum);
        }

        MyListP28<T>[] sorted = Arrays.copyOf(items, items.length);
        Arrays.sort(sorted, (a, b) -> {
            int freqComp = lengthFreq.get(Math.toIntExact(a.length()))
                    .compareTo(lengthFreq.get(Math.toIntExact(b.length())));
            return Math.toIntExact(freqComp != 0 ? freqComp : a.length() - b.length());
        });

        return new MyListP28<T>().withItems(sorted);
    }

    /**
     * Helper method to set items directly.
     */
    private MyListP28<T> withItems(MyListP28<T>[] items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        if (!items.getClass().getComponentType().equals(MyListP28.class)) {
            return Arrays.toString(items);
        }
        return Arrays.toString(Arrays.stream(items)
                .map(MyListP28::toString)
                .toArray());
    }
}
