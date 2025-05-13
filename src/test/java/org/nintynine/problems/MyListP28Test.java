package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP28's list sorting functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP28Test {

    private MyListP28<String> listOfLists;

    @BeforeEach
    void setUp() {
        listOfLists = MyListP28.of(
                new MyListP28<>("a", "b", "c"),
                new MyListP28<>("d", "e"),
                new MyListP28<>("f", "g", "h"),
                new MyListP28<>("d", "e"),
                new MyListP28<>("i", "j", "k", "l"),
                new MyListP28<>("m", "n"),
                new MyListP28<>("o")
        );
    }

    /**
     * Tests basic length-based sorting.
     */
    @Test
    void testLSort() {
        MyListP28<String> sorted = listOfLists.lsort();

        // Verify ascending order by length
        int prevLength = -1;
        for (int i = 0; i < sorted.length(); i++) {
            int currentLength = Math.toIntExact(sorted.elementAt(1 + i).length());
            assertTrue(currentLength >= prevLength, String.format("At index %d, expected length %d, got %d", i, prevLength, currentLength));
            prevLength = currentLength;
        }
    }

    /**
     * Tests frequency-based sorting.
     */
    @Test
    void testLFSort() {
        MyListP28<String> sorted = listOfLists.lfsort();

        // Verify rare lengths come first
        Map<Integer, Integer> lengthFreq = new HashMap<>();
        for (int i = 0; i < sorted.length(); i++) {
            lengthFreq.merge(Math.toIntExact(sorted.elementAt(1 + i).length()), 1, Integer::sum);
        }

        int prevFreq = -1;
        for (int i = 0; i < sorted.length(); i++) {
            int currentFreq = lengthFreq.get(Math.toIntExact(sorted.elementAt(1+i).length()));
            assertTrue(currentFreq >= prevFreq);
            prevFreq = currentFreq;
        }
    }

    /**
     * Tests empty list handling.
     */
    @Test
    void testEmptyList() {
        MyListP28<String> emptyList = MyListP28.of();

        assertEquals(0, emptyList.lsort().length());
        assertEquals(0, emptyList.lfsort().length());
    }

    /**
     * Tests list with a single element.
     */
    @Test
    void testSingleElement() {
        MyListP28<String> singleList = MyListP28.of(
                new MyListP28<>("a", "b")
        );

        assertEquals(1, singleList.lsort().length());
        assertEquals(1, singleList.lfsort().length());
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        MyListP28<String>[] original = Arrays.copyOf(
                listOfLists.items,
                Math.toIntExact(listOfLists.length())
        );

        listOfLists.lsort();
        listOfLists.lfsort();

        assertArrayEquals(original, listOfLists.items);
    }

    /**
     * Tests sorting with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP28<Integer> intListOfLists = MyListP28.of(
                new MyListP28<>(1, 2, 3),
                new MyListP28<>(4, 5),
                new MyListP28<>(6)
        );

        MyListP28<Integer> sortedByLength = intListOfLists.lsort();
        assertEquals(1, sortedByLength.elementAt(1+0).length());
        assertEquals(2, sortedByLength.elementAt(1+1).length());
        assertEquals(3, sortedByLength.elementAt(1+2).length());
    }

    /**
     * Tests lists with same lengths.
     */
    @Test
    void testSameLengths() {
        MyListP28<String> sameLengths = MyListP28.of(
                new MyListP28<>("a", "b"),
                new MyListP28<>("c", "d"),
                new MyListP28<>("e", "f")
        );

        assertEquals(3, sameLengths.lsort().length());
        assertEquals(3, sameLengths.lfsort().length());
    }

    /**
     * Tests lists with null elements.
     */
    @Test
    void testWithNulls() {
        MyListP28<String> listWithNulls = MyListP28.of(
                new MyListP28<>("a", null),
                new MyListP28<>(null, null, null)
        );

        MyListP28<String> sorted = listWithNulls.lsort();
        assertEquals(2, sorted.elementAt(1+0).length());
        assertEquals(3, sorted.elementAt(1+1).length());
    }

    /**
     * Tests stability of sorting.
     */
    @Test
    void testSortingStability() {
        MyListP28<String> list = MyListP28.of(
                new MyListP28<>("a", "b"),
                new MyListP28<>("c", "d"),
                new MyListP28<>("e", "f", "g"),
                new MyListP28<>("h", "i")
        );

        MyListP28<String> sorted = list.lsort();

        // Check that the relative order of same-length lists is preserved
        assertEquals("a", sorted.elementAt(1+0).elementAt(1+0).elementAt(1+0).toString());
        assertEquals("c", sorted.elementAt(1+1).elementAt(1+0).elementAt(1+0).toString());
    }

    /**
     * Tests edge cases with varying lengths.
     */
    @Test
    void testVaryingLengths() {
        MyListP28<Integer> varying = MyListP28.of(
                new MyListP28<>(1),
                new MyListP28<>(1, 2, 3, 4, 5),
                new MyListP28<>(1, 2),
                new MyListP28<>(1, 2, 3)
        );

        MyListP28<Integer> sorted = varying.lsort();
        assertEquals(1, sorted.elementAt(1+0).length());
        assertEquals(5, sorted.elementAt(1+3).length());
    }

    /**
     * Tests frequency sorting with equal frequencies.
     */
    @Test
    void testEqualFrequencies() {
        MyListP28<String> equalFreq = MyListP28.of(
                new MyListP28<>("a"),
                new MyListP28<>("b", "c"),
                new MyListP28<>("d", "e", "f")
        );

        MyListP28<String> sorted = equalFreq.lfsort();
        // When frequencies are equal, should maintain length order
        assertEquals(1, sorted.elementAt(1+0).length());
        assertEquals(2, sorted.elementAt(1+1).length());
        assertEquals(3, sorted.elementAt(1+2).length());
    }
}
