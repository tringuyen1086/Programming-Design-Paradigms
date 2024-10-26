import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import questions.Likert;
import questions.MultipleChoice;
import questions.MultipleSelect;
import questions.Question;
import questions.TrueFalse;

/**
 * Unit tests for the TrueFalse class.
 */
public class TrueFalseTest {

  private TrueFalse tfq;

  /**
   * Set up the TrueFalse object before each test.
   */
  @Before
  public void setUp() {
    tfq = new TrueFalse("Is the sky blue?", "True");
  }

  /**
   * Test that the constructor throws an IllegalArgumentException for invalid correct answers.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidAnswer() {
    new TrueFalse("Is the sky blue?", "Yes"); // Should throw an exception
  }

  /**
   * Test that the getText method returns the correct question text.
   */
  @Test
  public void testGetText() {
    assertEquals("Is the sky blue?", tfq.getText());
  }

  /**
   * Test that the answer method returns "Correct" for the correct answer.
   */
  @Test
  public void testCorrectAnswer() {
    assertEquals("Correct", tfq.answer("True"));
  }

  /**
   * Test that the answer method returns "Incorrect" for an incorrect answer.
   */
  @Test
  public void testIncorrectAnswer() {
    assertEquals("Incorrect", tfq.answer("False"));
  }

  /**
   * Test for an empty answer provided by the user. Should return "Incorrect".
   */
  @Test
  public void testEmptyAnswer() {
    String result = tfq.answer("");
    assertEquals("Incorrect", result);
  }

  /**
   * Test the compareTo method for ordering TrueFalse questions.
   * True/False questions should be sorted lexicographically within their type.
   */
  @Test
  public void testCompareTo() {
    TrueFalse tfq2 = new TrueFalse("Is the earth round?", "True");

    // Test lexicographical comparison
    // "Is the earth round?" comes before "Is the sky blue?"
    assertTrue(tfq.compareTo(tfq2) > 0);
    // "Is the sky blue?" comes after "Is the earth round?"
    assertTrue(tfq2.compareTo(tfq) < 0);
  }

  /**
   * Test ordering of TrueFalse questions for lexicographical sorting.
   */
  @Test
  public void testTrueFalseOrderingWithSameTypes() {
    Question[] questions = {new TrueFalse("Is Java fun?", "True"),
      new TrueFalse("Is Python better?", "False")};
    Arrays.sort(questions);

    // The assertTrue verifies that the first question "Is Java fun?"
    // is the one that should come first alphabetically
    // If the condition is false, the test will fail with the message "Order is incorrect".
    assertTrue("Order is incorrect", questions[0].getText().equals("Is Java fun?"));
  }

  /**
   * Test that TrueFalse questions come before other question types in sorting.
   * TrueFalse should be sorted first, followed by MultipleChoice, MultipleSelect, and Likert.
   */
  @Test
  public void testTrueFalseOrderingWithOtherTypes() {
    TrueFalse tfq1 = new TrueFalse("Is Java fun?", "True");
    MultipleChoice mcq = new MultipleChoice("What is the capital of France?",
            "2", "Berlin Paris Rome");
    MultipleSelect msq = new MultipleSelect("Which of these are fruits?",
            "1 3", "Apple Carrot Mango");
    Likert likert = new Likert("How satisfied are you with this product?");

    // Array to test ordering
    Question[] questions = {mcq, likert, tfq1, msq};

    // Sort the array
    Arrays.sort(questions);

    // The first question should be TrueFalse type
    assertTrue(questions[0] instanceof TrueFalse);
    // The second question should be MultipleChoice
    assertTrue(questions[1] instanceof MultipleChoice);
    // MultipleSelect should be after any MultipleChoice but before any Likert questions
    assertTrue(questions[2] instanceof MultipleSelect);
    // The last question should be Likert type
    assertTrue(questions[3] instanceof Likert);
  }

  /**
   * Test that equals returns true for two TrueFalses with the same text and correct answer.
   */
  @Test
  public void testEqualsSame() {
    TrueFalse other = new TrueFalse("Is the sky blue?", "True");
    assertTrue(tfq.equals(other));
  }

  /**
   * Test that equals returns false for two TrueFalses with different correct answers.
   */
  @Test
  public void testEqualsDifferentCorrectAnswer() {
    TrueFalse other = new TrueFalse("Is the sky blue?", "False");
    assertFalse(tfq.equals(other));
  }

  /**
   * Test that hashCode returns the same value for TrueFalses with the same text and correct answer.
   */
  @Test
  public void testHashCodeSame() {
    TrueFalse other = new TrueFalse("Is the sky blue?", "True");
    assertEquals(tfq.hashCode(), other.hashCode());
  }
}

