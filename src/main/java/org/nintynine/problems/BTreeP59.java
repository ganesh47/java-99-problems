package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
public class BTreeP59 {
    private BTreeP59() {
    }

    public static class Node {
        char value;
        Node left;
        Node right;

        Node(char value) {
            this.value = value;
            left = null;
            right = null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right);
        }

        @Override
        public String toString() {
            if (left == null && right == null) return String.valueOf(value);
            return String.format("%c(%s,%s)", value,
                    left == null ? "NIL" : left.toString(),
                    right == null ? "NIL" : right.toString());
        }
    }

    public static List<Node> hbalTrees(int height) {
        if (height < 0) return new ArrayList<>();
        return generateHbalTrees(height);
    }

    private static List<Node> generateHbalTrees(int height) {
        List<Node> result = new ArrayList<>();

        if (height == 0) {
            result.add(null);
            return result;
        }

        if (height == 1) {
            result.add(new Node('X'));
            return result;
        }

        // Trees of height h can have subtrees of height h-1 or h-2
        List<Node> heightMinus1 = generateHbalTrees(height - 1);
        List<Node> heightMinus2 = generateHbalTrees(height - 2);

        // Case 1: Both subtrees have height h-1
        for (Node left : heightMinus1) {
            for (Node right : heightMinus1) {
                Node root = new Node('X');
                root.left = cloneTree(left);
                root.right = cloneTree(right);
                result.add(root);
            }
        }

        // Case 2: Left subtree has height h-1, right has height h-2
        for (Node left : heightMinus1) {
            for (Node right : heightMinus2) {
                Node root = new Node('X');
                root.left = cloneTree(left);
                root.right = cloneTree(right);
                result.add(root);
            }
        }

        // Case 3: Left subtree has height h-2, right has height h-1
        for (Node left : heightMinus2) {
            for (Node right : heightMinus1) {
                Node root = new Node('X');
                root.left = cloneTree(left);
                root.right = cloneTree(right);
                result.add(root);
            }
        }

        return result;
    }

    private static Node cloneTree(Node root) {
        if (root == null) return null;
        Node clone = new Node(root.value);
        clone.left = cloneTree(root.left);
        clone.right = cloneTree(root.right);
        return clone;
    }

    public static int height(Node root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    protected static boolean isHeightBalanced(Node root) {
        if (root == null) return true;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && isHeightBalanced(root.left)
                && isHeightBalanced(root.right);
    }
}
