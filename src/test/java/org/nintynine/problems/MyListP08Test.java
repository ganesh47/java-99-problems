package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyListP08Test {
    @Test
    void testBasicCompression() {
        MyListP08<String> list = new MyListP08<>("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e");
        MyListP08<String> compressed = list.compress();

        assertArrayEquals(
                new String[]{"a", "b", "c", "a", "d", "e"},
                Arrays.stream(compressed.items).toArray()
        );
    }

    @Test
    void testNoConsecutiveDuplicates() {
        MyListP08<String> list = new MyListP08<>("a", "b", "c", "d");
        MyListP08<String> compressed = list.compress();

        assertArrayEquals(
                new String[]{"a", "b", "c", "d"},
                Arrays.stream(compressed.items).toArray()
        );
    }

    @Test
    void testAllDuplicates() {
        MyListP08<String> list = new MyListP08<>("a", "a", "a", "a");
        MyListP08<String> compressed = list.compress();

        assertArrayEquals(
                new String[]{"a"},
                Arrays.stream(compressed.items).toArray()
        );
    }

    @Test
    void testEmptyList() {
        MyListP08<String> list = new MyListP08<>();
        MyListP08<String> compressed = list.compress();

        assertEquals(0, compressed.length());
    }

    @Test
    void testSingleElement() {
        MyListP08<String> list = new MyListP08<>("a");
        MyListP08<String> compressed = list.compress();

        assertArrayEquals(
                new String[]{"a"},
                Arrays.stream(compressed.items).toArray()
        );
    }

    @Test
    void testWithNulls() {
        MyListP08<String> list = new MyListP08<>(null, null, "a", "a", null, "b", null, null);
        assertThrows(NullPointerException.class, list::compress);


    }

    @Test
    void testWithNumbers() {
        MyListP08<Integer> list = new MyListP08<>(1, 1, 1, 2, 3, 3, 4, 4, 4, 4);
        MyListP08<Integer> compressed = list.compress();

        assertArrayEquals(
                new Integer[]{1, 2, 3, 4},
                Arrays.stream(compressed.items).toArray()
        );
    }

    @Test
    void testAlternatingElements() {
        MyListP08<String> list = new MyListP08<>("a", "b", "a", "b", "a", "b");
        MyListP08<String> compressed = list.compress();

        assertArrayEquals(
                new String[]{"a", "b", "a", "b", "a", "b"},
                Arrays.stream(compressed.items).toArray()
        );
    }

}