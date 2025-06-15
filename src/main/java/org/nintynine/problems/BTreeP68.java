package org.nintynine.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Problem P68: Preorder and inorder sequences of binary trees.
 *
 * <p>See issue #61.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BTreeP68 {

  private BTreeP68() {
    // utility class
  }

  /** Simple binary tree node. */
  public static class Node {
    char value;
    Node left;
    Node right;

    Node(char value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node other)) {
        return false;
      }
      return value == other.value
          && Objects.equals(left, other.left)
          && Objects.equals(right, other.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, left, right);
    }

    @Override
    public String toString() {
      if (left == null && right == null) {
        return String.valueOf(value);
      }
      return value
          + "("
          + (left == null ? "NIL" : left.toString())
          + ","
          + (right == null ? "NIL" : right.toString())
          + ")";
    }
  }

  /** Preorder sequence for the given tree. */
  public static List<Character> preorder(Node node) {
    List<Character> result = new ArrayList<>();
    preorderRec(node, result);
    return result;
  }

  private static void preorderRec(Node node, List<Character> acc) {
    if (node == null) {
      return;
    }
    acc.add(node.value);
    preorderRec(node.left, acc);
    preorderRec(node.right, acc);
  }

  /** Inorder sequence for the given tree. */
  public static List<Character> inorder(Node node) {
    List<Character> result = new ArrayList<>();
    inorderRec(node, result);
    return result;
  }

  private static void inorderRec(Node node, List<Character> acc) {
    if (node == null) {
      return;
    }
    inorderRec(node.left, acc);
    acc.add(node.value);
    inorderRec(node.right, acc);
  }

  /**
   * Construct a binary search tree from its preorder sequence.
   *
   * @param seq preorder sequence
   * @return root of the constructed tree
   */
  public static Node fromPreorder(List<Character> seq) {
    Node root = null;
    if (seq == null) {
      return null;
    }
    for (char c : seq) {
      root = insert(root, c);
    }
    return root;
  }

  private static Node insert(Node node, char value) {
    if (node == null) {
      return new Node(value);
    }
    if (value < node.value) {
      node.left = insert(node.left, value);
    } else if (value > node.value) {
      node.right = insert(node.right, value);
    }
    return node;
  }

  /**
   * Reconstruct a tree from its preorder and inorder sequences.
   *
   * @param preorder preorder sequence
   * @param inorder inorder sequence
   * @return reconstructed tree root
   * @throws IllegalArgumentException if inputs are invalid
   */
  public static Node preInTree(List<Character> preorder, List<Character> inorder) {
    if (preorder == null || inorder == null || preorder.size() != inorder.size()) {
      throw new IllegalArgumentException("invalid traversals");
    }
    Map<Character, Integer> index = new HashMap<>();
    for (int i = 0; i < inorder.size(); i++) {
      index.put(inorder.get(i), i);
    }
    Index preIdx = new Index();
    return build(preorder, 0, inorder.size() - 1, index, preIdx);
  }

  private static Node build(
      List<Character> preorder,
      int inStart,
      int inEnd,
      Map<Character, Integer> index,
      Index preIdx) {
    if (inStart > inEnd) {
      return null;
    }
    char rootVal = preorder.get(preIdx.pos++);
    Node node = new Node(rootVal);
    int inIndex = index.get(rootVal);
    node.left = build(preorder, inStart, inIndex - 1, index, preIdx);
    node.right = build(preorder, inIndex + 1, inEnd, index, preIdx);
    return node;
  }

  private static class Index {
    int pos;
  }
}
