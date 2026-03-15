package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class BtreeP57Test {

  @Test
  void constructEmptyTree() {
    BtreeP61.Node<Integer> root = BtreeP57.construct(java.util.Collections.<Integer>emptyList());
    assertTrue(BtreeP61.isSymmetric(root));
  }

  @Test
  void constructSymmetricTree() {
    List<Integer> values = Arrays.asList(5, 3, 18, 1, 4, 12, 21);
    BtreeP61.Node<Integer> root = BtreeP57.construct(values);
    assertTrue(BtreeP61.isSymmetric(root));
  }

  @Test
  void constructAsymmetricTree() {
    List<Integer> values = Arrays.asList(3, 2, 5, 7);
    BtreeP61.Node<Integer> root = BtreeP57.construct(values);
    assertFalse(BtreeP61.isSymmetric(root));
  }

  @Test
  void verifyTreeStructure() {
    List<Integer> values = Arrays.asList(3, 2, 5, 7, 1);
    BtreeP61.Node<Integer> root = BtreeP57.construct(values);
    assertEquals("3(2(1,),5(,7))", root.toString());
  }

  @Test
  void insertSingleValue() {
    BtreeP61.Node<Integer> root = BtreeP57.insert(null, 5);
    assertEquals("5", root.toString());
  }

  @Test
  void insertDuplicateValues() {
    List<Integer> values = Arrays.asList(3, 3, 3);
    BtreeP61.Node<Integer> root = BtreeP57.construct(values);
    assertEquals("3", root.toString());
  }
}
