package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP23's random selection functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP23Test {

    private MyListP23<String> list;
    private static final int STATISTICAL_TEST_ITERATIONS = 10000;

    @BeforeEach
    void setUp() {
        list = new MyListP23<>("a", "b", "c", "d", "e");
    }

    /**
     * Tests basic random selection.
     */
    @Test
    void testBasicRandomSelection() {
        MyListP23<String> result = list.rndSelect(3);

        assertEquals(3, result.length());
        // All elements should be from the original list
        for (int i = 0; i < result.length(); i++) {
            assertTrue(containsElement(list, result.elementAt(1 + i)));
        }
    }

    /**
     * Tests selection with n=0.
     */
    @Test
    void testZeroSelection() {
        MyListP23<String> result = list.rndSelect(0);
        assertEquals(0, result.length());
    }

    /**
     * Tests selection with n greater than list size.
     */
    @Test
    void testOverflowSelection() {
        MyListP23<String> result = list.rndSelect(10);

        assertEquals(list.length(), result.length());
        // Should contain all original elements
        for (int i = 0; i < list.length(); i++) {
            assertTrue(containsElement(result, list.elementAt(1 + i)));
        }
    }

    /**
     * Tests selection with negative n.
     */
    @Test
    void testNegativeSelection() {
        assertThrows(IllegalArgumentException.class, () -> list.rndSelect(-1));
    }

    /**
     * Tests selection from a single element list.
     */
    @Test
    void testSingleElementList() {
        MyListP23<String> singleList = new MyListP23<>("a");
        MyListP23<String> result = singleList.rndSelect(1);

        assertEquals(1, result.length());
        assertEquals("a", result.elementAt(1 + 0));
    }

    /**
     * Tests selection from an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP23<String> emptyList = new MyListP23<>();
        MyListP23<String> result = emptyList.rndSelect(5);

        assertEquals(0, result.length());
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        int originalLength = Math.toIntExact(list.length());
        String[] originalElements = new String[originalLength];
        for (int i = 0; i < originalLength; i++) {
            originalElements[i] = list.elementAt(1 + i);
        }

        list.rndSelect(3);

        assertEquals(originalLength, list.length());
        for (int i = 0; i < originalLength; i++) {
            assertEquals(originalElements[i], list.elementAt(1 + i));
        }
    }

    /**
     * Tests statistical properties of random selection.
     */
    @Test
    void testRandomDistribution() {
        Map<String, Integer> selectionCounts = new HashMap<>();
        int selectSize = 2;

        // Perform many selections to check distribution
        for (int i = 0; i < STATISTICAL_TEST_ITERATIONS; i++) {
            MyListP23<String> selected = list.rndSelect(selectSize);
            for (int j = 0; j < selected.length(); j++) {
                String element = selected.elementAt(1 + j);
                selectionCounts.merge(element, 1, Integer::sum);
            }
        }

        // Check that all elements were selected at least once
        for (int i = 0; i < list.length(); i++) {
            assertTrue(selectionCounts.containsKey(list.elementAt(1 + i)));
        }

        // Check that selections are roughly evenly distributed
        // (within 20% of the expected frequency)
        double expectedCount = (STATISTICAL_TEST_ITERATIONS * selectSize) / (double) list.length();
        double tolerance = expectedCount * 0.2; // 20% tolerance

        for (Integer count : selectionCounts.values()) {
            assertTrue(Math.abs(count - expectedCount) < tolerance,
                    "Selection frequency " + count + " deviates too much from expected " + expectedCount);
        }
    }

    /**
     * Tests selection with null values.
     */
    @Test
    void testSelectionWithNulls() {
        MyListP23<String> listWithNulls = new MyListP23<>("a", null, "c", null);
        MyListP23<String> result = listWithNulls.rndSelect(2);

        assertEquals(2, result.length());
        // All elements should be from the original list
        IntStream.iterate(0, i -> i < result.length(), i -> i + 1)
                .forEachOrdered(i -> assertThrows(NullPointerException.class,
                        () -> containsElement(listWithNulls, result.elementAt(1 + i))));
    }

    /**
     * Tests consecutive random selections.
     */
    @Test
    void testConsecutiveSelections() {
        MyListP23<String> firstSelection = list.rndSelect(2);
        MyListP23<String> secondSelection = firstSelection.rndSelect(1);

        assertEquals(2, firstSelection.length());
        assertEquals(1, secondSelection.length());
        assertTrue(containsElement(firstSelection, secondSelection.elementAt(1 + 0)));
    }

    /**
     * Tests with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP23<Integer> intList = new MyListP23<>(1, 2, 3, 4, 5);
        MyListP23<Integer> intResult = intList.rndSelect(3);

        assertEquals(3, intResult.length());
        for (int i = 0; i < intResult.length(); i++) {
            assertTrue(containsElement(intList, intResult.elementAt(1 + i)));
        }

        MyListP23<Double> doubleList = new MyListP23<>(1.0, 2.0, 3.0);
        MyListP23<Double> doubleResult = doubleList.rndSelect(2);

        assertEquals(2, doubleResult.length());
        for (int i = 0; i < doubleResult.length(); i++) {
            assertTrue(containsElement(doubleList, doubleResult.elementAt(1 + i)));
        }
    }

    /**
     * Tests that no duplicates are selected.
     */
    @Test
    void testNoDuplicates() {
        MyListP23<String> result = list.rndSelect(3);
        Set<String> uniqueElements = new HashSet<>();

        for (int i = 0; i < result.length(); i++) {
            assertTrue(uniqueElements.add(result.elementAt(1 + i)),
                    "Duplicate element found: " + result.elementAt(1 + i));
        }
    }

    /**
     * Helper method to check if the list contains an element.
     */
    private <T> boolean containsElement(MyListP23<T> list, T element) {
        for (int i = 0; i < list.length(); i++) {
            if (Objects.equals(list.elementAt(1 + i), element)) {
                return true;
            }
        }
        return false;
    }
}
            
