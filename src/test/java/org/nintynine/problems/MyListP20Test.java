package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP20's element removal functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP20Test {

    private MyListP20<String> list;

    @BeforeEach
    void setUp() {
        list = new MyListP20<>("a", "b", "c", "d", "e");
    }

    /**
     * Tests basic element removal.
     */
    @Test
    void testBasicRemoval() {
        MyListP17.Pair<String, MyListP20<String>> result = list.removeAt(2);

        assertEquals("b", result.first());
        MyListP20<String> newList = result.second();

        assertEquals(4, newList.length());
        assertEquals("a", newList.elementAt(1+(0)));
        assertEquals("c", newList.elementAt(1+(1)));
        assertEquals("d", newList.elementAt(1+(2)));
        assertEquals("e", newList.elementAt(1+(3)));
    }

    /**
     * Tests removal of first element.
     */
    @Test
    void testRemoveFirst() {
        MyListP17.Pair<String, MyListP20<String>> result = list.removeAt(1);

        assertEquals("a", result.first());
        MyListP20<String> newList = result.second();

        assertEquals(4, newList.length());
        assertEquals("b", newList.elementAt(1+(0)));
        assertEquals("e", newList.elementAt(1+(3)));
    }

    /**
     * Tests removal of last element.
     */
    @Test
    void testRemoveLast() {
        MyListP17.Pair<String, MyListP20<String>> result = list.removeAt(5);

        assertEquals("e", result.first());
        MyListP20<String> newList = result.second();

        assertEquals(4, newList.length());
        assertEquals("a", newList.elementAt(1+(0)));
        assertEquals("d", newList.elementAt(1+(3)));
    }

    /**
     * Tests invalid positions.
     */
    @Test
    void testInvalidPositions() {
        assertThrows(IllegalArgumentException.class, () -> list.removeAt(0));
        assertThrows(IllegalArgumentException.class, () -> list.removeAt(-1));
        assertThrows(IllegalArgumentException.class, () -> list.removeAt(6));
    }

    /**
     * Tests removal from single element list.
     */
    @Test
    void testSingleElementList() {
        MyListP20<String> singleList = new MyListP20<>("a");
        MyListP17.Pair<String, MyListP20<String>> result = singleList.removeAt(1);

        assertEquals("a", result.first());
        assertEquals(0, result.second().length());
    }

    /**
     * Tests removal with null values.
     */
    @Test
    void testRemovalWithNulls() {
        MyListP20<String> listWithNulls = new MyListP20<>("a", null, "c");
        MyListP17.Pair<String, MyListP20<String>> result = listWithNulls.removeAt(2);

        assertNull(result.first());
        MyListP20<String> newList = result.second();

        assertEquals(2, newList.length());
        assertEquals("a", newList.elementAt(1+(0)));
        assertEquals("c", newList.elementAt(1+(1)));
    }

    /**
     * Tests that removal preserves object references.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb1 = new StringBuilder("1");
        StringBuilder sb2 = new StringBuilder("2");
        StringBuilder sb3 = new StringBuilder("3");

        MyListP20<StringBuilder> builderList = new MyListP20<>(sb1, sb2, sb3);
        MyListP17.Pair<StringBuilder, MyListP20<StringBuilder>> result = builderList.removeAt(2);

        assertSame(sb2, result.first());
        MyListP20<StringBuilder> newList = result.second();

        assertEquals(2, newList.length());
        assertSame(sb1, newList.elementAt(1+(0)));
        assertSame(sb3, newList.elementAt(1+(1)));
    }

    /**
     * Tests removal with different types.
     */
    @Test
    void testDifferentTypes() {
        // Test with integers
        MyListP20<Integer> intList = new MyListP20<>(1, 2, 3, 4);
        MyListP17.Pair<Integer, MyListP20<Integer>> intResult = intList.removeAt(2);

        assertEquals(Integer.valueOf(2), intResult.first());
        assertEquals(3, intResult.second().length());
        assertEquals(Integer.valueOf(1), intResult.second().elementAt(1+(0)));
        assertEquals(Integer.valueOf(4), intResult.second().elementAt(1+(2)));

        // Test with doubles
        MyListP20<Double> doubleList = new MyListP20<>(1.0, 2.0, 3.0);
        MyListP17.Pair<Double, MyListP20<Double>> doubleResult = doubleList.removeAt(2);

        assertEquals(2.0, doubleResult.first(), 0.001);
        assertEquals(2, doubleResult.second().length());
        assertEquals(1.0, doubleResult.second().elementAt(1+(0)), 0.001);
        assertEquals(3.0, doubleResult.second().elementAt(1+(1)), 0.001);
    }

    /**
     * Tests consecutive removals.
     */
    @Test
    void testConsecutiveRemovals() {
        MyListP20<String> result = list;
        List<String> removed = new ArrayList<>();

        // Remove all elements one by one
        while (result.length() > 0) {
            MyListP17.Pair<String, MyListP20<String>> pair = result.removeAt(1);
            removed.add(pair.first());
            result = pair.second();
        }

        assertEquals(5, removed.size());
        assertEquals("a", removed.get(0));
        assertEquals("e", removed.get(4));
        assertEquals(0, result.length());
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        int originalLength = Math.toIntExact(list.length());
        String originalFirstElement = list.elementAt(1+(0)) ;
        String originalLastElement = list.elementAt(1+(originalLength - 1));

        list.removeAt(2);

        // Original list should be unchanged
        assertEquals(originalLength, list.length());
        assertEquals(originalFirstElement, list.elementAt(1+(0)));
        assertEquals(originalLastElement, list.elementAt(1+(originalLength - 1)));
    }

    /**
     * Tests element removal and list modification combinations.
     */
    @Test
    void testRemovalAndModificationCombinations() {
        // Remove an element and then rotate the result
        MyListP17.Pair<String, MyListP20<String>> removeResult = list.removeAt(2);
        MyListP19<String> rotated =  removeResult.second().rotate(1);

        assertEquals(4, rotated.length());
        assertEquals("c", rotated.elementAt(1+(0)));
        assertEquals("a", rotated.elementAt(1+(3)));

        // Split the rotated list
        MyListP17.Pair<MyListP17<String>, MyListP17<String>> splitResult = rotated.split(2);

        assertEquals(2, splitResult.first().length());
        assertEquals(2, splitResult.second().length());
        assertEquals("c", splitResult.first().elementAt(1+(0)));
        assertEquals("d", splitResult.first().elementAt(1+(1)));
    }
}
