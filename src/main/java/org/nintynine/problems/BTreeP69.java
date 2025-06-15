package org.nintynine.problems;

/**
 * P69 (**): Dotstring representation of binary trees.
 *
 * <p>A binary tree where nodes are identified by single characters can be represented as a
 * "dotstring" using a preorder traversal in which null (empty) subtrees are encoded by a dot
 * character '.'. For example the tree from problem P67 is encoded as {@code ABD..E..C.FG...}.
 *
 * <p>This class provides utility methods to convert between a tree structure and its dotstring
 * representation.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class BTreeP69 {

  private BTreeP69() {
    // utility class
  }

  /** Node of the binary tree. */
  public static class Node {
    char value;
    Node left;
    Node right;

    Node(char value) {
      this.value = value;
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

  /**
   * Convert a dotstring representation to a tree.
   *
   * @param ds the dotstring
   * @return root node of the parsed tree or {@code null} if dotstring is just '.'
   * @throws IllegalArgumentException if the dotstring is null or malformed
   */
  public static Node tree(String ds) {
    if (ds == null) {
      throw new IllegalArgumentException("dotstring cannot be null");
    }
    Index index = new Index();
    Node root = parse(ds, index);
    if (index.pos != ds.length()) {
      throw new IllegalArgumentException("Extra characters in dotstring");
    }
    return root;
  }

  private static Node parse(String ds, Index index) {
    if (index.pos >= ds.length()) {
      throw new IllegalArgumentException("Incomplete dotstring");
    }
    char c = ds.charAt(index.pos++);
    if (c == '.') {
      return null;
    }
    Node node = new Node(c);
    node.left = parse(ds, index);
    node.right = parse(ds, index);
    return node;
  }

  /**
   * Convert a tree into its dotstring representation.
   *
   * @param node the root node
   * @return dotstring encoding
   */
  public static String dotstring(Node node) {
    if (node == null) {
      return ".";
    }
    return node.value + dotstring(node.left) + dotstring(node.right);
  }

  private static class Index {
    int pos = 0;
  }
}
