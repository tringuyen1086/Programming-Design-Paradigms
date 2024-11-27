package bst;

/**
 * Represents a non-empty node in the binary search tree, containing data
 * and references to left and right child nodes.
 *
 * @param <T> the type of element stored in the tree
 */
public class GenericTreeNode<T extends Comparable<T>> implements GenericElementNode<T> {
  private T data;
  private GenericElementNode<T> left;
  private GenericElementNode<T> right;

  /**
   * Constructs a GenericTreeNode with the specified data.
   *
   * @param data the data to be stored in this node
   */
  public GenericTreeNode(T data) {
    this.data = data;
    this.left = new GenericEmptyNode<>();
    this.right = new GenericEmptyNode<>();
  }

  /**
   * Inserts the specified data into the binary search tree
   * while maintaining the binary search tree properties.
   * This method recursively creates a union of subtrees by adding
   * the data to the appropriate subtree, either left or right.
   *
   * <p>Recursive Structure:
   * Each {@link GenericTreeNode} is a recursive union
   * of left and right subtrees.
   * The method calls itself on either the left or right subtree
   * based on the comparison result of the data.
   *
   * <p>Base case:
   * If this node is a {@link GenericEmptyNode}, a new {@link GenericTreeNode}
   * is created with the specified data.
   * Recursive case: If the data is less than this node's data,
   * it is inserted into the left subtree;
   * if greater, it is inserted into the right subtree.
   *
   * @param data the data to be inserted
   * @return the root of the modified subtree
   */
  @Override
  public GenericElementNode<T> insert(T data) {
    int cmp = data.compareTo(this.data);
    if (cmp < 0) {
      left = left.insert(data); // Recursive call on the left subtree
    } else if (cmp > 0) {
      right = right.insert(data); // Recursive call on the right subtree
    }
    return this;
  }

  /**
   * Recursively checks if the specified data
   * is present in the binary search tree.
   * This method calls itself on the left or right subtree
   * based on data comparison, until it either finds the data
   * or reaches a {@link GenericEmptyNode}.
   *
   * <p>Base case:
   * If this node is a {@link GenericEmptyNode}, the data is not found.
   * Recursive case: If the data is less than this node's data,
   * search left; if greater, search right.
   *
   * @param data the data to be searched
   * @return true if the data is found, false otherwise
   */
  @Override
  public boolean contains(T data) {
    int cmp = data.compareTo(this.data);
    if (cmp < 0) {
      return left.contains(data); // Recursive search in left subtree
    } else if (cmp > 0) {
      return right.contains(data); // Recursive search in right subtree
    }
    return true; // Data found
  }

  /**
   * Recursively deletes the specified data from the binary search tree
   * while maintaining the binary search tree properties.
   * This method creates a recursive union of subtrees after deletion.
   *
   * <p>Base case: If this node is a {@link GenericEmptyNode}, the data is not found.
   * Recursive case: If the data is less than this node's data, delete from left subtree;
   * if greater, delete from right subtree. If the data matches, handle replacement cases.
   *
   * @param data the data to be deleted
   * @return the root of the modified subtree
   */
  @Override
  public GenericElementNode<T> delete(T data) {
    int cmp = data.compareTo(this.data);
    if (cmp < 0) {
      left = left.delete(data); // Recursive deletion in left subtree
    } else if (cmp > 0) {
      right = right.delete(data); // Recursive deletion in right subtree
    } else {
      if (left instanceof GenericEmptyNode) {
        return right; // Case of single child or leaf
      }
      if (right instanceof GenericEmptyNode) {
        return left;
      }
      T minData = right.findMin();
      this.data = minData;
      right = right.delete(minData); // Recursive deletion of successor
    }
    return this;
  }

  /**
   * Recursively calculates the size of the subtree rooted at this node.
   * This method leverages the recursive union structure
   * by summing up the sizes of the left and right subtrees,
   * each of which is represented as a union of nodes.
   *
   * <p>Base case:
   * If the node is a {@link GenericEmptyNode}, the size is 0.
   * Recursive case: For a {@link GenericTreeNode},
   * the size is calculated as 1 (for this node)
   * plus the size of the left and right subtrees.
   *
   * @return the number of nodes in the subtree rooted at this node
   */
  @Override
  public int size() {
    return 1 + left.size() + right.size(); // Recursively sums sizes of subtrees
  }

