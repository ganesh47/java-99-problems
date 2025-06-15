package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP68Test {

  private BtreeP68.Node exampleTree() {
    BtreeP68.Node a = new BtreeP68.Node('A');
    BtreeP68.Node b = new BtreeP68.Node('B');
    BtreeP68.Node c = new BtreeP68.Node('C');
    BtreeP68.Node d = new BtreeP68.Node('D');
    BtreeP68.Node e = new BtreeP68.Node('E');
    BtreeP68.Node f = new BtreeP68.Node('F');
    BtreeP68.Node g = new BtreeP68.Node('G');
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
    BtreeP68.Node root = exampleTree();
    List<Character> pre = BtreeP68.preorder(root);
    List<Character> in = BtreeP68.inorder(root);
    assertEquals(List.of('A', 'B', 'D', 'E', 'C', 'F', 'G'), pre);
    assertEquals(List.of('D', 'B', 'E', 'A', 'C', 'G', 'F'), in);
  }

  @Test
  void testFromPreorder() {
    List<Character> seq = List.of('F', 'C', 'A', 'E', 'H', 'G');
    BtreeP68.Node root = BtreeP68.fromPreorder(seq);
    assertEquals(seq, BtreeP68.preorder(root));
    List<Character> inorder = BtreeP68.inorder(root);
    assertEquals(List.of('A', 'C', 'E', 'F', 'G', 'H'), inorder); // inorder sorted
  }

  @Test
  void testPreInTree() {
    BtreeP68.Node root = exampleTree();
    List<Character> pre = BtreeP68.preorder(root);
    List<Character> in = BtreeP68.inorder(root);
    BtreeP68.Node rebuilt = BtreeP68.preInTree(pre, in);
    assertEquals(pre, BtreeP68.preorder(rebuilt));
    assertEquals(in, BtreeP68.inorder(rebuilt));
    assertEquals(root, rebuilt);
  }

  @Test
  void testPreInTreeInvalid() {
    assertThrows(
        IllegalArgumentException.class, () -> BtreeP68.preInTree(List.of('A'), List.of('A', 'B')));
  }

  @Test
  void testNodeEqualityAndHash() {
    BtreeP68.Node n1 = new BtreeP68.Node('A');
    n1.left = new BtreeP68.Node('B');
    BtreeP68.Node n2 = new BtreeP68.Node('A');
    n2.left = new BtreeP68.Node('B');
    assertEquals(n1, n2); // same structure
    assertEquals(n1, n1); // self equality
    assertNotEquals(n1, "other");
    assertNotEquals(n1, null);
    assertEquals(n1.hashCode(), n2.hashCode());

    assertNotEquals(n1, new BtreeP68.Node('X'));
    BtreeP68.Node n3 = new BtreeP68.Node('A');
    n3.left = new BtreeP68.Node('B');
    n3.right = new BtreeP68.Node('C');
    assertNotEquals(n1, n3);

    n2.left.value = 'C';
    assertNotEquals(n1, n2);
  }

  @Test
  void testNodeToString() {
    BtreeP68.Node leaf = new BtreeP68.Node('X');
    assertEquals("X", leaf.toString());

    BtreeP68.Node root = new BtreeP68.Node('A');
    root.left = new BtreeP68.Node('B');
    root.right = new BtreeP68.Node('C');
    assertEquals("A(B,C)", root.toString());

    BtreeP68.Node half = new BtreeP68.Node('A');
    half.left = new BtreeP68.Node('B');
    assertEquals("A(B,NIL)", half.toString());

    BtreeP68.Node halfRight = new BtreeP68.Node('A');
    halfRight.right = new BtreeP68.Node('B');
    assertEquals("A(NIL,B)", halfRight.toString());
  }
}
