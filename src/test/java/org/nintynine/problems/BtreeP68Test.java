package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP68Test {

  private BtreeP61.Node<Character> exampleTree() {
    return BtreeP61.Node.of('A',
        BtreeP61.Node.of('B', BtreeP61.Node.leaf('D'), BtreeP61.Node.leaf('E')),
        BtreeP61.Node.of('C', null, BtreeP61.Node.of('F', BtreeP61.Node.leaf('G'), null))
    );
  }

  @Test
  void testPreorderAndInorder() {
    BtreeP61.Node<Character> root = exampleTree();
    List<Character> pre = BtreeP68.preorder(root);
    List<Character> in = BtreeP68.inorder(root);
    assertEquals(List.of('A', 'B', 'D', 'E', 'C', 'F', 'G'), pre);
    assertEquals(List.of('D', 'B', 'E', 'A', 'C', 'G', 'F'), in);
  }

  @Test
  void testFromPreorder() {
    List<Character> seq = List.of('F', 'C', 'A', 'E', 'H', 'G');
    BtreeP61.Node<Character> root = BtreeP68.fromPreorder(seq);
    assertEquals(seq, BtreeP68.preorder(root));
    List<Character> inorder = BtreeP68.inorder(root);
    assertEquals(List.of('A', 'C', 'E', 'F', 'G', 'H'), inorder); // inorder sorted for BST
  }

  @Test
  void testPreInTree() {
    BtreeP61.Node<Character> root = exampleTree();
    List<Character> pre = BtreeP68.preorder(root);
    List<Character> in = BtreeP68.inorder(root);
    BtreeP61.Node<Character> rebuilt = BtreeP68.preInTree(pre, in);
    assertEquals(pre, BtreeP68.preorder(rebuilt));
    assertEquals(in, BtreeP68.inorder(rebuilt));
    assertEquals(root, rebuilt);
  }

  @Test
  void testPreInTreeInvalid() {
    assertThrows(
        IllegalArgumentException.class, () -> BtreeP68.preInTree(List.of('A'), List.of('A', 'B')));
  }
}
