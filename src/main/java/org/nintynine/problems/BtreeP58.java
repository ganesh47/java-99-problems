package org.nintynine.problems;

import java.util.List;
import java.util.stream.Collectors;

/**
 * P58: Generate-and-test paradigm for symmetric balanced trees.
 */
public final class BtreeP58 {

  private BtreeP58() {
    // utility class
  }

  /**
   * Generates all symmetric height-balanced binary trees with n nodes.
   *
   * @param n number of nodes
   * @return list of symmetric height-balanced trees
   */
  public static List<BtreeP61.Node<String>> symHbalTrees(int n) {
    return BtreeP60.hbalTreeNodes(n).stream()
        .filter(BtreeP61::isSymmetric)
        .collect(Collectors.toList());
  }
}
