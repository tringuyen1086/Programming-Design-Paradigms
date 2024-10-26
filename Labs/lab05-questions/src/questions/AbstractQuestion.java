package questions;

import java.util.Objects;

/**
 * An abstract class representing a general question with shared behaviors.
 * All question types (Likert, TrueFalse, MultipleChoice, MultipleSelect) extend this class.
 */
public abstract class AbstractQuestion implements Question, Comparable<Question> {
  /** The text of the question. */
  protected String questionText;

  /**
   * Constructs an AbstractQuestion with the specified question text.
   *
   * @param questionText The text of the question.
   * @throws IllegalArgumentException if the question text is null or empty.
   */
  public AbstractQuestion(String questionText) {
    if (questionText == null || questionText.isEmpty()) {
      throw new IllegalArgumentException("Question text cannot be null or empty.");
    }
    this.questionText = questionText;
  }

  /**
   * Gets the text of this question.
   *
   * @return The text of the question.
   */
  @Override
  public String getText() {
    return questionText;
  }

  /**
   * Evaluates the given answer and returns "Correct" or "Incorrect".
   * This method must be implemented by subclasses.
   *
   * @param answer The answer provided as a String.
   * @return "Correct" if the answer is correct, otherwise "Incorrect".
   */
  public abstract String answer(String answer);

  /**
   * Compares this question to another for ordering.
   * Orders questions by their type first, and lexicographically within the same type.
   *
   * @param other The other question to compare.
   * @return A negative integer, zero, or a positive integer if this question is less than,
   *         equal to, or greater than the specified question.
   */
  @Override
  public int compareTo(Question other) {
    if (other == null) {
      throw new NullPointerException("Cannot compare to a null question");
    }
    // Cast the other question to AbstractQuestion to access getTypeOrder().
    if (!(other instanceof AbstractQuestion)) {
      throw new IllegalArgumentException("Cannot compare with non-AbstractQuestion instance");
    }

    AbstractQuestion otherQuestion = (AbstractQuestion) other;

    // Compare by question type order.
    int typeComparison = this.getTypeOrder().compareTo(otherQuestion.getTypeOrder());
    if (typeComparison != 0) {
      return typeComparison;
    }

    // If same type, compare lexicographically by question text.
    return this.questionText.compareTo(otherQuestion.getText());
  }

  /**
   * Gets the type order for this question.
   * This method must be implemented by subclasses to define the question type.
   *
   * @return The QuestionType of this question.
   */
  public abstract QuestionType getTypeOrder();

  /**
   * Compares this question with another object for equality.
   * Two questions are considered equal if they belong to the same class
   * and have the same question text.
   *
   * @param o The object to compare with this question.
   * @return {@code true} if the specified object is equal to this question,
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractQuestion that = (AbstractQuestion) o;
    return Objects.equals(this.questionText, that.questionText);
  }

  /**
   * Returns the hash code value for this question.
   * The hash code is based on the question text.
   *
   * @return The hash code value for this question.
   */
  @Override
  public int hashCode() {
    return Objects.hash(questionText);
  }
}
