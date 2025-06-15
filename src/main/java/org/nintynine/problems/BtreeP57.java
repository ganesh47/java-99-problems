package org.nintynine.problems;

/**
 * Binary tree utilities for problem 57.
 *
 * @param <T> type of node values
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BtreeP57<T extends Comparable<T>> {
  private Node<T> root;

  private static class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    Node(T value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  public BtreeP57() {
    this.root = null;
  }

  /**
   * Constructs the tree by inserting all given values.
   *
   * @param values values to insert
   */
  public void construct(T[] values) {
    for (T value : values) {
      insert(value);
    }
  }

  public void insert(T value) {
    root = insertRec(root, value);
  }

  private Node<T> insertRec(Node<T> node, T value) {
    if (node == null) {
      return new Node<>(value);
    }

    int comparison = value.compareTo(node.value);
    if (comparison < 0) {
      node.left = insertRec(node.left, value);
    } else if (comparison > 0) {
      node.right = insertRec(node.right, value);
    }

    return node;
  }

  /**
   * Checks if this tree is symmetric.
   *
   * @return {@code true} if the tree is symmetric, otherwise {@code false}
   */
  public boolean isSymmetric() {
    return root == null || isMirror(root.left, root.right);
  }

  @SuppressWarnings("java:S2234")
  private boolean isMirror(Node<T> left, Node<T> right) {
    if (left == null && right == null) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }
    return isMirror(left.left, right.right) && isMirror(left.right, right.left);
  }

  // Helper method to get tree structure as a string (for testing)
  public String getStructure() {
    return getStructureRec(root);
  }

  private String getStructureRec(Node<T> node) {
    if (node == null) {
      return "nil";
    }
    return "("
        + node.value
        + " "
        + getStructureRec(node.left)
        + " "
        + getStructureRec(node.right)
        + ")";
  }
}
