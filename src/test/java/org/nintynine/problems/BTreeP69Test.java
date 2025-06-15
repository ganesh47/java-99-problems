package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeP69Test {

    @Test
    void testDotstringExample() {
        String ds = "ABD..E..C.FG...";
        BTreeP69.Node tree = BTreeP69.tree(ds);
        assertEquals(ds, BTreeP69.dotstring(tree));
    }

    @Test
    void testSingleNode() {
        String ds = "A..";
        BTreeP69.Node tree = BTreeP69.tree(ds);
        assertEquals('A', tree.value);
        assertNull(tree.left);
        assertNull(tree.right);
        assertEquals(ds, BTreeP69.dotstring(tree));
    }

    @Test
    void testNullTree() {
        assertNull(BTreeP69.tree("."));
        assertEquals(".", BTreeP69.dotstring(null));
    }

    @Test
    void testInvalidDotstring() {
        assertThrows(IllegalArgumentException.class, () -> BTreeP69.tree("A."));
        assertThrows(IllegalArgumentException.class, () -> BTreeP69.tree("A..."));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> BTreeP69.tree(null));
    }

    @Test
    void testIncompleteDotstring() {
        assertThrows(IllegalArgumentException.class, () -> BTreeP69.tree("A"));
    }

    @Test
    void testExtraCharacters() {
        assertThrows(IllegalArgumentException.class, () -> BTreeP69.tree("A..B"));
    }

    @Test
    void testToStringRepresentation() {
        BTreeP69.Node tree = BTreeP69.tree("ABD..E..C.FG...");
        assertEquals("A(B(D,E),C(NIL,F(G,NIL)))", tree.toString());
    }
}
