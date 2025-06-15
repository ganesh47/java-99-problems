package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Additional tests to improve coverage of small utility classes. */
class AdditionalCoverageTest {

  @Test
  @DisplayName("BTree54Node handles raw expression values")
  void testBTree54NodeRawValue() {
    String raw = "(a nil nil)";
    BTree54.BTree54Node node = new BTree54.BTree54Node(raw);
    assertEquals(raw, node.toString());
  }

  @Test
  @DisplayName("BTreeP58Node equality and toString")
  void testBTreeP58NodeEquality() {
    BTreeP58.BTreeP58Node left1 = new BTreeP58.BTreeP58Node('A');
    BTreeP58.BTreeP58Node right1 = new BTreeP58.BTreeP58Node('B');
    BTreeP58.BTreeP58Node root1 = new BTreeP58.BTreeP58Node('X');
    root1.left = left1;
    root1.right = right1;

    BTreeP58.BTreeP58Node left2 = new BTreeP58.BTreeP58Node('A');
    BTreeP58.BTreeP58Node right2 = new BTreeP58.BTreeP58Node('B');
    BTreeP58.BTreeP58Node root2 = new BTreeP58.BTreeP58Node('X');
    root2.left = left2;
    root2.right = right2;

    assertEquals(root1, root2);
    assertEquals(root1.hashCode(), root2.hashCode());
    assertEquals("X(A,B)", root1.toString());
  }

  @Test
  @DisplayName("BTreeP59Node equality and toString")
  void testBTreeP59NodeEquality() {
    BTreeP59.BTree59Node left1 = new BTreeP59.BTree59Node('L');
    BTreeP59.BTree59Node right1 = new BTreeP59.BTree59Node('R');
    BTreeP59.BTree59Node root1 = new BTreeP59.BTree59Node('X');
    root1.left = left1;
    root1.right = right1;

    BTreeP59.BTree59Node left2 = new BTreeP59.BTree59Node('L');
    BTreeP59.BTree59Node right2 = new BTreeP59.BTree59Node('R');
    BTreeP59.BTree59Node root2 = new BTreeP59.BTree59Node('X');
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

  @Test
  @DisplayName("TruthP55.Node string and hash code")
  void testTruthP55NodeToStringHash() {
    TruthP55.Node left1 = new TruthP55.Node("X", null, null);
    TruthP55.Node right1 = new TruthP55.Node("X", null, null);
    TruthP55.Node root1 = new TruthP55.Node("X", left1, right1);

    TruthP55.Node left2 = new TruthP55.Node("X", null, null);
    TruthP55.Node right2 = new TruthP55.Node("X", null, null);
    TruthP55.Node root2 = new TruthP55.Node("X", left2, right2);

    assertEquals("(X (X NIL NIL) (X NIL NIL))", root1.toString());
    assertEquals(root1.toString(), root2.toString());
    assertEquals(root1.hashCode(), root2.hashCode());
    assertTrue(root1.equals(root1));
    assertFalse(root1.equals("X"));
  }

  @Test
  @DisplayName("GoldbachPair isValid handles invalid pairs")
  void testGoldbachPairInvalid() {
    MathP40.GoldbachPair invalid = new MathP40.GoldbachPair(4, 6);
    assertFalse(invalid.isValid());
  }

  @Test
  @DisplayName("HuffmanDecoder detects incomplete codes")
  void testDecoderIncomplete() {
    List<MathP50.HuffmanCode> codes =
        List.of(new MathP50.HuffmanCode("a", "0"), new MathP50.HuffmanCode("b", "10"));
    MathP50.HuffmanDecoder decoder = MathP50.createDecoder(codes);

    assertThrows(IllegalArgumentException.class, () -> decoder.decode("1"));
    assertEquals("b", decoder.decode("10"));
  }
}
