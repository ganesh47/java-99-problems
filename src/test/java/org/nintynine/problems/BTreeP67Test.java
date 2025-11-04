package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BTreeP67Test {

    @Test
    void rendersTreeToCompactString() {
        BTreeP67.Node<String> tree = BTreeP67.Node.of(
                "a",
                BTreeP67.Node.of(
                        "b",
                        BTreeP67.Node.leaf("d"),
                        BTreeP67.Node.leaf("e")),
                BTreeP67.Node.of(
                        "c",
                        null,
                        BTreeP67.Node.of(
                                "f",
                                BTreeP67.Node.leaf("g"),
                                null)));

        assertEquals("a(b(d,e),c(,f(g,)))", BTreeP67.toCompactString(tree));
    }

    @Test
    void parsesCompactStringToTree() {
        String representation = "a(b(d,e),c(,f(g,)))";

        BTreeP67.Node<String> expected = BTreeP67.Node.of(
                "a",
                BTreeP67.Node.of(
                        "b",
                        BTreeP67.Node.leaf("d"),
                        BTreeP67.Node.leaf("e")),
                BTreeP67.Node.of(
                        "c",
                        null,
                        BTreeP67.Node.of(
                                "f",
                                BTreeP67.Node.leaf("g"),
                                null)));

        assertEquals(expected, BTreeP67.parse(representation));
    }

    @Test
    void emptyRepresentationParsesToNullTree() {
        assertNull(BTreeP67.parse("   "));
    }

    @Test
    void handlesMissingChildren() {
        BTreeP67.Node<String> tree = BTreeP67.parse("root(,right)");
        assertEquals("root(,right)", BTreeP67.toCompactString(tree));
    }

    @Test
    void invalidInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> BTreeP67.parse("a(b"));
    }
}
