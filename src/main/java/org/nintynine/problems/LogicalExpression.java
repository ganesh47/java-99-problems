package org.nintynine.problems;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * Shared logic for logical expressions used in P46, P47, and P48.
 */
public final class LogicalExpression {

  private LogicalExpression() {
    // utility class
  }

  /** Represents a logical operation. */
  public enum LogicalOp {
    AND("and", (a, b) -> a && b),
    OR("or", (a, b) -> a || b),
    NAND("nand", (a, b) -> !(a && b)),
    NOR("nor", (a, b) -> !(a || b)),
    XOR("xor", (a, b) -> a ^ b),
    IMPL("impl", (a, b) -> !a || b),
    EQU("equ", (a, b) -> a.booleanValue() == b.booleanValue()),
    NOT("not", a -> !a);

    final String symbol;
    private final BiFunction<Boolean, Boolean, Boolean> binaryOp;
    private final UnaryOperator<Boolean> unaryOp;

    LogicalOp(String symbol, BiFunction<Boolean, Boolean, Boolean> operation) {
      this.symbol = symbol;
      this.binaryOp = operation;
      this.unaryOp = null;
    }

    LogicalOp(String symbol, UnaryOperator<Boolean> operation) {
      this.symbol = symbol;
      this.binaryOp = null;
      this.unaryOp = operation;
    }

    public static Optional<LogicalOp> fromString(String symbol) {
      return Arrays.stream(values()).filter(op -> op.symbol.equals(symbol)).findFirst();
    }

    public boolean apply(boolean a, boolean b) {
      if (binaryOp == null) {
        throw new IllegalStateException("Not a binary operator: " + this);
      }
      return binaryOp.apply(a, b);
    }

    public boolean apply(boolean a) {
      if (unaryOp == null) {
        throw new IllegalStateException("Not a unary operator: " + this);
      }
      return unaryOp.apply(a);
    }

    public boolean isUnary() {
      return unaryOp != null;
    }
  }

  /** Base node for expression tree. */
  public abstract static class ExpressionNode {
    /** Evaluates the expression with the given variable values. */
    public abstract boolean evaluate(Map<String, Boolean> variables);
  }

  /** Node representing a variable. */
  public static final class VariableNode extends ExpressionNode {
    private final String name;

    public VariableNode(String name) {
      this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> variables) {
      Boolean value = variables.get(name);
      if (value == null) {
        throw new IllegalArgumentException("Missing value for variable: " + name);
      }
      return value;
    }
  }

  /** Node representing a binary operation. */
  public static final class BinaryOperationNode extends ExpressionNode {
    private final LogicalOp operator;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinaryOperationNode(LogicalOp operator, ExpressionNode left, ExpressionNode right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> variables) {
      return operator.apply(left.evaluate(variables), right.evaluate(variables));
    }
  }

  /** Node representing a unary operation. */
  public static final class UnaryOperationNode extends ExpressionNode {
    private final LogicalOp operator;
    private final ExpressionNode operand;

    public UnaryOperationNode(LogicalOp operator, ExpressionNode operand) {
      this.operator = operator;
      this.operand = operand;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> variables) {
      return operator.apply(operand.evaluate(variables));
    }
  }
}
