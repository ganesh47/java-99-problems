package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BTreeP60Test {

  @Test
  void testMinNodes() {
    assertEquals(0, BTreeP60.minNodes(0));
    assertEquals(1, BTreeP60.minNodes(1));
    assertEquals(2, BTreeP60.minNodes(2));
    assertEquals(4, BTreeP60.minNodes(3));
    assertEquals(7, BTreeP60.minNodes(4));
  }

  @Test
  void testMaxHeight() {
    assertEquals(0, BTreeP60.maxHeight(0));
    assertEquals(1, BTreeP60.maxHeight(1));
    assertEquals(2, BTreeP60.maxHeight(2));
    assertEquals(2, BTreeP60.maxHeight(3));
    assertEquals(3, BTreeP60.maxHeight(4));
  }

  @Test
  void testHbalTreeNodesEmpty() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(0);
    assertTrue(trees.isEmpty());
  }

  @Test
  void testHbalTreeNodesSingleNode() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(1);
    assertEquals(1, trees.size());
    assertEquals("x", trees.getFirst().getValue());
    assertNull(trees.getFirst().getLeft());
    assertNull(trees.getFirst().getRight());
  }

  @Test
  void testHbalTreeNodesTwoNodes() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(2);
    assertEquals(2, trees.size()); // There should be 2 possible trees with 2 nodes
  }

  @Test
  void testHbalTreeNodesThreeNodes() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(3);
    assertEquals(1, trees.size()); // There should be 1 possible tree with 3 nodes
  }

  @Test
  void testTreeHeight() {
    BTreeP60<String> root = new BTreeP60<>("x");
    assertEquals(1, BTreeP60.getHeight(root));

    root.left = new BTreeP60<>("x");
    assertEquals(2, BTreeP60.getHeight(root));

    root.right = new BTreeP60<>("x");
    assertEquals(2, BTreeP60.getHeight(root));
  }

  @Test
  void testHeightBalancedProperty() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(4);
    for (BTreeP60<String> tree : trees) {
      assertTrue(isBalanced(tree));
    }
  }

  @Test
  void testFifteenNodes() {
    List<BTreeP60<String>> trees = BTreeP60.hbalTreeNodes(15);
    assertFalse(trees.isEmpty());
    // All trees should be height-balanced and have exactly 15 nodes
    for (BTreeP60<String> tree : trees) {
      assertTrue(isBalanced(tree));
      assertEquals(15, countNodes(tree));
    }
  }

  // Helper methods for testing
  private boolean isBalanced(BTreeP60<String> root) {
    if (root == null) return true;

    int leftHeight = BTreeP60.getHeight(root.getLeft());
    int rightHeight = BTreeP60.getHeight(root.getRight());

    return Math.abs(leftHeight - rightHeight) <= 1
        && isBalanced(root.getLeft())
        && isBalanced(root.getRight());
  }

  private int countNodes(BTreeP60<String> root) {
    if (root == null) return 0;
    return 1 + countNodes(root.getLeft()) + countNodes(root.getRight());
  }
}
