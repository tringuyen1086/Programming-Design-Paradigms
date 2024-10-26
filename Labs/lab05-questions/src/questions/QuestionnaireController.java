package questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for managing a list of questions in a questionnaire.
 */
public class QuestionnaireController {

  private final List<Question> questions;

  public QuestionnaireController() {
    this.questions = new ArrayList<>();
  }

  /**
   * Add a question to the questionnaire.
   *
   * @param question the question to add
   */
  public void addQuestion(Question question) {
    this.questions.add(question);
  }

  /**
   * Get the list of all questions.
   *
   * @return the list of questions
   */
  public List<Question> getQuestions() {
    return this.questions;
  }

  /**
   * Evaluate the answers provided by a user.
   *
   * @param answers the answers provided
   * @return the results for each answer ("Correct"/"Incorrect")
   */
  public List<String> evaluate(List<String> answers) {
    List<String> results = new ArrayList<>();
    for (int i = 0; i < answers.size(); i++) {
      results.add(this.questions.get(i).answer(answers.get(i)));
    }
    return results;
  }

  /**
   * Compares this QuestionnaireController with another object for equality.
   * Two controllers are considered equal if they contain the same list of questions.
   *
   * @param o The object to compare with this controller.
   * @return {@code true} if the specified object is equal to this controller,
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
    QuestionnaireController that = (QuestionnaireController) o;
    return Objects.equals(this.questions, that.questions);
  }

  /**
   * Returns the hash code value for this controller.
   * The hash code is based on the list of questions.
   *
   * @return The hash code value for this controller.
   */
  @Override
  public int hashCode() {
    return Objects.hash(questions);
  }
}
