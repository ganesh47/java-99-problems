package org.nintynine.problems;

import java.util.Objects;

/**
 * A generic list class that provides run-length encoding functionality for consecutive duplicate elements.
 * Uses MyListP09's packing functionality to implement run-length encoding compression.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP10<T> extends MyListP09<T> {

    /**
     * Constructs a new MyListP10 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP10(T... elements) {
        super(elements);
    }

    /**
     * Represents a run-length encoded element.
     *
     * @param <T> the type of the element
     */
    public static class EncodedElement<T> {
        final long count;
        final T element;

        /**
         * Creates a new EncodedElement with the specified count and element.
         *
         * @param count   the number of consecutive occurrences
         * @param element the element that was repeated
         */
        public EncodedElement(long count, T element) {
            this.count = count;
            this.element = element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EncodedElement<?> that = (EncodedElement<?>) o;
            return count == that.count && Objects.equals(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(count, element);
        }

        @Override
        public String toString() {
            return "(" + count + " " + element + ")";
        }
    }

    /**
     * Performs run-length encoding on the list. Consecutive duplicates of elements
     * are encoded as EncodedElement instances containing the count and the element.
     * 
     * <p>Examples:
     * <pre>
     * [a, a, a, a, b, c, c, a, a, d, e, e, e, e] → [(4 a), (1 b), (2 c), (2 a), (1 d), (4 e)]
     * [a, b, c] → [(1 a), (1 b), (1 c)]
     * [] → []
     * [a] → [(1 a)]
     * </pre>
     *
     * @return a new MyListP10 containing EncodedElement instances representing the run-length encoding
     * @throws NullPointerException if the list contains null elements
     */
    public MyListP10<EncodedElement<T>> encode() {
        MyListP09<MyListP09<T>> packed = pack();
        
        @SuppressWarnings("unchecked")
        EncodedElement<T>[] encoded = packed.stream()
                .map(sublist -> new EncodedElement<>(sublist.length(), sublist.elementAt(1)))
                .toArray(EncodedElement[]::new);
        
        return new MyListP10<>(encoded);
    }
}
