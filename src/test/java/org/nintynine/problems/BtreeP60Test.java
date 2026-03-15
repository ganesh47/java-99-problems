package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP60Test {

  @Test
  void testMinNodes() {
    assertEquals(0, BtreeP60.minNodes(0));
    assertEquals(1, BtreeP60.minNodes(1));
    assertEquals(2, BtreeP60.minNodes(2));
    assertEquals(4, BtreeP60.minNodes(3));
    assertEquals(7, BtreeP60.minNodes(4));
  }

  @Test
  void testMaxHeight() {
    assertEquals(0, BtreeP60.maxHeight(0));
    assertEquals(1, BtreeP60.maxHeight(1));
    assertEquals(2, BtreeP60.maxHeight(2));
    assertEquals(2, BtreeP60.maxHeight(3));
    assertEquals(3, BtreeP60.maxHeight(4));
  }

  @Test
  void testHbalTreeNodesEmpty() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(0);
    assertTrue(trees.isEmpty());
  }

  @Test
  void testHbalTreeNodesSingleNode() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(1);
    assertEquals(1, trees.size());
    assertEquals("x", trees.getFirst().getValue());
    assertNull(trees.getFirst().getLeft());
    assertNull(trees.getFirst().getRight());
  }

  @Test
  void testHbalTreeNodesTwoNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(2);
    assertEquals(2, trees.size());
  }

  @Test
  void testHbalTreeNodesThreeNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(3);
    assertEquals(1, trees.size());
  }

  @Test
  void testTreeHeight() {
    BtreeP61.Node<String> root = BtreeP61.Node.leaf("x");
    assertEquals(1, root.height());

    BtreeP61.Node<String> withLeft = BtreeP61.Node.of("x", BtreeP61.Node.leaf("x"), null);
    assertEquals(2, withLeft.height());

    BtreeP61.Node<String> full = BtreeP61.Node.of("x", BtreeP61.Node.leaf("x"), BtreeP61.Node.leaf("x"));
    assertEquals(2, full.height());
  }

  @Test
  void testHeightBalancedProperty() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(4);
    for (BtreeP61.Node<String> tree : trees) {
      assertTrue(tree.isHeightBalanced());
    }
  }

  @Test
  void testFifteenNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP60.hbalTreeNodes(15);
    assertFalse(trees.isEmpty());
    for (BtreeP61.Node<String> tree : trees) {
      assertTrue(tree.isHeightBalanced());
      assertEquals(15, tree.nodeCount());
    }
  }
}
