import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import bst.BinarySearchTree;
import bst.BinarySearchTreeImpl;
import bst.GenericElementNode;
import bst.GenericEmptyNode;
import bst.GenericTreeNode;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for BinarySearchTreeImpl,
 * GenericEmptyNode, and GenericTreeNode classes,
 * covering insertion, traversal, deletion, size, height,
 * and search functionalities.
 */
public class BinarySearchTreeTest {
  private BinarySearchTree<Integer> bst;
  private BinarySearchTree<Integer> otherBst;

  private GenericEmptyNode<Integer> emptyNode;
  private GenericTreeNode<Integer> rootNode;

  /**
   * Sets up an empty binary search tree, an empty node instance,
   * and a root node instance before each test.
   */
  @Before
  public void setUp() {
    bst = new BinarySearchTreeImpl<>();
    otherBst = new BinarySearchTreeImpl<>();
    emptyNode = new GenericEmptyNode<>();
    rootNode = new GenericTreeNode<>(10);
  }

  /**
   * Tests the default constructor by verifying that an empty tree has no elements.
   */
  @Test
  public void testDefaultConstructor() {
    assertEquals("[]", bst.toString());
  }

  /**
   * Tests and confirms that traversals (in-order, pre-order, post-order)
   * return empty lists for an empty tree.
   */
  @Test
  public void testAllTraversalsEmptyTree() {
    assertEquals("[]", bst.inOrder());
    assertEquals("[]", bst.preOrder());
    assertEquals("[]", bst.postOrder());
  }

  /**
   * Tests adding a single element to an initially empty tree.
   * Verifies that the element is added as the root
   * and that the tree contains only that element.
   */
  @Test
  public void testAddEmptyTree() {
    // Verify that the tree is empty
    assertEquals(0, bst.size());

    // Add an element to the tree
    bst.add(10);

    // Verify the tree contains only the added element and is correctly structured
    assertEquals("[10]", bst.toString());
    assertEquals(1, bst.size());
    assertEquals(1, bst.height());
    assertEquals("10", bst.minimum().toString());
    assertEquals("10", bst.maximum().toString());
  }

  /**
   * Tests add method to check if duplicates are handled correctly.
   * Verifies that adding duplicate elements
   * does not increase the size of the tree
   * and maintains a single instance of the element.
   */
  @Test
  public void testAddDuplicate() {
    bst.add(10);
    bst.add(10); // Duplicate insertion
    assertEquals(1, bst.size());
    assertTrue(bst.present(10));
  }

  /**
   * Tests adding a new element to a non-empty tree and verifies
   * the in-order traversal string to ensure elements are ordered correctly.
   */
  @Test
  public void testAddWhenTreeIsNotEmpty() {
    bst.add(8);
    bst.add(4);
    bst.add(11);
    bst.add(2);
    bst.add(5);
    bst.add(9);
    bst.add(15);
    bst.add(7);
    bst.add(14);
    assertEquals("[2 4 5 7 8 9 11 14 15]", bst.toString());
    bst.add(16);
    assertEquals("[2 4 5 7 8 9 11 14 15 16]", bst.toString());
  }

  /**
   * Tests that inserting data into an empty node
   * returns a new instance of GenericTreeNode.
   */
  @Test
  public void testEmptyNodeInsert() {
    GenericElementNode<Integer> resultNode = emptyNode.insert(5);
    assertTrue(resultNode instanceof GenericTreeNode);
  }

  /**
   * Tests inserting elements into a non-empty node to form a balanced tree.
   */
  @Test
  public void testInsertBalancedTree() {
    // Insert nodes to create a balanced tree
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);
    rootNode.insert(7);
    rootNode.insert(12);
    rootNode.insert(18);

