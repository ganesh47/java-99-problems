package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Utilities for constructing symmetric binary trees. */
public class BtreeP58 {
  private BtreeP58() {}

  public static class BtreeP58Node {
    char value;
    BtreeP58Node left;
    BtreeP58Node right;

    BtreeP58Node(char value) {
      this.value = value;
      left = null;
      right = null;
    }

    @Override
    public int hashCode() {
      return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BtreeP58Node bTreeP58Node = (BtreeP58Node) o;
      return value == bTreeP58Node.value
          && Objects.equals(left, bTreeP58Node.left)
          && Objects.equals(right, bTreeP58Node.right);
    }

    @Override
    public String toString() {
      if (left == null && right == null) return String.valueOf(value);
      return String.format(
          "%c(%s,%s)",
          value, left == null ? "NIL" : left.toString(), right == null ? "NIL" : right.toString());
    }
  }

  public static List<BtreeP58Node> symCbalTrees(int nodes) {
    if (nodes % 2 == 0) return new ArrayList<>();
    return generateSymCbalTrees(nodes);
  }

  private static List<BtreeP58Node> generateSymCbalTrees(int nodes) {
    List<BtreeP58Node> result = new ArrayList<>();

    if (nodes == 0) return result;
    if (nodes == 1) {
      result.add(new BtreeP58Node('X'));
      return result;
    }

    int remainingNodes = nodes - 1;
    if (remainingNodes % 2 != 0) return result;

    List<BtreeP58Node> subtrees = generateBalancedSubtrees(remainingNodes / 2);
    for (BtreeP58Node leftSubtree : subtrees) {
      BtreeP58Node root = new BtreeP58Node('X');
      root.left = cloneTree(leftSubtree);
      root.right = cloneTree(mirrorTree(leftSubtree));
      result.add(root);
    }

    return result;
  }

  private static List<BtreeP58Node> generateBalancedSubtrees(int nodes) {
    List<BtreeP58Node> result = new ArrayList<>();

    if (nodes == 0) {
      result.add(null);
      return result;
    }
    if (nodes == 1) {
      result.add(new BtreeP58Node('X'));
      return result;
    }

    int remainingNodes = nodes - 1;
    for (int leftNodes = remainingNodes / 2; leftNodes <= (remainingNodes + 1) / 2; leftNodes++) {
      int rightNodes = remainingNodes - leftNodes;
      List<BtreeP58Node> leftSubtrees = generateBalancedSubtrees(leftNodes);
      List<BtreeP58Node> rightSubtrees = generateBalancedSubtrees(rightNodes);

      for (BtreeP58Node left : leftSubtrees) {
        for (BtreeP58Node right : rightSubtrees) {
          BtreeP58Node root = new BtreeP58Node('X');
          root.left = cloneTree(left);
          root.right = cloneTree(right);
          result.add(root);
        }
      }
    }
    return result;
  }

  private static BtreeP58Node mirrorTree(BtreeP58Node root) {
    if (root == null) return null;
    BtreeP58Node mirrored = new BtreeP58Node(root.value);
    mirrored.left = mirrorTree(root.right);
    mirrored.right = mirrorTree(root.left);
    return mirrored;
  }

  private static BtreeP58Node cloneTree(BtreeP58Node root) {
    if (root == null) return null;
    BtreeP58Node clone = new BtreeP58Node(root.value);
    clone.left = cloneTree(root.left);
    clone.right = cloneTree(root.right);
    return clone;
  }

  public static int countSymCbalTrees(int nodes) {
    return symCbalTrees(nodes).size();
  }
}
