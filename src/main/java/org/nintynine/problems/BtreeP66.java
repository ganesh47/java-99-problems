package org.nintynine.problems;

/**
 * Problem P66: layout a binary tree as compact as possible while preserving symmetry.
 */
public final class BtreeP66 {

  private BtreeP66() {
    // utility class
  }

  /**
   * Layout the tree using the compact algorithm from 99-Problem P66.
   *
   * @param root source tree
   * @param <T> element type
   * @return positioned node or {@code null}
   */
  public static <T> BtreeP64.PositionedNode<T> layoutBinaryTree(BtreeP61.Node<T> root) {
    Result<T> result = layout(root, 1);
    if (result.node() == null) {
      return null;
    }
    int minX = 0;
    for (int value : result.leftContour()) {
      minX = Math.min(minX, value);
    }
    int offset = 1 - minX;
    return result.node().shift(offset);
  }

  private static <T> Result<T> layout(BtreeP61.Node<T> node, int depth) {
    if (node == null) {
      return new Result<>(null, new int[0], new int[0], 0);
    }

    Result<T> left = layout(node.getLeft(), depth + 1);
    Result<T> right = layout(node.getRight(), depth + 1);

    if (left.node() == null && right.node() == null) {
      BtreeP64.PositionedNode<T> positioned =
          new BtreeP64.PositionedNode<>(node.getValue(), 0, depth, null, null);
      return new Result<>(positioned, new int[] {0}, new int[] {0}, 1);
    }

    int shift;
    if (left.node() != null && right.node() != null) {
      shift = Math.max(1, requiredShift(left.rightContour(), right.leftContour()));
    } else {
      shift = 1;
    }

    int height = Math.max(left.height(), right.height()) + 1;
    int[] leftContour = new int[height];
    int[] rightContour = new int[height];
    leftContour[0] = 0;
    rightContour[0] = 0;

    for (int i = 1; i < height; i++) {
      int leftValue = getValueAt(left.leftContour(), i - 1, left.node() != null, -shift, Integer.MAX_VALUE);
      int rightValue = getValueAt(right.leftContour(), i - 1, right.node() != null, shift, Integer.MAX_VALUE);

      if (leftValue == Integer.MAX_VALUE && rightValue == Integer.MAX_VALUE) {
        leftContour[i] = 0;
      } else {
        leftContour[i] = Math.min(leftValue, rightValue);
      }

      int leftRight = getValueAt(left.rightContour(), i - 1, left.node() != null, -shift, Integer.MIN_VALUE);
      int rightRight = getValueAt(right.rightContour(), i - 1, right.node() != null, shift, Integer.MIN_VALUE);

      if (leftRight == Integer.MIN_VALUE && rightRight == Integer.MIN_VALUE) {
        rightContour[i] = 0;
      } else {
        rightContour[i] = Math.max(leftRight, rightRight);
      }
    }

    BtreeP64.PositionedNode<T> leftNode = left.node() == null ? null : left.node().shift(-shift);
    BtreeP64.PositionedNode<T> rightNode = right.node() == null ? null : right.node().shift(shift);
    BtreeP64.PositionedNode<T> positioned =
        new BtreeP64.PositionedNode<>(node.getValue(), 0, depth, leftNode, rightNode);

    return new Result<>(positioned, leftContour, rightContour, height);
  }

  private static int getValueAt(int[] contour, int idx, boolean exists, int shift, int defaultValue) {
    if (exists && idx < contour.length) {
      return contour[idx] + shift;
    }
    return defaultValue;
  }

  private static int requiredShift(int[] leftRightContour, int[] rightLeftContour) {
    int minLevels = Math.min(leftRightContour.length, rightLeftContour.length);
    int shift = 0;
    for (int i = 0; i < minLevels; i++) {
      int overlap = leftRightContour[i] - rightLeftContour[i] + 1;
      if (overlap > 0) {
        int needed = (overlap + 1) / 2;
        shift = Math.max(shift, needed);
      }
    }
    return shift;
  }

  private record Result<T>(
      BtreeP64.PositionedNode<T> node,
      int[] leftContour,
      int[] rightContour,
      int height) {}
}
