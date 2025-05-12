package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP17's list splitting functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP17Test {

    /**
     * Tests basic list splitting.
     */
    @Test
    void testBasicSplit() {
        MyListP17<String> list = new MyListP17<>("a", "b", "c", "d", "e");
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> result = list.split(3);

        MyListP17<String> first = result.first();
        MyListP17<String> second = result.second();

        assertEquals(3, first.length());
        assertEquals(2, second.length());

        assertEquals("a", first.elementAt(1+0));
        assertEquals("b", first.elementAt(1+1));
        assertEquals("c", first.elementAt(1+2));

        assertEquals("d", second.elementAt(1+0));
        assertEquals("e", second.elementAt(1+1));
    }

    /**
     * Tests splitting at position 0.
     */
    @Test
    void testSplitAtZero() {
        MyListP17<String> list = new MyListP17<>("a", "b", "c");
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> result = list.split(0);

        assertEquals(0, result.first().length());
        assertEquals(3, result.second().length());
        assertEquals("a", result.second().elementAt(1+0));
        assertEquals("c", result.second().elementAt(1+2));
    }

    /**
     * Tests splitting at list length.
     */
    @Test
    void testSplitAtLength() {
        MyListP17<String> list = new MyListP17<>("a", "b", "c");
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> result = list.split(3);

        assertEquals(3, result.first().length());
        assertEquals(0, result.second().length());
        assertEquals("a", result.first().elementAt(1+0));
        assertEquals("c", result.first().elementAt(1+2));
    }

    /**
     * Tests splitting empty list.
     */
    @Test
    void testEmptyList() {
        MyListP17<String> list = new MyListP17<>();
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> result = list.split(0);

        assertEquals(0, result.first().length());
        assertEquals(0, result.second().length());
    }

    /**
     * Tests invalid split positions.
     */
    @Test
    void testInvalidPositions() {
        MyListP17<String> list = new MyListP17<>("a", "b", "c");

        assertThrows(IllegalArgumentException.class, () -> list.split(-1));
        assertThrows(IllegalArgumentException.class, () -> list.split(4));
    }

    /**
     * Tests splitting with null values.
     */
    @Test
    void testWithNulls() {
        MyListP17<String> list = new MyListP17<>("a", null, "b", null);
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> result = list.split(2);

        assertEquals(2, result.first().length());
        assertEquals(2, result.second().length());

        assertEquals("a", result.first().elementAt(1+0));
        assertThrows(NullPointerException.class,()->result.first().elementAt(1+1));
        assertEquals("b", result.second().elementAt(1+0));
        assertThrows(NullPointerException.class,()->result.second().elementAt(1+1));
    }

    /**
     * Tests that object references are preserved.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb1 = new StringBuilder("test1");
        StringBuilder sb2 = new StringBuilder("test2");

        MyListP17<StringBuilder> list = new MyListP17<>(sb1, sb2);
        MyListP17.Pair<MyListP17<StringBuilder>, MyListP17<StringBuilder>> result = list.split(1);

        assertSame(sb1, result.first().elementAt(1+0));
        assertSame(sb2, result.second().elementAt(1+0));
    }

    /**
     * Tests splitting with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP17<Integer> intList = new MyListP17<>(1, 2, 3, 4);
        MyListP17.Pair<MyListP17<Integer>, MyListP17<Integer>> intResult = intList.split(2);

        assertEquals(2, intResult.first().length());
        assertEquals(2, intResult.second().length());
        assertEquals(Integer.valueOf(1), intResult.first().elementAt(1+0));
        assertEquals(Integer.valueOf(3), intResult.second().elementAt(1+0));

        MyListP17<Double> doubleList = new MyListP17<>(1.0, 2.0, 3.0);
        MyListP17.Pair<MyListP17<Double>, MyListP17<Double>> doubleResult = doubleList.split(2);

        assertEquals(2, doubleResult.first().length());
        assertEquals(1, doubleResult.second().length());
        assertEquals(1.0, doubleResult.first().elementAt(1+0), 0.001);
        assertEquals(3.0, doubleResult.second().elementAt(1+0), 0.001);
    }

    /**
     * Tests Pair class equality.
     */
    @Test
    void testPairEquality() {
        MyListP17<String> list1 = new MyListP17<>("a", "b");
        MyListP17<String> list2 = new MyListP17<>("c");

        MyListP17.Pair<MyListP17<String>, MyListP17<String>> pair1 = new MyListP17.Pair<>(list1, list2);
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> pair2 = new MyListP17.Pair<>(list1, list2);
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> pair3 = new MyListP17.Pair<>(list2, list1);

        assertEquals(pair1, pair2);
        assertNotEquals(pair1, pair3);
        assertNotEquals(null, pair1);
        assertEquals(pair1.hashCode(), pair2.hashCode());
    }
}
