package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem P61A: collect the leaves of a binary tree.
 */
public final class BtreeP61A {

  private BtreeP61A() {
    // utility class
  }

  /**
   * Return the values of all leaf nodes in depth-first order.
   *
   * @param root tree root, may be {@code null}
   * @param <T> element type
   * @return list of leaf values (left to right)
   */
  public static <T> List<T> leaves(BtreeP61.Node<T> root) {
    List<T> result = new ArrayList<>();
    collectLeaves(root, result);
    return result;
  }

  private static <T> void collectLeaves(BtreeP61.Node<T> node, List<T> acc) {
    if (node == null) {
      return;
    }
    if (node.getLeft() == null && node.getRight() == null) {
      acc.add(node.getValue());
      return;
    }
    collectLeaves(node.getLeft(), acc);
    collectLeaves(node.getRight(), acc);
  }
}
