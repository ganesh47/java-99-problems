package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP61ATest {

  @Test
  void emptyTreeHasNoLeaves() {
    assertEquals(List.of(), BtreeP61A.leaves(null));
  }

  @Test
  void singleNodeIsLeaf() {
    var tree = BtreeP61.Node.leaf("x");
    assertEquals(List.of("x"), BtreeP61A.leaves(tree));
  }

  @Test
  void collectsLeavesLeftToRight() {
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

    assertEquals(List.of("d", "g", "h", "i"), BtreeP61A.leaves(tree));
  }
}
