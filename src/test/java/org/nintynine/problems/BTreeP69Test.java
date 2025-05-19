package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeP69Test {

    @Test
    void testDotstringExampleRoundTrip() {
        String dot = "ABD..E..C.FG...";
        BTreeP69.BTreeP69Node tree = BTreeP69.tree(dot);
        assertEquals(dot, BTreeP69.dotstring(tree));
    }

    @Test
    void testTreeRoundTrip() {
        BTreeP69.BTreeP69Node root = new BTreeP69.BTreeP69Node('a');
        root.left = new BTreeP69.BTreeP69Node('b');
        root.right = new BTreeP69.BTreeP69Node('c');
        root.left.right = new BTreeP69.BTreeP69Node('d');

        String dot = BTreeP69.dotstring(root);
        BTreeP69.BTreeP69Node parsed = BTreeP69.tree(dot);
        assertEquals(root, parsed);
    }

    @Test
    void testEmptyTree() {
        assertNull(BTreeP69.tree(""));
        assertEquals(".", BTreeP69.dotstring(null));
    }

    @Test
    void testParseSingleDot() {
        assertNull(BTreeP69.tree("."));
    }

    @Test
    void testSingleNodeDotstring() {
        BTreeP69.BTreeP69Node node = new BTreeP69.BTreeP69Node('x');
        assertEquals("x..", BTreeP69.dotstring(node));
    }

    @Test
    void testToStringForLeafAndInternalNodes() {
        BTreeP69.BTreeP69Node root = new BTreeP69.BTreeP69Node('r');
        BTreeP69.BTreeP69Node left = new BTreeP69.BTreeP69Node('l');
        root.left = left;

        // Leaf
        assertEquals("l", left.toString());
        // Internal node representation
        assertEquals("r(l,NIL)", root.toString());
    }

    @Test
    void testUppercaseCharactersAreAccepted() {
        String dot = "A..";
        BTreeP69.BTreeP69Node tree = BTreeP69.tree(dot);
        assertEquals(dot, BTreeP69.dotstring(tree));
    }
}
