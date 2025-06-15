package org.nintynine.problems;

import org.nintynine.problems.PBTreeP67; // Added import
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // assertEquals is covered by this

public class PBTreeP67Test {

    @Test
    void testTreeToString_singleNode() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        assertEquals("a", tree.treeToString());
    }

    @Test
    void testTreeToString_leftNull() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.setRight(new PBTreeP67<>("c")); // Use setter
        assertEquals("a(,c)", tree.treeToString());
    }

    @Test
    void testTreeToString_rightNull() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.setLeft(new PBTreeP67<>("b")); // Use setter
        assertEquals("a(b,)", tree.treeToString());
    }

    @Test
    void testTreeToString_bothChildren() {
        PBTreeP67<String> tree = new PBTreeP67<>("a");
        tree.setLeft(new PBTreeP67<>("b"));
        tree.setRight(new PBTreeP67<>("c"));
        assertEquals("a(b,c)", tree.treeToString());
    }

    @Test
    void testTreeToString_complexTree() {
        PBTreeP67<String> root = new PBTreeP67<>("a");
        root.setLeft(new PBTreeP67<>("b"));
        root.getLeft().setLeft(new PBTreeP67<>("d"));
        root.getLeft().setRight(new PBTreeP67<>("e"));
        root.setRight(new PBTreeP67<>("c"));
        root.getRight().setRight(new PBTreeP67<>("f"));
        root.getRight().getRight().setLeft(new PBTreeP67<>("g"));
        assertEquals("a(b(d,e),c(,f(g,)))", root.treeToString());
    }

    @Test
    void testTreeToString_nodeWithNullValue() {
        PBTreeP67<String> tree = new PBTreeP67<>(null);
        assertEquals("null", tree.treeToString());

        PBTreeP67<String> tree2 = new PBTreeP67<>("a");
        tree2.setLeft(new PBTreeP67<>("b"));
        tree2.setRight(new PBTreeP67<>(null));
        assertEquals("a(b,null)", tree2.treeToString());
    }

    // Tests for stringToTree
    @Test
    void testStringToTree_nullOrEmpty() {
        assertNull(PBTreeP67.stringToTree(null));
        assertNull(PBTreeP67.stringToTree(""));
    }

    @Test
    void testStringToTree_singleNode() {
        PBTreeP67<String> tree = PBTreeP67.stringToTree("a");
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNull(tree.getLeft());
        assertNull(tree.getRight());
    }

    @Test
    void testStringToTree_leftNull() {
        PBTreeP67<String> tree = PBTreeP67.stringToTree("a(,c)");
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNull(tree.getLeft());
        assertNotNull(tree.getRight());
        assertEquals("c", tree.getRight().getValue());
    }

    @Test
    void testStringToTree_rightNull() {
        PBTreeP67<String> tree = PBTreeP67.stringToTree("a(b,)");
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNotNull(tree.getLeft());
        assertEquals("b", tree.getLeft().getValue());
        assertNull(tree.getRight());
    }

    @Test
    void testStringToTree_bothChildren() {
        PBTreeP67<String> tree = PBTreeP67.stringToTree("a(b,c)");
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNotNull(tree.getLeft());
        assertEquals("b", tree.getLeft().getValue());
        assertNotNull(tree.getRight());
        assertEquals("c", tree.getRight().getValue());
    }

    @Test
    void testStringToTree_complexTree() {
        String s = "a(b(d,e),c(,f(g,)))";
        PBTreeP67<String> tree = PBTreeP67.stringToTree(s);
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNotNull(tree.getLeft());
        assertEquals("b", tree.getLeft().getValue());
        assertNotNull(tree.getLeft().getLeft());
        assertEquals("d", tree.getLeft().getLeft().getValue());
        assertNotNull(tree.getLeft().getRight());
        assertEquals("e", tree.getLeft().getRight().getValue());
        assertNotNull(tree.getRight());
        assertEquals("c", tree.getRight().getValue());
        assertNull(tree.getRight().getLeft());
        assertNotNull(tree.getRight().getRight());
        assertEquals("f", tree.getRight().getRight().getValue());
        assertNotNull(tree.getRight().getRight().getLeft());
        assertEquals("g", tree.getRight().getRight().getLeft().getValue());
        assertNull(tree.getRight().getRight().getRight());
        assertEquals(s, tree.treeToString()); // Verify with treeToString
    }

    @Test
    void testStringToTree_nodeWithNullStringValue() {
        PBTreeP67<String> tree = PBTreeP67.stringToTree("null");
        assertNotNull(tree);
        assertEquals("null", tree.getValue());
        assertNull(tree.getLeft()); // Added missing assertions for single "null" node
        assertNull(tree.getRight());// Added missing assertions for single "null" node

        String s2 = "a(b,null)";
        PBTreeP67<String> tree2 = PBTreeP67.stringToTree(s2);
        assertNotNull(tree2);
        assertEquals("a", tree2.getValue());
        assertNotNull(tree2.getLeft());
        assertEquals("b", tree2.getLeft().getValue());
        assertNotNull(tree2.getRight());
        assertEquals("null", tree2.getRight().getValue());
        assertEquals(s2, tree2.treeToString());
    }

    @Test
    void testStringToTree_singleChildNoCommaAmbiguity() {
        // Test current behavior for "a(b)" - assumes left child, right null based on current stringToTree impl.
        PBTreeP67<String> tree = PBTreeP67.stringToTree("a(b)");
        assertNotNull(tree);
        assertEquals("a", tree.getValue());
        assertNotNull(tree.getLeft());
        assertEquals("b", tree.getLeft().getValue());
        assertNull(tree.getRight());
        assertEquals("a(b,)", tree.treeToString()); // Check its string representation
    }
}
