package org.nintynine.problems;

public class BTreeP56<T> {
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

  public BTreeP56() {
    this.root = null;
  }

  public void setRoot(T value) {
    this.root = new Node<>(value);
  }

  public void addLeft(T value) {
    if (root == null) throw new IllegalStateException("Tree has no root");
    root.left = new Node<>(value);
  }

  public void addRight(T value) {
    if (root == null) throw new IllegalStateException("Tree has no root");
    root.right = new Node<>(value);
  }

  public boolean isSymmetric() {
    if (root == null) return true;
    return isMirror(root.left, root.right);
  }

  @SuppressWarnings("java:S2234") // or just "S2234"
  private boolean isMirror(Node<T> left, Node<T> right) {
    // If both nodes are null, they are mirror images
    if (left == null && right == null) return true;

    // If only one node is null, they are not mirror images
    if (left == null || right == null) return false;

    // Check if the structure is mirrored
    return isMirror(left.left, right.right) && isMirror(left.right, right.left);
  }
}
