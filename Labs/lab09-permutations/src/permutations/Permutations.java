package permutations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An iterator that generates all possible permutations
 * of a given sequence of characters in lexicographical order,
 * starting from a specified length and allowing
 * both forward and backward traversal.
 */
public class Permutations implements BackAndForthIterator<String> {

  private final String base;
  private int currentLength;
  private int[] indices;

  /**
   * Constructor with only the base string. Starts at permutations of length 1.
   *
   * @param base The string to generate permutations from.
   * @throws IllegalArgumentException if the base is null,
    empty, or contains non-alphabetic characters.
   */
  public Permutations(String base) {
    this(base, 1);
  }

  /**
   * Constructor with base string and a starting length for the permutations.
   *
   * @param base The string to generate permutations from.
   * @param startLength The initial length of permutations to generate.
   * @throws IllegalArgumentException if base is null,
    empty, or contains non-alphabetic characters,
    or if startLength is out of range.
   */
  public Permutations(String base, int startLength) {
    if (base == null || base.isEmpty() || !base.matches("[a-zA-Z]+")) {
      throw new IllegalArgumentException("Invalid string. "
              + "Must be non-null, non-empty, and alphabetic.");
    }
    if (startLength < 1 || startLength > base.length()) {
      throw new IllegalArgumentException("Invalid start length.");
    }

    this.base = base;
    this.currentLength = startLength;
    this.indices = new int[currentLength];
    for (int i = 0; i < currentLength; i++) {
      indices[i] = i;
    }
  }

  @Override
  public boolean hasNext() {
    return currentLength <= base.length();
  }

  @Override
  public String next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No next permutation available.");
    }

    StringBuilder permutation = new StringBuilder();
    for (int i = 0; i < currentLength; i++) {
      permutation.append(base.charAt(indices[i]));
    }

    generateNextPermutation();

    return permutation.toString();
  }

  @Override
  public boolean hasPrevious() {
    return currentLength > 1 || (currentLength == 1 && indices[0] > 0);
  }

  @Override
  public String previous() {
    if (!hasPrevious()) {
      throw new NoSuchElementException("No previous permutation available.");
    }

    generatePreviousPermutation();

    StringBuilder permutation = new StringBuilder();
    for (int i = 0; i < currentLength; i++) {
      permutation.append(base.charAt(indices[i]));
    }

    return permutation.toString();
  }

  /**
   * Advances the indices to the next permutation in lexicographical order.
   * If the end of the current length's permutations is reached,
   * the length is incremented.
   */
  private void generateNextPermutation() {
    if (!incrementIndices()) {
      currentLength++;
      if (currentLength <= base.length()) {
        indices = new int[currentLength];
        for (int i = 0; i < currentLength; i++) {
          indices[i] = i;
        }
      }
    }
  }

  /**
   * Moves the indices to the previous permutation in lexicographical order.
   * If at the beginning of the current length's permutations,
   * decreases length.
   */
  private void generatePreviousPermutation() {
    if (!decrementIndices()) {
      currentLength--;
      if (currentLength > 0) {
        indices = new int[currentLength];
        for (int i = 0; i < currentLength; i++) {
          indices[i] = base.length() - currentLength + i;
        }
      }
    }
  }

  /**
   * Increments the current indices lexicographically,
   * modifying the indices array.
   * Returns true if successful;
   * false if reached the end of the length's permutations.
   */
  private boolean incrementIndices() {
    for (int i = currentLength - 1; i >= 0; i--) {
      if (indices[i] < base.length() - currentLength + i) {
        indices[i]++;
        for (int j = i + 1; j < currentLength; j++) {
          indices[j] = indices[j - 1] + 1;
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Decrements the current indices lexicographically,
   * modifying the indices array.
   * Returns true if successful;
   * false if reached the start of the length's permutations.
   */
  private boolean decrementIndices() {
    for (int i = currentLength - 1; i >= 0; i--) {
      if (indices[i] > i) {
        indices[i]--;
        for (int j = i + 1; j < currentLength; j++) {
          indices[j] = indices[j - 1] + 1;
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if this Permutations object is equal to another object.
   * @param obj the object to compare with
   * @return true if the other object is a Permutations instance
  with the same base string, current length, and indices; false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Permutations that = (Permutations) obj;
    return currentLength == that.currentLength
            && base.equals(that.base)
            && Arrays.equals(indices, that.indices);
  }

  /**
   * Computes the hash code for this Permutations object based on its base string,
   * current length, and indices.
   * @return the hash code of this Permutations object.
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(base, currentLength);
    result = 31 * result + Arrays.hashCode(indices);
    return result;
  }

  @Override
  public String toString() {
    return String.format("Permutations(base='%s', "
            + "currentLength=%d)", base, currentLength);
  }
}
