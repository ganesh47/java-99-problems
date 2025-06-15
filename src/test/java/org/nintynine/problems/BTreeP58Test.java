package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BTreeP58Test {

  @Test
  void testSymCbalTreesWithOneNode() {
    List<BTreeP58.BTreeP58Node> trees = BTreeP58.symCbalTrees(1);
    assertEquals(1, trees.size());
    assertEquals("X", trees.getFirst().toString());
  }

  @Test
  void testSymCbalTreesWithFiveNodes() {
    List<BTreeP58.BTreeP58Node> trees = BTreeP58.symCbalTrees(5);
    assertEquals(2, trees.size());
    assertTrue(
        trees.stream()
            .map(Object::toString)
            .allMatch(s -> s.equals("X(X(NIL,X),X(X,NIL))") || s.equals("X(X(X,NIL),X(NIL,X))")));
  }

  @Test
  void testSymCbalTreesWithEvenNodes() {
    List<BTreeP58.BTreeP58Node> trees = BTreeP58.symCbalTrees(6);
    assertEquals(0, trees.size());
  }

  @Test
  void testCountSymCbalTrees() {
    assertEquals(1, BTreeP58.countSymCbalTrees(1));
    assertEquals(0, BTreeP58.countSymCbalTrees(2));
    assertEquals(1, BTreeP58.countSymCbalTrees(3));
    assertEquals(0, BTreeP58.countSymCbalTrees(4));
    assertEquals(2, BTreeP58.countSymCbalTrees(5));
    assertEquals(0, BTreeP58.countSymCbalTrees(6));
    assertEquals(1, BTreeP58.countSymCbalTrees(7));
  }
}
