package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BTreeP59Test {

    @Test
    void testHbalTreesHeight0() {
        List<BTreeP59.BTree59Node> trees = BTreeP59.hbalTrees(0);
        assertEquals(1, trees.size());
        assertNull(trees.getFirst());
    }

    @Test
    void testHbalTreesHeight1() {
        List<BTreeP59.BTree59Node> trees = BTreeP59.hbalTrees(1);
        assertEquals(1, trees.size());
        assertEquals("X", trees.getFirst().toString());
    }

    @Test
    void testHbalTreesHeight2() {
        List<BTreeP59.BTree59Node> trees = BTreeP59.hbalTrees(2);
        assertEquals(3, trees.size());
        assertTrue(trees.stream()
                .map(Object::toString)
                .allMatch(s -> s.equals("X(X,X)") || s.equals("X(X,NIL)") || s.equals("X(NIL,X)")));
    }

    @Test
    void testHbalTreesHeight3() {
        List<BTreeP59.BTree59Node> trees = BTreeP59.hbalTrees(3);
        assertFalse(trees.isEmpty());
        // All trees should be height-balanced
        assertTrue(trees.stream().allMatch(BTreeP59::isHeightBalanced));
        // All trees should have height exactly 3
        assertTrue(trees.stream().allMatch(tree -> BTreeP59.height(tree) == 3));
    }

    @Test
    void testHbalTreesNegativeHeight() {
        List<BTreeP59.BTree59Node> trees = BTreeP59.hbalTrees(-1);
        assertTrue(trees.isEmpty());
    }
}
