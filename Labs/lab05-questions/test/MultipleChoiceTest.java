import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import questions.Likert;
import questions.MultipleChoice;
import questions.MultipleSelect;
import questions.TrueFalse;

/**
 * Unit tests for the MultipleChoiceQuestion class.
 */
public class MultipleChoiceTest {

  private MultipleChoice mcq;
  private MultipleChoice mcq2;
  private TrueFalse tfq;
  private MultipleSelect msq;
  private Likert likert;

  /**
   * Set up the MultipleChoiceQuestion object before each test.
   */
  @Before
  public void setUp() {
    mcq = new MultipleChoice("What is the capital of France?",
            "2", "Berlin Paris Rome");
    mcq2 = new MultipleChoice("What is the capital of Germany?",
            "1", "Berlin Munich Hamburg");
    tfq = new TrueFalse("Is Java fun?",
            "True");
    msq = new MultipleSelect("Which of these are fruits?",
            "1 3", "Apple Carrot Mango");
    likert = new Likert("How satisfied are you with this product?");
  }

  /**
   * Test that the constructor throws an IllegalArgumentException when
   * initialized with fewer than 3 options.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithTooFewOptions() {
    new MultipleChoice("What is the capital of France?",
            "1", "Paris");
  }

  /**
   * Test that the constructor throws an IllegalArgumentException when
   * initialized with more than 8 options.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithTooManyOptions() {
    new MultipleChoice("What is the capital of France?", "1",
            "Option1 Option2 Option3 Option4 Option5 Option6 Option7 Option8 Option9");
  }

  /**
   * Test that the getText method returns the correct question text.
   */
  @Test
  public void testGetText() {
    assertEquals("What is the capital of France?", mcq.getText());
  }

  /**
   * Test that the answer method returns "Correct" for the correct answer.
   */
  @Test
  public void testCorrectAnswer() {
    // Correct answer
    assertEquals("Correct", mcq.answer("2"));

    // Incorrect answer
    assertEquals("Incorrect", mcq.answer("1"));

    // Invalid answer (out of range)
    assertEquals("Incorrect", mcq.answer("5"));

    // Invalid answer (non-numeric)
    assertEquals("Incorrect", mcq.answer("abc"));
  }

  /**
   * Test that the answer method returns "Incorrect" for an incorrect answer.
   */
  @Test
  public void testIncorrectAnswer() {
    assertEquals("Incorrect", mcq.answer("1"));
    assertEquals("Incorrect", mcq.answer("3"));
  }

  /**
   * Test for an empty answer provided by the user. Should return "Incorrect".
   */
  @Test
  public void testEmptyAnswer() {
    String result = mcq.answer("");
    assertEquals("Incorrect", result);
  }

  /**
   * Test the compareTo method for comparing a MultipleChoice question with other types.
   * Verifies that MultipleChoice comes after TrueFalse but before MultipleSelect and Likert.
   */
  @Test
  public void testCompareToWithDifferentTypes() {
    // MultipleChoice comes after TrueFalse
    assertTrue(mcq.compareTo(tfq) > 0);

    // MultipleChoice comes before MultipleSelect and Likert
    assertTrue(mcq.compareTo(msq) < 0);
    assertTrue(mcq.compareTo(likert) < 0);
  }

  /**
   * Test lexicographical ordering of MultipleChoice questions.
   * Verifies that questions of the same type are ordered by text in lexicographical order.
   */
  @Test
  public void testCompareToWithinMultipleChoice() {
    // "What is the capital of Germany?" comes after mcq1 "What is the capital of France?"
    assertTrue(mcq2.compareTo(mcq) > 0);

    // "What is the capital of France?" comes before mcq2 "What is the capital of Germany?"
    assertTrue(mcq.compareTo(mcq2) < 0);
  }

  /**
   * Test that compareTo returns 0 for two identical MultipleChoice questions.
   */
  @Test
  public void testCompareToWithSameMultipleChoice() {
    MultipleChoice mcqDuplicate = new MultipleChoice("What is the capital of France?",
            "2", "Berlin Paris Rome");

    // Two identical MultipleChoice questions should be equal
    assertEquals(0, mcq.compareTo(mcqDuplicate));
  }

  /**
   * Test ordering of MultipleChoice questions for lexicographical sorting.
   * Test ordering of MultipleChoice questions for correct ordering logic.
   */
  @Test
  public void testMultipleChoiceOrdering() {

    // 1. MultipleChoice should come after TrueFalse
    assertTrue(mcq.compareTo(tfq) > 0);

    // 2. MultipleChoice should come before MultipleSelect and Likert
    assertTrue(mcq.compareTo(msq) < 0);
    assertTrue(mcq.compareTo(likert) < 0);

    // 3. Lexicographical ordering within MultipleChoice
    assertTrue(mcq.compareTo(mcq2) < 0);
  }

  /**
   * Test that equals returns true for two MultipleChoiceQuestions
   * with the same text, correct answer, and options.
   */
  @Test
  public void testEqualsSame() {
    MultipleChoice other = new MultipleChoice("What is the capital of France?",
            "2", "Berlin Paris Rome");
    assertTrue(mcq.equals(other));
  }

  /**
   * Test that equals returns false for two MultipleChoiceQuestions
   * with different correct answers.
   */
  @Test
  public void testEqualsDifferentCorrectAnswer() {
    MultipleChoice other = new MultipleChoice("What is the capital of France?",
            "1", "Berlin Paris Rome");
    assertFalse(mcq.equals(other));
  }

  /**
   * Test that hashCode returns the same value for MultipleChoiceQuestions
   * with the same text, correct answer, and options.
   */
  @Test
  public void testHashCodeSame() {
    MultipleChoice other = new MultipleChoice("What is the capital of France?",
            "2", "Berlin Paris Rome");
    assertEquals(mcq.hashCode(), other.hashCode());
  }
}