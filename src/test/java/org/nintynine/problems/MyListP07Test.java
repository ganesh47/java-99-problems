package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListP07Test {

    @Test
    void testDeepNesting() {
        // Create a deeply nested structure
        MyListP07<Object> level5 = new MyListP07<>("x");
        MyListP07<Object> level4 = new MyListP07<>(level5, "y");
        MyListP07<Object> level3 = new MyListP07<>(level4, "z");
        MyListP07<Object> level2 = new MyListP07<>(level3, "a");
        MyListP07<Object> level1 = new MyListP07<>(level2, "b");

        MyListP07<Object> flattened = level1.flatten();

        // Should be [x, y, z, a, b]
        assertArrayEquals(
                new String[]{"x", "y", "z", "a", "b"},
                Arrays.stream(flattened.items).map(Object::toString).toArray()
        );
    }

    @Test
    void testMixedCollectionTypes() {
        List<Object> javaList = Arrays.asList("p", "q");
        Object[] array = new Object[]{"r", "s"};
        MyListP07<Object> myList = new MyListP07<>("t", "u");

        MyListP07<Object> mixed = new MyListP07<>(javaList, array, myList);
        MyListP07<Object> flattened = mixed.flatten();

        // Should be [p, q, r, s, t, u]
        assertArrayEquals(
                new String[]{"p", "q", "r", "s", "t", "u"},
                Arrays.stream(flattened.items).map(Object::toString).toArray()
        );
    }

    @Test
    void testComplexNesting() {
        List<Object> innerJavaList = Arrays.asList(1, Arrays.asList(2, 3));
        Object[] innerArray = new Object[]{4, new Object[]{5, 6}};
        MyListP07<Object> innerMyList = new MyListP07<>(7, new MyListP07<>(8, 9));

        MyListP07<Object> complex = new MyListP07<>(innerJavaList, innerArray, innerMyList);
        MyListP07<Object> flattened = complex.flatten();

        // Should be [1, 2, 3, 4, 5, 6, 7, 8, 9]
        assertArrayEquals(
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                Arrays.stream(flattened.items).map(o -> (Integer) o).toArray()
        );
    }

    @Test
    void testWithNulls() {
        List<Object> listWithNull = Arrays.asList(null, 1);
        Object[] arrayWithNull = new Object[]{2, null};
        MyListP07<Object> myListWithNull = new MyListP07<>(3, null);

        MyListP07<Object> mixed = new MyListP07<>(listWithNull, arrayWithNull, myListWithNull);
        MyListP07<Object> flattened = mixed.flatten();

        // Should be [null, 1, 2, null, 3, null]
        assertEquals(6, flattened.length());
        assertThrows(NullPointerException.class,()->flattened.elementAt(1));
        assertEquals(1, flattened.elementAt(1+1));
        assertEquals(2, flattened.elementAt(2+1));
        assertThrows(NullPointerException.class,()->flattened.elementAt(3+1));
        assertEquals(3, flattened.elementAt(4+1));
        assertThrows(NullPointerException.class,()->flattened.elementAt(5+1));
    }

    @Test
    void testEmptyStructures() {
        List<Object> emptyList = Collections.emptyList();
        Object[] emptyArray = new Object[0];
        MyListP07<Object> emptyMyList = new MyListP07<>();

        MyListP07<Object> mixed = new MyListP07<>(emptyList, emptyArray, emptyMyList);
        MyListP07<Object> flattened = mixed.flatten();

        assertEquals(0, flattened.length());
    }

}