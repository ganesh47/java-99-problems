package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic list class that provides combination generation functionality.
 *
 * @param <T> the type of elements in the list
 */
public class MyListP26<T> extends MyListP25<T> {

  /**
   * Constructs a new MyListP26 instance with the given elements.
   *
   * @param elements the elements to initialize the list with
   */
  @SafeVarargs
  public MyListP26(T... elements) {
    super(elements);
  }

  /**
   * Calculates the number of possible combinations (n choose k).
   *
   * @param n total number of elements
   * @param k number of elements to choose
   * @return number of possible combinations
   */
  public static long binomialCoefficient(int n, int k) {
    if (k < 0 || k > n) return 0;
    if (k == 0 || k == n) return 1;

    // Use symmetry to optimize calculation
    if (k > n - k) {
      k = n - k;
    }

    long result = 1;
    for (int i = 0; i < k; i++) {
      result = result * (n - i) / (i + 1);
    }
    return result;
  }

  /**
   * Generates all combinations of K elements from the list.
   *
   * <p>Examples:
   *
   * <pre>
   * [a, b, c], k=2 → [[a, b], [a, c], [b, c]]
   * [1, 2, 3, 4], k=3 → [[1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4]]
   * [x, y], k=1 → [[x], [y]]
   * </pre>
   *
   * @param k number of elements in each combination
   * @return list of all possible combinations
   * @throws IllegalArgumentException if k is negative or greater than list size
   */
  public List<MyListP26<T>> combinations(int k) {
    if (k < 0) {
      throw new IllegalArgumentException("k cannot be negative");
    }
    if (k > length()) {
      throw new IllegalArgumentException("k cannot be greater than list size");
    }

    List<MyListP26<T>> result = new ArrayList<>();
    generateCombinations(0, k, new ArrayList<>(), result);
    return result;
  }

  @SuppressWarnings("unchecked")
  private void generateCombinations(int start, int k, List<T> current, List<MyListP26<T>> result) {
    if (k == 0) {
      // Create a new combination when we've selected k elements
      result.add(new MyListP26<>(current.toArray((T[]) new Object[0])));
      return;
    }

    // For each remaining position, try to include the element at that position
    for (int i = start; i <= length() - k; i++) {
      current.add(elementAt(1 + i));
      generateCombinations(i + 1, k - 1, current, result);
      current.removeLast();
    }
  }
}
