package org.nintynine.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem P48: truth tables for logical expressions.
 */
public final class TruthP48 {

  private TruthP48() {
    // utility class
  }

  /**
   * Generates a truth table for the given expression and variables.
   *
   * @param variables the variables in the expression
   * @param expression the logical expression
   */
  public static void table(List<String> variables, String expression) {
    LogicalExpression.ExpressionNode expr = parse(expression);
    int columnWidth = 8; // Width for each column to accommodate "false" and "true"

    // Print header
    for (String var : variables) {
      System.out.printf("%-" + columnWidth + "s", var);
    }
    System.out.println("Result");

    // Print separator line
    System.out.println("-".repeat(variables.size() * columnWidth + 6));

    // Generate all possible combinations
    int combinations = 1 << variables.size();
    for (int i = 0; i < combinations; i++) {
      Map<String, Boolean> values = new HashMap<>();
      for (int j = 0; j < variables.size(); j++) {
        boolean value = (i & (1 << j)) != 0;
        values.put(variables.get(j), value);
      }


      // Print values with proper spacing
      for (String var : variables) {
        System.out.printf("%-" + columnWidth + "s", values.get(var).toString());
      }

      // Evaluate and print result
      boolean result = expr.evaluate(values);
      System.out.printf("%s%n", result);
    }
  }

  private static int findMainOperator(String expression) {
    int parenthesesCount = 0;
    int position = 0;
    StringBuilder currentToken = new StringBuilder();

    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);

      if (c == '(') {
        parenthesesCount++;
        continue;
      } else if (c == ')') {
        parenthesesCount--;
        continue;
      }

      if (c == ' ') {
        if (parenthesesCount == 0 && !currentToken.isEmpty()) {
          String token = currentToken.toString().trim();
          if (LogicalExpression.LogicalOp.fromString(token).isPresent()) {
            return position;
          }
        }
        position++;
        currentToken = new StringBuilder();
        continue;
      }

      currentToken.append(c);
      if (currentToken.length() == 1) {
        position = i;
      }
    }

    if (parenthesesCount == 0 && !currentToken.isEmpty()) {
      String token = currentToken.toString().trim();
      if (LogicalExpression.LogicalOp.fromString(token).isPresent()) {
        return position;
      }
    }

    return -1;
  }

  private static LogicalExpression.ExpressionNode parse(String expression) {
    expression = expression.trim();

    while (expression.startsWith("(") && expression.endsWith(")")) {
      int count = 0;
      boolean valid = true;
      for (int i = 0; i < expression.length(); i++) {
        if (expression.charAt(i) == '(') {
          count++;
        }
        if (expression.charAt(i) == ')') {
          count--;
          if (count == 0 && i != expression.length() - 1) {
            valid = false;
            break;
          }
        }
      }
      if (!valid || count != 0) {
        break;
      }
      expression = expression.substring(1, expression.length() - 1).trim();
    }

    if (expression.split("\\s+").length == 1) {
      return new LogicalExpression.VariableNode(expression);
    }

    String[] tokens = expression.split("\\s+");
    if (tokens[0].equals("not")) {
      String remainingExpr = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
      return new LogicalExpression.UnaryOperationNode(LogicalExpression.LogicalOp.NOT, parse(remainingExpr));
    }

    int mainOpIndex = findMainOperator(expression);
    if (mainOpIndex == -1) {
      throw new IllegalArgumentException("Invalid expression: " + expression);
    }

    String leftExpr = expression.substring(0, mainOpIndex).trim();
    int nextSpace = expression.indexOf(' ', mainOpIndex);
    String opSymbol = (nextSpace == -1) ? expression.substring(mainOpIndex) : expression.substring(mainOpIndex, nextSpace).trim();
    String rightExpr = (nextSpace == -1) ? "" : expression.substring(nextSpace + 1).trim();

    LogicalExpression.LogicalOp operator =
        LogicalExpression.LogicalOp.fromString(opSymbol)
            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + opSymbol));

    return new LogicalExpression.BinaryOperationNode(operator, parse(leftExpr), parse(rightExpr));
  }
}
