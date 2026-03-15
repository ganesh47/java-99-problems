package org.nintynine.problems;

import java.util.List;

/**
 * P55: Construct completely balanced binary trees.
 */
public final class TruthP55 {

  private TruthP55() {
    // utility class
  }

  /**
   * Constructs all completely balanced binary trees with a given number of nodes.
   *
   * @param n the number of nodes
   * @return a list of all completely balanced binary trees
   */
  public static List<BtreeP61.Node<String>> cbalTree(int n) {
    return BtreeP61.cbalTree(n);
  }
}
