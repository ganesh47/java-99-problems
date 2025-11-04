package org.nintynine.problems;

import java.util.Objects;

/**
 * Problem P64: layout a binary tree using inorder x-positioning.
 */
public final class BtreeP64 {

  private BtreeP64() {
    // utility class
  }

  /**
   * Layout the supplied tree so that the x-coordinate follows the inorder traversal index and the y-coordinate is the depth.
   *
   * @param root tree root
   * @param <T> element type
   * @return positioned tree or {@code null}
   */
  public static <T> PositionedNode<T> layoutBinaryTree(BtreeP61.Node<T> root) {
    if (root == null) {
      return null;
    }
    return layout(root, 1, 1).node();
  }

  private static <T> LayoutResult<T> layout(BtreeP61.Node<T> node, int depth, int nextX) {
    if (node == null) {
      return new LayoutResult<>(null, nextX);
    }

    LayoutResult<T> leftResult = layout(node.getLeft(), depth + 1, nextX);
    int currentX = leftResult.nextX();
    LayoutResult<T> rightResult =
        layout(node.getRight(), depth + 1, currentX + 1);

    PositionedNode<T> positioned =
        new PositionedNode<>(
            node.getValue(),
            currentX,
            depth,
            leftResult.node(),
            rightResult.node());

    return new LayoutResult<>(positioned, rightResult.nextX());
  }

  private record LayoutResult<T>(PositionedNode<T> node, int nextX) {}

  /**
   * Positioned binary tree node.
   *
   * @param <T> element type
   */
  public static final class PositionedNode<T> {
    private final T value;
    private final int x;
    private final int y;
    private final PositionedNode<T> left;
    private final PositionedNode<T> right;

    public PositionedNode(
        T value, int x, int y, PositionedNode<T> left, PositionedNode<T> right) {
      this.value = Objects.requireNonNull(value, "value");
      this.x = x;
      this.y = y;
      this.left = left;
      this.right = right;
    }

    public T getValue() {
      return value;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public PositionedNode<T> getLeft() {
      return left;
    }

    public PositionedNode<T> getRight() {
      return right;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof PositionedNode<?> that)) {
        return false;
      }
      return x == that.x
          && y == that.y
          && Objects.equals(value, that.value)
          && Objects.equals(left, that.left)
          && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, x, y, left, right);
    }
  }
}
