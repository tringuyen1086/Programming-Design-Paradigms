package bst;

/**
 * Represents a node in a binary search tree, supporting core operations
 * such as insertion, search, deletion, and traversal.
 *
 * @param <T> the type of element stored in the tree, which must be comparable.
 */
public interface GenericElementNode<T extends Comparable<T>> {

  /**
   * Inserts the specified data into the binary search tree.
   *
   * @param data the data to be inserted
   * @return the root of the modified tree
   */
  GenericElementNode<T> insert(T data);

  /**
   * Checks if the specified data is present in the binary search tree.
   *
   * @param data the data to be searched
   * @return true if the data is found, false otherwise
   */
  boolean contains(T data);

  /**
   * Deletes the specified data from the binary search tree.
   *
   * @param data the data to be deleted
   * @return the root of the modified tree
   */
  GenericElementNode<T> delete(T data);

  /**
   * Returns the number of nodes in this subtree.
   *
   * @return the size of the subtree
   */
  int size();

  /**
   * Returns the height of the tree.
   *
   * @return the height of the tree
   */
  int getHeight();

  /**
   * Finds and returns the minimum value in the binary search tree.
   *
   * @return the minimum value if it exists, null otherwise
   */
  T findMin();

  /**
   * Finds and returns the maximum value in the binary search tree.
   *
   * @return the maximum value if it exists, null otherwise
   */
  T findMax();

  /**
   * Returns a pre-order traversal of the tree as a string.
   *
   * @return a string containing the pre-order traversal
   */
  String preOrder();

  /**
   * Returns an in-order traversal of the tree as a string.
   *
   * @return a string containing the in-order traversal
   */
  String inOrder();

  /**
   * Returns a post-order traversal of the tree as a string.
   *
   * @return a string containing the post-order traversal
   */
  String postOrder();
}