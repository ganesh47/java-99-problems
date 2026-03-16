package org.nintynine.problems;

/**
 * Simple binary tree used for symmetry checks in problem 56.
 *
 * @param <T> node value type
 */
public final class BtreeP56<T> {
  private BtreeP61.Node<T> root;

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
    this.root = BtreeP61.Node.leaf(value);
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
    root = BtreeP61.Node.of(root.getValue(), BtreeP61.Node.leaf(value), root.getRight());
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
    root = BtreeP61.Node.of(root.getValue(), root.getLeft(), BtreeP61.Node.leaf(value));
  }

  /**
   * Checks if the tree is symmetric.
   *
   * @return true if symmetric
   */
  public boolean isSymmetric() {
    return BtreeP61.isSymmetric(root);
  }
}
