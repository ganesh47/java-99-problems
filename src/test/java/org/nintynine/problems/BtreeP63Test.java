package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP63Test {

  @Test
  void zeroNodesProducesEmptyTree() {
    assertNull(BtreeP63.completeBinaryTree(0));
  }

  @Test
  void singleNodeTree() {
    var tree = BtreeP63.completeBinaryTree(1);
    assertEquals(List.of("x"), BtreeP62B.levelOrder(tree));
  }

  @Test
  void constructsLeftAdjustedBottomLevel() {
    var tree = BtreeP63.completeBinaryTree(6);

    assertEquals(List.of("x"), BtreeP62B.atLevel(tree, 1));
    assertEquals(List.of("x", "x"), BtreeP62B.atLevel(tree, 2));
    assertEquals(List.of("x", "x", "x"), BtreeP62B.atLevel(tree, 3));
    assertEquals(List.of(), BtreeP62B.atLevel(tree, 4));

    assertEquals(
        List.of("x", "x", "x", "x", "x", "x"),
        BtreeP62B.levelOrder(tree));
  }

  @Test
  void handlesNonFullLastLevel() {
    var tree = BtreeP63.completeBinaryTree(10);
    assertEquals(List.of("x", "x", "x"), BtreeP62B.atLevel(tree, 4));
  }
}
