package org.nintynine.problems;

/**
 * P67: A string representation of binary trees.
 *
 * <p>Supports rendering a binary tree to the compact representation used in the
 * 99-problems kata (e.g. {@code a(b(d,e),c(,f(g,)))}) and parsing the same
 * format back into a tree structure.</p>
 */
public final class BtreeP67 {

  private BtreeP67() {
    // Utility class
  }

  /**
   * Renders a binary tree to a compact string.
   *
   * @param root tree root
   * @return compact string representation
   */
  public static String toCompactString(BtreeP61.Node<?> root) {
    if (root == null) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    appendNode(root, builder);
    return builder.toString();
  }

  private static void appendNode(BtreeP61.Node<?> node, StringBuilder builder) {
    builder.append(node.getValue());
    if (node.getLeft() == null && node.getRight() == null) {
      return;
    }
    builder.append('(');
    if (node.getLeft() != null) {
      appendNode(node.getLeft(), builder);
    }
    builder.append(',');
    if (node.getRight() != null) {
      appendNode(node.getRight(), builder);
    }
    builder.append(')');
  }

  /**
   * Parses a compact string representation of a binary tree.
   *
   * @param representation compact string representation
   * @return tree root
   * @throws IllegalArgumentException if the input is null or invalid
   */
  public static BtreeP61.Node<String> parse(String representation) {
    if (representation == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    Parser parser = new Parser(representation);
    if (parser.isAtEnd()) {
      return null;
    }
    BtreeP61.Node<String> root = parser.parseNode();
    if (!parser.isAtEnd()) {
      throw new IllegalArgumentException("Unexpected trailing content at position "
          + parser.position);
    }
    return root;
  }

  private static final class Parser {
    private final String input;
    private int position;

    private Parser(String input) {
      this.input = input;
      this.position = 0;
    }

    private boolean isAtEnd() {
      return position >= input.length();
    }

    private char peek() {
      return isAtEnd() ? '\0' : input.charAt(position);
    }

    private void consume(char expected) {
      if (peek() != expected) {
        throw new IllegalArgumentException("Expected '" + expected + "' at position " + position);
      }
      position++;
    }

    private BtreeP61.Node<String> parseNode() {
      if (peek() == ',' || peek() == ')' || isAtEnd()) {
        return null;
      }

      String value = readValue();
      BtreeP61.Node<String> leftChild = null;
      BtreeP61.Node<String> rightChild = null;

      if (peek() == '(') {
        position++; // consume '('
        leftChild = parseNode();
        consume(',');
        rightChild = parseNode();
        consume(')');
      }

      return BtreeP61.Node.of(value, leftChild, rightChild);
    }

    private String readValue() {
      int start = position;
      while (!isAtEnd()) {
        char current = input.charAt(position);
        if (current == '(' || current == ')' || current == ',' || Character.isWhitespace(current)) {
          break;
        }
        position++;
      }
      if (start == position) {
        throw new IllegalArgumentException("Missing node value at position " + position);
      }
      return input.substring(start, position);
    }
  }
}
