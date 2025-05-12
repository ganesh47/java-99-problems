package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP27's group generation functionality.
 */
class MyListP27Test {

    private MyListP27<String> list9;
    private MyListP27<String> list5;

    @BeforeEach
    void setUp() {
        list9 = new MyListP27<>("a", "b", "c", "d", "e", "f", "g", "h", "i");
        list5 = new MyListP27<>("a", "b", "c", "d", "e");
    }

    /**
     * Tests basic group3 functionality.
     */
    @Test
    void testGroup3() {
        List<List<MyListP27<String>>> result = list9.group3();

        assertFalse(result.isEmpty());

        // Verify the structure of each grouping
        for (List<MyListP27<String>> grouping : result) {
            assertEquals(3, grouping.size());
            assertEquals(2, grouping.get(0).length());
            assertEquals(3, grouping.get(1).length());
            assertEquals(4, grouping.get(2).length());
        }
    }

    /**
     * Tests custom group sizes.
     */
    @Test
    void testCustomGroups() {
        List<List<MyListP27<String>>> result =
                list5.group(Arrays.asList(2, 3));

        assertFalse(result.isEmpty());

        // Verify the structure of each grouping
        for (List<MyListP27<String>> grouping : result) {
            assertEquals(2, grouping.size());
            assertEquals(2, grouping.get(0).length());
            assertEquals(3, grouping.get(1).length());
        }
    }

    /**
     * Tests invalid group sizes.
     */
    @Test
    void testInvalidGroupSizes() {
        assertThrows(IllegalArgumentException.class,
                () -> list5.group(Arrays.asList(2, 2)));
        assertThrows(IllegalArgumentException.class,
                () -> list5.group(Arrays.asList(2, 4)));
    }

    /**
     * Tests empty list and single element groups.
     */
    @Test
    void testEdgeCases() {
        MyListP27<String> emptyList = new MyListP27<>();
        assertEquals(1, emptyList.group(Collections.emptyList()).size());

        MyListP27<String> singleList = new MyListP27<>("a");
        List<List<MyListP27<String>>> result =
                singleList.group(Collections.singletonList(1));
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().size());
        assertEquals(1, result.getFirst().getFirst().length());
    }

    /**
     * Tests that original list remains unchanged.
     */
    @Test
    void testOriginalListUnchanged() {
        String[] original = new String[Math.toIntExact(list5.length())];
        for (int i = 0; i < list5.length(); i++) {
            original[i] = list5.elementAt(1+i);
        }

        list5.group(Arrays.asList(2, 3));

        for (int i = 0; i < list5.length(); i++) {
            assertEquals(original[i], list5.elementAt(1+i));
        }
    }

    /**
     * Tests multinomial coefficient calculation.
     */
    @Test
    void testMultinomialCoefficient() {
        assertEquals(1, MyListP27.multinomialCoefficient(1, 1));
        assertEquals(6, MyListP27.multinomialCoefficient(4, 2, 2));
        assertEquals(90, MyListP27.multinomialCoefficient(6, 2, 2, 2));
    }

    /**
     * Tests that the number of groups matches multinomial coefficient.
     */
    @Test
    void testGroupCount() {
        List<List<MyListP27<String>>> result = list5.group(Arrays.asList(2, 3));
        assertEquals(MyListP27.multinomialCoefficient(5, 2, 3), result.size());
    }

    /**
     * Tests groups with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP27<Integer> intList = new MyListP27<>(1, 2, 3, 4);
        List<List<MyListP27<Integer>>> intResult =
                intList.group(Arrays.asList(2, 2));

        assertFalse(intResult.isEmpty());
        for (List<MyListP27<Integer>> grouping : intResult) {
            assertEquals(2, grouping.size());
            assertEquals(2, grouping.get(0).length());
            assertEquals(2, grouping.get(1).length());
        }
    }

    /**
     * Tests that groups are disjoint.
     */
    @Test
    void testDisjointGroups() {
        List<List<MyListP27<String>>> result =
                list5.group(Arrays.asList(2, 3));

        for (List<MyListP27<String>> grouping : result) {
            Set<String> allElements = new HashSet<>();
            for (MyListP27<String> group : grouping) {
                for (int i = 0; i < group.length(); i++) {
                    assertTrue(allElements.add(group.elementAt(1+i)),
                            "Element appears in multiple groups: " + group.elementAt(1+i));
                }
            }
        }
    }

    /**
     * Tests that each element appears in some group.
     */
    @Test
    void testCompleteGroups() {
        List<List<MyListP27<String>>> result =
                list5.group(Arrays.asList(2, 3));

        for (List<MyListP27<String>> grouping : result) {
            Set<String> allElements = new HashSet<>();
            for (MyListP27<String> group : grouping) {
                for (int i = 0; i < group.length(); i++) {
                    allElements.add(group.elementAt(1+i));
                }
            }
            assertEquals(list5.length(), allElements.size());
        }
    }

    /**
     * Tests group order consistency.
     */
    @Test
    void testGroupOrder() {
        List<List<MyListP27<String>>> result =
                list5.group(Arrays.asList(2, 3));

        for (List<MyListP27<String>> grouping : result) {
            assertEquals(2, grouping.get(0).length());
            assertEquals(3, grouping.get(1).length());
        }
    }


    /**
     * Tests performance with larger groups.
     */
    @Test
    void testLargeGroups() {
        MyListP27<Integer> largeList = new MyListP27<>();
        for (int i = 0; i < 8; i++) {
            largeList = new MyListP27<>(Arrays.copyOf(
                    largeList.stream().toList().toArray(new Integer[0]),
                    Math.toIntExact(largeList.length() + 1)
            ));
            largeList.items[Math.toIntExact(largeList.length() - 1)] = i;
        }

        List<List<MyListP27<Integer>>> result =
                largeList.group(Arrays.asList(3, 2, 3));
        assertFalse(result.isEmpty());
    }
}
