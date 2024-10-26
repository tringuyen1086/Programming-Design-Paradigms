package sentence;

import java.util.Objects;

/**
 * Class representing the end of a sentence (an empty node).
 * This node is used to indicate the end of a recursive sentence structure.
 * It contains no words and serves as a base case for recursive methods on sentences.
 */
public class EmptyNode implements Sentence {

  /**
   * Constructs an EmptyNode, which represents the end of a sentence.
   */
  public EmptyNode() {}

  /**
   * Returns the number of words in the sentence.
   * Since this is an EmptyNode, it contains no words and returns 0.
   *
   * @return 0 because an empty node contains no words.
   */
  @Override
  public int getNumberOfWords() {
    return 0; // No words in an empty node
  }

  /**
   * Returns the longest word in the sentence.
   * Since this is an EmptyNode, it contains no words and returns an empty string.
   *
   * @return an empty string because an empty node contains no words.
   */
  @Override
  public String longestWord() {
    return ""; // No words in an empty node
  }

  /**
   * Merges this EmptyNode with another sentence.
   * Since this is an EmptyNode, merging it with another sentence
   * returns the other sentence unchanged.
   *
   * @param other the sentence to merge with this EmptyNode.
   * @return the other sentence, as merging with an empty node does not change the result.
   */
  @Override
  public Sentence merge(Sentence other) {
    return other; // Merging an empty sentence with another sentence
  }

  /**
   * Duplicates this EmptyNode.
   * Returns a new instance of EmptyNode, which is equivalent to the current node.
   *
   * @return a new EmptyNode instance.
   */
  @Override
  public Sentence duplicate() {
    return new EmptyNode();
  }

  /**
   * Returns the string representation of this EmptyNode.
   * Since this is the end of the sentence, it returns an empty string.
   *
   * @return an empty string.
   */
  @Override
  public String toString() {
    return ""; // Empty node represents the end of the sentence
  }

  /**
   * Returns the hash code for this EmptyNode.
   * All instances of EmptyNode should return the same hash code.
   *
   * @return the hash code for this EmptyNode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(); // Consistent hash code for EmptyNode
  }

  /**
   * Determines if this EmptyNode is equal to another object.
   * All instances of EmptyNode are considered equal.
   *
   * @param o the object to compare with this EmptyNode.
   * @return true if the other object is also an EmptyNode, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    return o instanceof EmptyNode; // All empty nodes are considered equal
  }
}
