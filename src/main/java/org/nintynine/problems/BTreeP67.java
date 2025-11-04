package org.nintynine.problems;

import java.util.Objects;

/**
 * P67: A string representation of binary trees.
 *
 * <p>Supports rendering a binary tree to the compact representation used in the
 * 99-problems kata (e.g. {@code a(b(d,e),c(,f(g,)))}) and parsing the same
 * format back into a tree structure.</p>
 */
public final class BTreeP67 {

    private BTreeP67() {
        // Utility class
    }

    public static final class Node<T> {
        private final T value;
        private final Node<T> left;
        private final Node<T> right;

        private Node(T value, Node<T> left, Node<T> right) {
            this.value = Objects.requireNonNull(value, "value");
            this.left = left;
            this.right = right;
        }

        public static <T> Node<T> leaf(T value) {
            return new Node<>(value, null, null);
        }

        public static <T> Node<T> of(T value, Node<T> left, Node<T> right) {
            return new Node<>(value, left, right);
        }

        public T getValue() {
            return value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Node<?> node)) {
                return false;
            }
            return Objects.equals(value, node.value)
                    && Objects.equals(left, node.left)
                    && Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right);
        }
    }

    public static String toCompactString(Node<?> root) {
        if (root == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        appendNode(root, builder);
        return builder.toString();
    }

    private static void appendNode(Node<?> node, StringBuilder builder) {
        builder.append(node.value);
        if (node.left == null && node.right == null) {
            return;
        }
        builder.append('(');
        if (node.left != null) {
            appendNode(node.left, builder);
        }
        builder.append(',');
        if (node.right != null) {
            appendNode(node.right, builder);
        }
        builder.append(')');
    }

    public static Node<String> parse(String representation) {
        if (representation == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        Parser parser = new Parser(representation);
        parser.skipWhitespace();
        if (parser.isAtEnd()) {
            return null;
        }
        Node<String> root = parser.parseNode();
        parser.skipWhitespace();
        if (!parser.isAtEnd()) {
            throw new IllegalArgumentException("Unexpected trailing content at position " + parser.position);
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
            skipWhitespace();
            if (peek() != expected) {
                throw new IllegalArgumentException("Expected '" + expected + "' at position " + position);
            }
            position++;
        }

        private void skipWhitespace() {
            while (!isAtEnd() && Character.isWhitespace(input.charAt(position))) {
                position++;
            }
        }

        private Node<String> parseNode() {
            skipWhitespace();
            if (peek() == ',' || peek() == ')' || isAtEnd()) {
                return null;
            }

            String value = readValue();
            Node<String> leftChild = null;
            Node<String> rightChild = null;

            skipWhitespace();
            if (peek() == '(') {
                position++; // consume '('
                leftChild = parseNode();
                skipWhitespace();
                consume(',');
                rightChild = parseNode();
                skipWhitespace();
                consume(')');
            }

            return Node.of(value, leftChild, rightChild);
        }

        private String readValue() {
            skipWhitespace();
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
