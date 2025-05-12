package org.nintynine.problems;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP24's lotto number generation functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP24Test {

    private static final int STATISTICAL_TEST_ITERATIONS = 10000;

    /**
     * Tests basic lotto selection.
     */
    @Test
    void testBasicLottoSelection() {
        MyListP24<Integer> result = MyListP24.lottoSelect(6, 49);

        assertEquals(6, result.length());
        // Check range
        IntStream.iterate(0, i -> i < result.length(), i -> i + 1).map(i -> result.elementAt(1 + i)).mapToObj(number -> number >= 1 && number <= 49).forEach(Assertions::assertTrue);
    }

    /**
     * Tests edge cases with minimum values.
     */
    @Test
    void testMinimumValues() {
        // Select one number from range 1..1
        MyListP24<Integer> result = MyListP24.lottoSelect(1, 1);

        assertEquals(1, result.length());
        assertEquals(Integer.valueOf(1), result.elementAt(1+0));
    }

    /**
     * Tests invalid inputs.
     */
    @Test
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> MyListP24.lottoSelect(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> MyListP24.lottoSelect(5, 0));
        assertThrows(IllegalArgumentException.class, () -> MyListP24.lottoSelect(10, 5));
        assertThrows(IllegalArgumentException.class, () -> MyListP24.lottoSelect(0, -1));
    }

    /**
     * Tests selecting zero numbers.
     */
    @Test
    void testZeroSelection() {
        MyListP24<Integer> result = MyListP24.lottoSelect(0, 10);
        assertEquals(0, result.length());
    }

    /**
     * Tests selecting all numbers in range.
     */
    @Test
    void testSelectAll() {
        MyListP24<Integer> result = MyListP24.lottoSelect(5, 5);

        assertEquals(5, result.length());
        // Should contain all numbers 1-5 in some order
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < result.length(); i++) {
            numbers.add(result.elementAt(1+i));
        }
        assertEquals(Set.of(1, 2, 3, 4, 5), numbers);
    }

    /**
     * Tests that no duplicates are generated.
     */
    @Test
    void testNoDuplicates() {
        MyListP24<Integer> result = MyListP24.lottoSelect(6, 49);
        Set<Integer> numbers = new HashSet<>();

        for (int i = 0; i < result.length(); i++) {
            assertTrue(numbers.add(result.elementAt(1+i)),
                    "Duplicate number found: " + result.elementAt(1+i));
        }
    }

    /**
     * Tests statistical properties of number generation.
     */
    @Test
    void testRandomDistribution() {
        Map<Integer, Integer> numberCounts = new HashMap<>();
        int n = 1;
        int m = 10;

        // Perform many selections to check distribution
        for (int i = 0; i < STATISTICAL_TEST_ITERATIONS; i++) {
            MyListP24<Integer> result = MyListP24.lottoSelect(n, m);
            for (int j = 0; j < result.length(); j++) {
                int number = result.elementAt(1+j);
                numberCounts.merge(number, 1, Integer::sum);
            }
        }

        // Check that all possible numbers were selected at least once
        for (int i = 1; i <= m; i++) {
            assertTrue(numberCounts.containsKey(i),
                    "Number " + i + " was never selected");
        }

        // Check that selections are roughly evenly distributed
        // (within 20% of expected frequency)
        double expectedCount = (STATISTICAL_TEST_ITERATIONS * n) / (double) m;
        double tolerance = expectedCount * 0.2; // 20% tolerance

        for (Map.Entry<Integer, Integer> entry : numberCounts.entrySet()) {
            assertTrue(Math.abs(entry.getValue() - expectedCount) < tolerance,
                    "Selection frequency for " + entry.getKey() +
                            " deviates too much from expected");
        }
    }

    /**
     * Tests consecutive lotto selections.
     */
    @Test
    void testConsecutiveSelections() {
        MyListP24<Integer> first = MyListP24.lottoSelect(3, 10);
        MyListP24<Integer> second = MyListP24.lottoSelect(3, 10);

        // Both selections should be valid
        assertEquals(3, first.length());
        assertEquals(3, second.length());

        // Check ranges
        for (int i = 0; i < first.length(); i++) {
            assertTrue(first.elementAt(1+i) >= 1 && first.elementAt(1+i) <= 10);
            assertTrue(second.elementAt(1+i) >= 1 && second.elementAt(1+i) <= 10);
        }
    }

    /**
     * Tests boundary conditions.
     */
    @Test
    void testBoundaryConditions() {
        // Test selecting just one less than maximum
        MyListP24<Integer> almostAll = MyListP24.lottoSelect(9, 10);
        assertEquals(9, almostAll.length());

        // Test with consecutive numbers
        MyListP24<Integer> consecutive = MyListP24.lottoSelect(3, 3);
        assertEquals(3, consecutive.length());
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < consecutive.length(); i++) {
            numbers.add(consecutive.elementAt(1+i));
        }
        assertEquals(Set.of(1, 2, 3), numbers);
    }

    /**
     * Tests that results are within specified range.
     */
    @Test
    void testNumbersInRange() {
        int n = 10;
        int m = 100;
        MyListP24<Integer> result = MyListP24.lottoSelect(n, m);

        for (int i = 0; i < result.length(); i++) {
            int number = result.elementAt(1+i);
            assertTrue(number >= 1 && number <= m,
                    "Generated number " + number + " is outside range 1.." + m);
        }
    }

    /**
     * Tests with varying range sizes.
     */
    @Test
    void testVaryingRangeSizes() {
        // Small range
        MyListP24<Integer> small = MyListP24.lottoSelect(2, 3);
        assertEquals(2, small.length());

        // Medium range
        MyListP24<Integer> medium = MyListP24.lottoSelect(5, 20);
        assertEquals(5, medium.length());

        // Large range
        MyListP24<Integer> large = MyListP24.lottoSelect(10, 100);
        assertEquals(10, large.length());

        // Verify all are within their respective ranges
        verifyRange(small, 3);
        verifyRange(medium, 20);
        verifyRange(large, 100);
    }

    /**
     * Helper method to verify numbers are within range.
     */
    private void verifyRange(MyListP24<Integer> list, int max) {
        for (int i = 0; i < list.length(); i++) {
            int number = list.elementAt(1+i);
            assertTrue(number >= 1 && number <= max,
                    "Number " + number + " outside range " + 1 + ".." + max);
        }
    }
}
