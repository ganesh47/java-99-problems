package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem P62: collect internal nodes of a binary tree.
 */
public final class BtreeP62 {

  private BtreeP62() {
    // utility class
  }

  /**
   * Return the values of all internal nodes (nodes with at least one child).
   *
   * @param root tree root, may be {@code null}
   * @param <T> element type
   * @return list of internal node values in depth-first (preorder) order
   */
  public static <T> List<T> internals(BtreeP61.Node<T> root) {
    List<T> result = new ArrayList<>();
    collectInternal(root, result);
    return result;
  }

  private static <T> void collectInternal(BtreeP61.Node<T> node, List<T> acc) {
    if (node == null) {
      return;
    }
    if (node.getLeft() != null || node.getRight() != null) {
      acc.add(node.getValue());
    }
    collectInternal(node.getLeft(), acc);
    collectInternal(node.getRight(), acc);
  }
}
