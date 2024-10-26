import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import sentence.EmptyNode;
import sentence.PunctuationNode;
import sentence.Sentence;
import sentence.WordNode;

/**
 * Integration tests for the Sentence classes.
 * These tests ensure that WordNode, PunctuationNode, and EmptyNode
 * work together as expected when constructing, merging, duplicating, and testing methods
 * such as toString, equals, and hashCode.
 */
public class SentenceIntegrationTest {

  private Sentence sentence1;
  private Sentence sentence2;
  private Sentence complexSentence;
  private Sentence identicalSentence1;

  /**
   * Set up instances of Sentence objects for testing.
   * - sentence1: "Hello world!"
   * - sentence2: "Java is awesome."
   * - complexSentence: A more complex sentence combining both.
   * - identicalSentence1: Another sentence identical to sentence1.
   */
  @Before
  public void setUp() {
    sentence1 = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!",
                            new EmptyNode())));
    identicalSentence1 = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!",
                            new EmptyNode())));
    sentence2 = new WordNode("Java",
            new WordNode("is",
                    new WordNode("awesome",
                            new PunctuationNode(".",
                                    new EmptyNode()))));
    complexSentence = sentence1.merge(sentence2);
  }

  /**
   * Test the getNumberOfWords method.
   * Ensures that the correct number of words is counted, ignoring punctuation.
   */
  @Test
  public void testGetNumberOfWordsComplexSentence() {
    assertEquals(2, sentence1.getNumberOfWords());
    assertEquals(3, sentence2.getNumberOfWords());
    assertEquals(5, complexSentence.getNumberOfWords());
  }

  /**
   * Test the longestWord method.
   * Ensures that the longest word in the combined sentence is returned correctly.
   */
  @Test
  public void testLongestWordComplexSentence() {
    assertEquals("Hello", sentence1.longestWord());
    assertEquals("awesome", sentence2.longestWord());
    assertEquals("awesome", complexSentence.longestWord());
  }

  /**
   * Test the merge method.
   * Ensures that sentences are merged correctly, handling spaces and punctuation properly.
   */
  @Test
  public void testMergeComplexSentence() {
    Sentence merged = sentence1.merge(sentence2);
    assertEquals("Hello world! Java is awesome.", merged.toString());
  }

  /**
   * Test the duplicate method.
   * Ensures that a deep copy of the sentence is created,
   * equal in content but not the same object.
   */
  @Test
  public void testDuplicateComplexSentence() {
    Sentence duplicateSentence1 = sentence1.duplicate();
    assertEquals(sentence1, duplicateSentence1);
    assertNotSame(sentence1, duplicateSentence1); // Ensure deep copy

    Sentence duplicateComplexSentence = complexSentence.duplicate();
    assertEquals(complexSentence, duplicateComplexSentence);
    assertNotSame(complexSentence, duplicateComplexSentence); // Ensure deep copy
  }

  /**
   * Test the toString method.
   * Ensures correct formatting with spaces and punctuation attached to the last word.
   */
  @Test
  public void testToStringComplexSentence() {
    assertEquals("Hello world!", sentence1.toString());
    assertEquals("Java is awesome.", sentence2.toString());
    assertEquals("Hello world! Java is awesome.", complexSentence.toString());
  }

  /**
   * Test edge cases for toString, such as punctuation followed by a word.
   * Ensures that spaces are correctly placed between words and after punctuation.
   */
  @Test
  public void testEdgeCasesToString() {
    Sentence sentenceWithExclamationAndWord = new PunctuationNode("!",
            new WordNode("Java", new EmptyNode()));
    assertEquals("! Java", sentenceWithExclamationAndWord.toString());
  }

  /**
   * Test the equals method.
   * Ensures that sentences with the same structure and content are considered equal.
   */
  @Test
  public void testEqualsComplexSentence() {
    assertTrue(sentence1.equals(identicalSentence1));
    assertFalse(sentence1.equals(sentence2));
    Sentence duplicateComplexSentence = complexSentence.duplicate();
    assertTrue(complexSentence.equals(duplicateComplexSentence));
  }

  /**
   * Test the hashCode method.
   * Ensures that equal sentences produce the same hash code.
   */
  @Test
  public void testHashCodeComplexSentence() {
    assertEquals(sentence1.hashCode(), identicalSentence1.hashCode());
    assertFalse(sentence1.hashCode() == sentence2.hashCode());
    Sentence duplicateComplexSentence = complexSentence.duplicate();
    assertEquals(complexSentence.hashCode(), duplicateComplexSentence.hashCode());
  }

  /**
   * Test the interaction of WordNode, PunctuationNode, and EmptyNode in a complex sentence.
   * Constructs: "Hello world! Java is awesome."
   * Merges another sentence to form: "Hello world! Java is awesome. Coding"
   * Ensures proper formatting and deep copying.
   */
  @Test
  public void testComplexSentence() {
    Sentence sentence = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!",
                new WordNode("Java",
                    new WordNode("is",
                            new WordNode("awesome",
                                    new PunctuationNode(".",
                                            new EmptyNode())))))));

    assertEquals("Hello world! Java is awesome.", sentence.toString());
    assertEquals(5, sentence.getNumberOfWords());
    assertEquals("awesome", sentence.longestWord());

    Sentence newSentence = new WordNode("Coding", new EmptyNode());
    Sentence mergedSentence = sentence.merge(newSentence);
    assertEquals("Hello world! Java is awesome. Coding", mergedSentence.toString());

    Sentence duplicated = sentence.duplicate();
    assertEquals(sentence, duplicated);
    assertNotSame(sentence, duplicated);  // Ensure it's a deep copy
  }
}
