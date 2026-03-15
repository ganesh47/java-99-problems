package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class TruthP55Test {

  @Test
  void testCbalTree4() {
    List<BtreeP61.Node<String>> trees = TruthP55.cbalTree(4);
    assertEquals(4, trees.size());
    for (BtreeP61.Node<String> tree : trees) {
      assertEquals(4, tree.nodeCount());
      assertTrue(isCompletelyBalanced(tree));
    }
  }

  @Test
  void testCbalTree0() {
    List<BtreeP61.Node<String>> trees = TruthP55.cbalTree(0);
    assertEquals(1, trees.size());
    assertNull(trees.getFirst());
  }

  @Test
  void testCbalTree1() {
    List<BtreeP61.Node<String>> trees = TruthP55.cbalTree(1);
    assertEquals(1, trees.size());
    assertEquals("x", trees.getFirst().getValue());
  }

  @Test
  void testCbalTree2() {
    List<BtreeP61.Node<String>> trees = TruthP55.cbalTree(2);
    assertEquals(2, trees.size());
    for (BtreeP61.Node<String> tree : trees) {
      assertEquals(2, tree.nodeCount());
    }
  }

  @Test
  void testCbalTree3() {
    List<BtreeP61.Node<String>> trees = TruthP55.cbalTree(3);
    assertEquals(1, trees.size());
    BtreeP61.Node<String> tree = trees.getFirst();
    assertEquals(3, tree.nodeCount());
    assertNotNull(tree.getLeft());
    assertNotNull(tree.getRight());
  }

  private boolean isCompletelyBalanced(BtreeP61.Node<String> node) {
    if (node == null) {
      return true;
    }
    int leftNodes = (node.getLeft() == null) ? 0 : node.getLeft().nodeCount();
    int rightNodes = (node.getRight() == null) ? 0 : node.getRight().nodeCount();
    return Math.abs(leftNodes - rightNodes) <= 1
        && isCompletelyBalanced(node.getLeft())
        && isCompletelyBalanced(node.getRight());
  }
}
