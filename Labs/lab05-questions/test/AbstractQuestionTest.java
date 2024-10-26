import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import questions.Likert;
import questions.QuestionType;
import questions.TrueFalse;



/**
 * Unit tests for the AbstractQuestion class using the Likert subclass.
 */
public class AbstractQuestionTest {

  /**
   * Test that the constructor initializes the question with valid text.
   */
  @Test
  public void testConstructor_validText() {
    Likert question = new Likert("How satisfied are you?");
    assertEquals("How satisfied are you?", question.getText());
  }

  /**
   * Test that the constructor throws IllegalArgumentException when the text is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_nullText() {
    new Likert(null);
  }

  /**
   * Test that the constructor throws IllegalArgumentException when the text is empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_emptyText() {
    new Likert("");
  }


  /**
   * Test that the compareTo method correctly orders two questions
   * based on their text lexicographically when they are of the same type.
   */
  @Test
  public void testCompareTo_sameType() {
    Likert q1 = new Likert("A question");
    Likert q2 = new Likert("B question");
    assertTrue(q1.compareTo(q2) < 0); // A comes before B lexicographically
  }

  /**
   * Test that the compareTo method throws NullPointerException when comparing to null.
   */
  @Test(expected = NullPointerException.class)
  public void testCompareTo_null() {
    Likert question = new Likert("How satisfied are you?");
    question.compareTo(null);
  }

  /**
   * Test that the compareTo method correctly orders different question types.
   */
  @Test
  public void testCompareTo_differentTypes() {
    Likert likertQuestion = new Likert("How satisfied are you?");
    TrueFalse trueFalseQuestion = new TrueFalse("Is Java a programming language?",
            "True");

    // TrueFalse should come before Likert, so compareTo should return a positive number
    assertTrue(trueFalseQuestion.compareTo(likertQuestion) < 0);
    // Likert should come after TrueFalse, so compareTo should return a negative number
    assertTrue(likertQuestion.compareTo(trueFalseQuestion) > 0);
  }

  /**
   * Test that getTypeOrder returns the correct QuestionType for a Likert question.
   */
  @Test
  public void testGetTypeOrder() {
    Likert question = new Likert("How satisfied are you?");
    assertEquals(QuestionType.LIKERT, question.getTypeOrder()); // Verify correct type order
  }

  /**
   * Test that two questions with the same text are considered equal and have the same hash code.
   */
  @Test
  public void testEqualsAndHashCode_sameText() {
    Likert q1 = new Likert("How satisfied are you?");
    Likert q2 = new Likert("How satisfied are you?");
    assertEquals(q1, q2); // Verify equality
    assertEquals(q1.hashCode(), q2.hashCode()); // Verify hash code consistency
  }

  /**
   * Test that two questions with different texts are not equal and have different hash codes.
   */
  @Test
  public void testEqualsAndHashCode_differentText() {
    Likert q1 = new Likert("How satisfied are you?");
    Likert q2 = new Likert("Are you happy?");
    assertNotEquals(q1, q2); // Verify inequality
    assertNotEquals(q1.hashCode(), q2.hashCode()); // Verify different hash codes
  }

}