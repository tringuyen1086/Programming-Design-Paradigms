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
 * Unit tests for the WordNode class.
 * These tests verify the functionality of the WordNode class,
 * including word count, the longest word, merging sentences,
 * formatting with punctuation, and handling deep copies.
 */
public class WordNodeTest {

  private Sentence wordNode;
  private Sentence sentenceWithPunctuation;
  private Sentence sentenceWithMultipleWords;

  /**
   * Set up instances of WordNode for testing.
   * This method is called before each test case is run.
   * It sets up basic sentences like:
   * - "Hello world"
   * - "Hello world!" (with punctuation)
   * - "Java is awesome."
   */
  @Before
  public void setUp() {
    // Basic sentence: "Hello world"
    wordNode = new WordNode("Hello",
            new WordNode("world",
                    new EmptyNode()));

    // Sentence with punctuation: "Hello world!"
    sentenceWithPunctuation = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!",
                            new EmptyNode())));

    // Sentence with multiple words and punctuation: "Java is awesome."
    sentenceWithMultipleWords = new WordNode("Java",
            new WordNode("is",
                    new WordNode("awesome",
                            new PunctuationNode(".",
                                    new EmptyNode()))));
  }

  /**
   * Test getNumberOfWords method in WordNode.
   * Ensures the correct number of words is counted, ignoring punctuation.
   */
  @Test
  public void testGetNumberOfWords() {
    assertEquals(2, wordNode.getNumberOfWords()); // "Hello world"
    assertEquals(2, sentenceWithPunctuation.getNumberOfWords()); // "Hello world!"
    assertEquals(3, sentenceWithMultipleWords.getNumberOfWords()); // "Java is awesome."
  }

  /**
   * Test longestWord method in WordNode.
   * Ensures the longest word is found correctly, ignoring punctuation.
   */
  @Test
  public void testLongestWord() {
    assertEquals("Hello", wordNode.longestWord());
    assertEquals("Hello", sentenceWithPunctuation.longestWord());
    assertEquals("awesome", sentenceWithMultipleWords.longestWord());
  }

  /**
   * Test toString with WordNode followed by PunctuationNode.
   * Ensures that punctuation is properly attached to the word with a space after.
   */
  @Test
  public void testWordNodeFollowedByPunctuation() {
    Sentence wordWithPunctuation = new WordNode("Hello",
            new PunctuationNode("!", new EmptyNode()));
    assertEquals("Hello!", wordWithPunctuation.toString());
  }

  /**
   * Test merge method in WordNode.
   * Merges two sentences and ensures correct formatting.
   */
  @Test
  public void testMerge() {
    Sentence otherSentence = new WordNode("Java", new EmptyNode());
    Sentence mergedSentence = wordNode.merge(otherSentence);
    assertEquals("Hello world Java", mergedSentence.toString());

    // Merge with punctuation sentence: "Hello world!" + "Java is awesome."
    Sentence mergedWithPunctuation = sentenceWithPunctuation.merge(sentenceWithMultipleWords);
    assertEquals("Hello world! Java is awesome.", mergedWithPunctuation.toString());
  }

  /**
   * Test duplicate method in WordNode.
   * Ensures that a deep copy of the sentence is created.
   */
  @Test
  public void testDuplicate() {
    // Duplicate the sentence "Hello world"
    Sentence duplicateSentence = wordNode.duplicate();
    assertEquals(wordNode, duplicateSentence); // Ensure contents are equal
    assertNotSame(wordNode, duplicateSentence); // Ensure deep copy

    // Test deep copy of sentence with punctuation
    Sentence duplicatePunctuation = sentenceWithPunctuation.duplicate();
    assertEquals(sentenceWithPunctuation, duplicatePunctuation); // Ensure contents are equal
    assertNotSame(sentenceWithPunctuation, duplicatePunctuation); // Ensure deep copy
  }

  /**
   * Test toString method in WordNode.
   * Ensures correct formatting of sentences, handling spaces and punctuation.
   */
  @Test
  public void testToString() {
    // No punctuation
    assertEquals("Hello world", wordNode.toString());
    // Punctuation at the end
    assertEquals("Hello world!", sentenceWithPunctuation.toString());
    // Punctuation at the end of a multi-word sentence
    assertEquals("Java is awesome.", sentenceWithMultipleWords.toString());

    // Edge case: Punctuation followed by a word
    Sentence sentenceWithExclamationAndWord = new PunctuationNode("!",
            new WordNode("Java", new EmptyNode()));
    // Ensure space after punctuation and before word
    assertEquals("! Java", sentenceWithExclamationAndWord.toString());
  }

  /**
   * Test equals method in WordNode.
   * Ensures that sentences with the same content are considered equal.
   */
  @Test
  public void testEquals() {
    // Test equality for sentence without punctuation
    Sentence sameSentence = new WordNode("Hello",
            new WordNode("world", new EmptyNode()));
    assertTrue(wordNode.equals(sameSentence));

    // Test equality for sentence with punctuation
    Sentence sameSentenceWithPunctuation = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!", new EmptyNode())));
    assertTrue(sentenceWithPunctuation.equals(sameSentenceWithPunctuation));
  }

  /**
   * Test hashCode method in WordNode.
   * Ensures that equal sentences produce the same hash code.
   */
  @Test
  public void testHashCode() {
    // Test the hashCode equality for sentences without punctuation
    Sentence sameSentence = new WordNode("Hello",
            new WordNode("world", new EmptyNode()));
    assertEquals(wordNode.hashCode(), sameSentence.hashCode());

    // Test hash code equality for sentence with punctuation
    Sentence sameSentenceWithPunctuation = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!", new EmptyNode())));
    assertEquals(sentenceWithPunctuation.hashCode(), sameSentenceWithPunctuation.hashCode());
  }
}
