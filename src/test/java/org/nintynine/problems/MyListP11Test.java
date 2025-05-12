package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP11's modified run-length encoding functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP11Test {

    /**
     * Tests basic modified run-length encoding with a sequence containing
     * both single elements and duplicates.
     */
    @Test
    void testBasicModifiedEncoding() {
        MyListP11<String> list = new MyListP11<>("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e");
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(6, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(4L, "a"), encoded.elementAt(1+0));
        assertEquals("b", encoded.elementAt(1+1));
        assertEquals(new MyListP10.EncodedElement<>(2L, "c"), encoded.elementAt(1+2));
        assertEquals(new MyListP10.EncodedElement<>(2L, "a"), encoded.elementAt(1+3));
        assertEquals("d", encoded.elementAt(1+4));
        assertEquals(new MyListP10.EncodedElement<>(4L, "e"), encoded.elementAt(1+5));
    }

    /**
     * Tests encoding when no elements have duplicates.
     * Should return the original list unchanged.
     */
    @Test
    void testNoConsecutiveDuplicates() {
        MyListP11<String> list = new MyListP11<>("a", "b", "c", "d");
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(4, encoded.length());
        assertEquals("a", encoded.elementAt(1+0));
        assertEquals("b", encoded.elementAt(1+1));
        assertEquals("c", encoded.elementAt(1+2));
        assertEquals("d", encoded.elementAt(1+3));
    }

    /**
     * Tests encoding when all elements are identical.
     * Should return a single encoded element.
     */
    @Test
    void testAllDuplicates() {
        MyListP11<String> list = new MyListP11<>("a", "a", "a", "a");
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(1, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(4L, "a"), encoded.elementAt(1+0));
    }

    /**
     * Tests encoding of an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP11<String> list = new MyListP11<>();
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(0, encoded.length());
    }

    /**
     * Tests encoding of a single element.
     * Should return the element unchanged.
     */
    @Test
    void testSingleElement() {
        MyListP11<String> list = new MyListP11<>("a");
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(1, encoded.length());
        assertEquals("a", encoded.elementAt(1+0));
    }

    /**
     * Tests encoding with numeric values.
     */
    @Test
    void testWithNumbers() {
        MyListP11<Integer> list = new MyListP11<>(1, 1, 1, 2, 3, 3, 4);
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(4, encoded.length());
        assertEquals(new MyListP10.EncodedElement<>(3L, 1), encoded.elementAt(1+0));
        assertEquals(2, encoded.elementAt(1+1));
        assertEquals(new MyListP10.EncodedElement<>(2L, 3), encoded.elementAt(1+2));
        assertEquals(4, encoded.elementAt(1+3));
    }

    /**
     * Tests encoding with alternating elements.
     * Should return the original list unchanged.
     */
    @Test
    void testAlternatingElements() {
        MyListP11<String> list = new MyListP11<>("a", "b", "a", "b", "a", "b");
        MyListP11<Object> encoded = list.encodeModified();

        assertEquals(6, encoded.length());
        assertEquals("a", encoded.elementAt(1+0));
        assertEquals("b", encoded.elementAt(1+1));
        assertEquals("a", encoded.elementAt(1+2));
        assertEquals("b", encoded.elementAt(1+3));
        assertEquals("a", encoded.elementAt(1+4));
        assertEquals("b", encoded.elementAt(1+5));
    }

    /**
     * Tests that the encode method throws NullPointerException when
     * the list contains null elements.
     */
    @Test
    void testWithNulls() {
        MyListP11<String> list = new MyListP11<>(null, null, "a", "a", null);
        assertThrows(NullPointerException.class, list::encodeModified);
    }
}
