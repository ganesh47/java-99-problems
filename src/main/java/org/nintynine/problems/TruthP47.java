package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** P47: Truth tables for logical expressions (infix notation). */
public final class TruthP47 {

  private TruthP47() {
    // utility class
  }

  /**
   * Generates a truth table for a given logical expression in infix notation.
   *
   * @param expression the logical expression
   * @return list of truth table rows
   * @throws IllegalArgumentException if expression is invalid
   */
  public static List<TruthTableRow> table(String expression) {
    LogicalExpression.ExpressionNode root = parseInfixExpression(expression);
    List<TruthTableRow> rows = new ArrayList<>();

    // Generate all possible combinations of A and B
    for (boolean a : new boolean[] {false, true}) {
      for (boolean b : new boolean[] {false, true}) {
        boolean result = root.evaluate(Map.of("A", a, "B", b));
        rows.add(new TruthTableRow(a, b, result));
      }
    }

    return rows;
  }

  private static LogicalExpression.ExpressionNode parseInfixExpression(String expression) {
    expression = expression.trim();

    if (expression.length() == 1) {
      char var = expression.charAt(0);
      if (var == 'A' || var == 'B') {
        return new LogicalExpression.VariableNode(String.valueOf(var));
      }
      throw new IllegalArgumentException("Invalid variable: " + var);
    }

    if (expression.startsWith("(") && expression.endsWith(")")) {
      String inner = expression.substring(1, expression.length() - 1).trim();
      if (inner.length() == 1 && (inner.equals("A") || inner.equals("B"))) {
        throw new IllegalArgumentException(
            "Single variables should not be parenthesized: " + expression);
      }
    }

    if (!expression.startsWith("(") || !expression.endsWith(")")) {
      throw new IllegalArgumentException("Expression must be fully parenthesized: " + expression);
    }

    expression = expression.substring(1, expression.length() - 1).trim();

    int operatorIndex = findMainOperator(expression);
    if (operatorIndex == -1) {
      if (expression.startsWith("not ")) {
        String operand = expression.substring(4).trim();
        return new LogicalExpression.UnaryOperationNode(
            LogicalExpression.LogicalOp.NOT, parseInfixExpression(operand));
      }
      return parseInfixExpression(expression);
    }

    String operatorSymbol = findOperatorString(expression, operatorIndex);
    String leftExpr = expression.substring(0, operatorIndex).trim();
    String rightExpr = expression.substring(operatorIndex + operatorSymbol.length()).trim();

    LogicalExpression.LogicalOp op =
        LogicalExpression.LogicalOp.fromString(operatorSymbol)
            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + operatorSymbol));

    if (op.isUnary()) {
      return new LogicalExpression.UnaryOperationNode(op, parseInfixExpression(rightExpr));
    } else {
      return new LogicalExpression.BinaryOperationNode(
          op, parseInfixExpression(leftExpr), parseInfixExpression(rightExpr));
    }
  }

  private static int findMainOperator(String expression) {
    int parenthesesCount = 0;
    int lastOperatorIndex = -1;

    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);

      if (c == '(') {
        parenthesesCount++;
      } else if (c == ')') {
        parenthesesCount--;
      } else if (parenthesesCount == 0) {
        String remainingExpr = expression.substring(i);
        for (LogicalExpression.LogicalOp op : LogicalExpression.LogicalOp.values()) {
          if (remainingExpr.startsWith(op.symbol)
              && (i == 0 || Character.isWhitespace(expression.charAt(i - 1)))) {
            lastOperatorIndex = i;
            break;
          }
        }
      }
    }

    return lastOperatorIndex;
  }

  private static String findOperatorString(String expression, int startIndex) {
    for (LogicalExpression.LogicalOp op : LogicalExpression.LogicalOp.values()) {
      if (expression.substring(startIndex).startsWith(op.symbol)) {
        return op.symbol;
      }
    }
    throw new IllegalArgumentException("No valid operator found at position " + startIndex);
  }

  /** Formats a truth table as a string. */
  public static String formatTruthTable(List<TruthTableRow> table) {
    StringBuilder sb = new StringBuilder();
    sb.append("   A     B   Result\n");
    sb.append("-------------------\n");
    table.forEach(row -> sb.append(row).append('\n'));
    return sb.toString();
  }

  /** Represents a truth table row. */
  public record TruthTableRow(boolean a, boolean b, boolean result) {
    @Override
    public String toString() {
      return String.format("%5s %5s %7s", a, b, result);
    }
  }
}
