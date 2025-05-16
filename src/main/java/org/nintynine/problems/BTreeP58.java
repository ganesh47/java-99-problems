package org.nintynine.problems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BTreeP58 {
    private BTreeP58() {}
    public static class BTreeP58Node {
        char value;
        BTreeP58Node left;
        BTreeP58Node right;

        BTreeP58Node(char value) {
            this.value = value;
            left = null;
            right = null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BTreeP58Node bTreeP58Node = (BTreeP58Node) o;
            return value == bTreeP58Node.value &&
                   Objects.equals(left, bTreeP58Node.left) &&
                   Objects.equals(right, bTreeP58Node.right);
        }

        @Override
        public String toString() {
            if (left == null && right == null) return String.valueOf(value);
            return String.format("%c(%s,%s)", value,
                    left == null ? "NIL" : left.toString(),
                    right == null ? "NIL" : right.toString());
        }
    }

    public static List<BTreeP58Node> symCbalTrees(int nodes) {
        if (nodes % 2 == 0) return new ArrayList<>();
        return generateSymCbalTrees(nodes);
    }

    private static List<BTreeP58Node> generateSymCbalTrees(int nodes) {
        List<BTreeP58Node> result = new ArrayList<>();
        
        if (nodes == 0) return result;
        if (nodes == 1) {
            result.add(new BTreeP58Node('X'));
            return result;
        }

        int remainingNodes = nodes - 1;
        if (remainingNodes % 2 != 0) return result;
        
        List<BTreeP58Node> subtrees = generateBalancedSubtrees(remainingNodes / 2);
        for (BTreeP58Node leftSubtree : subtrees) {
            BTreeP58Node root = new BTreeP58Node('X');
            root.left = cloneTree(leftSubtree);
            root.right = cloneTree(mirrorTree(leftSubtree));
            result.add(root);
        }
        
        return result;
    }

    private static List<BTreeP58Node> generateBalancedSubtrees(int nodes) {
        List<BTreeP58Node> result = new ArrayList<>();
        
        if (nodes == 0) {
            result.add(null);
            return result;
        }
        if (nodes == 1) {
            result.add(new BTreeP58Node('X'));
            return result;
        }

        int remainingNodes = nodes - 1;
        for (int leftNodes = remainingNodes / 2; leftNodes <= (remainingNodes + 1) / 2; leftNodes++) {
            int rightNodes = remainingNodes - leftNodes;
            List<BTreeP58Node> leftSubtrees = generateBalancedSubtrees(leftNodes);
            List<BTreeP58Node> rightSubtrees = generateBalancedSubtrees(rightNodes);

            for (BTreeP58Node left : leftSubtrees) {
                for (BTreeP58Node right : rightSubtrees) {
                    BTreeP58Node root = new BTreeP58Node('X');
                    root.left = cloneTree(left);
                    root.right = cloneTree(right);
                    result.add(root);
                }
            }
        }
        return result;
    }

    private static BTreeP58Node mirrorTree(BTreeP58Node root) {
        if (root == null) return null;
        BTreeP58Node mirrored = new BTreeP58Node(root.value);
        mirrored.left = mirrorTree(root.right);
        mirrored.right = mirrorTree(root.left);
        return mirrored;
    }

    private static BTreeP58Node cloneTree(BTreeP58Node root) {
        if (root == null) return null;
        BTreeP58Node clone = new BTreeP58Node(root.value);
        clone.left = cloneTree(root.left);
        clone.right = cloneTree(root.right);
        return clone;
    }

    public static int countSymCbalTrees(int nodes) {
        return symCbalTrees(nodes).size();
    }
}
