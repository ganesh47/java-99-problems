package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP14's element duplication functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP14Test {

    /**
     * Tests basic duplication of a list with distinct elements.
     */
    @Test
    void testBasicDuplication() {
        MyListP14<String> list = new MyListP14<>("a", "b", "c", "d");
        MyListP14<String> duplicated = list.duplicate();

        assertEquals(8, duplicated.length());
        assertEquals("a", duplicated.elementAt(1+0));
        assertEquals("a", duplicated.elementAt(1+1));
        assertEquals("b", duplicated.elementAt(1+2));
        assertEquals("b", duplicated.elementAt(1+3));
        assertEquals("c", duplicated.elementAt(1+4));
        assertEquals("c", duplicated.elementAt(1+5));
        assertEquals("d", duplicated.elementAt(1+6));
        assertEquals("d", duplicated.elementAt(1+7));
    }

    /**
     * Tests duplication of a list that already contains duplicates.
     */
    @Test
    void testDuplicatingDuplicates() {
        MyListP14<String> list = new MyListP14<>("a", "a", "b", "b");
        MyListP14<String> duplicated = list.duplicate();

        assertEquals(8, duplicated.length());
        assertEquals("a", duplicated.elementAt(1+0));
        assertEquals("a", duplicated.elementAt(1+1));
        assertEquals("a", duplicated.elementAt(1+2));
        assertEquals("a", duplicated.elementAt(1+3));
        assertEquals("b", duplicated.elementAt(1+4));
        assertEquals("b", duplicated.elementAt(1+5));
        assertEquals("b", duplicated.elementAt(1+6));
        assertEquals("b", duplicated.elementAt(1+7));
    }

    /**
     * Tests duplication of an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP14<String> list = new MyListP14<>();
        MyListP14<String> duplicated = list.duplicate();

        assertEquals(0, duplicated.length());
    }

    /**
     * Tests duplication of a single element.
     */
    @Test
    void testSingleElement() {
        MyListP14<String> list = new MyListP14<>("a");
        MyListP14<String> duplicated = list.duplicate();

        assertEquals(2, duplicated.length());
        assertEquals("a", duplicated.elementAt(1+0));
        assertEquals("a", duplicated.elementAt(1+1));
    }

    /**
     * Tests duplication with numeric values.
     */
    @Test
    void testWithNumbers() {
        MyListP14<Integer> list = new MyListP14<>(1, 2, 3);
        MyListP14<Integer> duplicated = list.duplicate();

        assertEquals(6, duplicated.length());
        assertEquals(Integer.valueOf(1), duplicated.elementAt(1+0));
        assertEquals(Integer.valueOf(1), duplicated.elementAt(1+1));
        assertEquals(Integer.valueOf(2), duplicated.elementAt(1+2));
        assertEquals(Integer.valueOf(2), duplicated.elementAt(1+3));
        assertEquals(Integer.valueOf(3), duplicated.elementAt(1+4));
        assertEquals(Integer.valueOf(3), duplicated.elementAt(1+5));
    }

    /**
     * Tests that the duplication preserves object references.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb1 = new StringBuilder("a");
        StringBuilder sb2 = new StringBuilder("b");
        MyListP14<StringBuilder> list = new MyListP14<>(sb1, sb2);
        MyListP14<StringBuilder> duplicated = list.duplicate();

        assertEquals(4, duplicated.length());
        assertSame(sb1, duplicated.elementAt(1+0));
        assertSame(sb1, duplicated.elementAt(1+1));
        assertSame(sb2, duplicated.elementAt(1+2));
        assertSame(sb2, duplicated.elementAt(1+3));
    }

    /**
     * Tests duplication with null values.
     */
    @Test
    void testWithNulls() {
        MyListP14<String> list = new MyListP14<>("a", null, "b");
        MyListP14<String> duplicated = list.duplicate();

        assertEquals(6, duplicated.length());
        assertEquals("a", duplicated.elementAt(1+0));
        assertEquals("a", duplicated.elementAt(1+1));
        assertThrows(NullPointerException.class,()->duplicated.elementAt(1+2));
        assertThrows(NullPointerException.class,()->duplicated.elementAt(1+3));
        assertEquals("b", duplicated.elementAt(1+4));
        assertEquals("b", duplicated.elementAt(1+5));
    }

    /**
     * Tests multiple duplications of the same list.
     */
    @Test
    void testMultipleDuplications() {
        MyListP14<String> list = new MyListP14<>("a", "b");
        MyListP14<String> duplicated1 = list.duplicate();
        MyListP14<String> duplicated2 = duplicated1.duplicate();

        assertEquals(4, duplicated1.length());
        assertEquals(8, duplicated2.length());

        // Check first duplication
        assertEquals("a", duplicated1.elementAt(1+0));
        assertEquals("a", duplicated1.elementAt(1+1));
        assertEquals("b", duplicated1.elementAt(1+2));
        assertEquals("b", duplicated1.elementAt(1+3));

        // Check second duplication
        assertEquals("a", duplicated2.elementAt(1+0));
        assertEquals("a", duplicated2.elementAt(1+1));
        assertEquals("a", duplicated2.elementAt(1+2));
        assertEquals("a", duplicated2.elementAt(1+3));
        assertEquals("b", duplicated2.elementAt(1+4));
        assertEquals("b", duplicated2.elementAt(1+5));
        assertEquals("b", duplicated2.elementAt(1+6));
        assertEquals("b", duplicated2.elementAt(1+7));
    }
}
