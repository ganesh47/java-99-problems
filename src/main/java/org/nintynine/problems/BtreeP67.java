package org.nintynine.problems;

import java.util.Objects; // For Objects.equals and Objects.hash

public class BtreeP67<T> {

    static class Node<T> { // Changed from private static to static (package-private)
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() { // For debugging
            return "Node{" + "value=" + value +
                   (left != null && left.value != null ? ", L:" + left.value : "") +
                   (right != null && right.value != null ? ", R:" + right.value : "") +
                   '}';
        }

        // Node equality needed for tree equality
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            // Check current node's value, then recursively check children
            return Objects.equals(value, node.value) &&
                   Objects.equals(left, node.left) &&  // relies on Node.equals for left subtree
                   Objects.equals(right, node.right); // relies on Node.equals for right subtree
        }

        @Override
        public int hashCode() {
            // Combine hash codes of value and children
            return Objects.hash(value, left, right); // relies on Node.hashCode for children
        }
    }

    private Node<T> root;

    public BtreeP67() {
        this.root = null;
    }

    // Constructor to create a tree with a given root.
    public BtreeP67(Node<T> root) {
        this.root = root;
    }

    // Getter for the root, might be useful for testing.
    public Node<T> getRoot() {
        return root;
    }

    public static BtreeP67<String> fromString(String representation) {
        if (representation == null || representation.isEmpty()) {
            return new BtreeP67<>(null); // Tree with null root for empty string
        }
        int[] index = {0}; // Current parsing position in the string
        Node<String> rootNode = parseInternal(representation, index);

        // After parsing, index should be at the end of the string.
        // If not, it means there are unparsed characters, so the string is malformed.
        if (index[0] != representation.length()) {
            throw new IllegalArgumentException("Malformed tree string: unexpected characters after parsing. Index: " + index[0] + ", String length: " + representation.length() + ", String: '" + representation + "'");
        }

        return new BtreeP67<>(rootNode);
    }

    private static Node<String> parseInternal(String s, int[] index) {
        // Base case: if we are at the end of string or at a separator for an empty spot
        if (index[0] >= s.length()) {
            // This can happen if e.g. string ends prematurely like "a("
            // The checks for comma/parenthesis later will catch this if it's mid-structure.
            // If it's a valid end of a recursive call (e.g. for a null child), this is fine.
            return null;
        }
        char currentChar = s.charAt(index[0]);
        if (currentChar == ',' || currentChar == ')') { // Indicates an empty subtree spot
            return null;
        }

        // Parse the value of the current node (assuming single character values)
        String value = String.valueOf(currentChar);
        Node<String> currentNode = new Node<>(value);
        index[0]++; // Consume the character for the node's value

        // Check if this node has children, indicated by an opening parenthesis '('
        if (index[0] < s.length() && s.charAt(index[0]) == '(') {
            index[0]++; // Consume '('

            // Parse the left child. This will return null if the left child is empty (e.g., ",c").
            currentNode.left = parseInternal(s, index);

            // After parsing the left child, a comma must follow.
            if (index[0] >= s.length() || s.charAt(index[0]) != ',') {
                throw new IllegalArgumentException("Malformed tree string: expected ',' separator after left child/subtree for node '" + value + "' near index " + index[0] + " in '" + s + "'");
            }
            index[0]++; // Consume ','

            // Parse the right child. This will return null if the right child is empty (e.g., "b,").
            currentNode.right = parseInternal(s, index);

            // After parsing the right child, a closing parenthesis must follow.
            if (index[0] >= s.length() || s.charAt(index[0]) != ')') {
                throw new IllegalArgumentException("Malformed tree string: expected ')' to close children of node '" + value + "' near index " + index[0] + " in '" + s + "'");
            }
            index[0]++; // Consume ')'
        }
        // If no '(', it's a leaf node; its left and right children remain null by default.
        return currentNode;
    }

    @Override
    public String toString() {
        if (root == null) {
            return ""; // Consistent with fromString("") creating a tree with null root
        }
        StringBuilder sb = new StringBuilder();
        buildStringInternal(root, sb);
        return sb.toString();
    }

    private void buildStringInternal(Node<T> node, StringBuilder sb) {
        // This method is called only for non-null nodes by its callers.
        sb.append(node.value);

        // A node is a "parent" (needs parentheses) if it has at least one non-null child.
        // If both children are null, it's a leaf node, and no parentheses are printed.
        if (node.left != null || node.right != null) {
            sb.append('(');
            if (node.left != null) {
                buildStringInternal(node.left, sb); // Recursively build string for left child
            }
            // If left child is null, nothing is appended for its part.

            sb.append(',');

            if (node.right != null) {
                buildStringInternal(node.right, sb); // Recursively build string for right child
            }
            // If right child is null, nothing is appended for its part.

            sb.append(')');
        }
    }

    // For comparing tree structures.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BtreeP67<?> that = (BtreeP67<?>) o;
        // Tree equality depends on root node equality (which is recursive)
        return Objects.equals(root, that.root);
    }

    @Override
    public int hashCode() {
        // Tree hash code depends on root node hash code (which is recursive)
        return Objects.hash(root);
    }
}
