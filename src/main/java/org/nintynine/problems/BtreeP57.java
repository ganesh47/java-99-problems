package org.nintynine.problems;

import java.util.List;

/**
 * P57: Binary search trees (dictionaries).
 */
public final class BtreeP57 {

  private BtreeP57() {
    // utility class
  }

  /**
   * Constructs a binary search tree from a list of values.
   *
   * @param values the values to insert
   * @param <T> value type
   * @return the constructed tree
   */
  public static <T extends Comparable<T>> BtreeP61.Node<T> construct(List<T> values) {
    BtreeP61.Node<T> root = null;
    for (T value : values) {
      root = insert(root, value);
    }
    return root;
  }

  /**
   * Inserts a value into a binary search tree.
   *
   * @param node the root of the tree
   * @param value the value to insert
   * @param <T> value type
   * @return the updated tree
   */
  public static <T extends Comparable<T>> BtreeP61.Node<T> insert(BtreeP61.Node<T> node, T value) {
    if (node == null) {
      return BtreeP61.Node.leaf(value);
    }
    int cmp = value.compareTo(node.getValue());
    if (cmp < 0) {
      return BtreeP61.Node.of(node.getValue(), insert(node.getLeft(), value), node.getRight());
    } else if (cmp > 0) {
      return BtreeP61.Node.of(node.getValue(), node.getLeft(), insert(node.getRight(), value));
    }
    return node;
  }
}
