package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Height-balanced binary tree utilities for problem 60.
 *
 * @param <T> node value type
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BtreeP60<T> {
  private final T value;
  protected BtreeP60<T> left;
  protected BtreeP60<T> right;

  /**
   * Creates a new tree node with the given value.
   *
   * @param value node value
   */
  public BtreeP60(T value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }

  /**
   * Computes the minimum number of nodes of a height-balanced tree for a
   * given height.
   *
   * @param h desired height
   * @return minimum node count
   */
  public static int minNodes(int h) {
    if (h <= 0) {
      return 0;
    }
    if (h == 1) {
      return 1;
    }
    return 1 + minNodes(h - 1) + minNodes(h - 2);
  }

  /**
   * Computes the maximum possible height of a tree with the given number of
   * nodes while remaining height balanced.
   *
   * @param n node count
   * @return maximum height
   */
  public static int maxHeight(int n) {
    if (n <= 0) {
      return 0;
    }
    int h = 0;
    while (minNodes(h) <= n) {
      h++;
    }
    return h - 1;
  }

  /**
   * Generates all height-balanced trees with the given number of nodes.
   *
   * @param n number of nodes
   * @return list of trees
   */
  public static List<BtreeP60<String>> hbalTreeNodes(int n) {
    if (n <= 0) {
      return Collections.emptyList();
    }
    if (n == 1) {
      List<BtreeP60<String>> result = new ArrayList<>();
      result.add(new BtreeP60<>("x"));
      return result;
    }

    // Special case for 2 nodes
    if (n == 2) {
      List<BtreeP60<String>> result = new ArrayList<>();
      // Create left-child tree
      BtreeP60<String> leftTree = new BtreeP60<>("x");
      leftTree.left = new BtreeP60<>("x");
      result.add(leftTree);

      // Create right-child tree
      BtreeP60<String> rightTree = new BtreeP60<>("x");
      rightTree.right = new BtreeP60<>("x");
      result.add(rightTree);

      return result;
    }

    List<BtreeP60<String>> result = new ArrayList<>();
    int maxH = maxHeight(n);
    int minH = (int) Math.ceil(Math.log(n + (double) 1) / Math.log(2));

    for (int h = minH; h <= maxH; h++) {
      result.addAll(generateTreesWithHeight(h, n));
    }

    return result;
  }

  /**
   * Generates trees of a specific height containing the given number of nodes.
   */
  private static List<BtreeP60<String>> generateTreesWithHeight(int height, int n) {
    List<BtreeP60<String>> result = new ArrayList<>();

    if (n == 0) {
      return Collections.emptyList();
    }
    if (n == 1) {
      result.add(new BtreeP60<>("x"));
      return result;
    }

    for (int leftNodes = 0; leftNodes < n; leftNodes++) {
      int rightNodes = n - 1 - leftNodes;

      List<BtreeP60<String>> leftSubtrees = hbalTreeNodes(leftNodes);
      List<BtreeP60<String>> rightSubtrees = hbalTreeNodes(rightNodes);

      for (BtreeP60<String> left : leftSubtrees) {
        for (BtreeP60<String> right : rightSubtrees) {
          if (isHeightBalanced(left, right)
              && getHeight(left) <= height - 1
              && getHeight(right) <= height - 1) {
            BtreeP60<String> root = new BtreeP60<>("x");
            root.left = cloneTree(left);
            root.right = cloneTree(right);
            result.add(root);
          }
        }
      }
    }

    return result;
  }

  /**
   * Checks if two subtrees are height balanced relative to each other.
   */
  private static boolean isHeightBalanced(BtreeP60<String> left, BtreeP60<String> right) {
    return Math.abs(getHeight(left) - getHeight(right)) <= 1;
  }

  /**
   * Returns the height of the given tree.
   *
   * @param node root node
   * @return tree height
   */
  public static int getHeight(BtreeP60<?> node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
  }

  /**
   * Creates a deep copy of the given tree.
   */
  private static BtreeP60<String> cloneTree(BtreeP60<String> node) {
    if (node == null) {
      return null;
    }
    BtreeP60<String> clone = new BtreeP60<>(node.value);
    clone.left = cloneTree(node.left);
    clone.right = cloneTree(node.right);
    return clone;
  }

  // Getters for testing
  public T getValue() {
    return value;
  }

  public BtreeP60<T> getLeft() {
    return left;
  }

  public BtreeP60<T> getRight() {
    return right;
  }
}
