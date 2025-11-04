package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP62BTest {

  @Test
  void levelOneReturnsRoot() {
    var tree = BtreeP61.Node.of("root", BtreeP61.Node.leaf("left"), BtreeP61.Node.leaf("right"));
    assertEquals(List.of("root"), BtreeP62B.atLevel(tree, 1));
  }

  @Test
  void returnsNodesAtRequestedLevel() {
    var tree =
        BtreeP61.Node.of(
            "a",
            BtreeP61.Node.of(
                "b",
                BtreeP61.Node.leaf("d"),
                BtreeP61.Node.of(
                    "e",
                    BtreeP61.Node.leaf("g"),
                    BtreeP61.Node.leaf("h"))),
            BtreeP61.Node.of(
                "c",
                null,
                BtreeP61.Node.of(
                    "f",
                    BtreeP61.Node.leaf("i"),
                    null)));

    assertEquals(List.of("b", "c"), BtreeP62B.atLevel(tree, 2));
    assertEquals(List.of("d", "e", "f"), BtreeP62B.atLevel(tree, 3));
    assertEquals(List.of("g", "h", "i"), BtreeP62B.atLevel(tree, 4));
  }

  @Test
  void invalidLevelReturnsEmpty() {
    var tree = BtreeP61.Node.leaf("x");
    assertEquals(List.of(), BtreeP62B.atLevel(tree, 0));
    assertEquals(List.of(), BtreeP62B.atLevel(tree, -1));
    assertEquals(List.of(), BtreeP62B.atLevel(null, 1));
  }

  @Test
  void levelOrderUsesAtLevel() {
    var tree =
        BtreeP61.Node.of(
            "root",
            BtreeP61.Node.of(
                "left",
                BtreeP61.Node.leaf("left.left"),
                null),
            BtreeP61.Node.leaf("right"));

    assertEquals(
        List.of("root", "left", "right", "left.left"),
        BtreeP62B.levelOrder(tree));
  }
}
