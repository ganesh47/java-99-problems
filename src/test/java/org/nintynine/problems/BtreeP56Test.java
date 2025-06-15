package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BtreeP56Test {

  @Test
  void emptyTreeIsSymmetric() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    assertTrue(tree.isSymmetric());
  }

  @Test
  void singleNodeTreeIsSymmetric() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    tree.setRoot(1);
    assertTrue(tree.isSymmetric());
  }

  @Test
  void symmetricTreeWithTwoNodes() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    tree.setRoot(1);
    tree.addLeft(2);
    tree.addRight(3);
    assertTrue(tree.isSymmetric());
  }

  @Test
  void asymmetricTreeWithLeftNodeOnly() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    tree.setRoot(1);
    tree.addLeft(2);
    assertFalse(tree.isSymmetric());
  }

  @Test
  void asymmetricTreeWithRightNodeOnly() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    tree.setRoot(1);
    tree.addRight(2);
    assertFalse(tree.isSymmetric());
  }

  @Test
  void throwsExceptionWhenAddingToEmptyTree() {
    BtreeP56<Integer> tree = new BtreeP56<>();
    assertThrows(IllegalStateException.class, () -> tree.addLeft(1));
    assertThrows(IllegalStateException.class, () -> tree.addRight(1));
  }
}
