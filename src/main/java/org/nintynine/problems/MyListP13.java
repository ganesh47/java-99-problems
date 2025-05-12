package org.nintynine.problems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A generic list class that provides direct run-length encoding functionality.
 * Encodes consecutive duplicates without creating intermediate sublists.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP13<T> extends MyListP12<T> {

    /**
     * Constructs a new org.nintynine.problems.MyListP13 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP13(T... elements) {
        super(elements);
    }

    /**
     * Performs direct run-length encoding on the list.
     * Consecutive duplicates are encoded as EncodedElement instances,
     * while single elements are kept as is.
     * Unlike P11, this implementation doesn't create intermediate sublists.
     * 
     * <p>Examples:
     * <pre>
     * [a, a, a, a, b, c, c, a, a, d, e, e, e, e] → [(4 a), b, (2 c), (2 a), d, (4 e)]
     * [a, b, c] → [a, b, c]
     * [] → []
     * [a] → [a]
     * [a, a] → [(2 a)]
     * </pre>
     *
     * @return a new org.nintynine.problems.MyListP13 containing a mix of original elements and EncodedElement instances
     * @throws NullPointerException if the list contains null elements
     */
    public MyListP13<Object> encodeDirect() {
        if (length() == 0) {
            return new MyListP13<>();
        }

        List<Object> result = new ArrayList<>();
        T currentElement = elementAt(1);
        long count = 1;

        for (int i = 1; i < length(); i++) {
            T element = elementAt(i+1);
            if (Objects.equals(currentElement, element)) {
                count++;
            } else {
                addEncodedElement(result, count, currentElement);
                currentElement = element;
                count = 1;
            }
        }
        
        // Handle the last group
        addEncodedElement(result, count, currentElement);

        return new MyListP13<>(result.toArray());
    }

    /**
     * Helper method to add either an encoded element or a single element to the result list.
     *
     * @param result the list to add the element to
     * @param count the count of consecutive occurrences
     * @param element the element to add
     */
    private void addEncodedElement(List<Object> result, long count, T element) {
        if (count == 1) {
            result.add(element);
        } else {
            result.add(new MyListP10.EncodedElement<>(count, element));
        }
    }

    /**
     * Override of decode method to return MyListP13 instead of MyListP12
     */
    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public <U> MyListP13<U> decode() {
        List<U> decoded = new ArrayList<>();

        for (T item : items) {
            if (item instanceof MyListP10.EncodedElement<?> encoded) {
                U element = (U) encoded.element;
                for (long i = 0; i < encoded.count; i++) {
                    decoded.add(element);
                }
            } else {
                decoded.add((U) item);
            }
        }

        return new MyListP13<>(decoded.toArray(size -> (U[]) Array.newInstance(
                decoded.isEmpty() ? Object.class : decoded.getFirst().getClass(), size)));
    }

}
