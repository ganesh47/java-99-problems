package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** P46: Truth tables for logical expressions. */
public final class TruthP46 {

  private TruthP46() {
    // utility class
  }

  /**
   * Generates a truth table for a given logical expression.
   *
   * @param expression the logical expression in prefix notation
   * @return list of truth table rows
   * @throws IllegalArgumentException if the expression is invalid
   */
  public static List<TruthTableRow> table(String expression) {
    LogicalExpression.ExpressionNode root = parseExpression(expression);
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

  private static LogicalExpression.ExpressionNode parseExpression(String expression) {
    expression = expression.trim();

    if (expression.length() == 1) {
      char var = expression.charAt(0);
      if (var == 'A' || var == 'B') {
        return new LogicalExpression.VariableNode(String.valueOf(var));
      }
      throw new IllegalArgumentException("Invalid variable: " + var + " (must be A or B)");
    }

    if (expression.startsWith("(") && expression.endsWith(")")) {
      expression = expression.substring(1, expression.length() - 1).trim();

      int firstSpace = expression.indexOf(' ');
      if (firstSpace == -1) {
        throw new IllegalArgumentException("Invalid expression format");
      }

      String operatorSymbol = expression.substring(0, firstSpace);
      String remainingExpr = expression.substring(firstSpace + 1).trim();

      String[] operands = splitOperands(remainingExpr);
      if (operands.length != 2) {
        throw new IllegalArgumentException("Expected two operands");
      }

      LogicalExpression.LogicalOp op =
          LogicalExpression.LogicalOp.fromString(operatorSymbol)
              .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + operatorSymbol));

      return new LogicalExpression.BinaryOperationNode(
          op, parseExpression(operands[0]), parseExpression(operands[1]));
    }

    throw new IllegalArgumentException("Invalid expression format");
  }

  private static String[] splitOperands(String expr) {
    List<String> operands = new ArrayList<>();
    int parenthesesCount = 0;
    StringBuilder current = new StringBuilder();

    for (char c : expr.toCharArray()) {
      if (c == '(') {
        parenthesesCount++;
      } else if (c == ')') {
        parenthesesCount--;
      }

      if (c == ' ' && parenthesesCount == 0) {
        if (!current.isEmpty()) {
          operands.add(current.toString().trim());
          current = new StringBuilder();
        }
      } else {
        current.append(c);
      }
    }

    if (!current.isEmpty()) {
      operands.add(current.toString().trim());
    }

    return operands.toArray(new String[0]);
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
