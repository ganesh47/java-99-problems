package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListP03Test {

    @Test
    void testElementAtMiddle() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c", "d", "e");
        assertEquals("c", list.elementAt(3));
    }

    @Test
    void testElementAtFirst() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c");
        assertEquals("a", list.elementAt(1));
    }

    @Test
    void testElementAtLast() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c");
        assertEquals("c", list.elementAt(3));
    }

    @Test
    void testElementAtWithIntegers() {
        MyListP03<Integer> list = new MyListP03<>(1, 2, 3, 4, 5);
        assertEquals(4, list.elementAt(4));
    }

    @Test
    void testElementAtWithZeroPosition() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c");
        assertThrows(IllegalArgumentException.class, () -> list.elementAt(0));
    }

    @Test
    void testElementAtWithNegativePosition() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c");
        assertThrows(IllegalArgumentException.class, () -> list.elementAt(-1));
    }

    @Test
    void testElementAtWithTooLargePosition() {
        MyListP03<String> list = new MyListP03<>("a", "b", "c");
        assertThrows(IllegalArgumentException.class, () -> list.elementAt(4));
    }

    @Test
    void testElementAtWithEmptyList() {
        MyListP03<String> list = new MyListP03<>();
        assertThrows(IllegalArgumentException.class, () -> list.elementAt(1));
    }

    @Test
    void testElementAtWithNullValues() {
        MyListP03<String> list = new MyListP03<>("a", null, "c");
        assertThrows(NullPointerException.class,()->list.elementAt(2));
    }
}