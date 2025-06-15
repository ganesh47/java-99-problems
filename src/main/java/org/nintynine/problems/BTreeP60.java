package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BTreeP60<T> {
  private final T value;
  protected BTreeP60<T> left;
  protected BTreeP60<T> right;

  public BTreeP60(T value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }

  public static int minNodes(int h) {
    if (h <= 0) return 0;
    if (h == 1) return 1;
    return 1 + minNodes(h - 1) + minNodes(h - 2);
  }

  public static int maxHeight(int n) {
    if (n <= 0) return 0;
    int h = 0;
    while (minNodes(h) <= n) h++;
    return h - 1;
  }

  public static List<BTreeP60<String>> hbalTreeNodes(int n) {
    if (n <= 0) return Collections.emptyList();
    if (n == 1) {
      List<BTreeP60<String>> result = new ArrayList<>();
      result.add(new BTreeP60<>("x"));
      return result;
    }

    // Special case for 2 nodes
    if (n == 2) {
      List<BTreeP60<String>> result = new ArrayList<>();
      // Create left-child tree
      BTreeP60<String> leftTree = new BTreeP60<>("x");
      leftTree.left = new BTreeP60<>("x");
      result.add(leftTree);

      // Create right-child tree
      BTreeP60<String> rightTree = new BTreeP60<>("x");
      rightTree.right = new BTreeP60<>("x");
      result.add(rightTree);

      return result;
    }

    List<BTreeP60<String>> result = new ArrayList<>();
    int maxH = maxHeight(n);
    int minH = (int) Math.ceil(Math.log(n + (double) 1) / Math.log(2));

    for (int h = minH; h <= maxH; h++) {
      result.addAll(generateTreesWithHeight(h, n));
    }

    return result;
  }

  private static List<BTreeP60<String>> generateTreesWithHeight(int height, int n) {
    List<BTreeP60<String>> result = new ArrayList<>();

    if (n == 0) {
      return Collections.emptyList();
    }
    if (n == 1) {
      result.add(new BTreeP60<>("x"));
      return result;
    }

    for (int leftNodes = 0; leftNodes < n; leftNodes++) {
      int rightNodes = n - 1 - leftNodes;

      List<BTreeP60<String>> leftSubtrees = hbalTreeNodes(leftNodes);
      List<BTreeP60<String>> rightSubtrees = hbalTreeNodes(rightNodes);

      for (BTreeP60<String> left : leftSubtrees) {
        for (BTreeP60<String> right : rightSubtrees) {
          if (isHeightBalanced(left, right)
              && getHeight(left) <= height - 1
              && getHeight(right) <= height - 1) {
            BTreeP60<String> root = new BTreeP60<>("x");
            root.left = cloneTree(left);
            root.right = cloneTree(right);
            result.add(root);
          }
        }
      }
    }

    return result;
  }

  private static boolean isHeightBalanced(BTreeP60<String> left, BTreeP60<String> right) {
    return Math.abs(getHeight(left) - getHeight(right)) <= 1;
  }

  public static int getHeight(BTreeP60<?> node) {
    if (node == null) return 0;
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
  }

  private static BTreeP60<String> cloneTree(BTreeP60<String> node) {
    if (node == null) return null;
    BTreeP60<String> clone = new BTreeP60<>(node.value);
    clone.left = cloneTree(node.left);
    clone.right = cloneTree(node.right);
    return clone;
  }

  // Getters for testing
  public T getValue() {
    return value;
  }

  public BTreeP60<T> getLeft() {
    return left;
  }

  public BTreeP60<T> getRight() {
    return right;
  }
}
