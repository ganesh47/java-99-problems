package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Additional tests to improve coverage of small utility classes. */
class AdditionalCoverageTest {

  @Test
  @DisplayName("Btree54Node handles raw expression values")
  void testBtree54NodeRawValue() {
    String raw = "(a nil nil)";
    Btree54.Btree54Node node = new Btree54.Btree54Node(raw);
    assertEquals(raw, node.toString());
  }

  @Test
  @DisplayName("BtreeP58Node equality and toString")
  void testBtreeP58NodeEquality() {
    BtreeP58.BtreeP58Node left1 = new BtreeP58.BtreeP58Node('A');
    BtreeP58.BtreeP58Node right1 = new BtreeP58.BtreeP58Node('B');
    BtreeP58.BtreeP58Node root1 = new BtreeP58.BtreeP58Node('X');
    root1.left = left1;
    root1.right = right1;

    BtreeP58.BtreeP58Node left2 = new BtreeP58.BtreeP58Node('A');
    BtreeP58.BtreeP58Node right2 = new BtreeP58.BtreeP58Node('B');
    BtreeP58.BtreeP58Node root2 = new BtreeP58.BtreeP58Node('X');
    root2.left = left2;
    root2.right = right2;

    assertEquals(root1, root2);
    assertEquals(root1.hashCode(), root2.hashCode());
    assertEquals("X(A,B)", root1.toString());
  }

  @Test
  @DisplayName("BtreeP59Node equality and toString")
  void testBtreeP59NodeEquality() {
    BtreeP59.BTree59Node left1 = new BtreeP59.BTree59Node('L');
    BtreeP59.BTree59Node right1 = new BtreeP59.BTree59Node('R');
    BtreeP59.BTree59Node root1 = new BtreeP59.BTree59Node('X');
    root1.left = left1;
    root1.right = right1;

    BtreeP59.BTree59Node left2 = new BtreeP59.BTree59Node('L');
    BtreeP59.BTree59Node right2 = new BtreeP59.BTree59Node('R');
    BtreeP59.BTree59Node root2 = new BtreeP59.BTree59Node('X');
    root2.left = left2;
    root2.right = right2;

    assertEquals(root1, root2);
    assertEquals(root1.hashCode(), root2.hashCode());
    assertEquals("X(L,R)", root1.toString());
  }

  @Test
  @DisplayName("Pair toString includes both lists")
  void testPairToString() {
    MyListP17<String> list1 = new MyListP17<>("a", "b");
    MyListP17<String> list2 = new MyListP17<>("c");
    MyListP17.Pair<MyListP17<String>, MyListP17<String>> pair = new MyListP17.Pair<>(list1, list2);
    assertEquals("(" + list1 + ", " + list2 + ")", pair.toString());
  }

  @Test
  @DisplayName("PrimeTotientResult toString formats correctly")
  void testPrimeTotientResultToString() {
    MathP34.PrimeTotientResult res1 = new MathP34.PrimeTotientResult(true, 9);
    assertEquals("Number is prime, \u03c6(m) = 9", res1.toString());

    MathP34.PrimeTotientResult res2 = new MathP34.PrimeTotientResult(false, 8);
    assertEquals("Number is not prime, \u03c6(m) = 8", res2.toString());
  }
}
