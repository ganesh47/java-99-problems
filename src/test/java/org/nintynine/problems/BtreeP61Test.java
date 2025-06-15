package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BtreeP61Test {

  @Test
  void testEmptyTree() {
    assertNull(null);
    assertEquals(0, BtreeP61.countLeaves(null));
  }

  @Test
  void testSingleNode() {
    BtreeP61<String> tree = new BtreeP61<>("a");
    assertEquals(1, BtreeP61.countLeaves(tree));
  }

  @Test
  void testOneChild() {
    BtreeP61<String> tree = new BtreeP61<>("a");
    tree.left = new BtreeP61<>("b");
    assertEquals(1, BtreeP61.countLeaves(tree));
  }

  @Test
  void testTwoChildren() {
    BtreeP61<String> tree = new BtreeP61<>("a");
    tree.left = new BtreeP61<>("b");
    tree.right = new BtreeP61<>("c");
    assertEquals(2, BtreeP61.countLeaves(tree));
  }

  @Test
  void testComplexTree() {
    BtreeP61<String> tree = new BtreeP61<>("a");
    tree.left = new BtreeP61<>("b");
    tree.right = new BtreeP61<>("c");
    tree.left.left = new BtreeP61<>("d");
    tree.left.right = new BtreeP61<>("e");
    tree.right.right = new BtreeP61<>("f");

    assertEquals(3, BtreeP61.countLeaves(tree));
  }

  @Test
  void testFullBinaryTree() {
    BtreeP61<String> tree = new BtreeP61<>("a");
    tree.left = new BtreeP61<>("b");
    tree.right = new BtreeP61<>("c");
    tree.left.left = new BtreeP61<>("d");
    tree.left.right = new BtreeP61<>("e");
    tree.right.left = new BtreeP61<>("f");
    tree.right.right = new BtreeP61<>("g");

    assertEquals(4, BtreeP61.countLeaves(tree));
  }
}
