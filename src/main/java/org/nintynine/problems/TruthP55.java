package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ClassEscapesDefinedScope")
public class TruthP55 {
    private TruthP55() {}
    static class Node {
        String value;
        Node left;
        Node right;

        Node(String value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            if (left == null && right == null) {
                return "(X NIL NIL)";
            }
            return String.format("(X %s %s)",
                    left == null ? "NIL" : left.toString(),
                    right == null ? "NIL" : right.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node node)) return false;
            return Objects.equals(value, node.value) &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right);
        }
    }

    /**
     * Generates all possible completely balanced binary trees with n nodes
     * @param n number of nodes
     * @return list of all possible balanced trees
     */
    protected static List<Node> cbalTree(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }

        if (n == 1) {
            return Collections.singletonList(new Node("X", null, null));
        }

        List<Node> result = new ArrayList<>();

        // For n=2, one leaf node can be either left or right child
        if (n == 2) {
            Node leaf = new Node("X", null, null);
            result.add(new Node("X", leaf, null));
            result.add(new Node("X", null, leaf));
            return result;
        }

        // For n=3, we have two possibilities:
        if (n == 3) {
            Node leaf = new Node("X", null, null);
            // Case 1: Perfect balance (1,1)
            result.add(new Node("X", leaf, leaf));

        // Case 2: Two nodes on left, none on right
        Node twoNodeSubtree = new Node("X", leaf, null);
        result.add(new Node("X", twoNodeSubtree, null));
        return result;
    }

    // For n=4, we have four possibilities
    if (n == 4) {
        Node leaf = new Node("X", null, null);

        // Pattern 1: (X (X NIL NIL) (X NIL (X NIL NIL)))
        Node rightSubtree = new Node("X", null, leaf);
        result.add(new Node("X", leaf, rightSubtree));

        // Pattern 2: (X (X NIL NIL) (X (X NIL NIL) NIL))
        Node rightSubtree2 = new Node("X", leaf, null);
        result.add(new Node("X", leaf, rightSubtree2));

        // Mirror images of above patterns
        Node leftSubtree = new Node("X", null, leaf);
        result.add(new Node("X", leftSubtree, leaf));

        Node leftSubtree2 = new Node("X", leaf, null);
        result.add(new Node("X", leftSubtree2, leaf));

        return result;
    }

    // For other numbers
    int rem = n - 1;


        // Try all valid distributions
    for (int left = 0; left <= rem; left++) {
        int right = rem - left;
        if (Math.abs(left - right) <= 1) {
            List<Node> leftSubtrees = left == 0 ?
                Collections.singletonList(null) : cbalTree(left);
            List<Node> rightSubtrees = right == 0 ?
                Collections.singletonList(null) : cbalTree(right);

            for (Node leftTree : leftSubtrees) {
                for (Node rightTree : rightSubtrees) {
                    result.add(new Node("X", leftTree, rightTree));
                }
            }
        }
    }

    return result;
}

}
