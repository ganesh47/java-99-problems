package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class BtreeP64Test {

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<BtreeP64> constructor = BtreeP64.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    assertNotNull(constructor.newInstance());
  }

  @Test
  void testPositionedNodeEquality() {
    var node1 = new BtreeP64.PositionedNode<>("a", 1, 1, null, null);
    var node2 = new BtreeP64.PositionedNode<>("a", 1, 1, null, null);
    var node3 = new BtreeP64.PositionedNode<>("b", 1, 1, null, null);
    var node4 = new BtreeP64.PositionedNode<>("a", 2, 1, null, null);
    var node5 = new BtreeP64.PositionedNode<>("a", 1, 2, null, null);

    assertEquals(node1, node1);
    assertEquals(node1, node2);
    assertEquals(node1.hashCode(), node2.hashCode());
    assertNotEquals(node1, node3);
    assertNotEquals(node1, node4);
    assertNotEquals(node1, node5);
    assertNotEquals(node1, null);
    assertNotEquals(node1, "string");

    var child = new BtreeP64.PositionedNode<>("c", 0, 0, null, null);
    var nodeWithLeft = new BtreeP64.PositionedNode<>("a", 1, 1, child, null);
    var nodeWithRight = new BtreeP64.PositionedNode<>("a", 1, 1, null, child);

    assertNotEquals(node1, nodeWithLeft);
    assertNotEquals(node1, nodeWithRight);
  }

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
