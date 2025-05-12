package org.nintynine.problems;

/**
 * A generic list class that provides modified run-length encoding functionality.
 * Single elements are kept as is, while only repeated elements are encoded as (N E) pairs.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP11<T> extends MyListP10<T> {

    /**
     * Constructs a new MyListP11 instance with the given elements.
     *
     * @param elements the elements to initialize the list with
     */
    @SafeVarargs
    public MyListP11(T... elements) {
        super(elements);
    }

    /**
     * Performs modified run-length encoding on the list.
     * Single elements are kept as is, while consecutive duplicates
     * are encoded as EncodedElement instances.
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
     * @return a new MyListP11 containing a mix of original elements and EncodedElement instances
     * @throws NullPointerException if the list contains null elements
     */
    public MyListP11<Object> encodeModified() {
        MyListP09<MyListP09<T>> packed = pack();
        
        Object[] encoded = packed.stream()
                .map(sublist -> {
                    if (sublist.length() == 1) {
                        return sublist.elementAt(1);
                    } else {
                        return new EncodedElement<>(sublist.length(), sublist.elementAt(1));
                    }
                })
                .toArray();
        
        return new MyListP11<>(encoded);
    }
}
