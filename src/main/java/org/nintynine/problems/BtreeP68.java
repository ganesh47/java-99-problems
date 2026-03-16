package org.nintynine.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem P68: Preorder and inorder sequences of binary trees.
 */
public final class BtreeP68 {

  private BtreeP68() {
    // utility class
  }

  /** Preorder sequence for the given tree. */
  public static List<Character> preorder(BtreeP61.Node<Character> node) {
    List<Character> result = new ArrayList<>();
    preorderRec(node, result);
    return result;
  }

  private static void preorderRec(BtreeP61.Node<Character> node, List<Character> acc) {
    if (node == null) {
      return;
    }
    acc.add(node.getValue());
    preorderRec(node.getLeft(), acc);
    preorderRec(node.getRight(), acc);
  }

  /** Inorder sequence for the given tree. */
  public static List<Character> inorder(BtreeP61.Node<Character> node) {
    List<Character> result = new ArrayList<>();
    inorderRec(node, result);
    return result;
  }

  private static void inorderRec(BtreeP61.Node<Character> node, List<Character> acc) {
    if (node == null) {
      return;
    }
    inorderRec(node.getLeft(), acc);
    acc.add(node.getValue());
    inorderRec(node.getRight(), acc);
  }

  /**
   * Construct a binary search tree from its preorder sequence.
   *
   * @param seq preorder sequence
   * @return root of the constructed tree
   */
  public static BtreeP61.Node<Character> fromPreorder(List<Character> seq) {
    if (seq == null) {
      return null;
    }
    BtreeP61.Node<Character> root = null;
    for (char c : seq) {
      root = BtreeP57.insert(root, c);
    }
    return root;
  }

  /**
   * Reconstruct a tree from its preorder and inorder sequences.
   *
   * @param preorder preorder sequence
   * @param inorder inorder sequence
   * @return reconstructed tree root
   * @throws IllegalArgumentException if inputs are invalid
   */
  public static BtreeP61.Node<Character> preInTree(List<Character> preorder, List<Character> inorder) {
    if (preorder == null || inorder == null || preorder.size() != inorder.size()) {
      throw new IllegalArgumentException("invalid traversals");
    }
    Map<Character, Integer> indexMap = new HashMap<>();
    for (int i = 0; i < inorder.size(); i++) {
      indexMap.put(inorder.get(i), i);
    }
    return build(preorder, 0, inorder.size() - 1, indexMap, new int[] {0});
  }

  private static BtreeP61.Node<Character> build(
      List<Character> preorder,
      int inStart,
      int inEnd,
      Map<Character, Integer> indexMap,
      int[] preIdx) {
    if (inStart > inEnd) {
      return null;
    }
    char rootVal = preorder.get(preIdx[0]++);
    int inIdx = indexMap.get(rootVal);

    BtreeP61.Node<Character> left = build(preorder, inStart, inIdx - 1, indexMap, preIdx);
    BtreeP61.Node<Character> right = build(preorder, inIdx + 1, inEnd, indexMap, preIdx);

    return BtreeP61.Node.of(rootVal, left, right);
  }
}
