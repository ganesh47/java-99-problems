package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for MyListP12's run-length decoding functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP12Test {

    /**
     * Tests decoding of a modified run-length encoded list with mixed
     * single elements and encoded runs.
     */
    @Test
    void testBasicDecoding() {
        MyListP12<Object> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(4L, "a"),
                "b",
                new MyListP10.EncodedElement<>(2L, "c"),
                new MyListP10.EncodedElement<>(2L, "a"),
                "d",
                new MyListP10.EncodedElement<>(4L, "e")
        );

        MyListP12<String> decoded = encoded.decode();

        String[] expected = {"a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"};
        assertEquals(expected.length, decoded.length());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], decoded.elementAt(1 + i));
        }
    }

    /**
     * Tests decoding of a list with only single elements (no encoding).
     */
    @Test
    void testNoEncodedElements() {
        MyListP12<String> encoded = new MyListP12<>("a", "b", "c", "d");
        MyListP12<String> decoded = encoded.decode();

        assertEquals(4, decoded.length());
        assertEquals("a", decoded.elementAt(1 + 0));
        assertEquals("b", decoded.elementAt(1 + 1));
        assertEquals("c", decoded.elementAt(1 + 2));
        assertEquals("d", decoded.elementAt(1 + 3));
    }

    /**
     * Tests decoding of a list with only encoded elements.
     */
    @Test
    void testAllEncodedElements() {
        MyListP12<MyListP10.EncodedElement<String>> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(2L, "a"),
                new MyListP10.EncodedElement<>(3L, "b"),
                new MyListP10.EncodedElement<>(1L, "c")
        );

        MyListP12<String> decoded = encoded.decode();

        String[] expected = {"a", "a", "b", "b", "b", "c"};
        assertEquals(expected.length, decoded.length());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], decoded.elementAt(1 + i));
        }
    }

    /**
     * Tests decoding of an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP12<Object> encoded = new MyListP12<>();
        MyListP12<Object> decoded = encoded.decode();

        assertEquals(0, decoded.length());
    }

    /**
     * Tests decoding of a list with a single non-encoded element.
     */
    @Test
    void testSingleElement() {
        MyListP12<Object> encoded = new MyListP12<>("a");
        MyListP12<String> decoded = encoded.decode();

        assertEquals(1, decoded.length());
        assertEquals("a", decoded.elementAt(1 + 0));
    }

    /**
     * Tests decoding of a list with a single encoded element.
     */
    @Test
    void testSingleEncodedElement() {
        MyListP12<MyListP10.EncodedElement<String>> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(3L, "a")
        );

        MyListP12<String> decoded = encoded.decode();

        assertEquals(3, decoded.length());
        assertEquals("a", decoded.elementAt(1 + 0));
        assertEquals("a", decoded.elementAt(1 + 1));
        assertEquals("a", decoded.elementAt(1 + 2));
    }

    /**
     * Tests decoding with numeric values.
     */
    @Test
    void testWithNumbers() {
        MyListP12<Object> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(2L, 1),
                3,
                new MyListP10.EncodedElement<>(3L, 4)
        );

        MyListP12<Integer> decoded = encoded.decode();

        Integer[] expected = {1, 1, 3, 4, 4, 4};
        assertEquals(expected.length, decoded.length());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], decoded.elementAt(1 + i));
        }
    }

    /**
     * Tests that decoding preserves the order of elements.
     */
    @Test
    void testPreservesOrder() {
        MyListP12<Object> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(2L, "a"),
                "b",
                new MyListP10.EncodedElement<>(2L, "a")
        );

        MyListP12<String> decoded = encoded.decode();

        String[] expected = {"a", "a", "b", "a", "a"};
        assertEquals(expected.length, decoded.length());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], decoded.elementAt(1 + i));
        }
    }

    /**
     * Tests decoding of elements with count zero.
     */
    @Test
    void testZeroCount() {
        MyListP12<Object> encoded = new MyListP12<>(
                new MyListP10.EncodedElement<>(0L, "a"),
                "b"
        );

        MyListP12<String> decoded = encoded.decode();

        assertEquals(1, decoded.length());
        assertEquals("b", decoded.elementAt(1 + 0));
    }
}

