package org.nintynine.problems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BTreeP58 {
    private BTreeP58() {}
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
            return super.hashCode();
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

    public static List<Node> symCbalTrees(int nodes) {
        if (nodes % 2 == 0) return new ArrayList<>();
        return generateSymCbalTrees(nodes);
    }

    private static List<Node> generateSymCbalTrees(int nodes) {
        List<Node> result = new ArrayList<>();
        
        if (nodes == 0) return result;
        if (nodes == 1) {
            result.add(new Node('X'));
            return result;
        }

        int remainingNodes = nodes - 1;
        if (remainingNodes % 2 != 0) return result;
        
        List<Node> subtrees = generateBalancedSubtrees(remainingNodes / 2);
        for (Node leftSubtree : subtrees) {
            Node root = new Node('X');
            root.left = cloneTree(leftSubtree);
            root.right = cloneTree(mirrorTree(leftSubtree));
            result.add(root);
        }
        
        return result;
    }

    private static List<Node> generateBalancedSubtrees(int nodes) {
        List<Node> result = new ArrayList<>();
        
        if (nodes == 0) {
            result.add(null);
            return result;
        }
        if (nodes == 1) {
            result.add(new Node('X'));
            return result;
        }

        int remainingNodes = nodes - 1;
        for (int leftNodes = remainingNodes / 2; leftNodes <= (remainingNodes + 1) / 2; leftNodes++) {
            int rightNodes = remainingNodes - leftNodes;
            List<Node> leftSubtrees = generateBalancedSubtrees(leftNodes);
            List<Node> rightSubtrees = generateBalancedSubtrees(rightNodes);

            for (Node left : leftSubtrees) {
                for (Node right : rightSubtrees) {
                    Node root = new Node('X');
                    root.left = cloneTree(left);
                    root.right = cloneTree(right);
                    result.add(root);
                }
            }
        }
        return result;
    }

    private static Node mirrorTree(Node root) {
        if (root == null) return null;
        Node mirrored = new Node(root.value);
        mirrored.left = mirrorTree(root.right);
        mirrored.right = mirrorTree(root.left);
        return mirrored;
    }

    private static Node cloneTree(Node root) {
        if (root == null) return null;
        Node clone = new Node(root.value);
        clone.left = cloneTree(root.left);
        clone.right = cloneTree(root.right);
        return clone;
    }

    public static int countSymCbalTrees(int nodes) {
        return symCbalTrees(nodes).size();
    }
}
