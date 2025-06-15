package org.nintynine.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class TruthP48 {
  public static void table(List<String> variables, String expression) {
    ExpressionNode expr = parse(expression);
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
          if (Arrays.stream(LogicalOp.values()).anyMatch(op -> op.symbol.equals(token))) {
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

    // Check the last token
    if (parenthesesCount == 0 && !currentToken.isEmpty()) {
      String token = currentToken.toString().trim();
      if (Arrays.stream(LogicalOp.values()).anyMatch(op -> op.symbol.equals(token))) {
        return position;
      }
    }

    return -1;
  }

  private static ExpressionNode parse(String expression) {
    expression = expression.trim();

    // Handle outer parentheses
    while (expression.startsWith("(") && expression.endsWith(")")) {
      // Verify matching parentheses
      int count = 0;
      boolean valid = true;
      for (int i = 0; i < expression.length(); i++) {
        if (expression.charAt(i) == '(') count++;
        if (expression.charAt(i) == ')') {
          count--;
          if (count == 0 && i != expression.length() - 1) {
            valid = false;
            break;
          }
        }
      }
      if (!valid || count != 0) break;
      expression = expression.substring(1, expression.length() - 1).trim();
    }

    // Handle single token
    if (expression.split("\\s+").length == 1) {
      try {
        // It's a variable
        return new VariableNode(expression);
      } catch (NumberFormatException _) {
        throw new IllegalArgumentException("Invalid expression: " + expression);
      }
    }

    // Unary operation (not)
    String[] tokens = expression.split("\\s+");
    if (tokens[0].equals("not")) {
      String remainingExpr = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
      return new UnaryOperationNode(LogicalOp.NOT, parse(remainingExpr));
    }

    // Find the main operator
    int mainOpIndex = findMainOperator(expression);
    if (mainOpIndex == -1) {
      throw new IllegalArgumentException("Invalid expression: " + expression);
    }

    String leftExpr = expression.substring(0, mainOpIndex).trim();
    String op = expression.substring(mainOpIndex, expression.indexOf(' ', mainOpIndex)).trim();
    String rightExpr = expression.substring(expression.indexOf(' ', mainOpIndex) + 1).trim();

    LogicalOp operator =
        Arrays.stream(LogicalOp.values())
            .filter(o -> o.symbol.equals(op))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + op));

    return new BinaryOperationNode(operator, parse(leftExpr), parse(rightExpr));
  }

  private enum LogicalOp {
    AND("and", (a, b) -> a && b),
    OR("or", (a, b) -> a || b),
    NOT("not", a -> !a),
    EQU("equ", (a, b) -> a.booleanValue() == b.booleanValue());

    @SuppressWarnings("PMD.UnusedPrivateField")
    final String symbol;

    final BinaryOperator<Boolean> binaryOp;
    final UnaryOperator<Boolean> unaryOp;

    LogicalOp(String symbol, BinaryOperator<Boolean> op) {
      this.symbol = symbol;
      this.binaryOp = op;
      this.unaryOp = null;
    }

    LogicalOp(String symbol, UnaryOperator<Boolean> op) {
      this.symbol = symbol;
      this.binaryOp = null;
      this.unaryOp = op;
    }

    boolean apply(boolean a, boolean b) {
      assert binaryOp != null;
      return binaryOp.apply(a, b);
    }

    boolean apply(boolean a) {
      assert unaryOp != null;
      return unaryOp.apply(a);
    }
  }

  private abstract static class ExpressionNode {
    abstract boolean evaluate(Map<String, Boolean> variables);

    public abstract String toString();
  }

  private static class BinaryOperationNode extends ExpressionNode {
    private final LogicalOp operator;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinaryOperationNode(LogicalOp operator, ExpressionNode left, ExpressionNode right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
    }

    @Override
    boolean evaluate(Map<String, Boolean> variables) {
      boolean leftResult = left.evaluate(variables);
      boolean rightResult = right.evaluate(variables);
      return operator.apply(leftResult, rightResult);
    }

    @Override
    public String toString() {
      return String.format("(%s %s %s)", left, operator, right);
    }
  }

  private static class UnaryOperationNode extends ExpressionNode {
    private final LogicalOp operator;
    private final ExpressionNode operand;

    public UnaryOperationNode(LogicalOp operator, ExpressionNode operand) {
      this.operator = operator;
      this.operand = operand;
    }

    @Override
    boolean evaluate(Map<String, Boolean> variables) {
      boolean operandResult = operand.evaluate(variables);
      return operator.apply(operandResult);
    }

    @Override
    public String toString() {
      return String.format("(%s %s)", operator, operand);
    }
  }

  private static class VariableNode extends ExpressionNode {
    private final String name;

    public VariableNode(String name) {
      this.name = name;
    }

    @Override
    boolean evaluate(Map<String, Boolean> variables) {
      return variables.get(name);
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
