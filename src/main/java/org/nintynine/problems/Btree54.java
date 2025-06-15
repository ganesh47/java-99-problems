package org.nintynine.problems;

import java.util.Objects;

/** P54A: Check whether a given expression represents a binary tree */
public class Btree54 {
  /** Represents a node in the binary tree expression */
  public static class Btree54Node {
    private final String value;
    private final Btree54Node left;
    private final Btree54Node right;

    /**
     * Constructs a binary tree node
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
     * Creates a leaf node with no children
     *
     * @param value The value at this node
     */
    public Btree54Node(String value) {
      this(value, null, null);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Btree54Node bTree54Node)) return false;
      return Objects.equals(value, bTree54Node.value)
          && Objects.equals(left, bTree54Node.left)
          && Objects.equals(right, bTree54Node.right);
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
        return String.format("(%s nil nil)", value);
      }
      return String.format(
          "(%s %s %s)",
          value, left == null ? "nil" : left.toString(), right == null ? "nil" : right.toString());
    }
  }

  private Btree54() {} // Prevent instantiation

  private static boolean isValidValue(String value) {
    // Check for any special characters or improper formatting
    return value != null
        && !value.isEmpty()
        && !value.contains("(")
        && !value.contains(")")
        && !value.contains(",")
        && !value.contains(" ")
        && !"nil".equals(value);
  }

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
    } catch (IllegalArgumentException _) {
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

    // Handle single value case
    if (!expression.startsWith("(")) {
      if (isValidValue(expression)) {
        return new Btree54Node(expression);
      }
      throw new IllegalArgumentException("Invalid value: " + expression);
    }

    // Remove outer parentheses
    if (!expression.endsWith(")")) {
      throw new IllegalArgumentException("Missing closing parenthesis");
    }
    expression = expression.substring(1, expression.length() - 1).trim();

    // Split into value and subtrees
    String[] parts = splitExpression(expression);
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid node format");
    }

    String value = parts[0];
    String leftExpr = parts[1];
    String rightExpr = parts[2];

    if (!isValidValue(value)) {
      throw new IllegalArgumentException("Invalid node value");
    }

    Btree54Node left = "nil".equals(leftExpr) ? null : parseTree(leftExpr);
    Btree54Node right = "nil".equals(rightExpr) ? null : parseTree(rightExpr);

    return new Btree54Node(value, left, right);
  }

  @SuppressWarnings("java:S5852")
  private static String[] splitExpression(String expr) {
    // Add space validation
    if (expr.contains(")(") || expr.matches(".*\\w\\(.*") || expr.matches(".*\\)\\w.*")) {
      throw new IllegalArgumentException("Invalid expression format");
    }

    String[] result = new String[3];
    int idx = 0;
    StringBuilder current = new StringBuilder();
    int parenthesesCount = 0;

    for (char c : expr.toCharArray()) {
      if (c == '(') {
        parenthesesCount++;
        current.append(c);
      } else if (c == ')') {
        parenthesesCount--;
        current.append(c);
        if (parenthesesCount < 0) {
          throw new IllegalArgumentException("Unmatched parentheses");
        }
      } else if (c == ' ' && parenthesesCount == 0 && !current.isEmpty()) {
        // Only split on space when not inside parentheses
        if (idx >= 3) {
          throw new IllegalArgumentException("Too many parts in expression");
        }
        result[idx++] = current.toString();
        current = new StringBuilder();
      } else if (c != ' ' || parenthesesCount > 0) {
        current.append(c);
      }
    }

    if (!current.isEmpty()) {
      if (idx >= 3) {
        throw new IllegalArgumentException("Too many parts in expression");
      }
      result[idx] = current.toString();
    }

    // Must have exactly three parts
    if (idx != 2 || result[0] == null || result[1] == null || result[2] == null) {
      throw new IllegalArgumentException("Invalid expression format");
    }

    // Validate "nil" tokens
    if (!"nil".equals(result[1]) && !result[1].startsWith("(")) {
      throw new IllegalArgumentException("Invalid left child format");
    }
    if (!"nil".equals(result[2]) && !result[2].startsWith("(")) {
      throw new IllegalArgumentException("Invalid right child format");
    }

    return result;
  }
}
