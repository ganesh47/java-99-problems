package org.nintynine.problems;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeP68Test {

    private BTreeP68.Node exampleTree() {
        BTreeP68.Node a = new BTreeP68.Node('A');
        BTreeP68.Node b = new BTreeP68.Node('B');
        BTreeP68.Node c = new BTreeP68.Node('C');
        BTreeP68.Node d = new BTreeP68.Node('D');
        BTreeP68.Node e = new BTreeP68.Node('E');
        BTreeP68.Node f = new BTreeP68.Node('F');
        BTreeP68.Node g = new BTreeP68.Node('G');
        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.right = f;
        f.left = g;
        return a;
    }

    @Test
    void testPreorderAndInorder() {
        BTreeP68.Node root = exampleTree();
        List<Character> pre = BTreeP68.preorder(root);
        List<Character> in = BTreeP68.inorder(root);
        assertEquals(List.of('A','B','D','E','C','F','G'), pre);
        assertEquals(List.of('D','B','E','A','C','G','F'), in);
    }

    @Test
    void testFromPreorder() {
        List<Character> seq = List.of('F','C','A','E','H','G');
        BTreeP68.Node root = BTreeP68.fromPreorder(seq);
        assertEquals(seq, BTreeP68.preorder(root));
        List<Character> inorder = BTreeP68.inorder(root);
        assertEquals(List.of('A','C','E','F','G','H'), inorder); // inorder sorted
    }

    @Test
    void testPreInTree() {
        BTreeP68.Node root = exampleTree();
        List<Character> pre = BTreeP68.preorder(root);
        List<Character> in = BTreeP68.inorder(root);
        BTreeP68.Node rebuilt = BTreeP68.preInTree(pre, in);
        assertEquals(pre, BTreeP68.preorder(rebuilt));
        assertEquals(in, BTreeP68.inorder(rebuilt));
        assertEquals(root, rebuilt);
    }

    @Test
    void testPreInTreeInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                BTreeP68.preInTree(List.of('A'), List.of('A','B')));
    }
}
