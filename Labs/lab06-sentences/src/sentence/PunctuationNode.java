package sentence;

/**
 * Class representing a node in the sentence that contains punctuation.
 * This node handles punctuation characters (e.g., ".", "!", "?")
 * and their position within a sentence.
 */
public class PunctuationNode extends AbstractSentence {

  /**
   * Constructs a PunctuationNode with the given punctuation
   * and the reference to the next node.
   * This constructor is used
   * when the punctuation node is followed by another sentence node.
   *
   * @param punctuation the punctuation at this node
   * @param next the next node in the sentence
   */
  public PunctuationNode(String punctuation, Sentence next) {
    super(punctuation, next);
  }

  /**
   * Constructs a PunctuationNode with the given punctuation,
   * setting the next node to an EmptyNode.
   * This constructor is used when the punctuation node is the last node in the sentence.
   *
   * @param punctuation the punctuation at this node
   */
  public PunctuationNode(String punctuation) {
    super(punctuation);
  }

  /**
   * Duplicates this PunctuationNode and its subsequent nodes.
   * Creates a deep copy of the punctuation node
   * and recursively duplicates the rest of the sentence.
   *
   * @return a deep copy of this PunctuationNode
   */
  @Override
  public Sentence duplicate() {
    return new PunctuationNode(this.content, this.next.duplicate());
  }

  /**
   * Returns the string representation of this PunctuationNode.
   * Punctuation is attached to the previous word without a space.
   * If the next node is a WordNode, a space is added after the punctuation.
   * No space is added between consecutive PunctuationNode instances.
   *
   * @return the string representation of this punctuation node and its subsequent nodes
   */
  @Override
  public String toString() {
    // No space before punctuation, and also no space between consecutive punctuation nodes
    if (this.next instanceof WordNode) {
      // Add a space only if the next node is a WordNode
      return this.content + " " + this.next.toString();
    }
    // No space between consecutive punctuation nodes or other types of nodes
    return this.content + this.next.toString();
  }

  /**
   * Compares this PunctuationNode with another object for equality.
   * Two PunctuationNode instances are considered equal
   * if they have the same punctuation
   * and the same subsequent nodes.
   *
   * @param o the other object to compare with this PunctuationNode
   * @return true if the two nodes are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PunctuationNode)) {
      return false;
    }
    PunctuationNode other = (PunctuationNode) o;
    return content.equals(other.content) && next.equals(other.next);
  }

  /**
   * Returns the hash code of this PunctuationNode.
   * The hash code is based on the string representation of the punctuation
   * and its subsequent nodes.
   *
   * @return the hash code of this PunctuationNode
   */
  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }
}
