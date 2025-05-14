package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TruthP55Test {

    @Test
    @DisplayName("Test empty and single node trees")
    void testBasicCases() {
        // Test empty tree
        List<TruthP55.Node> emptyTree = TruthP55.cbalTree(0);
        assertTrue(emptyTree.isEmpty(), "Tree with 0 nodes should be empty");

        // Test single node tree
        List<TruthP55.Node> singleNode = TruthP55.cbalTree(1);
        assertEquals(1, singleNode.size(), "Should be exactly one tree with 1 node");
        assertEquals("(X NIL NIL)", singleNode.getFirst().toString(), "Single node should have NIL children");
    }

    @Test
    @DisplayName("Test two node trees")
    void testTwoNodes() {
        List<TruthP55.Node> twoNodes = TruthP55.cbalTree(2);
        assertEquals(2, twoNodes.size(), "Should be exactly one tree with 2 nodes");
        assertEquals("(X (X NIL NIL) NIL)", twoNodes.getFirst().toString());
    }

    @Test
    @DisplayName("Test three node trees")
    void testThreeNodes() {
        List<TruthP55.Node> threeNodes = TruthP55.cbalTree(3);
        assertEquals(2, threeNodes.size(), "Should be exactly two trees with 3 nodes");
        assertTrue(threeNodes.stream().anyMatch(n -> n.toString().equals("(X (X NIL NIL) (X NIL NIL))")));
    }

    @Test
    @DisplayName("Test four node trees")
    void testFourNodes() {
        List<TruthP55.Node> fourNodes = TruthP55.cbalTree(4);
        assertEquals(4, fourNodes.size(), "Should be exactly four trees with 4 nodes");

        // Check for expected patterns
        assertTrue(fourNodes.stream().anyMatch(n ->
                n.toString().equals("(X (X NIL NIL) (X NIL (X NIL NIL)))") ||
                        n.toString().equals("(X (X NIL NIL) (X (X NIL NIL) NIL))")
        ));
    }

    @ParameterizedTest
    @DisplayName("Test node count in generated trees")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testNodeCount(int n) {
        List<TruthP55.Node> trees = TruthP55.cbalTree(n);
        for (TruthP55.Node tree : trees) {
            assertEquals(n, countNodes(tree),
                    "Each generated tree should have exactly " + n + " nodes");
        }
    }

    @Test
    @DisplayName("Test balance property")
    void testBalanceProperty() {
        // Test for trees with 5 nodes
        List<TruthP55.Node> trees = TruthP55.cbalTree(5);
        for (TruthP55.Node tree : trees) {
            assertTrue(isBalanced(tree),
                    "Tree should be completely balanced");
        }
    }

    @Test
    @DisplayName("Test node equality")
    void testNodeEquality() {
        TruthP55.Node node1 = new TruthP55.Node("X", null, null);
        TruthP55.Node node2 = new TruthP55.Node("X", null, null);
        TruthP55.Node node3 = new TruthP55.Node("X",
                new TruthP55.Node("X", null, null), null);

        assertEquals(node1, node2, "Identical nodes should be equal");
        assertNotEquals(node1, node3, "Different nodes should not be equal");
        assertNotEquals(null, node1, "Node should not be equal to null");
        assertNotEquals("X", node1, "Node should not be equal to string");
    }

    // Helper method to count nodes in a tree
    private int countNodes(TruthP55.Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    // Helper method to check if tree is balanced
    private boolean isBalanced(TruthP55.Node root) {
        if (root == null) return true;

        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right);
    }

    // Helper method to get height of tree
    private int getHeight(TruthP55.Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
