package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public static <T> Node<T> leaf(T value) {
      return new Node<>(value, null, null);
    }

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

    /** Checks if this node is a leaf. */
    public boolean isLeaf() {
      return left == null && right == null;
    }

    /** Returns the height of the tree rooted at this node. */
    public int height() {
      return 1 + Math.max(
          left == null ? 0 : left.height(),
          right == null ? 0 : right.height()
      );
    }

    /** Returns the total number of nodes in the tree rooted at this node. */
    public int nodeCount() {
      return 1 + (left == null ? 0 : left.nodeCount()) + (right == null ? 0 : right.nodeCount());
    }

    /** Checks if the tree rooted at this node is height-balanced. */
    public boolean isHeightBalanced() {
      int lh = (left == null) ? 0 : left.height();
      int rh = (right == null) ? 0 : right.height();
      return Math.abs(lh - rh) <= 1
          && (left == null || left.isHeightBalanced())
          && (right == null || right.isHeightBalanced());
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

    @Override
    public String toString() {
      if (isLeaf()) {
        return String.valueOf(value);
      }
      return String.format("%s(%s,%s)",
          value,
          left == null ? "" : left.toString(),
          right == null ? "" : right.toString());
    }
  }

  /**
   * Counts the leaves of a binary tree.
   *
   * @param root tree root
   * @param <T> element type
   * @return leaf count
   */
  public static <T> int countLeaves(Node<T> root) {
    if (root == null) {
      return 0;
    }
    if (root.isLeaf()) {
      return 1;
    }
    return countLeaves(root.left) + countLeaves(root.right);
  }

  /**
   * Generates all completely balanced binary trees with n nodes.
   *
   * @param n number of nodes
   * @return list of completely balanced trees
   */
  public static List<Node<String>> cbalTree(int n) {
    if (n == 0) {
      return Collections.singletonList(null);
    }
    if (n == 1) {
      return Collections.singletonList(Node.leaf("x"));
    }

    List<Node<String>> result = new ArrayList<>();
    int n1 = (n - 1) / 2;
    int n2 = n - 1 - n1;

    for (Node<String> l : cbalTree(n1)) {
      for (Node<String> r : cbalTree(n2)) {
        result.add(Node.of("x", l, r));
      }
    }

    if (n1 != n2) {
      for (Node<String> l : cbalTree(n2)) {
        for (Node<String> r : cbalTree(n1)) {
          result.add(Node.of("x", l, r));
        }
      }
    }
    return result;
  }

  /** Checks if two trees are mirrors of each other. */
  public static <T> boolean isMirror(Node<T> left, Node<T> right) {
    if (left == null && right == null) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }
    return isMirror(left.getLeft(), right.getRight()) && isMirror(left.getRight(), right.getLeft());
  }

  /** Checks if a tree is symmetric. */
  public static <T> boolean isSymmetric(Node<T> root) {
    return root == null || isMirror(root.getLeft(), root.getRight());
  }
}
