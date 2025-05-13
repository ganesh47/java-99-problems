
package org.nintynine.problems;

import java.util.*;
import java.util.function.BiFunction;

/**
 * P46: Truth tables for logical expressions.
 */
public class TruthP46 {
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
        EQU("equ", (a, b) -> a == b);

        private final String symbol;
        private final BiFunction<Boolean, Boolean, Boolean> operation;

        LogicalOp(String symbol, BiFunction<Boolean, Boolean, Boolean> operation) {
            this.symbol = symbol;
            this.operation = operation;
        }

        public boolean apply(boolean a, boolean b) {
            return operation.apply(a, b);
        }

        public static Optional<LogicalOp> fromString(String symbol) {
            return Arrays.stream(values())
                    .filter(op -> op.symbol.equals(symbol))
                    .findFirst();
        }
    }

    /**
     * Represents a logical expression node in the expression tree.
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
     * Represents an operation node with two operands.
     */
    private static class OperationNode extends ExpressionNode {
        private final LogicalOp operator;
        private final ExpressionNode left;
        private final ExpressionNode right;

        public OperationNode(LogicalOp operator, ExpressionNode left, ExpressionNode right) {
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
     * Represents a truth table row.
     */
    public record TruthTableRow(boolean a, boolean b, boolean result) {
        @Override
        public String toString() {
            return String.format("%5s %5s %7s", a, b, result);
        }
    }

    /**
     * Generates a truth table for a given logical expression.
     *
     * @param expression the logical expression in prefix notation
     * @return list of truth table rows
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static List<TruthTableRow> table(String expression) {
        ExpressionNode root = parseExpression(expression);
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
     * Parses a logical expression in prefix notation.
     *
     * @param expression the expression to parse
     * @return root node of the expression tree
     * @throws IllegalArgumentException if the expression is invalid
     */
    private static ExpressionNode parseExpression(String expression) {
        expression = expression.trim();

        // Handle single variables
        if (expression.length() == 1) {
            char var = expression.charAt(0);
            if (var == 'A' || var == 'B') {
                return new VariableNode(var);
            }
            throw new IllegalArgumentException("Invalid variable: " + var+" (must be A or B)");
        }

        // Handle operations
        if (expression.startsWith("(") && expression.endsWith(")")) {
            // Remove outer parentheses
            expression = expression.substring(1, expression.length() - 1).trim();

            // Split into operator and operands
            int firstSpace = expression.indexOf(' ');
            if (firstSpace == -1) {
                throw new IllegalArgumentException("Invalid expression format : " + expression + " (missing space between operator and operands)");
            }

            String operator = expression.substring(0, firstSpace);
            String remainingExpr = expression.substring(firstSpace + 1).trim();

            // Find the two operands
            String[] operands = splitOperands(remainingExpr);
            if (operands.length != 2) {
                throw new IllegalArgumentException("Expected two operands");
            }

            LogicalOp op = LogicalOp.fromString(operator)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + operator));

            return new OperationNode(
                    op,
                    parseExpression(operands[0]),
                    parseExpression(operands[1])
            );
        }

        throw new IllegalArgumentException("Invalid expression format");
    }

    /**
     * Splits an expression into operands, handling nested parentheses.
     */
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
