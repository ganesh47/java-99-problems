package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class BtreeP65Test {

  @Test
  void emptyTreeYieldsNull() {
    assertNull(BtreeP65.layoutBinaryTree(null));
  }

  @Test
  void positionsSingleNodeAtRoot() {
    var positioned = BtreeP65.layoutBinaryTree(BtreeP61.Node.leaf("root"));
    assertEquals(1, positioned.getX());
    assertEquals(1, positioned.getY());
  }

  @Test
  void symmetricSpacingPerLevel() {
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

    var positioned = BtreeP65.layoutBinaryTree(tree);

    // tree height is 3 -> root x = 4, level spacing: depth2 offset=2, depth3 offset=1
    assertNode(positioned, "a", 4, 1);
    assertNode(positioned.getLeft(), "b", 2, 2);
    assertNode(positioned.getRight(), "c", 6, 2);
    assertNode(positioned.getLeft().getLeft(), "d", 1, 3);
    assertNode(positioned.getLeft().getRight(), "e", 3, 3);
    assertNode(positioned.getRight().getLeft(), "f", 5, 3);
  }

  private static void assertNode(
      BtreeP64.PositionedNode<String> node, String value, int x, int y) {
    assertEquals(value, node.getValue());
    assertEquals(x, node.getX());
    assertEquals(y, node.getY());
  }
}
