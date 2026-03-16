package org.nintynine.problems;

/**
 * Problem P69: Dotstring representation of binary trees.
 */
public final class BtreeP69 {

  private BtreeP69() {
    // utility class
  }

  /**
   * Render a binary tree to its dotstring representation.
   *
   * @param node tree root
   * @return dotstring representation
   */
  public static String toDotstring(BtreeP61.Node<Character> node) {
    StringBuilder sb = new StringBuilder();
    toDotstringRec(node, sb);
    return sb.toString();
  }

  private static void toDotstringRec(BtreeP61.Node<Character> node, StringBuilder sb) {
    if (node == null) {
      sb.append(".");
      return;
    }
    sb.append(node.getValue());
    toDotstringRec(node.getLeft(), sb);
    toDotstringRec(node.getRight(), sb);
  }

  /**
   * Parse a dotstring representation into a binary tree.
   *
   * @param s dotstring
   * @return tree root
   */
  public static BtreeP61.Node<Character> fromDotstring(String s) {
    if (s == null || s.isEmpty()) {
      return null;
    }
    return fromDotstringRec(s, new int[] {0});
  }

  private static BtreeP61.Node<Character> fromDotstringRec(String s, int[] pos) {
    if (pos[0] >= s.length()) {
      return null;
    }
    char c = s.charAt(pos[0]++);
    if (c == '.') {
      return null;
    }
    BtreeP61.Node<Character> left = fromDotstringRec(s, pos);
    BtreeP61.Node<Character> right = fromDotstringRec(s, pos);
    return BtreeP61.Node.of(c, left, right);
  }
}
