package org.nintynine.problems;

/**
 * Binary tree utilities for problem 61.
 *
 * @param <T> node value type
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BtreeP61<T> extends BtreeP60<T> {

  /**
   * Creates a new tree node with the given value.
   *
   * @param value node value
   */
  public BtreeP61(T value) {
    super(value);
  }

  /**
   * Counts the number of leaves in the given tree.
   *
   * @param tree tree to inspect
   * @param <T>  element type
   * @return leaf count
   */
  public static <T> int countLeaves(BtreeP61<T> tree) {
    if (tree == null) {
      return 0;
    }

    // A leaf is a node with no children
    if (tree.getLeft() == null && tree.getRight() == null) {
      return 1;
    }

    // Recursively count leaves in left and right subtrees
    return countLeaves((BtreeP61<T>) tree.getLeft()) + countLeaves((BtreeP61<T>) tree.getRight());
  }
}
