package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BtreeP67Test {

    private boolean isLeaf(BtreeP67.Node<?> node) {
        return node != null && node.left == null && node.right == null;
    }

    @Test
    void testFromString_complexExample() {
        String s = "a(b(d,e),c(,f(g,)))";
        BtreeP67<String> tree = BtreeP67.fromString(s);
        assertNotNull(tree.getRoot(), "Root should not be null for: " + s);
        assertEquals("a", tree.getRoot().value, "Root value mismatch");

        // Left branch: b(d,e)
        BtreeP67.Node<String> nodeB = tree.getRoot().left;
        assertNotNull(nodeB, "Node B (a's left child) should not be null");
        assertEquals("b", nodeB.value, "Node B value mismatch");

        BtreeP67.Node<String> nodeD = nodeB.left;
        assertNotNull(nodeD, "Node D (b's left child) should not be null");
        assertEquals("d", nodeD.value, "Node D value mismatch");
        assertTrue(isLeaf(nodeD), "Node D should be a leaf");

        BtreeP67.Node<String> nodeE = nodeB.right;
        assertNotNull(nodeE, "Node E (b's right child) should not be null");
        assertEquals("e", nodeE.value, "Node E value mismatch");
        assertTrue(isLeaf(nodeE), "Node E should be a leaf");

        // Right branch: c(,f(g,))
        BtreeP67.Node<String> nodeC = tree.getRoot().right;
        assertNotNull(nodeC, "Node C (a's right child) should not be null");
        assertEquals("c", nodeC.value, "Node C value mismatch");
        assertNull(nodeC.left, "Node C's left child should be null");

        BtreeP67.Node<String> nodeF = nodeC.right;
        assertNotNull(nodeF, "Node F (c's right child) should not be null");
        assertEquals("f", nodeF.value, "Node F value mismatch");

        BtreeP67.Node<String> nodeG = nodeF.left;
        assertNotNull(nodeG, "Node G (f's left child) should not be null");
        assertEquals("g", nodeG.value, "Node G value mismatch");
        assertTrue(isLeaf(nodeG), "Node G should be a leaf");

        assertNull(nodeF.right, "Node F's right child should be null");
    }

    @Test
    void testToString_complexExample() {
        // Build the tree for "a(b(d,e),c(,f(g,)))" manually
        BtreeP67.Node<String> root = new BtreeP67.Node<>("a");
        root.left = new BtreeP67.Node<>("b");
        root.left.left = new BtreeP67.Node<>("d");
        root.left.right = new BtreeP67.Node<>("e");
        root.right = new BtreeP67.Node<>("c");
        root.right.right = new BtreeP67.Node<>("f");
        root.right.right.left = new BtreeP67.Node<>("g");
        BtreeP67<String> tree = new BtreeP67<>(root);
        assertEquals("a(b(d,e),c(,f(g,)))", tree.toString());
    }

    @Test
    void testFromString_singleNode() {
        BtreeP67<String> tree = BtreeP67.fromString("a");
        assertNotNull(tree.getRoot(), "Root of single node tree 'a' should not be null");
        assertEquals("a", tree.getRoot().value);
        assertTrue(isLeaf(tree.getRoot()), "Single node 'a' should be a leaf");
    }

    @Test
    void testFromString_leftHeavy() {
        // a has left child b, b has left child c. c is a leaf. b has no right child. a has no right child.
        String s = "a(b(c,),)";
        BtreeP67<String> tree = BtreeP67.fromString(s);
        assertNotNull(tree.getRoot(), "Root for 'a(b(c,),)' should not be null");
        assertEquals("a", tree.getRoot().value);

        BtreeP67.Node<String> nodeB = tree.getRoot().left;
        assertNotNull(nodeB, "Node B for 'a(b(c,),)' should not be null");
        assertEquals("b", nodeB.value);
        assertNull(tree.getRoot().right, "Root 'a' should have no right child in 'a(b(c,),)'");

        BtreeP67.Node<String> nodeC = nodeB.left;
        assertNotNull(nodeC, "Node C for 'a(b(c,),)' should not be null");
        assertEquals("c", nodeC.value);
        assertNull(nodeB.right, "Node B should have no right child in 'a(b(c,),)'");
        assertTrue(isLeaf(nodeC), "Node C should be a leaf in 'a(b(c,),)'");
    }

    @Test
    void testRoundTripConversions() {
        String[] testStrings = {
            "a(b(d,e),c(,f(g,)))",
            "a",
            "x(y,z)",
            "a(b,)",
            "a(,c)",
            "m(n(o,p),q(r(s,t),u))",
            "a(b(c(d(e,),),),)"
        };

        for (String s : testStrings) {
            BtreeP67<String> tree = BtreeP67.fromString(s);
            String roundTripString = tree.toString();
            assertEquals(s, roundTripString, "Round trip toString(fromString(s)) failed for: " + s);

            BtreeP67<String> treeFromRoundTripString = BtreeP67.fromString(roundTripString);
            assertEquals(tree, treeFromRoundTripString, "Round trip fromString(toString(tree)) failed for: " + s);
        }

        BtreeP67<String> treeFromVerboseLeaf = BtreeP67.fromString("z(,)");
        assertEquals("z", treeFromVerboseLeaf.toString(), "Tree from 'z(,)' should stringify to 'z'");
        BtreeP67<String> treeFromSimpleLeaf = BtreeP67.fromString("z");
        assertEquals(treeFromSimpleLeaf, treeFromVerboseLeaf, "Tree from 'z(,)' should be equal to tree from 'z'");
    }

    @Test
    void testToStringForManuallyConstructedTrees() {
        BtreeP67<String> leaf = new BtreeP67<>(new BtreeP67.Node<>("x"));
        assertEquals("x", leaf.toString());

        BtreeP67.Node<String> rootLeft = new BtreeP67.Node<>("p");
        rootLeft.left = new BtreeP67.Node<>("q");
        BtreeP67<String> treeLeft = new BtreeP67<>(rootLeft);
        assertEquals("p(q,)", treeLeft.toString());

        BtreeP67.Node<String> rootRight = new BtreeP67.Node<>("m");
        rootRight.right = new BtreeP67.Node<>("n");
        BtreeP67<String> treeRight = new BtreeP67<>(rootRight);
        assertEquals("m(,n)", treeRight.toString());

        BtreeP67.Node<String> rootTwo = new BtreeP67.Node<>("k");
        rootTwo.left = new BtreeP67.Node<>("l");
        rootTwo.right = new BtreeP67.Node<>("m");
        BtreeP67<String> treeTwo = new BtreeP67<>(rootTwo);
        assertEquals("k(l,m)", treeTwo.toString());

        BtreeP67.Node<String> rootWithEmptyChildren = new BtreeP67.Node<>("j");
        BtreeP67<String> treeEmptyChildren = new BtreeP67<>(rootWithEmptyChildren);
        assertEquals("j", treeEmptyChildren.toString());
    }

    @Test
    void testFromString_emptyString() {
        BtreeP67<String> tree = BtreeP67.fromString("");
        assertNull(tree.getRoot(), "Tree from empty string should have null root");
        assertEquals("", tree.toString(), "toString of empty tree should be empty string");
    }

    @Test
    void testFromString_malformedStrings() {
        String[] malformed = {
            "a(b", "a(b,c", "a(b,,c)", "a(b,c)d", "()", "a(()b)", "a((b),c)",
            "a(b,c))", "a(b,c,", "a(,", "a(b c)", "a(b,(c,d)", "a(b(", "a(b,c(",
            "a(b,c) ", " a(b,c)"
        };

        for (String s : malformed) {
            final String currentInput = s;
            assertThrows(IllegalArgumentException.class, () -> BtreeP67.fromString(currentInput),
                         "Expected IllegalArgumentException for malformed string: \"" + currentInput + "\"");
        }
    }

    @Test
    void testTreeEquality() {
        BtreeP67<String> tree1 = BtreeP67.fromString("a(b,c)");
        BtreeP67<String> tree2 = BtreeP67.fromString("a(b,c)");
        BtreeP67<String> tree3 = BtreeP67.fromString("a(b,d)");
        BtreeP67<String> tree4 = BtreeP67.fromString("a(c,b)");
        BtreeP67<String> tree5 = BtreeP67.fromString("x(b,c)");
        BtreeP67<String> tree6 = BtreeP67.fromString("a(b(c,),)");

        assertEquals(tree1, tree2, "Trees from identical strings should be equal");
        assertEquals(tree1.hashCode(), tree2.hashCode(), "Hash codes for equal trees should be equal");

        assertNotEquals(tree1, tree3);
        assertNotEquals(tree1, tree4);
        assertNotEquals(tree1, tree5);
        assertNotEquals(tree1, tree6);
        assertNotEquals(tree1, null);
        assertNotEquals(tree1, new Object());

        BtreeP67<String> emptyTree1 = BtreeP67.fromString("");
        BtreeP67<String> emptyTree2 = new BtreeP67<>(null);
        assertEquals(emptyTree1, emptyTree2);
        assertEquals(emptyTree1.hashCode(), emptyTree2.hashCode());
        assertNotEquals(tree1, emptyTree1);
    }

    @Test
    void testFromStringSpecificCasesAndStructure() {
        BtreeP67<String> treeAB = BtreeP67.fromString("a(b,)");
        assertNotNull(treeAB.getRoot());
        assertEquals("a", treeAB.getRoot().value);
        assertNotNull(treeAB.getRoot().left);
        assertEquals("b", treeAB.getRoot().left.value);
        assertNull(treeAB.getRoot().right);
        assertTrue(isLeaf(treeAB.getRoot().left));

        BtreeP67<String> treeAC = BtreeP67.fromString("a(,c)");
        assertNotNull(treeAC.getRoot());
        assertEquals("a", treeAC.getRoot().value);
        assertNull(treeAC.getRoot().left);
        assertNotNull(treeAC.getRoot().right);
        assertEquals("c", treeAC.getRoot().right.value);
        assertTrue(isLeaf(treeAC.getRoot().right));

        BtreeP67<String> treeXYZ = BtreeP67.fromString("x(y,z)");
        assertNotNull(treeXYZ.getRoot());
        assertEquals("x", treeXYZ.getRoot().value);
        assertNotNull(treeXYZ.getRoot().left);
        assertEquals("y", treeXYZ.getRoot().left.value);
        assertTrue(isLeaf(treeXYZ.getRoot().left));
        assertNotNull(treeXYZ.getRoot().right);
        assertEquals("z", treeXYZ.getRoot().right.value);
        assertTrue(isLeaf(treeXYZ.getRoot().right));
    }
}
