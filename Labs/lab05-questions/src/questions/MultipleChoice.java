package questions;

import java.util.Arrays;
import java.util.Objects;

/**
 *  Represents a multiple-choice question with several options,
 *  only one of which is correct.
 *  Answers are given as a number representing one of the available options.
 */
public class MultipleChoice extends AbstractQuestion {

  private final String correctAnswer; // Single correct answer
  private final String[] options; // Store the options in an array

  /**
   * Constructor for a Multiple Choice question.
   *
   * @param text the question text
   * @param correctAnswer the correct answer must be numeric and within the valid option range.
   * @param options the list of options (must be between 3 and 8)
   * @throws IllegalArgumentException if options are fewer than 3 or more than 8,
    or if correct answer is not numeric
   */
  public MultipleChoice(String text, String correctAnswer, String... options) {
    super(text); // Call the AbstractQuestion constructor
    String[] optionArray;

    // If only one string is passed in options, split it by space
    if (options.length == 1) {
      optionArray = options[0].split(" ");
    } else {
      // Otherwise, treat the varargs as multiple options
      optionArray = options;
    }

    // Validate the number of options
    if (optionArray.length < 3 || optionArray.length > 8) {
      throw new IllegalArgumentException("A question must have at least 3 options, "
              + "but no more than 8.");
    }

    // Validate that all correct answers are numeric
    if (!correctAnswer.matches("[1-8]")
            || Integer.parseInt(correctAnswer) > optionArray.length) {
      throw new IllegalArgumentException(
              "Correct answer must be numeric and correspond to one of the provided options.");
    }

    this.correctAnswer = correctAnswer;
    this.options = Arrays.copyOf(optionArray, optionArray.length);
  }

  /**
   * Gets the text of the Multiple Choice question.
   *
   * @return the text of the question
   */
  @Override
  public String getText() {
    return super.getText();
  }


  /**
   * Evaluates the provided answer to determine
   * whether it matches the correct option for the question.
   *
   * @param answer the user's answer,
   *               which should be a number representing one of the options
   * @return "Correct" if the answer matches the correct option, otherwise "Incorrect"
   */
  @Override
  public String answer(String answer) {
    return answer.equals(this.correctAnswer) ? "Correct" : "Incorrect";
  }

  /**
   * Returns the type order for this question as MULTIPLE_CHOICE.
   *
   * @return The QuestionType of this question.
   */
  @Override
  public QuestionType getTypeOrder() {
    return QuestionType.MULTIPLE_CHOICE;
  }

  /**
   * Compares this MultipleChoice to another Question for ordering.
   * Multiple choice questions come after True/False but before others.
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
    // Multiple choice questions come after True/False but before others.
    if (other instanceof TrueFalse) {
      return 1;
    }
    // MultipleChoice comes before MultipleSelect and Likert.
    if (other instanceof Likert || other instanceof MultipleSelect) {
      return -1;
    }
    // If both are MultipleChoice, compare based on text lexicographically.
    return this.questionText.compareTo(other.getText());
  }

  /**
   * Checks if this question is equal to another object.
   *
   * @param o the object to compare
   * @return {@code true} if the object is a MultipleChoice with the same text and options
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MultipleChoice)) {
      return false;
    }
    MultipleChoice that = (MultipleChoice) o;
    return this.questionText.equals(that.questionText)
            && this.correctAnswer.equals(that.correctAnswer)
            && Arrays.equals(this.options, that.options);
  }

  /**
   * Returns the hash code for this question.
   *
   * @return the hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.questionText, this.correctAnswer, Arrays.hashCode(this.options));
  }
}
