package org.nintynine.problems;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BTreeP56Test {

    @Test
    void emptyTreeIsSymmetric() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        assertTrue(tree.isSymmetric());
    }

    @Test
    void singleNodeTreeIsSymmetric() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        tree.setRoot(1);
        assertTrue(tree.isSymmetric());
    }

    @Test
    void symmetricTreeWithTwoNodes() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        tree.setRoot(1);
        tree.addLeft(2);
        tree.addRight(3);
        assertTrue(tree.isSymmetric());
    }

    @Test
    void asymmetricTreeWithLeftNodeOnly() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        tree.setRoot(1);
        tree.addLeft(2);
        assertFalse(tree.isSymmetric());
    }

    @Test
    void asymmetricTreeWithRightNodeOnly() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        tree.setRoot(1);
        tree.addRight(2);
        assertFalse(tree.isSymmetric());
    }

    @Test
    void throwsExceptionWhenAddingToEmptyTree() {
        BTreeP56<Integer> tree = new BTreeP56<>();
        assertThrows(IllegalStateException.class, () -> tree.addLeft(1));
        assertThrows(IllegalStateException.class, () -> tree.addRight(1));
    }
}
