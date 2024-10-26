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
 * Unit tests for the PunctuationNode class.
 * These tests verify the functionality of PunctuationNode,
 * which handles punctuation in a sentence,
 * including handling consecutive punctuation, merging, and formatting.
 */
public class PunctuationNodeTest {

  private Sentence punctuationNode;

  /**
   * Set up a PunctuationNode instance for testing.
   * This method is called before each test case is run.
   */
  @Before
  public void setUp() {
    punctuationNode = new PunctuationNode("!", new EmptyNode());
  }

  /**
   * Test getNumberOfWords method in PunctuationNode.
   * Ensures that punctuation does not count as a word.
   */
  @Test
  public void testGetNumberOfWords() {
    // Punctuation should not count as a word
    assertEquals(0, punctuationNode.getNumberOfWords());
  }

  /**
   * Test longestWord method in PunctuationNode.
   * Ensures that punctuation is not considered a word, returning an empty string.
   */
  @Test
  public void testLongestWord() {
    // Punctuation should not be considered a word
    assertEquals("", punctuationNode.longestWord());
  }

  /**
   * Test merge method in PunctuationNode.
   * Ensure that merging a PunctuationNode with another sentence
   * results in the punctuation being attached properly before the other sentence.
   */
  @Test
  public void testMerge() {
    Sentence otherSentence = new WordNode("Java", new EmptyNode());
    Sentence mergedSentence = punctuationNode.merge(otherSentence);
    // Ensure the punctuation is followed by a space before the next word
    assertEquals("! Java", mergedSentence.toString());
  }

  /**
   * Test consecutive punctuation nodes.
   * Ensures that consecutive punctuation nodes are formatted without spaces in between.
   */
  @Test
  public void testConsecutivePunctuation() {
    // Create consecutive punctuation: "!.."
    Sentence consecutivePunctuation = new PunctuationNode("!",
            new PunctuationNode(".",
                    new PunctuationNode(".", new EmptyNode())));

    // Ensure there are no spaces between consecutive punctuation marks
    assertEquals("!..", consecutivePunctuation.toString());
  }

  /**
   * Test duplicate method in PunctuationNode.
   * Ensures that the duplicate method returns a deep copy of the PunctuationNode,
   * which should be a new but equivalent instance.
   */
  @Test
  public void testDuplicate() {
    Sentence duplicatePunctuation = punctuationNode.duplicate();
    assertEquals(punctuationNode, duplicatePunctuation);
    assertNotSame(punctuationNode, duplicatePunctuation); // Ensure deep copy
  }

  /**
   * Test toString method in PunctuationNode.
   * Ensures that the toString method returns the correct string representation of punctuation,
   * handling spaces correctly when followed by a word.
   */
  @Test
  public void testToString() {
    assertEquals("!", punctuationNode.toString());

    // Test punctuation followed by a word
    Sentence punctuationFollowedByWord = new PunctuationNode("!",
            new WordNode("Java", new EmptyNode()));
    // Ensure space after punctuation and before word
    assertEquals("! Java", punctuationFollowedByWord.toString());
  }

  /**
   * Test equals method in PunctuationNode.
   * Ensures that PunctuationNodes with the same punctuation content are considered equal.
   */
  @Test
  public void testEquals() {
    Sentence samePunctuation = new PunctuationNode("!", new EmptyNode());
    assertTrue(punctuationNode.equals(samePunctuation));
  }

  /**
   * Test hashCode method in PunctuationNode.
   * Ensures that equal PunctuationNode instances produce the same hash code.
   */
  @Test
  public void testHashCode() {
    Sentence samePunctuation = new PunctuationNode("!", new EmptyNode());
    assertEquals(punctuationNode.hashCode(), samePunctuation.hashCode());
  }
}
