package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class BTree54Test {

    @Test
    @DisplayName("Test single node expressions")
    void testSingleNode() {
        assertTrue(BTree54.isTree("a"));
        assertTrue(BTree54.isTree("x"));
        assertTrue(BTree54.isTree("(a nil nil)"));
    }

    @Test
    @DisplayName("Test complex valid expressions")
    void testValidComplexExpressions() {
        assertTrue(BTree54.isTree("(a (b nil nil) nil)"));
        assertTrue(BTree54.isTree("(a nil (b nil nil))"));
        assertTrue(BTree54.isTree("(a (b nil nil) (c nil nil))"));
        assertTrue(BTree54.isTree("(a (b (c nil nil) nil) nil)"));
    }

    @ParameterizedTest
    @DisplayName("Test invalid expressions")
    @ValueSource(strings = {
            "",
            "nil",
            "()",
            "(a)",
            "(a nil)",
            "(a b c)",
            "(a (b nil nil)",
            "a nil nil",
            "(a (nil nil) nil)",
            "(a (b) nil)"
    })
    void testInvalidExpressions(String expression) {
        assertFalse(BTree54.isTree(expression));
    }

    @Test
    @DisplayName("Test tree parsing and reconstruction")
    void testTreeParsing() {
        String expr = "(a (b nil nil) (c nil nil))";
        BTree54.BTree54Node tree = BTree54.parseTree(expr);
        assertEquals(expr, tree.toString());
    }

    @Test
    @DisplayName("Test null and empty inputs")
    void testNullAndEmpty() {
        assertFalse(BTree54.isTree(null));
        assertFalse(BTree54.isTree(""));
        assertFalse(BTree54.isTree("  "));
    }

    @Test
    @DisplayName("Test node creation and equality")
    void testNodeCreation() {
        BTree54.BTree54Node leaf1 = new BTree54.BTree54Node("x");
        BTree54.BTree54Node leaf2 = new BTree54.BTree54Node("x");
        BTree54.BTree54Node bTree54Node1 = new BTree54.BTree54Node("a", leaf1, null);
        BTree54.BTree54Node bTree54Node = new BTree54.BTree54Node("a", leaf2, null);

        assertEquals(leaf1, leaf2);
        assertEquals(bTree54Node1, bTree54Node);
        assertNotEquals(leaf1, bTree54Node1);
    }

    @Test
    @DisplayName("Test node construction with null value")
    void testNodeNullValue() {
        assertThrows(NullPointerException.class,
                () -> new BTree54.BTree54Node(null));
    }

    @Test
    @DisplayName("Test complex tree structure")
    void testComplexTree() {
        String complexExpr = "(root (left (ll nil nil) (lr nil nil)) (right nil (rr nil nil)))";
        assertTrue(BTree54.isTree(complexExpr));

        BTree54.BTree54Node tree = BTree54.parseTree(complexExpr);
        assertEquals(complexExpr, tree.toString());
    }

    @Test
    @DisplayName("Test invalid characters in values")
    void testInvalidCharacters() {
        assertFalse(BTree54.isTree("(a(b nil nil) nil)"));
        assertFalse(BTree54.isTree("(a) (b nil nil) nil)"));
        assertFalse(BTree54.isTree("(a,b nil nil)"));
    }
}
