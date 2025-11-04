package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BtreeP61Test {

  @Test
  void countsLeavesInEmptyTree() {
    assertEquals(0, BtreeP61.countLeaves(null));
  }

  @Test
  void countsSingleLeaf() {
    var tree = BtreeP61.Node.leaf("a");
    assertEquals(1, BtreeP61.countLeaves(tree));
  }

  @Test
  void countsLeanerTree() {
    var tree =
        BtreeP61.Node.of(
            "root",
            BtreeP61.Node.of(
                "left",
                BtreeP61.Node.leaf("left.left"),
                null),
            null);

    assertEquals(1, BtreeP61.countLeaves(tree));
  }

  @Test
  void countsBalancedTree() {
    var tree =
        BtreeP61.Node.of(
            "root",
            BtreeP61.Node.of(
                "left",
                BtreeP61.Node.leaf("left.left"),
                BtreeP61.Node.leaf("left.right")),
            BtreeP61.Node.of(
                "right",
                BtreeP61.Node.leaf("right.left"),
                BtreeP61.Node.leaf("right.right")));

    assertEquals(4, BtreeP61.countLeaves(tree));
  }

  @Test
  void countsAsymmetricTree() {
    var tree =
        BtreeP61.Node.of(
            "a",
            BtreeP61.Node.of(
                "b",
                BtreeP61.Node.leaf("d"),
                BtreeP61.Node.leaf("e")),
            BtreeP61.Node.of(
                "c",
                null,
                BtreeP61.Node.of(
                    "f",
                    BtreeP61.Node.leaf("g"),
                    null)));

    assertEquals(3, BtreeP61.countLeaves(tree));
  }
}
