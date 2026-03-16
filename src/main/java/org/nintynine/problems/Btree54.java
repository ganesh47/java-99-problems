package org.nintynine.problems;

import java.util.Objects;

/** P54A: Check whether a given expression represents a binary tree. */
public final class Btree54 {
  private static final String NIL = "nil";

  /** Represents a node in the binary tree expression. */
  public static class Btree54Node {
    private final String value;
    private final Btree54Node left;
    private final Btree54Node right;

    /**
     * Constructs a binary tree node.
     *
     * @param value The value at this node
     * @param left Left child node or null
     * @param right Right child node or null
     */
    public Btree54Node(String value, Btree54Node left, Btree54Node right) {
      this.value = Objects.requireNonNull(value, "Node value cannot be null");
      this.left = left;
      this.right = right;
    }

    /**
     * Creates a leaf node with no children.
     *
     * @param value The value at this node
     */
    public Btree54Node(String value) {
      this(value, null, null);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Btree54Node btree54Node)) {
        return false;
      }
      return Objects.equals(value, btree54Node.value)
          && Objects.equals(left, btree54Node.left)
          && Objects.equals(right, btree54Node.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, left, right);
    }

    @Override
    public String toString() {
      if (left == null && right == null) {
        if (value.startsWith("(")) {
          return value;
        }
        return String.format("(%s %s %s)", value, NIL, NIL);
      }
      return String.format(
          "(%s %s %s)",
          value, left == null ? NIL : left.toString(), right == null ? NIL : right.toString());
    }
  }

  private Btree54() {} // Prevent instantiation

  /**
   * Checks if a value is valid for a tree node.
   *
   * @param value the value to check
   * @return true if valid, false otherwise
   */
  private static boolean isValidValue(String value) {
    // Check for any special characters or improper formatting
    return value != null
        && !value.isEmpty()
        && !value.contains("(")
        && !value.contains(")")
        && !value.contains(",")
        && !value.contains(" ")
        && !NIL.equals(value);
  }

  /**
   * Checks whether the given expression represents a valid binary tree.
   *
   * @param expression the expression to check
   * @return true if the expression represents a valid binary tree, false otherwise
   */
  public static boolean isTree(String expression) {
    if (expression == null || expression.trim().isEmpty()) {
      return false;
    }

    // Pre-validate the expression format
    String trimmed = expression.trim();

    // Check for invalid formatting patterns
    if (trimmed.contains(")(")
        || // Multiple expressions
        trimmed.contains(",")
        || // Commas
        trimmed.matches(".*\\w\\(.*")
        || // No space before opening parenthesis
        trimmed.matches(".*\\)\\w.*")) { // No space after closing parenthesis
      return false;
    }

    try {
      // Handle single value case
      if (!trimmed.startsWith("(")) {
        return isValidValue(trimmed);
      }

      // For expressions in parentheses, try parsing
      parseTree(trimmed);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Parses a string expression into a binary tree.
   *
   * @param expression The string expression to parse
   * @return The root node of the parsed tree
   * @throws IllegalArgumentException if the expression is invalid
   */
  public static Btree54Node parseTree(String expression) {
    if (expression == null) {
      throw new IllegalArgumentException("Expression cannot be null");
    }

    expression = expression.trim();

    if (!expression.startsWith("(") || !expression.endsWith(")")) {
      if (isValidValue(expression)) {
        return new Btree54Node(expression);
      }
      throw new IllegalArgumentException("Invalid node format: " + expression);
    }

    String[] result = splitExpression(expression);
    String value = result[0];
    String leftExpr = result[1];
    String rightExpr = result[2];

    if (!isValidValue(value)) {
      throw new IllegalArgumentException("Invalid root value: " + value);
    }

    Btree54Node left = NIL.equals(leftExpr) ? null : parseTree(leftExpr);
    Btree54Node right = NIL.equals(rightExpr) ? null : parseTree(rightExpr);

    return new Btree54Node(value, left, right);
  }

  /**
   * Helper to split an expression into root value, left child, and right child.
   *
   * @param expression the expression to split
   * @return String array with [value, left, right]
   */
  private static String[] splitExpression(String expression) {
    // Remove outer parentheses
    String inner = expression.substring(1, expression.length() - 1).trim();

    // The root value is the first token
    int firstSpace = inner.indexOf(' ');
    if (firstSpace == -1) {
      throw new IllegalArgumentException("Invalid expression: " + expression);
    }

    String value = inner.substring(0, firstSpace);
    String remaining = inner.substring(firstSpace + 1).trim();

    // Now split the children
    String[] children = splitChildren(remaining);

    String[] result = new String[3];
    result[0] = value;
    result[1] = children[0];
    result[2] = children[1];

    // Validate "nil" tokens
    if (!NIL.equals(result[1]) && !result[1].startsWith("(")) {
      throw new IllegalArgumentException("Invalid left child format");
    }
    if (!NIL.equals(result[2]) && !result[2].startsWith("(")) {
      throw new IllegalArgumentException("Invalid right child format");
    }

    return result;
  }

  /** Splits the children part of an expression into left and right. */
  private static String[] splitChildren(String childrenPart) {
    String[] result = new String[2];

    if (childrenPart.startsWith(NIL)) {
      result[0] = NIL;
      result[1] = childrenPart.substring(3).trim();
    } else if (childrenPart.startsWith("(")) {
      int splitPos = findClosingParenthesis(childrenPart);
      result[0] = childrenPart.substring(0, splitPos + 1);
      result[1] = childrenPart.substring(splitPos + 1).trim();
    } else {
      throw new IllegalArgumentException("Invalid children format");
    }

    return result;
  }

  /** Finds the index of the closing parenthesis matching the first opening one. */
  private static int findClosingParenthesis(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        count++;
      } else if (s.charAt(i) == ')') {
        count--;
        if (count == 0) {
          return i;
        }
      }
    }
    throw new IllegalArgumentException("Unbalanced parentheses");
  }
}
