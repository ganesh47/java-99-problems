package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * P47: Truth tables for logical expressions (infix notation).
 */
public class TruthP47 {
    /**
     * Represents a logical operation that can be performed on boolean values.
     */
    public enum LogicalOp {
        AND("and", (a, b) -> a && b),
        OR("or", (a, b) -> a || b),
        NAND("nand", (a, b) -> !(a && b)),
        NOR("nor", (a, b) -> !(a || b)),
        XOR("xor", (a, b) -> a ^ b),
        IMPL("impl", (a, b) -> !a || b),
        EQU("equ", (a, b) -> a.booleanValue() == b.booleanValue()),
        NOT("not", a -> !a, true);

        @SuppressWarnings("PMD.UnusedPrivateField")
        private final String symbol;
        private final BiFunction<Boolean, Boolean, Boolean> binaryOp;
        private final UnaryOperator<Boolean> unaryOp;
        private final boolean isUnary;

        LogicalOp(String symbol, BiFunction<Boolean, Boolean, Boolean> operation) {
            this(symbol, operation, null, false);
        }

        LogicalOp(String symbol, UnaryOperator<Boolean> operation, boolean isUnary) {
            this(symbol, null, operation, isUnary);
        }

        LogicalOp(String symbol, BiFunction<Boolean, Boolean, Boolean> binaryOp,
                  UnaryOperator<Boolean> unaryOp, boolean isUnary) {
            this.symbol = symbol;
            this.binaryOp = binaryOp;
            this.unaryOp = unaryOp;
            this.isUnary = isUnary;
        }

        public boolean apply(boolean a, boolean b) {
            if (isUnary) {
                throw new IllegalStateException("Cannot apply binary operation to unary operator");
            }
            return binaryOp.apply(a, b);
        }

        public boolean apply(boolean a) {
            if (!isUnary) {
                throw new IllegalStateException("Cannot apply unary operation to binary operator");
            }
            return unaryOp.apply(a);
        }

        public boolean isUnary() {
            return isUnary;
        }

        public static Optional<LogicalOp> fromString(String symbol) {
            return Arrays.stream(values())
                    .filter(op -> op.symbol.equals(symbol))
                    .findFirst();
        }
    }

    /**
     * Represents a node in the expression tree.
     */
    private static abstract class ExpressionNode {
        abstract boolean evaluate(boolean a, boolean b);
    }

    /**
     * Represents a variable (A or B) in the expression.
     */
    private static class VariableNode extends ExpressionNode {
        private final char variable;

        public VariableNode(char variable) {
            this.variable = variable;
        }

        @Override
        boolean evaluate(boolean a, boolean b) {
            return variable == 'A' ? a : b;
        }
    }

    /**
     * Represents a binary operation node.
     */
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
        boolean evaluate(boolean a, boolean b) {
            return operator.apply(
                    left.evaluate(a, b),
                    right.evaluate(a, b)
            );
        }
    }

    /**
     * Represents a unary operation node (NOT).
     */
    private static class UnaryOperationNode extends ExpressionNode {
        private final LogicalOp operator;
        private final ExpressionNode operand;

        public UnaryOperationNode(LogicalOp operator, ExpressionNode operand) {
            this.operator = operator;
            this.operand = operand;
        }

        @Override
        boolean evaluate(boolean a, boolean b) {
            return operator.apply(operand.evaluate(a, b));
        }
    }

    /**
     * Represents a truth table row.
     */
    public record TruthTableRow(boolean a, boolean b, boolean result) {
        @Override
        public String toString() {
            return String.format("%5s %5s %7s", a, b, result);
        }
    }

    /**
     * Generates a truth table for a given logical expression in infix notation.
     *
     * @param expression the logical expression
     * @return list of truth table rows
     * @throws IllegalArgumentException if expression is invalid
     */
    public static List<TruthTableRow> table(String expression) {
        ExpressionNode root = parseInfixExpression(expression);
        List<TruthTableRow> rows = new ArrayList<>();

        // Generate all possible combinations of A and B
        for (boolean a : new boolean[]{false, true}) {
            for (boolean b : new boolean[]{false, true}) {
                boolean result = root.evaluate(a, b);
                rows.add(new TruthTableRow(a, b, result));
            }
        }

        return rows;
    }

    /**
     * Parses an infix expression and builds an expression tree.
     */
    private static ExpressionNode parseInfixExpression(String expression) {
        expression = expression.trim();

        // Handle single variables
        if (expression.length() == 1) {
            char var = expression.charAt(0);
            if (var == 'A' || var == 'B') {
                return new VariableNode(var);
            }
            throw new IllegalArgumentException("Invalid variable: " + var);
        }

        // Check if this is a parenthesized single variable - illegal
        if (expression.startsWith("(") && expression.endsWith(")")) {
            String inner = expression.substring(1, expression.length() - 1).trim();
            if (inner.length() == 1 && (inner.equals("A") || inner.equals("B"))) {
                throw new IllegalArgumentException(
                    "Single variables should not be parenthesized: " + expression);
            }
        }

        // Rest of the validation and parsing...
        // Require parentheses for all non-single-variable expressions
        if (!expression.startsWith("(") || !expression.endsWith(")")) {
            throw new IllegalArgumentException(
                "Expression must be fully parenthesized: " + expression);
        }

        // Remove outer parentheses
        expression = expression.substring(1, expression.length() - 1).trim();

        // Find the main operator
        int operatorIndex = findMainOperator(expression);
        if (operatorIndex == -1) {
            // Might be a NOT expression or nested expression
            if (expression.startsWith("not ")) {
                String operand = expression.substring(4).trim();
                return new UnaryOperationNode(
                        LogicalOp.NOT,
                        parseInfixExpression(operand)
                );
            }
            return parseInfixExpression(expression);
        }

        // Split into operator and operands
        String operator = findOperatorString(expression, operatorIndex);
        String leftExpr = expression.substring(0, operatorIndex).trim();
        String rightExpr = expression.substring(operatorIndex + operator.length()).trim();

        LogicalOp op = LogicalOp.fromString(operator)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + operator));

        if (op.isUnary()) {
            return new UnaryOperationNode(
                    op,
                    parseInfixExpression(rightExpr)
            );
        } else {
            return new BinaryOperationNode(
                    op,
                    parseInfixExpression(leftExpr),
                    parseInfixExpression(rightExpr)
            );
        }

        // Rest of the method remains the same...
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
                // Try to match any operator starting at current position
                String remainingExpr = expression.substring(i);
                for (LogicalOp op : LogicalOp.values()) {
                    if (remainingExpr.startsWith(op.symbol) &&
                        (i == 0 || Character.isWhitespace(expression.charAt(i - 1)))) {
                        lastOperatorIndex = i;
                        break;
                    }
                }
            }
        }

        return lastOperatorIndex;
    }

    /**
     * Extracts the operator string from an expression.
     */
    private static String findOperatorString(String expression, int startIndex) {
        for (LogicalOp op : LogicalOp.values()) {
            if (expression.substring(startIndex).startsWith(op.symbol)) {
                return op.symbol;
            }
        }
        throw new IllegalArgumentException("No valid operator found at position " + startIndex);
    }

    /**
     * Formats a truth table as a string.
     */
    public static String formatTruthTable(List<TruthTableRow> table) {
        StringBuilder sb = new StringBuilder();
        sb.append("   A     B   Result\n");
        sb.append("-------------------\n");
        table.forEach(row -> sb.append(row).append('\n'));
        return sb.toString();
    }
}
