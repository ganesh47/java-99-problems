package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem P62B: collect nodes at a given level.
 */
public final class BtreeP62B {

  private BtreeP62B() {
    // utility class
  }

  /**
   * Return the values of nodes located at the provided level.
   *
   * @param root tree root (may be {@code null})
   * @param level level to extract (1-based)
   * @param <T> element type
   * @return list of node values at that level in left-to-right order
   */
  public static <T> List<T> atLevel(BtreeP61.Node<T> root, int level) {
    List<T> result = new ArrayList<>();
    collectAtLevel(root, level, result);
    return result;
  }

  /**
   * Produce the level-order traversal by repeatedly calling {@link #atLevel}.
   *
   * @param root tree root
   * @param <T> element type
   * @return list of values in breadth-first order
   */
  public static <T> List<T> levelOrder(BtreeP61.Node<T> root) {
    List<T> result = new ArrayList<>();
    int height = height(root);
    for (int level = 1; level <= height; level++) {
      result.addAll(atLevel(root, level));
    }
    return result;
  }

  private static <T> void collectAtLevel(BtreeP61.Node<T> node, int level, List<T> acc) {
    if (node == null || level < 1) {
      return;
    }
    if (level == 1) {
      acc.add(node.getValue());
      return;
    }
    collectAtLevel(node.getLeft(), level - 1, acc);
    collectAtLevel(node.getRight(), level - 1, acc);
  }

  private static <T> int height(BtreeP61.Node<T> node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
  }
}
