package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP21's element insertion functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP21Test {

    private MyListP21<String> list;

    @BeforeEach
    void setUp() {
        list = new MyListP21<>("a", "b", "c", "d");
    }

    /**
     * Tests basic insertion.
     */
    @Test
    void testBasicInsertion() {
        MyListP21<String> result = list.insertAt("x", 2);

        assertEquals(5, result.length());
        assertEquals("a", result.elementAt(1+0));
        assertEquals("x", result.elementAt(1+1));
        assertEquals("b", result.elementAt(1+2));
        assertEquals("c", result.elementAt(1+3));
        assertEquals("d", result.elementAt(1+4));
    }

    /**
     * Tests insertion at the start.
     */
    @Test
    void testInsertAtStart() {
        MyListP21<String> result = list.insertAt("x", 1);

        assertEquals(5, result.length());
        assertEquals("x", result.elementAt(1+0));
        assertEquals("a", result.elementAt(1+1));
        assertEquals("d", result.elementAt(1+4));
    }

    /**
     * Tests insertion at the end.
     */
    @Test
    void testInsertAtEnd() {
        MyListP21<String> result = list.insertAt("x", Math.toIntExact(list.length() + 1));

        assertEquals(5, result.length());
        assertEquals("a", result.elementAt(1+0));
        assertEquals("d", result.elementAt(1+3));
        assertEquals("x", result.elementAt(1+4));
    }

    /**
     * Tests invalid positions.
     */
    @Test
    void testInvalidPositions() {
        assertThrows(IllegalArgumentException.class, () -> list.insertAt("x", 0));
        assertThrows(IllegalArgumentException.class, () -> list.insertAt("x", -1));
        assertThrows(IllegalArgumentException.class, () -> list.insertAt("x", Math.toIntExact(list.length() + 2)));
    }

    /**
     * Tests insertion into an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP21<String> emptyList = new MyListP21<>();
        MyListP21<String> result = emptyList.insertAt("x", 1);

        assertEquals(1, result.length());
        assertEquals("x", result.elementAt(1+0));
    }

    /**
     * Tests insertion of null value.
     */
    @Test
    void testInsertNull() {
        MyListP21<String> result = list.insertAt(null, 2);

        assertEquals(5, result.length());
        assertEquals("a", result.elementAt(1+0));
        assertThrows(NullPointerException.class,()->result.elementAt(1+1));
        assertEquals("b", result.elementAt(1+2));
    }

    /**
     * Tests that insertion preserves object references.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb1 = new StringBuilder("1");
        StringBuilder sb2 = new StringBuilder("2");
        MyListP21<StringBuilder> builderList = new MyListP21<>(sb1);

        MyListP21<StringBuilder> result = builderList.insertAt(sb2, 1);

        assertEquals(2, result.length());
        assertSame(sb2, result.elementAt(1+0));
        assertSame(sb1, result.elementAt(1+1));
    }

    /**
     * Tests insertion with different types.
     */
    @Test
    void testDifferentTypes() {
        // Test with integers
        MyListP21<Integer> intList = new MyListP21<>(1, 2, 3);
        MyListP21<Integer> intResult = intList.insertAt(99, 2);

        assertEquals(4, intResult.length());
        assertEquals(Integer.valueOf(1), intResult.elementAt(1+0));
        assertEquals(Integer.valueOf(99), intResult.elementAt(1+1));
        assertEquals(Integer.valueOf(3), intResult.elementAt(1+3));

        // Test with doubles
        MyListP21<Double> doubleList = new MyListP21<>(1.0, 2.0);
        MyListP21<Double> doubleResult = doubleList.insertAt(3.5, 2);

        assertEquals(3, doubleResult.length());
        assertEquals(1.0, doubleResult.elementAt(1+0), 0.001);
        assertEquals(3.5, doubleResult.elementAt(1+1), 0.001);
        assertEquals(2.0, doubleResult.elementAt(1+2), 0.001);
    }

    /**
     * Tests multiple consecutive insertions.
     */
    @Test
    void testConsecutiveInsertions() {
        MyListP21<String> result = list;

        // Insert multiple elements at different positions
        result = result.insertAt("x", 1)  // At start
                .insertAt("y", 3)   // In middle
                .insertAt("z", Math.toIntExact(result.length()+3));  // At end

        assertEquals(7, result.length());
        assertEquals("x", result.elementAt(1+0));
        assertEquals("y", result.elementAt(1+2));
        assertEquals("z", result.elementAt(1+6));
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        long originalLength = list.length();
        String originalFirstElement = list.elementAt(1+0);
        String originalLastElement = list.elementAt(1+originalLength - 1);

        list.insertAt("x", 2);

        // The original list should be unchanged
        assertEquals(originalLength, list.length());
        assertEquals(originalFirstElement, list.elementAt(1+0));
        assertEquals(originalLastElement, list.elementAt(1+originalLength - 1));
    }

    /**
     * Tests insertion and other operations combinations.
     */
    @Test
    void testInsertionCombinations() {
        // Insert and then rotate
        MyListP21<String> inserted = list.insertAt("x", 2);
        MyListP19<String> rotated = inserted.rotate(2);

        assertEquals(5, rotated.length());
        assertEquals("x", rotated.elementAt(5));

        // Insert and then remove
        MyListP17.Pair<String, MyListP20<String>> removed = inserted.removeAt(2);
        assertEquals("x", removed.first());
        assertEquals(4, removed.second().length());
    }

    /**
     * Tests insertion at every possible position.
     */
    @Test
    void testInsertionAtEveryPosition() {
        for (int i = 1; i <= list.length() + 1; i++) {
            MyListP21<String> result = list.insertAt("x", i);

            assertEquals(list.length() + 1, result.length());
            assertEquals("x", result.elementAt(1+i - 1));

            // Check elements before the insertion point
            for (int j = 0; j < i - 1; j++) {
                assertEquals(list.elementAt(1+j), result.elementAt(1+j));
            }

            // Check elements after the insertion point
            for (int j = i; j < result.length(); j++) {
                assertEquals(list.elementAt(1+j - 1), result.elementAt(1+j));
            }
        }
    }

    /**
     * Tests insertion with mutable objects.
     */
    @Test
    void testMutableObjects() {
        List<StringBuilder> builders = Arrays.asList(
                new StringBuilder("1"),
                new StringBuilder("2")
        );

        MyListP21<StringBuilder> builderList = new MyListP21<>(
                builders.toArray(new StringBuilder[0])
        );
        StringBuilder newBuilder = new StringBuilder("3");

        MyListP21<StringBuilder> result = builderList.insertAt(newBuilder, 2);

        // Modify the inserted object
        newBuilder.append("-modified");

        // The modification should be reflected in the new list
        assertEquals("3-modified", result.elementAt(1+1).toString());

        // Original objects should still be in their positions
        assertEquals("1", result.elementAt(1+0).toString());
        assertEquals("2", result.elementAt(1+2).toString());
    }
}
