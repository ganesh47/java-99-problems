package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BTreeP61Test {
    
    @Test
    void testEmptyTree() {
        assertNull(null);
        assertEquals(0, BTreeP61.countLeaves(null));
    }
    
    @Test
    void testSingleNode() {
        BTreeP61<String> tree = new BTreeP61<>("a");
        assertEquals(1, BTreeP61.countLeaves(tree));
    }
    
    @Test
    void testOneChild() {
        BTreeP61<String> tree = new BTreeP61<>("a");
        tree.left = new BTreeP61<>("b");
        assertEquals(1, BTreeP61.countLeaves(tree));
    }
    
    @Test
    void testTwoChildren() {
        BTreeP61<String> tree = new BTreeP61<>("a");
        tree.left = new BTreeP61<>("b");
        tree.right = new BTreeP61<>("c");
        assertEquals(2, BTreeP61.countLeaves(tree));
    }
    
    @Test
    void testComplexTree() {
        BTreeP61<String> tree = new BTreeP61<>("a");
        tree.left = new BTreeP61<>("b");
        tree.right = new BTreeP61<>("c");
        tree.left.left = new BTreeP61<>("d");
        tree.left.right = new BTreeP61<>("e");
        tree.right.right = new BTreeP61<>("f");
        
        assertEquals(3, BTreeP61.countLeaves(tree));
    }
    
    @Test
    void testFullBinaryTree() {
        BTreeP61<String> tree = new BTreeP61<>("a");
        tree.left = new BTreeP61<>("b");
        tree.right = new BTreeP61<>("c");
        tree.left.left = new BTreeP61<>("d");
        tree.left.right = new BTreeP61<>("e");
        tree.right.left = new BTreeP61<>("f");
        tree.right.right = new BTreeP61<>("g");
        
        assertEquals(4, BTreeP61.countLeaves(tree));
    }
}
