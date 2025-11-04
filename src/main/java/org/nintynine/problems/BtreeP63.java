package org.nintynine.problems;

/**
 * Problem P63: construct a complete binary tree.
 */
public final class BtreeP63 {

  private BtreeP63() {
    // utility class
  }

  /**
   * Build a complete binary tree with the specified number of nodes.
   *
   * <p>Each node holds the constant value {@code "x"} to mirror the original kata.
   *
   * @param nodes total number of nodes
   * @return root node or {@code null} when {@code nodes <= 0}
   */
  public static BtreeP61.Node<String> completeBinaryTree(int nodes) {
    if (nodes <= 0) {
      return null;
    }
    return build(1, nodes);
  }

  private static BtreeP61.Node<String> build(int index, int max) {
    if (index > max) {
      return null;
    }
    return BtreeP61.Node.of("x", build(index * 2, max), build(index * 2 + 1, max));
  }
}
