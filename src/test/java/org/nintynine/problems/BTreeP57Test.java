package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BTreeP57Test {

    @Test
    void constructEmptyTree() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        assertTrue(tree.isSymmetric());
    }

    @Test
    void constructSymmetricTree() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {5, 3, 18, 1, 4, 12, 21};
        tree.construct(values);
        assertTrue(tree.isSymmetric());
    }

    @Test
    void constructAnotherSymmetricTree() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {3, 2, 5, 7, 1};
        tree.construct(values);
        assertTrue(tree.isSymmetric());
    }

    @Test
    void constructAsymmetricTree() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {3, 2, 5, 7};
        tree.construct(values);
        assertFalse(tree.isSymmetric());
    }

    @Test
    void verifyTreeStructure() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {3, 2, 5, 7, 1};
        tree.construct(values);
        String expected = "(3 (2 (1 nil nil) nil) (5 nil (7 nil nil)))";
        assertEquals(expected, tree.getStructure());
    }

    @Test
    void insertSingleValue() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        tree.insert(5);
        assertEquals("(5 nil nil)", tree.getStructure());
    }

    @Test
    void insertDuplicateValues() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {3, 3, 3};
        tree.construct(values);
        assertEquals("(3 nil nil)", tree.getStructure());
    }

    @Test
    void constructLargerSymmetricTree() {
        BTreeP57<Integer> tree = new BTreeP57<>();
        Integer[] values = {10, 5, 15, 3, 7, 13, 17, 1, 4, 6, 8, 12, 14, 16, 18};
        tree.construct(values);
        assertTrue(tree.isSymmetric());
    }
}
