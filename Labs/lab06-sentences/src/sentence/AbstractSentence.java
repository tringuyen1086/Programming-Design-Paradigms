package sentence;

/**
 * Abstract class representing a node in a sentence.
 * This node can either be a word or punctuation
 * and has a reference to the next node in the sentence.
 * Subclasses of this class will be WordNode or PunctuationNode,
 * which contain the actual content.
 */
abstract class AbstractSentence implements Sentence {

  /**
   * The content of this sentence node (can be a word or punctuation).
   */
  protected final String content;

  /**
   * The reference to the next node in the sentence.
   */
  protected Sentence next;

  /**
   * Constructs an AbstractSentence node with the given content
   * and the reference to the next node.
   *
   * @param content the content (either a word or punctuation) at this node
   * @param next the next node in the sentence
   */
  public AbstractSentence(String content, Sentence next) {
    this.content = content;
    this.next = next;
  }

  /**
   * Constructs an AbstractSentence node with only the content.
   * The next node is automatically set to an EmptyNode.
   *
   * @param content the content (either a word or punctuation) at this node
   */
  public AbstractSentence(String content) {
    this.content = content;
    this.next = new EmptyNode();
  }

  /**
   * Returns the number of words in the sentence,
   * determined recursively. Punctuation nodes do not count as words.
   *
   * @return the number of words in this sentence
   */
  @Override
  public int getNumberOfWords() {
    // Recursively call getNumberOfWords on the next node
    int countInNext = this.next.getNumberOfWords();
    if (this instanceof WordNode) {
      return 1 + countInNext; // Count this node as a word if it's a WordNode
    } else {
      // If it's punctuation, just return the count from the next node
      return countInNext;
    }
  }

  /**
   * Returns the longest word in the sentence.
   * Punctuation is ignored, and only WordNodes are considered
   * for determining the longest word.
   *
   * @return the longest word in the sentence, or an empty string if no words are present
   */
  @Override
  public String longestWord() {
    // Find the longest word in the rest of the sentence
    String longestInRest = this.next.longestWord();

    // Compare the current word with the longest in the rest
    if (this instanceof WordNode) {
      return this.content.length() >= longestInRest.length() ? this.content : longestInRest;
    }

    return longestInRest; // Ignore non-word nodes (punctuation, etc.)
  }

  /**
   * Merges this sentence with another sentence.
   * It creates a deep copy of this sentence and appends the other sentence to the end.
   *
   * @param other the sentence to merge with
   * @return a new Sentence representing the merged result
   */
  @Override
  public Sentence merge(Sentence other) {
    // Create a deep copy of the current sentence
    AbstractSentence node = (AbstractSentence) this.duplicate();
    Sentence head = node;

    // Traverse to the end of the current sentence
    while (!(node.next instanceof EmptyNode)) {
      node = (AbstractSentence) node.next;
    }

    // Attach the second sentence directly without inserting any space
    node.next = other;

    return head;
  }

  /**
   * Abstract method to duplicate the current sentence node.
   * Subclasses (WordNode and PunctuationNode) will implement this method to
   * duplicate their specific node type and recursively duplicate the rest of the sentence.
   *
   * @return a deep copy of this sentence node
   */
  @Override
  public abstract Sentence duplicate();

  /**
   * Returns the string representation of the sentence.
   * Words are separated by spaces, and punctuation marks
   * are attached to words without spaces.
   * Merged sentences are separated by spaces.
   *
   * @return the string representation of this sentence
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(this.content);
    AbstractSentence node = this;

    while (!(node.next instanceof EmptyNode)) {
      node = (AbstractSentence) node.next;

      // Add a space if the current node is a WordNode and the next node is a WordNode
      if (node instanceof WordNode
              && !(result.toString().endsWith(" ")
              || result.toString().endsWith(".")
              || result.toString().endsWith("!")
              || result.toString().endsWith("?"))) {
        result.append(" ");
      }

      // Append the content of the next node
      result.append(node.content);
    }

    return result.toString().trim(); // Ensure no trailing spaces
  }

  /**
   * Returns the hash code of the sentence,
   * based on the string representation of the sentence.
   *
   * @return the hash code of the sentence
   */
  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  /**
   * Checks if two sentences are equal.
   * Two sentences are considered equal if their content and structure are the same.
   *
   * @param o the other object to compare with
   * @return true if the sentences are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractSentence)) {
      return false;
    }
    AbstractSentence other = (AbstractSentence) o;
    return content.equals(other.content) && next.equals(other.next);
  }
}
