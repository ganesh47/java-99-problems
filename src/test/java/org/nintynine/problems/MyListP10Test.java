package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP10's run-length encoding functionality.
 * Verifies the encode() method behavior under various scenarios.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP10Test {

    /**
     * Tests basic run-length encoding with a sequence containing
     * multiple consecutive duplicates.
     */
    @Test
    void testBasicEncoding() {
        MyListP10<String> list = new MyListP10<>("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e");
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(6, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(4, "a"), encoded.elementAt(1+0));
        assertEquals(new MyListP10.EncodedElement<>(1, "b"), encoded.elementAt(1+1));
        assertEquals(new MyListP10.EncodedElement<>(2, "c"), encoded.elementAt(1+2));
        assertEquals(new MyListP10.EncodedElement<>(2, "a"), encoded.elementAt(1+3));
        assertEquals(new MyListP10.EncodedElement<>(1, "d"), encoded.elementAt(1+4));
        assertEquals(new MyListP10.EncodedElement<>(4, "e"), encoded.elementAt(1+5));
    }

    /**
     * Tests encoding behavior when the input list has no consecutive duplicates.
     * Each element should be encoded with count 1.
     */
    @Test
    void testNoConsecutiveDuplicates() {
        MyListP10<String> list = new MyListP10<>("a", "b", "c", "d");
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(4, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(1, "a"), encoded.elementAt(1+0));
        assertEquals(new MyListP10.EncodedElement<>(1, "b"), encoded.elementAt(1+1));
        assertEquals(new MyListP10.EncodedElement<>(1, "c"), encoded.elementAt(1+2));
        assertEquals(new MyListP10.EncodedElement<>(1, "d"), encoded.elementAt(1+3));
    }

    /**
     * Tests encoding when all elements in the list are identical.
     * Should result in a single encoded element with the total count.
     */
    @Test
    void testAllDuplicates() {
        MyListP10<String> list = new MyListP10<>("a", "a", "a", "a");
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(1, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(4, "a"), encoded.elementAt(1+0));
    }

    /**
     * Tests encoding behavior with an empty list.
     * Should return an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP10<String> list = new MyListP10<>();
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(0, encoded.length());
    }

    /**
     * Tests encoding behavior with a single-element list.
     * Should return a list with one encoded element with count 1.
     */
    @Test
    void testSingleElement() {
        MyListP10<String> list = new MyListP10<>("a");
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(1, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(1, "a"), encoded.elementAt(1+0));
    }

    /**
     * Tests encoding with numeric values.
     * Verifies that encoding works correctly with Integer objects.
     */
    @Test
    void testWithNumbers() {
        MyListP10<Integer> list = new MyListP10<>(1, 1, 1, 2, 3, 3, 4, 4, 4, 4);
        MyListP10<MyListP10.EncodedElement<Integer>> encoded = list.encode();
        System.out.println(encoded);
        assertEquals(4, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(3, 1), encoded.elementAt(1+0));
        assertEquals(new MyListP10.EncodedElement<>(1, 2), encoded.elementAt(1+1));
        assertEquals(new MyListP10.EncodedElement<>(2, 3), encoded.elementAt(1+2));
        assertEquals(new MyListP10.EncodedElement<>(4, 4), encoded.elementAt(1+3));
    }

    /**
     * Tests encoding behavior with alternating elements.
     * Each element should be encoded with count 1.
     */
    @Test
    void testAlternatingElements() {
        MyListP10<String> list = new MyListP10<>("a", "b", "a", "b", "a", "b");
        MyListP10<MyListP10.EncodedElement<String>> encoded = list.encode();

        assertEquals(6, encoded.length());
        for (int i = 0; i < encoded.length(); i++) {
            assertEquals(new MyListP10.EncodedElement<>(1, i % 2 == 0 ? "a" : "b"), encoded.elementAt(1+i));
        }
    }

    /**
     * Tests that the encode method throws NullPointerException when
     * the list contains null elements.
     */
    @Test
    void testWithNulls() {
        MyListP10<String> list = new MyListP10<>(null, null, "a", "a", null);
        assertThrows(NullPointerException.class, list::encode);
    }
}
