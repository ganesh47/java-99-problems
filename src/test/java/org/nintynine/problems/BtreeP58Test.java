package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP58Test {

  @Test
  void testSymHbalTreesWithOneNode() {
    List<BtreeP61.Node<String>> trees = BtreeP58.symHbalTrees(1);
    assertEquals(1, trees.size());
    assertEquals("x", trees.getFirst().getValue());
  }

  @Test
  void testSymHbalTreesWithThreeNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP58.symHbalTrees(3);
    assertEquals(1, trees.size());
    assertTrue(BtreeP61.isSymmetric(trees.getFirst()));
  }

  @Test
  void testSymHbalTreesWithEvenNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP58.symHbalTrees(2);
    assertEquals(0, trees.size());
  }

  @Test
  void testSymHbalTreesWithFiveNodes() {
    List<BtreeP61.Node<String>> trees = BtreeP58.symHbalTrees(5);
    // There are 2 symmetric completely balanced trees with 5 nodes.
    // hbalTreeNodes(5) includes these and potentially more if height allows.
    assertFalse(trees.isEmpty());
    for (BtreeP61.Node<String> tree : trees) {
      assertTrue(BtreeP61.isSymmetric(tree));
      assertEquals(5, tree.nodeCount());
    }
  }
}
