package org.nintynine.problems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyListP19's rotation functionality.
 */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP19Test {

    private MyListP19<String> list;

    @BeforeEach
    void setUp() {
        list = new MyListP19<>("a", "b", "c", "d", "e");
    }

    /**
     * Tests basic left rotation.
     */
    @Test
    void testLeftRotation() {
        MyListP19<String> rotated = list.rotate(2);

        assertEquals(5, rotated.length());
        assertEquals("c", rotated.elementAt(1+(0)));
        assertEquals("d", rotated.elementAt(1+(1)));
        assertEquals("e", rotated.elementAt(1+(2)));
        assertEquals("a", rotated.elementAt(1+(3)));
        assertEquals("b", rotated.elementAt(1+(4)));
    }

    /**
     * Tests basic right rotation.
     */
    @Test
    void testRightRotation() {
        MyListP19<String> rotated = list.rotate(-2);

        assertEquals(5, rotated.length());
        assertEquals("d", rotated.elementAt(1+(0)));
        assertEquals("e", rotated.elementAt(1+(1)));
        assertEquals("a", rotated.elementAt(1+(2)));
        assertEquals("b", rotated.elementAt(1+(3)));
        assertEquals("c", rotated.elementAt(1+(4)));
    }

    /**
     * Tests rotation with zero places.
     */
    @Test
    void testZeroRotation() {
        MyListP19<String> rotated = list.rotate(0);

        assertEquals(5, rotated.length());
        for (int i = 0; i < list.length(); i++) {
            assertEquals(list.elementAt(1+(i)), rotated.elementAt(1+(i)));
        }
    }

    /**
     * Tests rotation with list length places.
     */
    @Test
    void testFullRotation() {
        MyListP19<String> rotated = list.rotate(Math.toIntExact(list.length()));

        assertEquals(5, rotated.length());
        for (int i = 0; i < list.length(); i++) {
            assertEquals(list.elementAt(1+(i)), rotated.elementAt(1+(i)));
        }
    }

    /**
     * Tests rotation with places greater than list length.
     */
    @Test
    void testRotationGreaterThanLength() {
        MyListP19<String> rotated = list.rotate(7);  // Equivalent to rotate(2)
        MyListP19<String> expected = list.rotate(2);

        assertEquals(expected.length(), rotated.length());
        for (int i = 0; i < rotated.length(); i++) {
            assertEquals(expected.elementAt(1+(i)), rotated.elementAt(1+(i)));
        }
    }

    /**
     * Tests rotation with empty list.
     */
    @Test
    void testEmptyList() {
        MyListP19<String> emptyList = new MyListP19<>();
        MyListP19<String> rotated = emptyList.rotate(5);

        assertEquals(0, rotated.length());
    }

    /**
     * Tests rotation with single element.
     */
    @Test
    void testSingleElement() {
        MyListP19<String> singleList = new MyListP19<>("a");
        MyListP19<String> rotated = singleList.rotate(3);

        assertEquals(1, rotated.length());
        assertEquals("a", rotated.elementAt(1+(0)));
    }

    /**
     * Tests rotation with null values.
     */
    @Test
    void testRotationWithNulls() {
        MyListP19<String> listWithNulls = new MyListP19<>("a", null, "c", null);
        assertThrows(NullPointerException.class,()->listWithNulls.rotate(2));

    }

    /**
     * Tests that rotation preserves object references.
     */
    @Test
    void testObjectReferences() {
        StringBuilder sb1 = new StringBuilder("1");
        StringBuilder sb2 = new StringBuilder("2");
        StringBuilder sb3 = new StringBuilder("3");

        MyListP19<StringBuilder> builderList = new MyListP19<>(sb1, sb2, sb3);
        MyListP19<StringBuilder> rotated = builderList.rotate(1);

        assertEquals(3, rotated.length());
        assertSame(sb2, rotated.elementAt(1+(0)));
        assertSame(sb3, rotated.elementAt(1+(1)));
        assertSame(sb1, rotated.elementAt(1+(2)));
    }

    /**
     * Tests rotation with different types.
     */
    @Test
    void testDifferentTypes() {
        MyListP19<Integer> intList = new MyListP19<>(1, 2, 3, 4);
        MyListP19<Integer> rotatedInts = intList.rotate(2);

        assertEquals(4, rotatedInts.length());
        assertEquals(Integer.valueOf(3), rotatedInts.elementAt(1+(0)));
        assertEquals(Integer.valueOf(4), rotatedInts.elementAt(1+(1)));
        assertEquals(Integer.valueOf(1), rotatedInts.elementAt(1+(2)));
        assertEquals(Integer.valueOf(2), rotatedInts.elementAt(1+(3)));

        MyListP19<Double> doubleList = new MyListP19<>(1.0, 2.0, 3.0);
        MyListP19<Double> rotatedDoubles = doubleList.rotate(-1);

        assertEquals(3, rotatedDoubles.length());
        assertEquals(3.0, rotatedDoubles.elementAt(1+(0)), 0.001);
        assertEquals(1.0, rotatedDoubles.elementAt(1+(1)), 0.001);
        assertEquals(2.0, rotatedDoubles.elementAt(1+(2)), 0.001);
    }

    /**
     * Tests rotation with large numbers.
     */
    @Test
    void testLargeRotations() {
        int bigPositive = Integer.MAX_VALUE;
        int bigNegative = Integer.MIN_VALUE;

        MyListP19<String> rotated1 = list.rotate(bigPositive);
        MyListP19<String> rotated2 = list.rotate(bigNegative);

        // Both rotations should be equivalent to some rotation between 0 and list.length()
        assertEquals(5, rotated1.length());
        assertEquals(5, rotated2.length());
    }

    /**
     * Tests consecutive rotations.
     */
    @Test
    void testConsecutiveRotations() {
        MyListP19<String> rotated = list
                .rotate(2)    // rotate left by 2
                .rotate(-2);  // rotate right by 2

        // Should be back to original
        assertEquals(5, rotated.length());
        for (int i = 0; i < list.length(); i++) {
            assertEquals(list.elementAt(1+(i)), rotated.elementAt(1+(i)));
        }
    }
}
