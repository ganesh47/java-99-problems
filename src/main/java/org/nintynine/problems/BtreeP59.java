package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Utility class for generating completely balanced binary trees. */
public class BtreeP59 {
  private BtreeP59() {}

  /**
   * Represents a node in a height-balanced tree.
   */
  public static class Btree59Node {
    char value;
    Btree59Node left;
    Btree59Node right;

    Btree59Node(char value) {
      this.value = value;
      left = null;
      right = null;
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, left, right);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Btree59Node btree59Node = (Btree59Node) o;
      return value == btree59Node.value
          && Objects.equals(left, btree59Node.left)
          && Objects.equals(right, btree59Node.right);
    }

    @Override
    public String toString() {
      if (left == null && right == null) {
        return String.valueOf(value);
      }
      return String.format(
          "%c(%s,%s)",
          value, left == null ? "NIL" : left.toString(), right == null ? "NIL" : right.toString());
    }
  }

  /**
   * Generates all height-balanced binary trees for a given height.
   *
   * @param height the height of the trees
   * @return a list of height-balanced trees
   */
  public static List<Btree59Node> hbalTrees(int height) {
    if (height < 0) {
      return new ArrayList<>();
    }
    return generateHbalTrees(height);
  }

  private static List<Btree59Node> generateHbalTrees(int height) {
    List<Btree59Node> result = new ArrayList<>();

    if (height == 0) {
      result.add(null);
      return result;
    }

    if (height == 1) {
      result.add(new Btree59Node('X'));
      return result;
    }

    // Trees of height h can have subtrees of height h-1 or h-2
    List<Btree59Node> heightMinus1 = generateHbalTrees(height - 1);
    List<Btree59Node> heightMinus2 = generateHbalTrees(height - 2);

    // Case 1: Both subtrees have height h-1
    for (Btree59Node left : heightMinus1) {
      for (Btree59Node right : heightMinus1) {
        Btree59Node root = new Btree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    // Case 2: Left subtree has height h-1, right has height h-2
    for (Btree59Node left : heightMinus1) {
      for (Btree59Node right : heightMinus2) {
        Btree59Node root = new Btree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    // Case 3: Left subtree has height h-2, right has height h-1
    for (Btree59Node left : heightMinus2) {
      for (Btree59Node right : heightMinus1) {
        Btree59Node root = new Btree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    return result;
  }

  private static Btree59Node cloneTree(Btree59Node root) {
    if (root == null) {
      return null;
    }
    Btree59Node clone = new Btree59Node(root.value);
    clone.left = cloneTree(root.left);
    clone.right = cloneTree(root.right);
    return clone;
  }

  /**
   * Calculates the height of a tree.
   *
   * @param root the root of the tree
   * @return the height of the tree
   */
  public static int height(Btree59Node root) {
    if (root == null) {
      return 0;
    }
    return 1 + Math.max(height(root.left), height(root.right));
  }

  /**
   * Checks if a tree is height-balanced.
   *
   * @param root the root of the tree
   * @return true if the tree is height-balanced, false otherwise
   */
  protected static boolean isHeightBalanced(Btree59Node root) {
    if (root == null) {
      return true;
    }

    int leftHeight = height(root.left);
    int rightHeight = height(root.right);

    return Math.abs(leftHeight - rightHeight) <= 1
        && isHeightBalanced(root.left)
        && isHeightBalanced(root.right);
  }
}
