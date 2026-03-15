package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/** Utility class for generating height-balanced binary trees (P59). */
public final class BtreeP59 {
  private BtreeP59() {}

  /**
   * Generates all height-balanced binary trees for a given height.
   *
   * @param height the height of the trees
   * @return a list of height-balanced trees
   */
  public static List<BtreeP61.Node<String>> hbalTrees(int height) {
    if (height < 0) {
      return new ArrayList<>();
    }
    return generateHbalTrees(height);
  }

  private static List<BtreeP61.Node<String>> generateHbalTrees(int height) {
    List<BtreeP61.Node<String>> result = new ArrayList<>();

    if (height == 0) {
      result.add(null);
      return result;
    }

    if (height == 1) {
      result.add(BtreeP61.Node.leaf("x"));
      return result;
    }

    // Trees of height h can have subtrees of height h-1 or h-2
    List<BtreeP61.Node<String>> h1 = generateHbalTrees(height - 1);
    List<BtreeP61.Node<String>> h2 = generateHbalTrees(height - 2);

    // Case 1: Both subtrees have height h-1
    for (BtreeP61.Node<String> l : h1) {
      for (BtreeP61.Node<String> r : h1) {
        result.add(BtreeP61.Node.of("x", l, r));
      }
    }

    // Case 2: Left subtree has height h-1, right has height h-2
    for (BtreeP61.Node<String> l : h1) {
      for (BtreeP61.Node<String> r : h2) {
        result.add(BtreeP61.Node.of("x", l, r));
      }
    }

    // Case 3: Left subtree has height h-2, right has height h-1
    for (BtreeP61.Node<String> l : h2) {
      for (BtreeP61.Node<String> r : h1) {
        result.add(BtreeP61.Node.of("x", l, r));
      }
    }

    return result;
  }
}
