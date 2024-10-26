package questions;

import java.util.Objects;

/**
 * Represents a True/False question.
 * The answer can only be "True" or "False".
 */
public class TrueFalse extends AbstractQuestion {

  private final String correctAnswer;

  /**
   * Constructor for a True/False question.
   *
   * @param text the question text
   * @param correctAnswer the correct answer ("True" or "False")
   * @throws IllegalArgumentException if the correct answer is not "True" or "False"
   */
  public TrueFalse(String text, String correctAnswer) {
    super(text); // Call the constructor of AbstractQuestion

    if (!"True".equals(correctAnswer) && !"False".equals(correctAnswer)) {
      throw new IllegalArgumentException("Correct answer must be 'True' or 'False'.");
    }
    this.correctAnswer = correctAnswer;
  }

  /**
   * Gets the text of the True/False question.
   *
   * @return the text of the question
   */
  @Override
  public String getText() {
    return super.getText();
  }

  /**
   * Evaluates the provided answer to determine
   * whether it matches the correct answer ("True" or "False").
   *
   * @param answer the user's answer, which should be either "True" or "False"
   * @return "Correct" if the answer matches the correct answer, otherwise "Incorrect"
   */
  @Override
  public String answer(String answer) {
    return answer.equalsIgnoreCase(this.correctAnswer) ? "Correct" : "Incorrect";
  }

  /**
   * Returns the type order for this question as TRUE_FALSE.
   *
   * @return The QuestionType of this question.
   */
  @Override
  public QuestionType getTypeOrder() {
    return QuestionType.TRUE_FALSE;
  }

  /**
   * Compares this TrueFalse to another Question for ordering.
   * True/False questions come first in the order of types.
   *
   * @param other the other question to compare
   * @return a positive integer if this question is "greater",
   *         0 if equal, or a negative integer if "lesser"
   */
  @Override
  public int compareTo(Question other) {
    if (other == null) {
      throw new NullPointerException("Cannot compare to a null question");
    }
    // TrueFalse comes first, so return negative if other is not a TrueFalse question
    if (!(other instanceof TrueFalse)) {
      return -1;
    }
    // If both are TrueFalse, compare lexicographically by question text
    return this.questionText.compareTo(other.getText());
  }

  /**
   * Checks if this question is equal to another object.
   *
   * @param o the object to compare
   * @return true if the object is a TrueFalse with the same text and correct answer
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TrueFalse)) {
      return false;
    }
    TrueFalse that = (TrueFalse) o;
    return this.questionText.equals(that.questionText)
            && this.correctAnswer.equalsIgnoreCase(that.correctAnswer);
  }

  /**
   * Returns the hash code for this question.
   *
   * @return the hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.questionText, this.correctAnswer);
  }
}
