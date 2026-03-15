package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Utility class for generating height-balanced binary trees with fixed node count (P60). */
public final class BtreeP60 {

  private BtreeP60() {}

  /**
   * Computes the minimum number of nodes of a height-balanced tree for a given height.
   *
   * @param h desired height
   * @return minimum node count
   */
  public static int minNodes(int h) {
    if (h <= 0) {
      return 0;
    }
    if (h == 1) {
      return 1;
    }
    return 1 + minNodes(h - 1) + minNodes(h - 2);
  }

  /**
   * Computes the maximum possible height of a tree with the given number of
   * nodes while remaining height balanced.
   *
   * @param n node count
   * @return maximum height
   */
  public static int maxHeight(long n) {
    if (n <= 0) {
      return 0;
    }
    int h = 0;
    while (minNodes(h) <= n) {
      h++;
    }
    return h - 1;
  }

  /**
   * Generates all height-balanced trees with the given number of nodes.
   *
   * @param n number of nodes
   * @return list of trees
   */
  public static List<BtreeP61.Node<String>> hbalTreeNodes(int n) {
    if (n <= 0) {
      return Collections.emptyList();
    }
    List<BtreeP61.Node<String>> result = new ArrayList<>();
    int minH = (int) Math.ceil(Math.log(n + (double) 1) / Math.log(2));
    int maxH = maxHeight(n);

    for (int h = minH; h <= maxH; h++) {
      result.addAll(generateTreesWithHeightAndNodes(h, n));
    }
    return result;
  }

  private static List<BtreeP61.Node<String>> generateTreesWithHeightAndNodes(int h, int n) {
    if (n <= 0) {
      return h == 0 ? Collections.singletonList(null) : Collections.emptyList();
    }
    if (h <= 0) {
      return Collections.emptyList();
    }
    if (n == 1) {
      return h == 1 ? Collections.singletonList(BtreeP61.Node.leaf("x")) : Collections.emptyList();
    }

    List<BtreeP61.Node<String>> result = new ArrayList<>();
    // A tree of height h and n nodes must have subtrees with heights (h-1, h-1), (h-1, h-2), or (h-2, h-1)
    // and total nodes n-1.
    for (int ln = 0; ln < n; ln++) {
      int rn = n - 1 - ln;

      // Heights: (h-1, h-1)
      addCombinations(result, generateTreesWithHeightAndNodes(h - 1, ln), generateTreesWithHeightAndNodes(h - 1, rn));
      // Heights: (h-1, h-2)
      addCombinations(result, generateTreesWithHeightAndNodes(h - 1, ln), generateTreesWithHeightAndNodes(h - 2, rn));
      // Heights: (h-2, h-1)
      addCombinations(result, generateTreesWithHeightAndNodes(h - 2, ln), generateTreesWithHeightAndNodes(h - 1, rn));
    }
    return result;
  }

  private static void addCombinations(
      List<BtreeP61.Node<String>> result,
      List<BtreeP61.Node<String>> lefts,
      List<BtreeP61.Node<String>> rights) {
    for (BtreeP61.Node<String> l : lefts) {
      for (BtreeP61.Node<String> r : rights) {
        BtreeP61.Node<String> node = BtreeP61.Node.of("x", l, r);
        if (!result.contains(node)) {
          result.add(node);
        }
      }
    }
  }
}
