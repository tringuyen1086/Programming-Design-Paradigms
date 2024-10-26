import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import questions.Likert;
import questions.MultipleChoice;
import questions.MultipleSelect;
import questions.Question;
import questions.TrueFalse;

/**
 * Unit tests for the Likert class.
 */
public class LikertTest {

  private Likert likert;

  /**
   * Set up the Likert object before each test.
   */
  @Before
  public void setUp() {
    likert = new Likert("How satisfied are you with our service?");
  }

  /**
   * Test that the constructor correctly initializes the text of the Likert question.
   */
  @Test
  public void testConstructor() {
    Likert likertTest = new Likert("Is Java fun?");
    // Ensure the object is instantiated
    assertNotNull(likertTest);
    // Check the text is correctly set
    assertEquals("Is Java fun?", likertTest.getText());
  }

  /**
   * Test that the getText method returns the correct question text.
   */
  @Test
  public void testGetText() {
    assertEquals("How satisfied are you with our service?", likert.getText());
  }

  /**
   * Test that the answer method returns "Correct" for valid Likert scale answers (1-5).
   */
  @Test
  public void testValidAnswer() {
    assertEquals("Correct", likert.answer("1"));
    assertEquals("Correct", likert.answer("5"));
    assertEquals("Correct", likert.answer("3"));
  }

  /**
   * Test that the answer method returns "Incorrect"
   * for answers outside the valid Likert scale (not 1-5).
   */
  @Test
  public void testInvalidAnswer() {
    assertEquals("Incorrect", likert.answer("6"));
    assertEquals("Incorrect", likert.answer("0"));
    assertEquals("Incorrect", likert.answer("Not sure"));
  }

  /**
   * Test for an empty answer provided by the user. Should return "Incorrect".
   */
  @Test
  public void testEmptyAnswer() {
    String result = likert.answer("");
    assertEquals("Incorrect", result);
  }

  /**
   * Test that the compareTo method correctly orders Likert questions
   * lexicographically by their text.
   */
  @Test
  public void testCompareToWithLikert() {
    Likert likert2 = new Likert("Do you recommend our service?");
    // How satisfied are you with our service? > ""Do you recommend our service?"
    assertTrue(likert.compareTo(likert2) > 0);
    // "Do you recommend our service?" < How satisfied are you with our service?
    assertTrue(likert2.compareTo(likert) < 0);
  }

  /**
   * Test ordering of Likert questions for lexicographical sorting.
   */
  @Test
  public void testLikertOrdering() {
    Likert likert1 = new Likert("Are you satisfied with our product?");
    Likert likert2 = new Likert("Do you recommend our service?");
    assertTrue(likert1.compareTo(likert2) < 0);
  }

  /**
   * Test that Likert questions come last in sorting order.
   */
  @Test
  public void testLikertComesLastInSorting() {
    // Initialize your questions array with non-null elements
    Question[] questions = {
      new TrueFalse("Is the sky blue?", "True"),
      new MultipleChoice("What is the capital of France?",
      "2", "Berlin Paris Rome"),
      new MultipleSelect("Select the prime numbers:",
        "2 3 5", "2 3 4 5"),
      new Likert("How satisfied are you with our service?")
    };

    // Sort the questions array
    Arrays.sort(questions);

    // Assert that the Likert question is last
    assertEquals(questions[questions.length - 1] instanceof Likert, true);
  }

  /**
   * Test that equals returns true for two Likerts with the same text.
   */
  @Test
  public void testEqualsSameText() {
    Likert other = new Likert("How satisfied are you with our service?");
    assertTrue(likert.equals(other));
  }

  /**
   * Test that equals returns false for two Likerts with different text.
   */
  @Test
  public void testEqualsDifferentText() {
    Likert other = new Likert("How happy are you with our service?");
    assertFalse(likert.equals(other));
  }

  /**
   * Test that hashCode returns the same value for Likerts with the same text.
   */
  @Test
  public void testHashCodeSameText() {
    Likert other = new Likert("How satisfied are you with our service?");
    assertEquals(likert.hashCode(), other.hashCode());
  }
}