package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
public class BTreeP59 {
  private BTreeP59() {}

  public static class BTree59Node {
    char value;
    BTree59Node left;
    BTree59Node right;

    BTree59Node(char value) {
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
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BTree59Node bTree59Node = (BTree59Node) o;
      return value == bTree59Node.value
          && Objects.equals(left, bTree59Node.left)
          && Objects.equals(right, bTree59Node.right);
    }

    @Override
    public String toString() {
      if (left == null && right == null) return String.valueOf(value);
      return String.format(
          "%c(%s,%s)",
          value, left == null ? "NIL" : left.toString(), right == null ? "NIL" : right.toString());
    }
  }

  public static List<BTree59Node> hbalTrees(int height) {
    if (height < 0) return new ArrayList<>();
    return generateHbalTrees(height);
  }

  private static List<BTree59Node> generateHbalTrees(int height) {
    List<BTree59Node> result = new ArrayList<>();

    if (height == 0) {
      result.add(null);
      return result;
    }

    if (height == 1) {
      result.add(new BTree59Node('X'));
      return result;
    }

    // Trees of height h can have subtrees of height h-1 or h-2
    List<BTree59Node> heightMinus1 = generateHbalTrees(height - 1);
    List<BTree59Node> heightMinus2 = generateHbalTrees(height - 2);

    // Case 1: Both subtrees have height h-1
    for (BTree59Node left : heightMinus1) {
      for (BTree59Node right : heightMinus1) {
        BTree59Node root = new BTree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    // Case 2: Left subtree has height h-1, right has height h-2
    for (BTree59Node left : heightMinus1) {
      for (BTree59Node right : heightMinus2) {
        BTree59Node root = new BTree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    // Case 3: Left subtree has height h-2, right has height h-1
    for (BTree59Node left : heightMinus2) {
      for (BTree59Node right : heightMinus1) {
        BTree59Node root = new BTree59Node('X');
        root.left = cloneTree(left);
        root.right = cloneTree(right);
        result.add(root);
      }
    }

    return result;
  }

  private static BTree59Node cloneTree(BTree59Node root) {
    if (root == null) return null;
    BTree59Node clone = new BTree59Node(root.value);
    clone.left = cloneTree(root.left);
    clone.right = cloneTree(root.right);
    return clone;
  }

  public static int height(BTree59Node root) {
    if (root == null) return 0;
    return 1 + Math.max(height(root.left), height(root.right));
  }

  protected static boolean isHeightBalanced(BTree59Node root) {
    if (root == null) return true;

    int leftHeight = height(root.left);
    int rightHeight = height(root.right);

    return Math.abs(leftHeight - rightHeight) <= 1
        && isHeightBalanced(root.left)
        && isHeightBalanced(root.right);
  }
}
