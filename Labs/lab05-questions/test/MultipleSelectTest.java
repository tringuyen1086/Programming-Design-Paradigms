import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Unit tests for the MultipleSelectQuestion class.
 */
public class MultipleSelectTest {

  private MultipleSelect msq;

  /**
   * Set up the MultipleSelectQuestion object before each test.
   */
  @Before
  public void setUp() {
    msq = new MultipleSelect("Which of these are fruits?",
            "1 3", "Apple Carrot Mango");
  }

  /**
   * Test that the constructor throws an IllegalArgumentException when
   * initialized with fewer than 3 options.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithTooFewOptions() {
    new MultipleSelect("Which of these are fruits?",
            "1 3", "Apple Carrot");
  }

  /**
   * Test that the constructor throws an IllegalArgumentException when
   * initialized with more than 8 options.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithTooManyOptions() {
    new MultipleSelect("Which of these are fruits?",
            "1 3",
            "Apple Carrot Mango Fruit4 Fruit5 Fruit6 Fruit7 Fruit8 Fruit9");
  }

  /**
   * Test that the constructor handles duplicate correct answers and options.
   */
  @Test
  public void testConstructorWithDuplicateCorrectAnswers() {
    MultipleSelect msqWithDuplicates = new MultipleSelect("Which of these are fruits?",
            "1 3 3", "Apple Carrot Mango");
    // Expect no exception; correct answers should automatically remove duplicates
    assertEquals("Correct", msqWithDuplicates.answer("1 3"));
  }

  /**
   * Test that the getText method returns the correct question text.
   */
  @Test
  public void testGetText() {
    assertEquals("Which of these are fruits?", msq.getText());
  }

  /**
   * Test for correct answer: The user's answer contains all the correct options
   * and no incorrect ones.
   */
  @Test
  public void testCorrectAnswer() {
    // User provides correct answers (1 and 3)
    String result = msq.answer("1 3");
    assertEquals("Correct", result);
  }

  /**
   * Test that the answer method returns "Incorrect"
   * for an incomplete or incorrect set of answers.
   */
  @Test
  public void testIncorrectAnswer() {
    assertEquals("Incorrect", msq.answer("1"));
    assertEquals("Incorrect", msq.answer("2 3"));
  }

  /**
   * Test for an empty answer provided by the user. Should return "Incorrect".
   */
  @Test
  public void testEmptyAnswer() {
    String result = msq.answer("");
    assertEquals("Incorrect", result);
  }

  /**
   * Test for partially correct answer: The user's answer is missing some correct options.
   * The answer should be considered incorrect.
   */
  @Test
  public void testMissingCorrectOptions() {
    // User provides an answer missing one correct option (only "1")
    String result = msq.answer("1");
    assertEquals("Incorrect", result);
  }

  /**
   * Test for incorrect answer: The user's answer contains an incorrect option
   * (in this case, option "2").
   */
  @Test
  public void testIncorrectAnswerWithInvalidOption() {
    // User provides an answer with an incorrect option (option "2")
    String result = msq.answer("1 2");
    assertEquals("Incorrect", result);
  }

  /**
   * Test for out of range option: The user's answer contains an option number
   * that is out of the range of available options (more than the total number of options).
   */
  @Test
  public void testOutOfRangeOption() {
    // User provides an answer with an out-of-range option ("4")
    String result = msq.answer("1 4");
    assertEquals("Incorrect", result);
  }

  /**
   * Test for invalid input: The user's answer contains a non-numeric input,
   * which should be considered invalid and return "Incorrect".
   */
  @Test
  public void testNonNumericAnswer() {
    // User provides an invalid non-numeric answer ("one")
    String result = msq.answer("one three");
    assertEquals("Incorrect", result);
  }

  /**
   * Test for correct answer in different order: The user's answer contains
   * all the correct options in a different order, which should still be considered correct.
   */
  @Test
  public void testCorrectAnswerInDifferentOrder() {
    // User provides correct answers in a different order ("3 1")
    String result = msq.answer("3 1");
    assertEquals("Correct", result);
  }

  /**
   * Test ordering of MultipleSelect questions for lexicographical sorting.
   */
  @Test
  public void testMultipleSelectOrdering() {
    // Create different types of questions
    TrueFalse tfQuestion = new TrueFalse("Is the sky blue?", "True");
    MultipleChoice mcQuestion = new MultipleChoice("What is 2 + 2?",
            "2", "1 2 3 4");
    MultipleSelect msQuestion = new MultipleSelect("Select the prime numbers",
            "2 3", "1 2 3 4");
    Likert likertQuestion = new Likert("How satisfied are you with our service?");

    // Store in an array and sort them
    Question[] questions = {likertQuestion, msQuestion, mcQuestion, tfQuestion};
    Arrays.sort(questions);

    // Verify the correct ordering
    // TrueFalse should come first, followed by MultipleChoice, MultipleSelect, then Likert
    assertTrue(questions[0] instanceof TrueFalse);
    assertTrue(questions[1] instanceof MultipleChoice);
    assertTrue(questions[2] instanceof MultipleSelect);
    assertTrue(questions[3] instanceof Likert);
  }

  @Test
  public void testLexicographicalOrderingWithinSameType() {
    // Create multiple MultipleSelect questions with different texts
    MultipleSelect msq1 = new MultipleSelect("Select the animals.",
            "1 3", "Dog Apple Cat");
    MultipleSelect msq2 = new MultipleSelect("Select the colors.",
            "1 3", "Red Apple Green");

    // Store in an array and sort them
    Question[] questions = {msq2, msq1};
    Arrays.sort(questions);

    // Verify that they are ordered lexicographically by their text
    assertTrue(questions[0].getText().equals("Select the animals."));
    assertTrue(questions[1].getText().equals("Select the colors."));
  }

  /**
   * Test that equals returns true for two MultipleSelectQuestions
   * with the same text, correct answers, and options.
   */
  @Test
  public void testEqualsSame() {
    MultipleSelect other = new MultipleSelect(
            "Which of these are fruits?",
            "1 3", "Apple Carrot Mango");
    assertTrue(msq.equals(other));
  }

  /**
   * Test that equals returns false for two MultipleSelectQuestions
   * with different correct answers.
   */
  @Test
  public void testEqualsDifferentCorrectAnswers() {
    MultipleSelect other = new MultipleSelect(
            "Which of these are fruits?",
            "1 2", "Apple Carrot Mango");
    assertFalse(msq.equals(other));
  }

  /**
   * Test that hashCode returns the same value for MultipleSelectQuestions
   * with the same text, correct answers, and options.
   */
  @Test
  public void testHashCodeSame() {
    MultipleSelect other = new MultipleSelect(
            "Which of these are fruits?",
            "1 3", "Apple Carrot Mango");
    assertEquals(msq.hashCode(), other.hashCode());
  }
}