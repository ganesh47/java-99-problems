package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PBTreeP67Test {

    @Test
    void testTreeToString_singleNode() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        assertEquals("a", tree.treeToString());
    }

    @Test
    void testTreeToString_leftNull() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.right = new PBTreeP67<>("c");
        assertEquals("a(,c)", tree.treeToString());
    }

    @Test
    void testTreeToString_rightNull() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.left = new PBTreeP67<>("b");
        assertEquals("a(b,)", tree.treeToString());
    }

    @Test
    void testTreeToString_bothChildren() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.left = new PBTreeP67<>("b");
        tree.right = new PBTreeP67<>("c");
        assertEquals("a(b,c)", tree.treeToString());
    }

    @Test
    void testTreeToString_complexTree() {
        PBTreeP67<String> root = new PBTreeP67<>("a");
        root.left = new PBTreeP67<>("b");
        root.left.left = new PBTreeP67<>("d");
        root.left.right = new PBTreeP67<>("e");
        root.right = new PBTreeP67<>("c");
        root.right.right = new PBTreeP67<>("f");
        root.right.right.left = new PBTreeP67<>("g");
        // Expected: "a(b(d,e),c(,f(g,)))"
        // c's left child is null, f's right child is null
        assertEquals("a(b(d,e),c(,f(g,)))", root.treeToString());
    }

    @Test
    void testTreeToString_nodeWithNullValue() {
        PBTreeP67<String> tree = new PBTreeP67<>(null);
        assertEquals("null", tree.treeToString());

        PBTreeP67<String> tree2 = new PBTreeP67<>("a");
        tree2.left = new PBTreeP67<>("b");
        tree2.right = new PBTreeP67<>(null);
        assertEquals("a(b,null)", tree2.treeToString());
    }
}
