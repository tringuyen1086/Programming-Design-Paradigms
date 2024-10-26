import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sentence.EmptyNode;
import sentence.PunctuationNode;
import sentence.Sentence;
import sentence.WordNode;

/**
 * Unit tests for the EmptyNode class.
 * These tests verify the functionality of an EmptyNode,
 * which represents the end of a sentence or an empty sentence.
 */
public class EmptyNodeTest {

  private Sentence emptyNode;

  /**
   * Set up an EmptyNode instance for testing.
   * This method is called before each test case is run.
   */
  @Before
  public void setUp() {
    emptyNode = new EmptyNode();
  }

  /**
   * Test getNumberOfWords method in EmptyNode.
   * Ensures that the getNumberOfWords method returns 0
   * because an EmptyNode contains no words.
   */
  @Test
  public void testGetNumberOfWords() {
    // Empty node should have no words
    assertEquals(0, emptyNode.getNumberOfWords());
  }

  /**
   * Test longestWord method in EmptyNode.
   * Ensures that the longestWord method returns an empty string
   * because there are no words in an EmptyNode.
   */
  @Test
  public void testLongestWord() {
    // No words in EmptyNode
    assertEquals("", emptyNode.longestWord());
  }

  /**
   * Test merge method in EmptyNode.
   * Ensures that merging an EmptyNode with another sentence results
   * in the other sentence being returned.
   */
  @Test
  public void testMerge() {
    Sentence otherSentence = new WordNode("Java", new EmptyNode());
    Sentence mergedSentence = emptyNode.merge(otherSentence);
    assertEquals("Java", mergedSentence.toString());
  }

  /**
   * Test merging EmptyNode with a sentence containing punctuation.
   * Ensures that merging an empty sentence does not alter the punctuation behavior.
   */
  @Test
  public void testMergeWithPunctuationInEmptyNode() {
    Sentence punctuationSentence = new PunctuationNode("!", new EmptyNode());
    Sentence mergedSentence = emptyNode.merge(punctuationSentence);

    assertEquals("!", mergedSentence.toString());
  }

  /**
   * Test duplicate method in EmptyNode.
   * Ensures that the duplicate method returns a deep copy
   * of the EmptyNode, which should be a new but equivalent instance.
   */
  @Test
  public void testDuplicate() {
    Sentence duplicateEmptyNode = emptyNode.duplicate();
    assertEquals(emptyNode, duplicateEmptyNode);
    assertNotSame(emptyNode, duplicateEmptyNode); // Ensure deep copy
  }

  /**
   * Test toString method in EmptyNode.
   * Ensures that the toString method returns an empty string
   * because an EmptyNode represents an empty sentence.
   */
  @Test
  public void testToString() {
    // Empty node should return empty string
    assertEquals("", emptyNode.toString());
  }

  /**
   * Test equals method in EmptyNode.
   * Ensures that all EmptyNode instances are considered equal
   * since they represent the same concept (an empty sentence).
   */
  @Test
  public void testEquals() {
    // All EmptyNode instances should be equal
    assertTrue(emptyNode.equals(new EmptyNode()));
  }

  /**
   * Test hashCode method in EmptyNode.
   * Ensures that all EmptyNode instances produce the same hash code,
   * as they all represent the same empty sentence.
   */
  @Test
  public void testHashCode() {
    // All EmptyNode instances should have the same hash code
    assertEquals(emptyNode.hashCode(), new EmptyNode().hashCode());
  }
}
