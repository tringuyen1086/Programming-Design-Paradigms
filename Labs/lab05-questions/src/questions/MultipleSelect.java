package questions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a multiple select question.
 */
public class MultipleSelect extends AbstractQuestion {

  private final Set<String> correctAnswers;
  private final String[] options;

  /**
   * Constructor for a Multiple Select question.
   *
   * @param text the question text
   * @param correctAnswers the correct answers (set of option numbers)
   * @param options the list of options (must be between 3 and 8)
   * @throws IllegalArgumentException if options are fewer than 3 or more than 8
   */
  public MultipleSelect(String text, String correctAnswers, String... options) {
    super(text); // Call the AbstractQuestion constructor

    String[] optionArray;

    // Handle both cases:
    // 1. If a single string of space-separated options is passed.
    // 2. If multiple options are passed as separate varargs.
    if (options.length == 1) {
      optionArray = options[0].split(" ");
    } else {
      optionArray = options;
    }

    // Validate the number of options
    if (optionArray.length < 3 || optionArray.length > 8) {
      throw new IllegalArgumentException("A question may have at least 3 options, "
              + "but no more than 8.");
    }
    this.correctAnswers = new HashSet<>(Arrays.asList(correctAnswers.split(" ")));
    this.options = Arrays.copyOf(optionArray, optionArray.length);  // Use split options
  }

  /**
   * Gets the text of the MultipleSelect question.
   *
   * @return the text of the question
   */
  @Override
  public String getText() {
    return super.getText();
  }

  /**
   * Returns the type order for this question as MULTIPLE_SELECT.
   *
   * @return The QuestionType of this question.
   */
  @Override
  public QuestionType getTypeOrder() {
    return QuestionType.MULTIPLE_SELECT;
  }

  /**
   * Evaluates the provided answer to determine
   * whether it matches the set of correct options for the question.
   * The user's answer is considered correct
   * if it includes all correct options and no incorrect ones.
   *
   * @param answer the user's answer,
   *               which should be a set of option numbers as a space-separated string
   * @return "Correct" if the answer matches the correct options, otherwise "Incorrect"
   */
  @Override
  public String answer(String answer) {
    // Split the answer into individual options
    Set<String> userAnswers = new HashSet<>(Arrays.asList(answer.split(" ")));

    // Check for valid numbers in the provided answer
    try {
      for (String providedAnswer : userAnswers) {
        int answerIndex = Integer.parseInt(providedAnswer);
        if (answerIndex < 1 || answerIndex > this.options.length) {
          return "Incorrect"; // Out of range option
        }
      }
    } catch (NumberFormatException e) {
      return "Incorrect"; // Non-numeric input
    }

    // Check if the provided answer matches the correct answers
    if (userAnswers.containsAll(correctAnswers)
            && correctAnswers.containsAll(userAnswers)) {
      return "Correct";
    }

    return "Incorrect";
  }

  /**
   * Compares this MultipleSelect to another Question for ordering.
   * Multiple select questions come after multiple choice but before Likert.
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
    // TrueFalse and MultipleChoice come first
    // Multiple select questions come after multiple choice but before Likert.
    if (other instanceof TrueFalse || other instanceof MultipleChoice) {
      return 1;
    }
    // Likert comes after MultipleSelect, so return -1.
    if (other instanceof Likert) {
      return -1;
    }
    // If both are MultipleSelect, compare based on text lexicographically.
    return this.questionText.compareTo(other.getText());
  }

  /**
   * Checks if this question is equal to another object.
   *
   * @param o the object to compare
   * @return {@code true} if the object is a MultipleSelect
    with the same text and correct answers
   *         {@code false} otherwise.

   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MultipleSelect)) {
      return false;
    }
    MultipleSelect that = (MultipleSelect) o;
    return this.questionText.equals(that.questionText)
            && this.correctAnswers.equals(that.correctAnswers)
            && Arrays.equals(this.options, that.options);
  }

  /**
   * Returns the hash code for this question.
   *
   * @return the hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.questionText, this.correctAnswers, Arrays.hashCode(this.options));
  }
}
