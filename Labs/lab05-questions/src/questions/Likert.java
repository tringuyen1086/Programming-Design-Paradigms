package questions;

import java.util.Objects;

/**
 * Represents a Likert question with a fixed 5-point scale.
 * Likert questions are opinion-based and do not have a "correct" answer.
 * Valid answers range from 1 to 5.
 * Strongly Agree, Agree, Neither Agree nor Disagree, Disagree, Strongly Disagree
 */
public class Likert extends AbstractQuestion {

  /**
   * Constructor for a Likert question with the given text.
   *
   * @param text the question text
   */
  public Likert(String text) {
    super(text); // Call the AbstractQuestion constructor
  }

  /**
   * Gets the text of the Likert question.
   *
   * @return the text of the question
   */
  @Override
  public String getText() {
    return super.getText();
  }

  /**
   * Evaluates the provided answer to determine whether it is valid for a Likert question.
   * A valid answer must be a number between 1 and 5 (as a String).
   *
   * @param answer the user's answer, which should be a number between 1 and 5
   * @return "Correct" if the answer is valid, otherwise "Incorrect"
   */
  @Override
  public String answer(String answer) {
    switch (answer) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
        return "Correct";
      default:
        return "Incorrect";
    }
  }

  /**
   * Compares this Likert to another Question for ordering.
   * Likert questions come last in the order of types, so they are always
   * considered "greater" than other types.
   *
   * @param other the other question to compare
   * @return a positive integer if this question is "greater",
   *         0 if equal, or a negative integer if "lesser"
   * @throws NullPointerException if the question is null
   */
  @Override
  public int compareTo(Question other) {
    if (other == null) {
      throw new NullPointerException("Cannot compare to a null question");
    }
    // Likert questions should come last,
    // so return a positive number unless comparing to another Likert question.
    if (!(other instanceof Likert)) {
      return 1;  // Likert questions are last.
    }
    // Compare based on text lexicographically if both are Likert questions.
    return this.questionText.compareTo(other.getText());
  }

  /**
   * Returns the type order for this question as LIKERT.
   *
   * @return The QuestionType of this question.
   */
  @Override
  public QuestionType getTypeOrder() {
    return QuestionType.LIKERT;
  }

  /**
   * Checks if this question is equal to another object.
   *
   * @param o the object to compare
   * @return {@code true} if the specified object is equal to this question,
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Likert)) {
      return false;
    }
    Likert that = (Likert) o;
    return this.questionText.equals(that.questionText);
  }

  /**
   * Returns the hash code for this question.
   *
   * @return the hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.questionText);
  }
}