    String formattedInOrder = "[" + rootNode.inOrder().trim() + "]";
    assertEquals("[3 5 7 10 12 15 18]", formattedInOrder);
  }

  /**
   * Tests that the contains method returns false for any data in an empty node.
   */
  @Test
  public void testEmptyNodeContains() {
    assertFalse(emptyNode.contains(5));
  }

  /**
   * Tests that contains method correctly identifies existing elements
   * and returns false for non-existent elements in a balanced tree.
   */
  @Test
  public void testContainsInTree() {
    rootNode.insert(5);
    rootNode.insert(15);
    assertTrue(rootNode.contains(10)); // Root
    assertTrue(rootNode.contains(5));  // Left child
    assertTrue(rootNode.contains(15)); // Right child
    assertFalse(rootNode.contains(8)); // Non-existent
  }

  /**
   * Tests that deleting data from an empty node has no effect,
   * returning the same empty node.
   */
  @Test
  public void testEmptyNodeDelete() {
    assertEquals(emptyNode, emptyNode.delete(5));
  }

  @Test
  public void testDeleteLeafNode() {
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);

    // Delete the leaf node with value 3
    rootNode = (GenericTreeNode<Integer>) rootNode.delete(3);

    String formattedInOrder = "[" + rootNode.inOrder().trim() + "]";
    assertEquals("[5 10 15]", formattedInOrder);
  }

  /**
   * Tests deleting a node with two children
   * and confirms the in-order successor
   * replaces the deleted node.
   */
  @Test
  public void testDeleteNodeWithTwoChildren() {
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);
    rootNode.insert(7);
    rootNode.insert(12);
    rootNode.insert(18);

    // Deleting the root node (10) with two children
    rootNode = (GenericTreeNode<Integer>) rootNode.delete(10);

    String formattedInOrder = "[" + rootNode.inOrder().trim() + "]";
    assertEquals("[3 5 7 12 15 18]", formattedInOrder);
  }

  /**
   * Tests size method on a tree with multiple nodes
   * and verifies accurate node count.
   */
  @Test
  public void testSizeMultipleNodes() {
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);
    rootNode.insert(7);

    assertEquals(5, rootNode.size()); // Root node + 4 inserted nodes
  }

  /**
   * Tests that duplicate insertions of multiple values do not
   * change the tree structure or size after the initial addition.
   */
  @Test
  public void testMultipleDuplicateInsertions() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    bst.add(10); // Duplicate
    bst.add(5);  // Duplicate
    bst.add(15); // Duplicate
    assertEquals(3, bst.size());
    assertEquals("[5 10 15]", bst.inOrder());
  }

  /**
   * Tests the size method on an empty tree.
   * Verifies that the size of an empty tree is 0.
   */
  @Test
  public void testSizeEmptyTree() {
    assertEquals(0, bst.size());
  }

  /**
   * Tests that the size of an empty node is 0,
   * representing no data.
   */
  @Test
  public void testEmptyNodeSize() {
    assertEquals(0, emptyNode.size());
  }


  /**
   * Tests the size method on a single-node tree.
   * Verifies that a tree with one element has size 1.
   */
  @Test
  public void testSizeSingleNode() {
    bst.add(10);
    assertEquals(1, bst.size());
  }

  /**
   * Tests the size method on a non-empty tree and verifies the correct number of elements.
   */
  @Test
  public void testSizeOfNonEmptyTree() {
    bst.add(8);
    bst.add(4);
    bst.add(11);
    bst.add(2);
    bst.add(5);
    bst.add(9);
    bst.add(15);
    bst.add(7);
    bst.add(14);
    assertEquals("[2 4 5 7 8 9 11 14 15]", bst.toString());
    assertEquals(9, bst.size());
  }

  /**
   * Tests that the height of an empty node is 0,
   * representing no depth.
   */
  @Test
  public void testEmptyNodeHeight() {
    assertEquals(0, emptyNode.getHeight());
  }

  /**
   * Tests the height method on a balanced tree
   * to verify correct height calculation.
   */
  @Test
  public void testHeightBalancedTree() {
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);
    rootNode.insert(7);

    assertEquals(3, rootNode.getHeight());
  }

  /**
   * Tests height on a left-heavy tree
   * (all nodes added in decreasing order).
   * Verifies that height should equal the number of nodes (unbalanced tree).
   */
  @Test
  public void testHeightLeftHeavyTree() {
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(1);
    assertEquals(4, bst.height());
  }

  /**
   * Tests height on a right-heavy tree
   * (all nodes added in increasing order).
   * Verifies that height should equal the number of nodes (unbalanced tree).
   */
  @Test
  public void testHeightRightHeavyTree() {
    bst.add(1);
    bst.add(3);
    bst.add(5);
    bst.add(10);
    assertEquals(4, bst.height());
  }

  /**
   * Tests the height method on an empty tree.
   * Verifies that the height of an empty tree is 0.
   */
  @Test
  public void testHeightEmptyTree() {
    assertEquals(0, bst.height());
  }

  /**
   * Tests the height method on a single-node tree.
   * Verifies that the height of a tree with only one node is 1.
   */
  @Test
  public void testHeightSingleNode() {
    bst.add(10);
    assertEquals(1, bst.height());
  }

  /**
   * Tests a tree with varying structures
   * by inserting elements and verifying height
   * and the correct pre-order, in-order, and post-order traversals.
   */
  @Test
  public void testDifferentHeights() {
    bst.add(5);
    bst.add(4);
    bst.add(3);
    bst.add(2);
    bst.add(6);
    assertEquals("[2 3 4 5 6]", bst.toString());
    assertEquals("[2 3 4 5 6]", bst.inOrder());
    assertEquals("[5 4 3 2 6]", bst.preOrder());
    assertEquals("[2 3 4 6 5]", bst.postOrder());
    assertEquals(4, bst.height());
  }

  /**
   * Tests the present method on an empty tree.
   * Verifies that searching for an element in an empty tree returns false.
   */
  @Test
  public void testPresentEmptyTree() {
    assertFalse(bst.present(10));
  }

  /**
   * A test that verifies that present returns true
   * when an object is present in the tree.
   */
  @Test
  public void testPresentTrue() {
    BinarySearchTreeImpl<Integer> bst1 = new BinarySearchTreeImpl<>();
    bst1.add(4);
    bst1.add(6);
    bst1.add(2);
    bst1.add(1);
    bst1.add(3);
    bst1.add(5);
    bst1.add(7);
    BinarySearchTreeImpl<Integer> bst2 = new BinarySearchTreeImpl<>();
    bst2.add(6);
    bst2.add(4);
    bst2.add(2);
    bst2.add(1);
    bst2.add(3);
    bst2.add(5);
    bst2.add(7);
    bst2.add(8);
    assertTrue(bst1.present(5));
    assertTrue(bst2.present(8));
  }

  /**
   * A test that verifies that present returns false
   * when an object is not present in the tree.
   */
  @Test
  public void testPresentFalse() {
    BinarySearchTreeImpl<Integer> bst1 = new BinarySearchTreeImpl<>();
    bst1.add(4);
    bst1.add(6);
    bst1.add(2);
    bst1.add(1);
    bst1.add(3);
    bst1.add(5);
    bst1.add(7);
    BinarySearchTreeImpl<Integer> bst2 = new BinarySearchTreeImpl<>();
    bst2.add(6);
    bst2.add(4);
    bst2.add(2);
    bst2.add(1);
    bst2.add(3);
    bst2.add(5);
    bst2.add(7);
    bst2.add(8);
    assertTrue(bst1.present(5));
    assertTrue(bst2.present(8));
    assertFalse(bst1.present(8));
    assertFalse(bst2.present(10));
  }

  /**
   * Tests the present method with only one element in the tree.
   * Verifies that the method correctly identifies the presence of the root node
   * and the absence of any other element.
   */
  @Test
  public void testPresentSingleNode() {
    bst.add(10);
    assertTrue(bst.present(10));
    assertFalse(bst.present(5));  // Non-existent element
  }

  /**
   * tests presence of elements after insertion.
   */
  @Test
  public void testPresenceAfterInsertion() {
    bst.add(10);
    bst.add(5);
    bst.add(15);

    assertTrue(bst.present(10));
    assertTrue(bst.present(5));
    assertTrue(bst.present(15));
    assertFalse(bst.present(3)); // Non-existent element
  }

  /**
   * Tests if an element is present in the tree
   * by searching for an element that exists.
   */
  @Test
  public void testPresentElementIfElementPresentInTree() {
    bst.add(8);
    bst.add(4);
    bst.add(11);
    bst.add(2);
    bst.add(5);
    bst.add(9);
    bst.add(15);
    bst.add(7);
    bst.add(14);
    assertEquals("[2 4 5 7 8 9 11 14 15]", bst.toString());
    assertTrue(bst.present(14));
  }

  /**
   * Tests if an element is not present in the tree
   * by searching for an element that does not exist.
   */
  @Test
  public void testPresentElementIfElementNotPresentInTree() {
    bst.add(8);
    bst.add(4);
    bst.add(11);
    bst.add(2);
    bst.add(5);
    bst.add(9);
    bst.add(15);
    bst.add(7);
    bst.add(14);
    assertEquals("[2 4 5 7 8 9 11 14 15]", bst.toString());
    assertFalse(bst.present(16));
  }


  /**
   * Tests that the findMin method on an empty node returns null,
   * as there is no minimum value.
   */
  @Test
  public void testMinimumNull() {
    assertNull(emptyNode.findMin());
  }

  /**
   * Tests findMin method in a tree with multiple nodes,
   * confirming leftmost node.
   */
  @Test
  public void testFindMin() {
    rootNode.insert(5);
    rootNode.insert(3);
    rootNode.insert(7);

    assertEquals(Integer.valueOf(3), rootNode.findMin());
  }

  /**
   * Tests the minimum method on an empty tree.
   * Verifies that the minimum value of an empty tree is null.
   */
  @Test
  public void testMinimumEmptyTree() {
    assertNull(bst.minimum());
  }

  /**
   * Tests the minimum method on a single-node tree.
   * Verifies that the minimum value of a single-node tree
   * is the root element itself.
   */
  @Test
  public void testMinimumSingleNode() {
    bst.add(10);
    assertEquals(Integer.valueOf(10), bst.minimum());
  }

  /**
   * Tests minimum on a left-heavy tree
   * (all nodes added in decreasing order).
   * Verifies that minimum should be the leftmost element.
   */
  @Test
  public void testMinimumLeftHeavyTree() {
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(1);
    assertEquals(Integer.valueOf(1), bst.minimum());
  }

  /**
   * Tests that the findMax method on an empty node returns null,
   * as there is no maximum value.
   */
  @Test
  public void testEmptyNodeFindMax() {
    assertNull(emptyNode.findMax());
  }

  /**
   * Tests findMax method in a tree with multiple nodes,
   * confirming rightmost node.
   */
  @Test
  public void testFindMax() {
    rootNode.insert(15);
    rootNode.insert(12);
    rootNode.insert(18);

    assertEquals(Integer.valueOf(18), rootNode.findMax());
  }

  /**
   * Tests the maximum method on an empty tree.
   * Verifies that the maximum value of an empty tree is null.
   */
  @Test
  public void testMaximumNull() {
    assertNull(bst.maximum());
  }

  /**
   * Tests the maximum method on a single-node tree.
   * Verifies that the maximum value of a single-node tree
   * is the root element itself.
   */
  @Test
  public void testMaximumSingleNode() {
    bst.add(10);
    assertEquals(Integer.valueOf(10), bst.maximum());
  }

  /**
   * Tests maximum on a right-heavy tree
   * (all nodes added in increasing order).
   * Verifies that maximum should be the rightmost element.
   */
  @Test
  public void testMaximumRightHeavyTree() {
    bst.add(1);
    bst.add(3);
    bst.add(5);
    bst.add(10);
    assertEquals(Integer.valueOf(10), bst.maximum());
  }

  /**
   * tests minimum and maximum values after multiple insertions.
   */
  @Test
  public void testMinMaxValues() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    bst.add(3);
    bst.add(7);

    // Confirm minimum and maximum values
    assertEquals(Integer.valueOf(3), bst.minimum());
    assertEquals(Integer.valueOf(15), bst.maximum());
  }

  /**
   * Tests repetitive calls to minimum and maximum methods on a populated tree
   * to ensure the values remain consistent after multiple queries.
   */
  @Test
  public void testRepeatedMinMaxCalls() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    bst.add(3);
    bst.add(7);
    bst.add(12);
    bst.add(18);

    // Ensure minimum and maximum calls return stable values.
    assertEquals(Integer.valueOf(3), bst.minimum());
    assertEquals(Integer.valueOf(3), bst.minimum());  // Repeat to confirm stability
    assertEquals(Integer.valueOf(18), bst.maximum());
    assertEquals(Integer.valueOf(18), bst.maximum()); // Repeat to confirm stability
  }

  /**
   * Tests that the preOrder traversal of an empty node returns an empty string.
   */
  @Test
  public void testPreOrderEmptyNode() {
    assertEquals("", emptyNode.preOrder());
  }

  /**
   * Tests pre-order traversal on an empty tree.
   * Verifies that the pre-order traversal of an empty tree returns an empty string.
   */
  @Test
  public void testPreOrderTraversalEmptyTree() {
    assertEquals("[]", bst.preOrder());
  }

  /**
   * Tests pre-order traversal of a tree with multiple nodes.
   * Ensures elements are visited in root-left-right order.
   */
  @Test
  public void testPreOrderTraversalMultipleNodes() {
    // Add the root element to the BinarySearchTreeImpl instance
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(7);
    bst.add(15);

    // bst handles adding the brackets around the output
    assertEquals("[10 5 3 7 15]", bst.preOrder());
  }

  /**
   * Tests pre-order traversal on a single-node tree.
   * Verifies that the pre-order traversal of a tree with one element
   * returns that element alone.
   */
  @Test
  public void testPreOrderTraversalSingleNode() {
    bst.add(10);
    assertEquals("[10]", bst.preOrder());
  }

  /**
   * Tests pre-order traversal on a left-heavy tree to ensure
   * elements are visited in root-left-right order.
   */
  @Test
  public void testPreOrderTraversalLeftHeavyTree() {
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(1);
    assertEquals("[10 5 3 1]", bst.preOrder());
  }

  /**
   * Tests pre-order traversal on a right-heavy tree to ensure
   * elements are visited in root-left-right order.
   */
  @Test
  public void testPreOrderTraversalRightHeavyTree() {
    bst.add(1);
    bst.add(3);
    bst.add(5);
    bst.add(10);
    assertEquals("[1 3 5 10]", bst.preOrder());
  }

  /**
   * Tests that the inOrder traversal of an empty node returns an empty string.
   */
  @Test
  public void testInOrderEmptyNode() {
    assertEquals("", emptyNode.inOrder());
  }

  /**
   * Tests in-order traversal of a tree with multiple nodes.
   * Ensures elements appear in increasing order.
   */
  @Test
  public void testInOrderTraversalMultipleNodes() {
    rootNode.insert(5);
    rootNode.insert(15);
    rootNode.insert(3);
    rootNode.insert(7);

    String formattedInOrder = "[" + rootNode.inOrder().trim() + "]";
    assertEquals("[3 5 7 10 15]", formattedInOrder);
  }

  /**
   * Tests in-order traversal on an empty tree.
   * Verifies that the in-order traversal of an empty tree returns an empty string.
   */
  @Test
  public void testInOrderTraversalEmptyTree() {
    assertEquals("[]", bst.inOrder());
  }

  /**
   * Tests in-order traversal on a single-node tree.
   * Verifies that the in-order traversal of a tree with one element
   * returns that element alone.
   */
  @Test
  public void testInOrderTraversalSingleNode() {
    bst.add(10);
    assertEquals("[10]", bst.inOrder());
  }

  /**
   * Tests in-order traversal on a left-heavy tree to ensure
   * elements appear in increasing order.
   */
  @Test
  public void testInOrderTraversalLeftHeavyTree() {
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(1);
    assertEquals("[1 3 5 10]", bst.inOrder());
  }

  /**
   * Tests in-order traversal on a right-heavy tree to ensure
   * elements appear in increasing order.
   */
  @Test
  public void testInOrderTraversalRightHeavyTree() {
    bst.add(1);
    bst.add(3);
    bst.add(5);
    bst.add(10);
    assertEquals("[1 3 5 10]", bst.inOrder());
  }

  /**
   * Tests insertion and validates the in-order traversal
   * to confirm correct structure.
   */
  @Test
  public void testInsertionAndInOrderTraversal() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    bst.add(3);
    bst.add(7);
    bst.add(12);
    bst.add(18);

    // Verify size and in-order structure
    assertEquals(7, bst.size());
    assertEquals("[3 5 7 10 12 15 18]", bst.inOrder());
  }


  /**
   * Tests all traversals on a balanced tree to ensure correct
   * order for each traversal type.
   */
  @Test
  public void testAllTraversalsBalancedTree() {
    bst.add(6);
    bst.add(4);
    bst.add(8);
    bst.add(3);
    bst.add(5);
    bst.add(7);
    bst.add(9);
    assertEquals("[3 4 5 6 7 8 9]", bst.inOrder());
    assertEquals("[6 4 3 5 8 7 9]", bst.preOrder());
    assertEquals("[3 5 4 7 9 8 6]", bst.postOrder());
  }

  /**
   * Tests that the postOrder traversal of an empty node returns an empty string.
   */
  @Test
  public void testPostOrderEmptyNode() {
    assertEquals("", emptyNode.postOrder());
  }

  /**
   * Tests post-order traversal on an empty tree.
   * Verifies that the post-order traversal of an empty tree
   * returns an empty string.
   */
  @Test
  public void testPostOrderTraversalEmptyTree() {
    assertEquals("[]", bst.postOrder());
  }

  /**
   * Tests post-order traversal of a tree with multiple nodes.
   * Ensures elements are visited in left-right-root order.
   */
  @Test
  public void testPostOrderTraversalMultipleNodes() {
    // Start with adding root node in the BinarySearchTreeImpl instance
    bst.add(10);
    bst.add(5);
    bst.add(15);
    bst.add(3);
    bst.add(7);

    // bst handles the brackets around the output
    assertEquals("[3 7 5 15 10]", bst.postOrder());
  }

  /**
   * Tests post-order traversal on a single-node tree.
   * Verifies that the post-order traversal of a tree with one element
   * returns that element alone.
   */
  @Test
  public void testPostOrderTraversalSingleNode() {
    bst.add(10);
    assertEquals("[10]", bst.postOrder());
  }

  /**
   * Tests post-order traversal on a left-heavy tree to ensure
   * elements are visited in left-right-root order.
   */
  @Test
  public void testPostOrderTraversalLeftHeavyTree() {
    bst.add(10);
    bst.add(5);
    bst.add(3);
    bst.add(1);
    assertEquals("[1 3 5 10]", bst.postOrder());
  }

  /**
   * Validates post-order traversal on a right-heavy tree.
   * Expected output for post-order is from the bottom-most node up to the root.
   */
  @Test
  public void testPostOrderTraversalRightHeavyTree() {
    bst.add(1);
    bst.add(3);
    bst.add(5);
    bst.add(10);

    // Corrected expected result for post-order traversal in a right-heavy tree
    assertEquals("[10 5 3 1]", bst.postOrder());
  }


  /**
   * Tests that the toString method of an empty node returns an empty string.
   */
  @Test
  public void testToStringEmptyNode() {
    assertEquals("", emptyNode.toString());
  }

  /**
   * Tests the toString method for GenericTreeNode.
   * Ensures that the toString method
   * returns the string representation of the data
   * in the node, and confirms that multiple nodes
   * are represented in in-order format.
   */
  @Test
  public void testToStringGenericTreeNode() {
    // Test with a single node
    GenericTreeNode<Integer> singleNode = new GenericTreeNode<>(10);
    assertEquals("10", singleNode.toString());

    // Test with multiple nodes to verify in-order structure in string representation
    GenericTreeNode<Integer> rootNode = new GenericTreeNode<>(20);
    rootNode.insert(10);
    rootNode.insert(30);
    rootNode.insert(5);
    rootNode.insert(15);

    String formattedInOrder = "[" + rootNode.inOrder().trim() + "]";
    assertEquals("[5 10 15 20 30]", formattedInOrder);
  }

  /**
   * Tests adding elements
   * and verifying in-order traversal format for toString method.
   */
  @Test
  public void testToStringAfterAdditions() {
    bst.add(10);
    bst.add(5);
    bst.add(20);
    bst.add(15);
    bst.add(30);
    assertEquals("[5 10 15 20 30]", bst.toString());
  }

  /**
   * Tests that the equals method returns true for two empty nodes,
   * as they both represent empty base cases in the binary search tree.
   */
  @Test
  public void testEqualsEmptyNode() {
    GenericEmptyNode<Integer> anotherEmptyNode = new GenericEmptyNode<>();
    assertTrue(emptyNode.equals(anotherEmptyNode));
  }

  /**
   * Tests equality between two identical binary search trees.
   */
  @Test
  public void testEqualsWithIdenticalTrees() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    otherBst.add(10);
    otherBst.add(5);
    otherBst.add(15);
    assertTrue(bst.equals(otherBst));
  }

  /**
   * Tests inequality between two different binary search trees.
   */
  @Test
  public void testNotEqualsWithDifferentTrees() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    otherBst.add(10);
    otherBst.add(20); // Different structure
    assertFalse(bst.equals(otherBst));
  }

  /**
   * Tests equals method for two identical trees and ensures they are equal.
   */
  @Test
  public void testEqualsIdenticalTrees() {
    GenericTreeNode<Integer> otherNode = new GenericTreeNode<>(10);
    rootNode.insert(5);
    rootNode.insert(15);
    otherNode.insert(5);
    otherNode.insert(15);

    assertTrue(rootNode.equals(otherNode));
  }

  /**
   * Tests that the hashCode method of an empty node is 0,
   * representing a consistent hash code for empty nodes.
   */
  @Test
  public void testHashCodeEmptyNode() {
    assertEquals(0, emptyNode.hashCode());
  }

  /**
   * Tests hashCode method to ensure
   * that identical trees produce the same hash code.
   */
  @Test
  public void testHashCodeIdenticalTrees() {
    GenericTreeNode<Integer> otherNode = new GenericTreeNode<>(10);
    rootNode.insert(5);
    rootNode.insert(15);
    otherNode.insert(5);
    otherNode.insert(15);

    assertEquals(rootNode.hashCode(), otherNode.hashCode());
  }

  /**
   * Tests hashCode consistency between identical binary search trees.
   */
  @Test
  public void testHashCodeWithIdenticalTrees() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    otherBst.add(10);
    otherBst.add(5);
    otherBst.add(15);
    assertEquals(bst.hashCode(), otherBst.hashCode());
  }

  /**
   * Tests hashCode difference between two different binary search trees.
   */
  @Test
  public void testHashCodeWithDifferentTrees() {
    bst.add(10);
    bst.add(5);
    bst.add(15);
    otherBst.add(10);
    otherBst.add(20); // Different structure
    assertNotEquals(bst.hashCode(), otherBst.hashCode());
  }
}