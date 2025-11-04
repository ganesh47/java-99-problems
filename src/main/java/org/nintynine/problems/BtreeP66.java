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
    return shiftNode(result.node(), offset);
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

    BtreeP64.PositionedNode<T> leftNode =
        shiftNode(left.node(), -shift);
    BtreeP64.PositionedNode<T> rightNode =
        shiftNode(right.node(), shift);

    BtreeP64.PositionedNode<T> positioned =
        new BtreeP64.PositionedNode<>(node.getValue(), 0, depth, leftNode, rightNode);

    int height = Math.max(left.height(), right.height()) + 1;
    int[] leftContour = new int[height];
    int[] rightContour = new int[height];
    leftContour[0] = 0;
    rightContour[0] = 0;

    for (int i = 1; i < height; i++) {
      int leftValue = Integer.MAX_VALUE;
      int rightValue = Integer.MAX_VALUE;
      if (left.node() != null && i - 1 < left.leftContour().length) {
        leftValue = left.leftContour()[i - 1] - shift;
      }
      if (right.node() != null && i - 1 < right.leftContour().length) {
        rightValue = right.leftContour()[i - 1] + shift;
      }
      if (leftValue == Integer.MAX_VALUE && rightValue == Integer.MAX_VALUE) {
        leftContour[i] = 0;
      } else if (rightValue == Integer.MAX_VALUE) {
        leftContour[i] = leftValue;
      } else if (leftValue == Integer.MAX_VALUE) {
        leftContour[i] = rightValue;
      } else {
        leftContour[i] = Math.min(leftValue, rightValue);
      }

      int leftRight = Integer.MIN_VALUE;
      int rightRight = Integer.MIN_VALUE;
      if (left.node() != null && i - 1 < left.rightContour().length) {
        leftRight = left.rightContour()[i - 1] - shift;
      }
      if (right.node() != null && i - 1 < right.rightContour().length) {
        rightRight = right.rightContour()[i - 1] + shift;
      }
      if (leftRight == Integer.MIN_VALUE && rightRight == Integer.MIN_VALUE) {
        rightContour[i] = 0;
      } else if (rightRight == Integer.MIN_VALUE) {
        rightContour[i] = leftRight;
      } else if (leftRight == Integer.MIN_VALUE) {
        rightContour[i] = rightRight;
      } else {
        rightContour[i] = Math.max(leftRight, rightRight);
      }
    }

    return new Result<>(positioned, leftContour, rightContour, height);
  }

  private static <T> BtreeP64.PositionedNode<T> shiftNode(
      BtreeP64.PositionedNode<T> node, int delta) {
    if (node == null || delta == 0) {
      return node;
    }
    BtreeP64.PositionedNode<T> left =
        shiftNode(node.getLeft(), delta);
    BtreeP64.PositionedNode<T> right =
        shiftNode(node.getRight(), delta);
    return new BtreeP64.PositionedNode<>(
        node.getValue(), node.getX() + delta, node.getY(), left, right);
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
