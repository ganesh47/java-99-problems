package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class BtreeP64Test {

  @Test
  void returnsNullForEmptyTree() {
    assertNull(BtreeP64.layoutBinaryTree(null));
  }

  @Test
  void singleNodePositionedAtOriginDepthOne() {
    var input = BtreeP61.Node.leaf("root");
    var positioned = BtreeP64.layoutBinaryTree(input);

    assertEquals("root", positioned.getValue());
    assertEquals(1, positioned.getX());
    assertEquals(1, positioned.getY());
    assertNull(positioned.getLeft());
    assertNull(positioned.getRight());
  }

  @Test
  void layoutsTreeAccordingToInorder() {
    var tree =
        BtreeP61.Node.of(
            "root",
            BtreeP61.Node.of(
                "left",
                BtreeP61.Node.leaf("left.left"),
                BtreeP61.Node.leaf("left.right")),
            BtreeP61.Node.leaf("right"));

    var positioned = BtreeP64.layoutBinaryTree(tree);

    // inorder: left.left (x=1), left (2), left.right (3), root (4), right (5)
    assertNode(positioned.getLeft().getLeft(), "left.left", 1, 3);
    assertNode(positioned.getLeft(), "left", 2, 2);
    assertNode(positioned.getLeft().getRight(), "left.right", 3, 3);
    assertNode(positioned, "root", 4, 1);
    assertNode(positioned.getRight(), "right", 5, 2);
  }

  private static void assertNode(
      BtreeP64.PositionedNode<String> node, String value, int x, int y) {
    assertEquals(value, node.getValue());
    assertEquals(x, node.getX());
    assertEquals(y, node.getY());
  }
}
