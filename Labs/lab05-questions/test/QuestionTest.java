import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import questions.Likert;
import questions.MultipleChoice;
import questions.MultipleSelect;
import questions.TrueFalse;

/**
 * Unit tests for the different question types in the questionnaire system.
 * This class tests the behavior of Likert, MultipleChoiceQuestion,
 * MultipleSelectQuestion, and TrueFalse.
 */
public class QuestionTest {

  private Likert likert;
  private MultipleChoice multipleChoice;
  private MultipleSelect multipleSelect;
  private TrueFalse trueFalse;

  /**
   * Set up the test environment before each test.
   * Initializes one instance of each question type with appropriate data.
   */
  @Before
  public void setUp() {
    likert = new Likert("How do you feel?");
    multipleChoice = new MultipleChoice("What is the capital?",
            "2", "London", "Paris", "Rome");
    multipleSelect = new MultipleSelect("Select valid colors.",
            "1 3", "Red", "Blue", "Green");
    trueFalse = new TrueFalse("Is the sky blue?", "True");
  }

  /**
   * Test the answer method of Likert.
   * Verifies that valid answers on the Likert scale (1-5) return "Correct"
   * and invalid answers return "Incorrect".
   */
  @Test
  public void testLikert() {
    assertEquals("Correct", likert.answer("1"));
    assertEquals("Correct", likert.answer("5"));
    assertEquals("Incorrect", likert.answer("6"));  // Invalid answer outside range
    assertEquals("Incorrect", likert.answer("0"));  // Invalid answer below range
  }

  /**
   * Test the answer method of MultipleChoiceQuestion.
   * Verifies that the correct answer returns "Correct" and incorrect answers
   * return "Incorrect".
   */
  @Test
  public void testMultipleChoice() {
    assertEquals("Correct", multipleChoice.answer("2"));  // Correct answer
    assertEquals("Incorrect", multipleChoice.answer("1"));  // Wrong answer
    assertEquals("Incorrect", multipleChoice.answer("4"));  // Invalid option
  }

  /**
   * Test the answer method of MultipleSelectQuestion.
   * Verifies that a correct set of answers returns "Correct", and incomplete
   * or incorrect sets of answers return "Incorrect".
   */
  @Test
  public void testMultipleSelect() {
    assertEquals("Correct", multipleSelect.answer("1 3"));  // All correct
    assertEquals("Incorrect", multipleSelect.answer("1"));  // Missing correct option
    assertEquals("Incorrect", multipleSelect.answer("2 3"));  // Contains incorrect option
    assertEquals("Incorrect", multipleSelect.answer("1 2 3"));  // Extra option
  }

  /**
   * Test for extra options in MultipleSelect.
   * Verifies that providing an answer with extra options returns "Incorrect".
   */
  @Test
  public void testMultipleSelectWithExtraOption() {
    // Extra invalid option included
    assertEquals("Incorrect", multipleSelect.answer("1 2 3"));
  }

  /**
   * Test for non-numeric answers in MultipleSelect.
   * Verifies that providing non-numeric answers returns "Incorrect".
   */
  @Test
  public void testMultipleSelectWithNonNumericAnswer() {
    assertEquals("Incorrect", multipleSelect.answer("one three")); // Non-numeric input
  }

  /**
   * Test the answer method of TrueFalse.
   * Verifies that the correct answer ("True" or "False") returns "Correct"
   * and an incorrect answer returns "Incorrect".
   */
  @Test
  public void testTrueFalse() {
    assertEquals("Correct", trueFalse.answer("True"));
    assertEquals("Incorrect", trueFalse.answer("False"));
  }

  /**
   * Test the compareTo method for ordering different question types.
   * Verifies that
   * TrueFalse < MultipleChoiceQuestion < MultipleSelectQuestion < Likert.
   */
  @Test
  public void testCompareTo() {
    assertTrue(trueFalse.compareTo(multipleChoice) < 0);
    assertTrue(multipleChoice.compareTo(multipleSelect) < 0);
    assertTrue(multipleSelect.compareTo(likert) < 0);
  }

  /**
   * Test the compareTo method for ordering different question types.
   * Verifies that TrueFalse < MultipleChoice < MultipleSelect < Likert.
   */
  @Test
  public void testQuestionOrdering() {
    // TrueFalse comes before MultipleChoice
    assertTrue(trueFalse.compareTo(multipleChoice) < 0);
    // MultipleChoice comes before MultipleSelect
    assertTrue(multipleChoice.compareTo(multipleSelect) < 0);
    // MultipleSelect comes before Likert
    assertTrue(multipleSelect.compareTo(likert) < 0);
  }

  /**
   * Test lexicographical ordering within the same question type.
   * Verifies that questions of the same type are sorted by their text.
   */
  @Test
  public void testSameTypeOrdering() {
    // Test lexicographical ordering within the same question type (Likert).
    Likert likert2 = new Likert("How are you?");
    int likertComparison = likert.compareTo(likert2);
    assertTrue("Likert comparison failed", likertComparison > 0);

    // Test lexicographical ordering within the same question type (MultipleChoice).
    MultipleChoice multipleChoice2 = new MultipleChoice("What is 2 + 2?",
            "4", "1 2 3 4");
    int multipleChoiceComparison = multipleChoice.compareTo(multipleChoice2);
    assertTrue("MultipleChoice comparison failed.", multipleChoiceComparison > 0);

    // Test lexicographical ordering within the same question type (MultipleSelect).
    MultipleSelect multipleSelect2 = new MultipleSelect("Select the fruits.",
            "1 2", "Apple Carrot Mango");
    int multipleSelectComparison = multipleSelect.compareTo(multipleSelect2);
    assertTrue("MultipleSelect comparison failed.", multipleSelectComparison > 0);

    // Test lexicographical ordering within the same question type (TrueFalse).
    TrueFalse trueFalse2 = new TrueFalse("Apple is a vegetable", "False");
    int trueFalseComparison = trueFalse.compareTo(trueFalse2);
    assertTrue("TrueFalse comparison failed.", trueFalseComparison > 0);
  }
}