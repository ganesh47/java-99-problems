package org.nintynine.problems;

import java.util.Objects;

/**
 * Problem P61: count the leaves of a binary tree.
 */
public final class BtreeP61 {

  private BtreeP61() {
    // utility class
  }

  /**
   * Simple immutable binary tree node.
   *
   * @param <T> element type
   */
  public static final class Node<T> {
    private final T value;
    private final Node<T> left;
    private final Node<T> right;

    private Node(T value, Node<T> left, Node<T> right) {
      this.value = Objects.requireNonNull(value, "value");
      this.left = left;
      this.right = right;
    }

    /**
     * Create a leaf node.
     *
     * @param value node value
     * @param <T> element type
     * @return new leaf node
     */
    public static <T> Node<T> leaf(T value) {
      return new Node<>(value, null, null);
    }

    /**
     * Create a node with optional children.
     *
     * @param value node value
     * @param left left child (nullable)
     * @param right right child (nullable)
     * @param <T> element type
     * @return new node
     */
    public static <T> Node<T> of(T value, Node<T> left, Node<T> right) {
      return new Node<>(value, left, right);
    }

    public T getValue() {
      return value;
    }

    public Node<T> getLeft() {
      return left;
    }

    public Node<T> getRight() {
      return right;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node<?> node)) {
        return false;
      }
      return Objects.equals(value, node.value)
          && Objects.equals(left, node.left)
          && Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, left, right);
    }
  }

  /**
   * Count the number of leaves in the supplied binary tree.
   *
   * @param root tree root, may be {@code null}
   * @param <T> element type
   * @return number of leaf nodes
   */
  public static <T> int countLeaves(Node<T> root) {
    if (root == null) {
      return 0;
    }
    if (root.left == null && root.right == null) {
      return 1;
    }
    return countLeaves(root.left) + countLeaves(root.right);
  }
}
