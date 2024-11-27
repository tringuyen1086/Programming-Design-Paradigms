package bst;

/**
 * Represents an empty node in the binary search tree.
 * It acts as a leaf node and provides base cases for recursive operations.
 *
 * @param <T> the type of element stored in the tree
 */
public class GenericEmptyNode<T extends Comparable<T>> implements GenericElementNode<T> {

  @Override
  public GenericElementNode<T> insert(T data) {
    return new GenericTreeNode<>(data);
  }

  @Override
  public boolean contains(T data) {
    return false;
  }

  @Override
  public GenericElementNode<T> delete(T data) {
    return this;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public T findMin() {
    return null;
  }

  @Override
  public T findMax() {
    return null;
  }

  @Override
  public String preOrder() {
    return "";
  }

  @Override
  public String inOrder() {
    return "";
  }

  @Override
  public String postOrder() {
    return "";
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof GenericEmptyNode;
  }

  @Override
  public int hashCode() {
    return 0; // Simple constant for empty nodes
  }
}
