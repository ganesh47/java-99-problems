package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP26's combination generation functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP26Test {

    private MyListP26<String> list;

    @BeforeEach
    void setUp() {
        list = new MyListP26<>("a", "b", "c", "d");
    }

    /**
     * Tests basic combination generation.
     */
    @Test
    void testBasicCombinations() {
        List<MyListP26<String>> result = list.combinations(2);

        // C(4,2) = 6 combinations
        assertEquals(6, result.size());

        // Verify some specific combinations
        assertTrue(containsCombination(result, "a", "b"));
        assertTrue(containsCombination(result, "a", "c"));
        assertTrue(containsCombination(result, "a", "d"));
        assertTrue(containsCombination(result, "b", "c"));
        assertTrue(containsCombination(result, "b", "d"));
        assertTrue(containsCombination(result, "c", "d"));
    }

    /**
     * Tests combinations with k=1.
     */
    @Test
    void testSingleElementCombinations() {
        List<MyListP26<String>> result = list.combinations(1);

        assertEquals(4, result.size());
        assertTrue(containsCombination(result, "a"));
        assertTrue(containsCombination(result, "b"));
        assertTrue(containsCombination(result, "c"));
        assertTrue(containsCombination(result, "d"));
    }

    /**
     * Tests combinations with k=n (full set).
     */
    @Test
    void testFullSetCombination() {
        List<MyListP26<String>> result = list.combinations(4);

        assertEquals(1, result.size());
        assertTrue(containsCombination(result, "a", "b", "c", "d"));
    }

    /**
     * Tests combinations with k=0.
     */
    @Test
    void testEmptyCombination() {
        List<MyListP26<String>> result = list.combinations(0);

        assertEquals(1, result.size());
        assertEquals(0, result.getFirst().length());
    }

    /**
     * Tests invalid k values.
     */
    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> list.combinations(-1));
        assertThrows(IllegalArgumentException.class, () -> list.combinations(5));
    }

    /**
     * Tests combinations with an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP26<String> emptyList = new MyListP26<>();
        List<MyListP26<String>> result = emptyList.combinations(0);

        assertEquals(1, result.size());
        assertEquals(0, result.getFirst().length());

        assertThrows(IllegalArgumentException.class, () -> emptyList.combinations(1));
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

        list.combinations(2);

        for (int i = 0; i < list.length(); i++) {
            assertEquals(original[i], list.elementAt(1+i));
        }
    }

    /**
     * Tests binomial coefficient calculations.
     */
    @Test
    void testBinomialCoefficient() {
        assertEquals(1, MyListP26.binomialCoefficient(5, 0));
        assertEquals(5, MyListP26.binomialCoefficient(5, 1));
        assertEquals(10, MyListP26.binomialCoefficient(5, 2));
        assertEquals(10, MyListP26.binomialCoefficient(5, 3));
        assertEquals(5, MyListP26.binomialCoefficient(5, 4));
        assertEquals(1, MyListP26.binomialCoefficient(5, 5));
        assertEquals(0, MyListP26.binomialCoefficient(5, 6));
    }

    /**
     * Tests that the number of combinations matches binomial coefficient.
     */
    @Test
    void testCombinationCount() {
        for (int k = 0; k <= list.length(); k++) {
            List<MyListP26<String>> combinations = list.combinations(k);
            assertEquals(MyListP26.binomialCoefficient(Math.toIntExact(list.length()), k),
                    combinations.size());
        }
    }

    /**
     * Tests combinations with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP26<Integer> intList = new MyListP26<>(1, 2, 3, 4);
        List<MyListP26<Integer>> intResult = intList.combinations(2);

        assertEquals(6, intResult.size());
        assertTrue(containsCombination(intResult, 1, 2));
        assertTrue(containsCombination(intResult, 3, 4));

        MyListP26<Double> doubleList = new MyListP26<>(1.0, 2.0, 3.0);
        List<MyListP26<Double>> doubleResult = doubleList.combinations(2);

        assertEquals(3, doubleResult.size());
        assertTrue(containsCombination(doubleResult, 1.0, 2.0));
        assertTrue(containsCombination(doubleResult, 2.0, 3.0));
    }

    /**
     * Tests combinations with null values.
     */
    @Test
    void testCombinationsWithNulls() {
        MyListP26<String> listWithNulls = new MyListP26<>("a", null, "c");
        assertThrows(NullPointerException.class,()->listWithNulls.combinations(2));


    }

    /**
     * Tests combination order consistency.
     */
    @Test
    void testCombinationOrder() {
        List<MyListP26<String>> result = list.combinations(2);

        // First combination should be first two elements
        MyListP26<String> firstCombo = result.getFirst();
        assertEquals("a", firstCombo.elementAt(1+0));
        assertEquals("b", firstCombo.elementAt(1+1));

        // Last combination should be last two elements
        MyListP26<String> lastCombo = result.getLast();
        assertEquals("c", lastCombo.elementAt(1+0));
        assertEquals("d", lastCombo.elementAt(1+1));
    }

    /**
     * Tests large combinations for performance.
     */
    @Test
    void testLargeCombinations() {
        MyListP26<Integer> largeList = new MyListP26<>();
        for (int i = 0; i < 20; i++) {
            largeList = new MyListP26<>(Arrays.copyOf(
                    largeList.stream().toList().toArray(new Integer[0]),
                    (int) (largeList.length() + 1)
            ));
            largeList.items[Math.toIntExact(largeList.length() - 1)] = i;
        }

        // Test combinations of size 3 from 20 elements
        List<MyListP26<Integer>> result = largeList.combinations(3);
        assertEquals(MyListP26.binomialCoefficient(20, 3), result.size());
    }

    /**
     * Helper method to check if combinations contain specific elements.
     */
    @SafeVarargs
    private <T> boolean containsCombination(List<MyListP26<T>> combinations, T... elements) {
        outer:
        for (MyListP26<T> combination : combinations) {
            if (combination.length() != elements.length) continue;

            for (int i = 0; i < elements.length; i++) {
                if (!Objects.equals(combination.elementAt(1+i), elements[i])) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }
}
