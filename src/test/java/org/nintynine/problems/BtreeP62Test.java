package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP62Test {

  @Test
  void emptyTreeHasNoInternals() {
    assertEquals(List.of(), BtreeP62.internals(null));
  }

  @Test
  void singleNodeHasNoInternals() {
    var tree = BtreeP61.Node.leaf("x");
    assertEquals(List.of(), BtreeP62.internals(tree));
  }

  @Test
  void collectsInternalNodesPreorder() {
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

    assertEquals(List.of("a", "b", "e", "c", "f"), BtreeP62.internals(tree));
  }
}
