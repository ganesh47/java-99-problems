package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for MyListP25's random permutation functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP25Test {

    private MyListP25<String> list;
    private static final int STATISTICAL_TEST_ITERATIONS = 10000;

    @BeforeEach
    void setUp() {
        list = new MyListP25<>("a", "b", "c", "d", "e");
    }

    /**
     * Tests basic permutation functionality.
     */
    @Test
    void testBasicPermutation() {
        MyListP25<String> result = list.randomPermutation();

        assertEquals(list.length(), result.length());
        // All elements should be present
        for (int i = 0; i < list.length(); i++) {
            assertTrue(containsElement(result, list.elementAt(1+i)));
        }
    }



    /**
     * Tests permutation of a single element list.
     */
    @Test
    void testSingleElementPermutation() {
        MyListP25<String> singleList = new MyListP25<>("a");
        MyListP25<String> result = singleList.randomPermutation();

        assertEquals(1, result.length());
        assertEquals("a", result.elementAt(1+0));
    }

    /**
     * Tests permutation of an empty list.
     */
    @Test
    void testEmptyListPermutation() {
        MyListP25<String> emptyList = new MyListP25<>();
        MyListP25<String> result = emptyList.randomPermutation();

        assertEquals(0, result.length());
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        String[] original = new String[Math.toIntExact(list.length())];
        for (int i = 0; i < list.length(); i++) {
            original[i] = list.elementAt(1+i);
        }

        list.randomPermutation();

        // The original list should be unchanged
        for (int i = 0; i < list.length(); i++) {
            assertEquals(original[i], list.elementAt(1+i));
        }
    }

    /**
     * Tests that elements appear exactly once.
     */
    @Test
    void testNoDuplicates() {
        MyListP25<String> result = list.randomPermutation();
        Set<String> uniqueElements = new HashSet<>();

        for (int i = 0; i < result.length(); i++) {
            assertTrue(uniqueElements.add(result.elementAt(1+i)),
                    "Duplicate element found: " + result.elementAt(1+i));
        }
    }

    /**
     * Tests statistical properties of permutations.
     */
    @Test
    void testRandomDistribution() {
        // Count how many times each element appears at each position
        Map<String, Map<Integer, Integer>> positionCounts = new HashMap<>();

        for (int i = 0; i < list.length(); i++) {
            positionCounts.put(list.elementAt(1+i), new HashMap<>());
        }

        for (int i = 0; i < STATISTICAL_TEST_ITERATIONS; i++) {
            MyListP25<String> permutation = list.randomPermutation();
            for (int pos = 0; pos < permutation.length(); pos++) {
                String element = permutation.elementAt(1+pos);
                positionCounts.get(element).merge(pos, 1, Integer::sum);
            }
        }

        // Each element should appear at each position roughly equally often
        double expectedCount = STATISTICAL_TEST_ITERATIONS / (double) list.length();
        double tolerance = expectedCount * 0.2; // 20% tolerance

        for (Map<Integer, Integer> counts : positionCounts.values()) {
            for (int pos = 0; pos < list.length(); pos++) {
                int count = counts.getOrDefault(pos, 0);
                assertTrue(Math.abs(count - expectedCount) < tolerance,
                        "Position distribution not random enough: " + count +
                                " vs expected " + expectedCount);
            }
        }
    }



    /**
     * Tests consecutive permutations.
     */
    @Test
    void testConsecutivePermutations() {
        MyListP25<String> firstPermutation = list.randomPermutation();
        MyListP25<String> secondPermutation = firstPermutation.randomPermutation();

        assertEquals(list.length(), firstPermutation.length());
        assertEquals(list.length(), secondPermutation.length());

        // Both should contain all original elements
        for (int i = 0; i < list.length(); i++) {
            String element = list.elementAt(1+i);
            assertTrue(containsElement(firstPermutation, element));
            assertTrue(containsElement(secondPermutation, element));
        }
    }

    /**
     * Tests permutation with different types.
     */
    @Test
    void testDifferentTypes() {
        // Test with integers
        MyListP25<Integer> intList = new MyListP25<>(1, 2, 3, 4, 5);
        MyListP25<Integer> intResult = intList.randomPermutation();

        assertEquals(intList.length(), intResult.length());
        for (int i = 0; i < intList.length(); i++) {
            assertTrue(containsElement(intResult, intList.elementAt(1+i)));
        }

        // Test with doubles
        MyListP25<Double> doubleList = new MyListP25<>(1.0, 2.0, 3.0);
        MyListP25<Double> doubleResult = doubleList.randomPermutation();

        assertEquals(doubleList.length(), doubleResult.length());
        for (int i = 0; i < doubleList.length(); i++) {
            assertTrue(containsElement(doubleResult, doubleList.elementAt(1+i)));
        }
    }

    /**
     * Tests that at least some permutations are different.
     */
    @Test
    void testPermutationsAreDifferent() {
        Set<String> differentPermutations = new HashSet<>();

        // Generate several permutations
        for (int i = 0; i < 100; i++) {
            MyListP25<String> result = list.randomPermutation();
            differentPermutations.add(permutationToString(result));
        }

        // We should get more than one different permutation
        assertTrue(differentPermutations.size() > 1,
                "All permutations were identical");
    }

    /**
     * Tests permutations of lists with duplicate elements.
     */
    @Test
    void testPermutationWithDuplicates() {
        MyListP25<String> listWithDuplicates = new MyListP25<>("a", "b", "a", "b");
        MyListP25<String> result = listWithDuplicates.randomPermutation();

        assertEquals(4, result.length());

        // Count occurrences of each element
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < result.length(); i++) {
            counts.merge(result.elementAt(1+i), 1, Integer::sum);
        }

        assertEquals(2, counts.get("a"));
        assertEquals(2, counts.get("b"));
    }

    /**
     * Helper method to check if a list contains an element.
     */
    private <T> boolean containsElement(MyListP25<T> list, T element) {
        for (int i = 0; i < list.length(); i++) {
            if (Objects.equals(list.elementAt(1+i), element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to convert permutation to string for comparison.
     */
    private String permutationToString(MyListP25<String> permutation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < permutation.length(); i++) {
            sb.append(permutation.elementAt(1+i)).append(",");
        }
        return sb.toString();
    }
}
