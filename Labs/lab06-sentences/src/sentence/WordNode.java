package sentence;

/**
 * Class representing a node in the sentence that contains a word.
 * A WordNode contains a word and a reference to the next node in the sentence,
 * which can be either another word, punctuation, or an empty node.
 */
public class WordNode extends AbstractSentence {

  /**
   * Constructs a WordNode with the given word and the reference to the next node.
   * This constructor is used when the word is followed by another sentence node.
   *
   * @param word the word at this node
   * @param next the next node in the sentence
   */
  public WordNode(String word, Sentence next) {
    super(word, next);
  }

  /**
   * Constructs a WordNode with the given word, setting the next node to an EmptyNode.
   * This constructor is used when the word is the last node in the sentence.
   *
   * @param word the word at this node
   */
  public WordNode(String word) {
    super(word);
  }

  /**
   * Duplicates this WordNode and its subsequent nodes.
   * Creates a deep copy of the word node and recursively
   * duplicates the rest of the sentence.
   *
   * @return a deep copy of this WordNode
   */
  @Override
  public Sentence duplicate() {
    return new WordNode(this.content, this.next.duplicate());
  }

  /**
   * Returns the string representation of this WordNode.
   * Words are separated by spaces, but no space is added before punctuation.
   * If the next node is not punctuation or an EmptyNode, a space is added between words.
   *
   * @return the string representation of this WordNode and its subsequent nodes
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(this.content);

    // Check if the next node is not an EmptyNode or punctuation,
    // and add a space before appending the next word
    if (!(this.next instanceof EmptyNode)
            && !(this.next instanceof PunctuationNode)) {
      result.append(" ");  // Add space between words
    }

    // Recursively call next node's toString
    result.append(this.next.toString());

    return result.toString();
  }

  /**
   * Compares this WordNode with another object for equality.
   * Two WordNode instances are considered equal if they have the same word
   * and the same subsequent nodes.
   *
   * @param o the other object to compare with this WordNode
   * @return true if the two nodes are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WordNode)) {
      return false;
    }
    WordNode other = (WordNode) o;
    return content.equals(other.content) && next.equals(other.next);
  }

  /**
   * Returns the hash code of this WordNode.
   * The hash code is based on the string representation of the word
   * and its subsequent nodes.
   *
   * @return the hash code of this WordNode
   */
  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }
}

