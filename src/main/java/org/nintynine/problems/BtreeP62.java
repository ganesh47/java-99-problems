package org.nintynine.problems;

import java.util.List;

/**
 * Problem P62: collect internal nodes of a binary tree.
 */
public final class BtreeP62 {

  private BtreeP62() {
    // utility class
  }

  /**
   * Return the values of all internal nodes.
   *
   * @param root tree root
   * @param <T> element type
   * @return list of internal node values
   */
  public static <T> List<T> internals(BtreeP61.Node<T> root) {
    return BtreeP61.collect(root, node -> !node.isLeaf());
  }
}
