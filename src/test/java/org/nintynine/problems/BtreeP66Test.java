package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP66Test {

  @Test
  void emptyTreeReturnsNull() {
    assertNull(BtreeP66.layoutBinaryTree(null));
  }

  @Test
  void positionsSymmetricTree() {
    var tree =
        BtreeP61.Node.of(
            "a",
            BtreeP61.Node.of(
                "b",
                BtreeP61.Node.leaf("d"),
                BtreeP61.Node.leaf("e")),
            BtreeP61.Node.of(
                "c",
                BtreeP61.Node.leaf("f"),
                null));

    var positioned = BtreeP66.layoutBinaryTree(tree);

    assertNode(positioned, "a", 4, 1);
    assertNode(positioned.getLeft(), "b", 2, 2);
    assertNode(positioned.getRight(), "c", 6, 2);
    assertNode(positioned.getLeft().getLeft(), "d", 1, 3);
    assertNode(positioned.getLeft().getRight(), "e", 3, 3);
    assertNode(positioned.getRight().getLeft(), "f", 5, 3);
  }

  @Test
  void inorderSequenceProducesStrictlyIncreasingX() {
    var tree =
        BtreeP61.Node.of(
            "root",
            BtreeP61.Node.of(
                "left",
                BtreeP61.Node.of(
                    "left.left",
                    null,
                    BtreeP61.Node.leaf("left.left.right")),
                BtreeP61.Node.leaf("left.right")),
            BtreeP61.Node.of(
                "right",
                null,
                BtreeP61.Node.of(
                    "right.right",
                    BtreeP61.Node.leaf("right.right.left"),
                    BtreeP61.Node.leaf("right.right.right"))));

    var positioned = BtreeP66.layoutBinaryTree(tree);
    List<Integer> xs = new ArrayList<>();
    collectInorderX(positioned, xs);
    for (int i = 1; i < xs.size(); i++) {
      assertTrue(xs.get(i - 1) <= xs.get(i), "inorder x positions must be non-decreasing");
    }
  }

  @Test
  void layoutIsNoWiderThanMethodTwo() {
    var tree =
        BtreeP61.Node.of(
            "a",
            BtreeP61.Node.of(
                "b",
                BtreeP61.Node.of(
                    "d",
                    null,
                    BtreeP61.Node.leaf("g")),
                BtreeP61.Node.leaf("e")),
            BtreeP61.Node.of(
                "c",
                null,
                BtreeP61.Node.of(
                    "f",
                    BtreeP61.Node.leaf("h"),
                    BtreeP61.Node.leaf("i"))));

    var compact = BtreeP66.layoutBinaryTree(tree);
    var spaced = BtreeP65.layoutBinaryTree(tree);

    assertTrue(width(compact) <= width(spaced));
  }

  private static int width(BtreeP64.PositionedNode<String> node) {
    List<Integer> xs = new ArrayList<>();
    collectX(node, xs);
    return xs.stream().max(Integer::compareTo).orElse(0)
        - xs.stream().min(Integer::compareTo).orElse(0);
  }

  private static void collectX(BtreeP64.PositionedNode<String> node, List<Integer> xs) {
    if (node == null) {
      return;
    }
    xs.add(node.getX());
    collectX(node.getLeft(), xs);
    collectX(node.getRight(), xs);
  }

  private static void collectInorderX(BtreeP64.PositionedNode<String> node, List<Integer> xs) {
    if (node == null) {
      return;
    }
    collectInorderX(node.getLeft(), xs);
    xs.add(node.getX());
    collectInorderX(node.getRight(), xs);
  }

  private static void assertNode(
      BtreeP64.PositionedNode<String> node, String value, int x, int y) {
    assertEquals(value, node.getValue());
    assertEquals(x, node.getX());
    assertEquals(y, node.getY());
  }
}