  /**
   * Recursively calculates the height of the subtree rooted at this node,
   * which is defined as the number of edges on the longest path
   * from this node to a descendant leaf node.
   * This method exploits the recursive union structure by calculating
   * the height of each subtree independently.
   *
   * <p>Base case:
   * If the node is a {@link GenericEmptyNode}, the height is 0.
   * Recursive case: For a {@link GenericTreeNode},
   * the height is calculated as 1 plus the maximum height
   * of the left and right subtrees.
   *
   * @return the height of the subtree rooted at this node
   */
  @Override
  public int getHeight() {
    // Computes height recursively
    return 1 + Math.max(left.getHeight(), right.getHeight());
  }

  @Override
  public T findMin() {
    return (left instanceof GenericEmptyNode) ? data : left.findMin();
  }

  @Override
  public T findMax() {
    return (right instanceof GenericEmptyNode) ? data : right.findMax();
  }

  /**
   * Performs a pre-order traversal of the binary search tree.
   * Visits the root node first, followed by the left subtree,
   * and then the right subtree.
   * Each subtree traversal result is concatenated
   * to form the full pre-order traversal string.
   *
   * <p>If the left or right subtree traversal results are not empty,
   * a space is added between the data of the current node
   * and the subtree traversal result, ensuring proper formatting.</p>
   *
   * @return a string containing the elements of the tree in pre-order traversal,
    separated by spaces.
   */
  @Override
  public String preOrder() {
    StringBuilder result = new StringBuilder();

    result.append(data.toString()); // root node should be appended first

    String leftStr = left.preOrder().trim();
    if (!leftStr.isEmpty()) {
      result.append(" ").append(leftStr);
    }

    String rightStr = right.preOrder().trim();
    if (!rightStr.isEmpty()) {
      result.append(" ").append(rightStr);
    }

    return result.toString();
  }

  /**
   * Performs an in-order traversal of the binary search tree.
   * Visits the left subtree first, followed by the root node,
   * and then the right subtree.
   * Each subtree traversal result is concatenated with the current node’s data
   * to form the full in-order traversal string.
   *
   * <p>If the left or right subtree traversal results are not empty,
   * spaces are added as needed to separate the current node’s data
   * from the subtree traversal result, ensuring proper formatting.</p>
   *
   * @return a string containing the elements of the tree in in-order traversal,
    separated by spaces.
   */
  @Override
  public String inOrder() {
    StringBuilder result = new StringBuilder();

    String leftStr = left.inOrder().trim();
    if (!leftStr.isEmpty()) {
      result.append(leftStr).append(" ");
    }

    result.append(data.toString()); // root node should be appended in the middle

    String rightStr = right.inOrder().trim();
    if (!rightStr.isEmpty()) {
      result.append(" ").append(rightStr);
    }

    return result.toString();
  }

  /**
   * Performs a post-order traversal of the binary search tree.
   * Visits the left subtree first, followed by the right subtree,
   * and then the root node.
   * Each subtree traversal result is concatenated
   * to form the full post-order traversal string.
   *
   * <p>If the left or right subtree traversal results are not empty,
   * a space is added as needed to separate the subtree traversal result
   * from the current node’s data, ensuring proper formatting.</p>
   *
   * @return a string containing the elements of the tree in post-order traversal,
    separated by spaces.
   */
  @Override
  public String postOrder() {
    StringBuilder result = new StringBuilder();

    String leftStr = left.postOrder().trim();
    if (!leftStr.isEmpty()) {
      result.append(leftStr).append(" ");
    }

    String rightStr = right.postOrder().trim();
    if (!rightStr.isEmpty()) {
      result.append(rightStr).append(" ");
    }

    result.append(data.toString()); // root node should be appended last

    return result.toString();
  }

  /**
   * Returns a string representation of the binary search tree
   * in in-order format.
   *
   * @return a string containing the elements of the tree in in-order traversal
   */
  @Override
  public String toString() {
    return data.toString();
  }

  /**
   * Compares this node to another object for equality.
   * Two nodes are considered equal if they have the same
   * data and their left and right subtrees are
   * structurally identical and contain the same data.
   *
   * @param obj the object to compare with
   * @return true if the nodes are structurally identical
    with the same data, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof GenericTreeNode)) {
      return false;
    }
    GenericTreeNode<?> other = (GenericTreeNode<?>) obj;
    return data.equals(other.data)
            && left.equals(other.left)
            && right.equals(other.right);
  }

  /**
   * Returns the hash code for this node.
   * The hash code is computed based on the data of this node
   * and the hash codes of the left and right subtrees.
   *
   * @return the hash code for this node
   */
  @Override
  public int hashCode() {
    // Multiply by different values for left and right to ensure order matters
    return data.hashCode() + 2 * left.hashCode() + 3 * right.hashCode();
  }
}
