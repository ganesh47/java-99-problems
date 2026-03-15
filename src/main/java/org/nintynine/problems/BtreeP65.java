package org.nintynine.problems;

/**
 * Problem P65: layout a binary tree with exponentially decreasing horizontal distance per level.
 */
public final class BtreeP65 {

  private BtreeP65() {
    // utility class
  }

  /**
   * Layout a binary tree such that nodes on level {@code d} are spaced by {@code 2^{h-d}}
   * where {@code h} is the tree height.
   *
   * @param root tree root
   * @param <T> element type
   * @return positioned tree representation
   */
  public static <T> BtreeP64.PositionedNode<T> layoutBinaryTree(BtreeP61.Node<T> root) {
    if (root == null) {
      return null;
    }
    int height = root.height();
    int rootX = 1 << (height - 1);
    return layout(root, 1, height, rootX);
  }

  private static <T> BtreeP64.PositionedNode<T> layout(
      BtreeP61.Node<T> node, int depth, int height, int x) {
    if (node == null) {
      return null;
    }
    int offset = (depth >= height) ? 0 : 1 << (height - depth - 1);
    BtreeP64.PositionedNode<T> left = layout(node.getLeft(), depth + 1, height, x - offset);
    BtreeP64.PositionedNode<T> right = layout(node.getRight(), depth + 1, height, x + offset);
    return new BtreeP64.PositionedNode<>(node.getValue(), x, depth, left, right);
  }
}
