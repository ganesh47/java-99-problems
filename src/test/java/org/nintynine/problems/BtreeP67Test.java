package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BtreeP67Test {

  private boolean isLeaf(BtreeP61.Node<?> node) {
    return node != null && node.getLeft() == null && node.getRight() == null;
  }

  @Test
  void testParse_complexExample() {
    String s = "a(b(d,e),c(,f(g,)))";
    BtreeP61.Node<String> root = BtreeP67.parse(s);
    assertNotNull(root, "Root should not be null for: " + s);
    assertEquals("a", root.getValue(), "Root value mismatch");

    // Left branch: b(d,e)
    BtreeP61.Node<String> nodeB = root.getLeft();
    assertNotNull(nodeB, "Node B (a's left child) should not be null");
    assertEquals("b", nodeB.getValue(), "Node B value mismatch");

    BtreeP61.Node<String> nodeD = nodeB.getLeft();
    assertNotNull(nodeD, "Node D (b's left child) should not be null");
    assertEquals("d", nodeD.getValue(), "Node D value mismatch");
    assertTrue(isLeaf(nodeD), "Node D should be a leaf");

    BtreeP61.Node<String> nodeE = nodeB.getRight();
    assertNotNull(nodeE, "Node E (b's right child) should not be null");
    assertEquals("e", nodeE.getValue(), "Node E value mismatch");
    assertTrue(isLeaf(nodeE), "Node E should be a leaf");

    // Right branch: c(,f(g,))
    BtreeP61.Node<String> nodeC = root.getRight();
    assertNotNull(nodeC, "Node C (a's right child) should not be null");
    assertEquals("c", nodeC.getValue(), "Node C value mismatch");
    assertNull(nodeC.getLeft(), "Node C's left child should be null");

    BtreeP61.Node<String> nodeF = nodeC.getRight();
    assertNotNull(nodeF, "Node F (c's right child) should not be null");
    assertEquals("f", nodeF.getValue(), "Node F value mismatch");

    BtreeP61.Node<String> nodeG = nodeF.getLeft();
    assertNotNull(nodeG, "Node G (f's left child) should not be null");
    assertEquals("g", nodeG.getValue(), "Node G value mismatch");
    assertTrue(isLeaf(nodeG), "Node G should be a leaf");

    assertNull(nodeF.getRight(), "Node F's right child should be null");
  }

  @Test
  void testToCompactString_complexExample() {
    // Build the tree for "a(b(d,e),c(,f(g,)))" manually
    BtreeP61.Node<String> root = BtreeP61.Node.of(
        "a",
        BtreeP61.Node.of(
            "b",
            BtreeP61.Node.leaf("d"),
            BtreeP61.Node.leaf("e")),
        BtreeP61.Node.of(
            "c",
            null,
            BtreeP61.Node.of(
                "f",
                BtreeP61.Node.leaf("g"),
                null)));
    assertEquals("a(b(d,e),c(,f(g,)))", BtreeP67.toCompactString(root));
  }

  @Test
  void testParse_singleNode() {
    BtreeP61.Node<String> root = BtreeP67.parse("a");
    assertNotNull(root, "Root of single node tree 'a' should not be null");
    assertEquals("a", root.getValue());
    assertTrue(isLeaf(root), "Single node 'a' should be a leaf");
  }

  @Test
  void testParse_leftHeavy() {
    String s = "a(b(c,),)";
    BtreeP61.Node<String> root = BtreeP67.parse(s);
    assertNotNull(root, "Root for 'a(b(c,),)' should not be null");
    assertEquals("a", root.getValue());

    BtreeP61.Node<String> nodeB = root.getLeft();
    assertNotNull(nodeB, "Node B for 'a(b(c,),)' should not be null");
    assertEquals("b", nodeB.getValue());
    assertNull(root.getRight(), "Root 'a' should have no right child in 'a(b(c,),)'");

    BtreeP61.Node<String> nodeC = nodeB.getLeft();
    assertNotNull(nodeC, "Node C for 'a(b(c,),)' should not be null");
    assertEquals("c", nodeC.getValue());
    assertNull(nodeB.getRight(), "Node B should have no right child in 'a(b(c,),)'");
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
      BtreeP61.Node<String> root = BtreeP67.parse(s);
      String roundTripString = BtreeP67.toCompactString(root);
      assertEquals(s, roundTripString, "Round trip toCompactString(parse(s)) failed for: " + s);

      BtreeP61.Node<String> rootFromRoundTrip = BtreeP67.parse(roundTripString);
      assertEquals(root, rootFromRoundTrip, "Round trip parse(toCompactString(root)) failed for: " + s);
    }

    BtreeP61.Node<String> rootFromVerboseLeaf = BtreeP67.parse("z(,)");
    assertEquals("z", BtreeP67.toCompactString(rootFromVerboseLeaf), "Tree from 'z(,)' should stringify to 'z'");
    BtreeP61.Node<String> rootFromSimpleLeaf = BtreeP67.parse("z");
    assertEquals(rootFromSimpleLeaf, rootFromVerboseLeaf, "Tree from 'z(,)' should be equal to tree from 'z'");
  }

  @Test
  void testParse_emptyString() {
    BtreeP61.Node<String> root = BtreeP67.parse("");
    assertNull(root, "Tree from empty string should have null root");
    assertEquals("", BtreeP67.toCompactString(root), "toCompactString of null should be empty string");
  }

  @Test
  void testParse_malformedStrings() {
    String[] malformed = {
        "a(b", "a(b,c", "a(b,,c)", "a(b,c)d", "()", "a(()b)", "a((b),c)",
        "a(b,c))", "a(b,c,", "a(,", "a(b c)", "a(b,(c,d)", "a(b(", "a(b,c(",
        "a(b,c) ", " a(b,c)"
    };

    for (String s : malformed) {
      final String currentInput = s;
      assertThrows(IllegalArgumentException.class, () -> BtreeP67.parse(currentInput),
          "Expected IllegalArgumentException for malformed string: \"" + currentInput + "\"");
    }
  }

  @Test
  void testNodeEquality() {
    BtreeP61.Node<String> node1 = BtreeP67.parse("a(b,c)");
    BtreeP61.Node<String> node2 = BtreeP67.parse("a(b,c)");
    BtreeP61.Node<String> node3 = BtreeP67.parse("a(b,d)");
    BtreeP61.Node<String> node4 = BtreeP67.parse("a(c,b)");

    assertEquals(node1, node2, "Nodes from identical strings should be equal");
    assertEquals(node1.hashCode(), node2.hashCode(), "Hash codes for equal nodes should be equal");

    assertNotEquals(node1, node3);
    assertNotEquals(node1, node4);
    assertNotEquals(node1, null);
    assertNotEquals(node1, new Object());
  }
}
