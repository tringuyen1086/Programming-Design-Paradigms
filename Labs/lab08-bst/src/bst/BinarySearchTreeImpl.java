package bst;

/**
 * Implementation of a Binary Search Tree (BST) that supports insertion, deletion,
 * search, and traversal operations. This implementation uses recursive nodes
 * to handle tree structure and operations efficiently.
 *
 * @param <T> the type of elements stored in the tree, which must be comparable
 */
public class BinarySearchTreeImpl<T extends Comparable<T>> implements BinarySearchTree<T> {
  private GenericElementNode<T> root;

  /**
   * Constructs an empty Binary Search Tree.
   */
  public BinarySearchTreeImpl() {
    this.root = new GenericEmptyNode<>();
  }

  @Override
  public void add(T data) {
    root = root.insert(data);
  }

  @Override
  public int size() {
    return root.size();
  }

  @Override
  public int height() {
    return root.getHeight();
  }

  @Override
  public boolean present(T data) {
    return root.contains(data);
  }

  @Override
  public T minimum() {
    return root.findMin();
  }

  @Override
  public T maximum() {
    return root.findMax();
  }

  @Override
  public String preOrder() {
    return "[" + root.preOrder().trim() + "]";
  }

  @Override
  public String inOrder() {
    return "[" + root.inOrder().trim() + "]";
  }

  @Override
  public String postOrder() {
    return "[" + root.postOrder().trim() + "]";
  }

  @Override
  public String toString() {
    return "[" + root.inOrder().trim() + "]";
  }

  /**
   * Compares this tree with another object for equality. Two trees are considered equal
   * if they have the same structure and data in each corresponding node.
   *
   * @param obj the object to compare with
   * @return true if the trees are structurally identical with the same data, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof BinarySearchTreeImpl)) {
      return false;
    }
    BinarySearchTreeImpl<?> other = (BinarySearchTreeImpl<?>) obj;
    return root.equals(other.root);
  }

  /**
   * Returns the hash code for this binary search tree.
   *
   * @return the hash code based on the structure and data of the tree
   */
  @Override
  public int hashCode() {
    return root.hashCode();
  }
}