package org.nintynine.problems;

/**
 * Simple binary tree used for symmetry checks in problem 56.
 *
 * @param <T> node value type
 */
public final class BtreeP56<T> {
  private Node<T> root;

  /**
   * Represents a node in the binary tree.
   *
   * @param <T> node value type
   */
  private static class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    /**
     * Constructs a node with the specified value.
     *
     * @param value the node value
     */
    Node(T value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  /**
   * Constructs an empty binary tree.
   */
  public BtreeP56() {
    this.root = null;
  }

  /**
   * Sets the root of the tree.
   *
   * @param value the root value
   */
  public void setRoot(T value) {
    this.root = new Node<>(value);
  }

  /**
   * Adds a left child to the root.
   *
   * @param value the left child value
   */
  public void addLeft(T value) {
    if (root == null) {
      throw new IllegalStateException("Tree has no root");
    }
    root.left = new Node<>(value);
  }

  /**
   * Adds a right child to the root.
   *
   * @param value the right child value
   */
  public void addRight(T value) {
    if (root == null) {
      throw new IllegalStateException("Tree has no root");
    }
    root.right = new Node<>(value);
  }

  /**
   * Checks if the tree is symmetric.
   *
   * @return true if symmetric
   */
  public boolean isSymmetric() {
    if (root == null) {
      return true;
    }
    return isMirror(root.left, root.right);
  }

  @SuppressWarnings("java:S2234") // or just "S2234"
  private boolean isMirror(Node<T> left, Node<T> right) {
    // If both nodes are null, they are mirror images
    if (left == null && right == null) {
      return true;
    }

    // If only one node is null, they are not mirror images
    if (left == null || right == null) {
      return false;
    }

    // Check if the structure is mirrored
    return isMirror(left.left, right.right) && isMirror(left.right, right.left);
  }
}
