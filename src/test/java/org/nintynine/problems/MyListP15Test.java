package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP15's element replication functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP15Test {

    /**
     * Tests basic replication with different counts.
     */
    @Test
    void testBasicReplication() {
        MyListP15<String> list = new MyListP15<>("a", "b", "c");

        MyListP15<String> replicated = list.replicate(3);
        assertEquals(9, replicated.length());
        assertEquals("a", replicated.elementAt(1+0));
        assertEquals("a", replicated.elementAt(1+1));
        assertEquals("a", replicated.elementAt(1+2));
        assertEquals("b", replicated.elementAt(1+3));
        assertEquals("b", replicated.elementAt(1+4));
        assertEquals("b", replicated.elementAt(1+5));
        assertEquals("c", replicated.elementAt(1+6));
        assertEquals("c", replicated.elementAt(1+7));
        assertEquals("c", replicated.elementAt(1+8));
    }

    /**
     * Tests replication with zero count.
     */
    @Test
    void testZeroReplication() {
        MyListP15<String> list = new MyListP15<>("a", "b", "c");
        MyListP15<String> replicated = list.replicate(0);
        assertEquals(0, replicated.length());
    }

    /**
     * Tests replication with negative count.
     */
    @Test
    void testNegativeReplication() {
        MyListP15<String> list = new MyListP15<>("a", "b", "c");
        assertThrows(IllegalArgumentException.class, () -> list.replicate(-1));
    }

    /**
     * Tests replication of an empty list.
     */
    @Test
    void testEmptyList() {
        MyListP15<String> list = new MyListP15<>();
        MyListP15<String> replicated = list.replicate(3);
        assertEquals(0, replicated.length());
    }

    /**
     * Tests replication of a single element.
     */
    @Test
    void testSingleElement() {
        MyListP15<String> list = new MyListP15<>("a");
        MyListP15<String> replicated = list.replicate(2);
        assertEquals(2, replicated.length());
        assertEquals("a", replicated.elementAt(1+0));
        assertEquals("a", replicated.elementAt(1+1));
    }

    /**
     * Tests replication with null values.
     */
    @Test
    void testWithNulls() {
        MyListP15<String> list = new MyListP15<>("a", null, "b");
        MyListP15<String> replicated = list.replicate(2);
        assertEquals(6, replicated.length());
        assertEquals("a", replicated.elementAt(1+0));
        assertEquals("a", replicated.elementAt(1+1));
        assertThrows(NullPointerException.class,()->replicated.elementAt(1+2));
        assertThrows(NullPointerException.class,()->replicated.elementAt(1+3));
        assertEquals("b", replicated.elementAt(1+4));
        assertEquals("b", replicated.elementAt(1+5));
    }

    /**
     * Tests that replication preserves object references.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb = new StringBuilder("test");
        MyListP15<StringBuilder> list = new MyListP15<>(sb);
        MyListP15<StringBuilder> replicated = list.replicate(3);

        assertEquals(3, replicated.length());
        for (int i = 0; i < 3; i++) {
            assertSame(sb, replicated.elementAt(1+i));
        }
    }

    /**
     * Tests replication with different types.
     */
    @Test
    void testDifferentTypes() {
        // Test with Integer
        MyListP15<Integer> intList = new MyListP15<>(1, 2);
        MyListP15<Integer> replicatedInts = intList.replicate(2);
        assertEquals(4, replicatedInts.length());
        assertEquals(Integer.valueOf(1), replicatedInts.elementAt(1+0));
        assertEquals(Integer.valueOf(1), replicatedInts.elementAt(1+1));
        assertEquals(Integer.valueOf(2), replicatedInts.elementAt(1+2));
        assertEquals(Integer.valueOf(2), replicatedInts.elementAt(1+3));

        // Test with Double
        MyListP15<Double> doubleList = new MyListP15<>(1.0, 2.0);
        MyListP15<Double> replicatedDoubles = doubleList.replicate(2);
        assertEquals(4, replicatedDoubles.length());
        assertEquals(1.0, replicatedDoubles.elementAt(1+0), 0.001);
        assertEquals(1.0, replicatedDoubles.elementAt(1+1), 0.001);
        assertEquals(2.0, replicatedDoubles.elementAt(1+2), 0.001);
        assertEquals(2.0, replicatedDoubles.elementAt(1+3), 0.001);
    }

    /**
     * Tests replication with large numbers.
     */
    @Test
    void testLargeReplication() {
        MyListP15<String> list = new MyListP15<>("a");
        MyListP15<String> replicated = list.replicate(1000);
        assertEquals(1000, replicated.length());
        for (int i = 0; i < 1000; i++) {
            assertEquals("a", replicated.elementAt(1+i));
        }
    }

    /**
     * Tests replication with single occurrence.
     */
    @Test
    void testSingleReplication() {
        MyListP15<String> list = new MyListP15<>("a", "b", "c");
        MyListP15<String> replicated = list.replicate(1);
        assertEquals(3, replicated.length());
        assertEquals("a", replicated.elementAt(1+0));
        assertEquals("b", replicated.elementAt(1+1));
        assertEquals("c", replicated.elementAt(1+2));
    }
}
