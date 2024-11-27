import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import permutations.Permutations;

/**
 * Unit tests for the Permutations class,
 * covering edge cases and boundary conditions.
 */
public class PermutationsTest {

  private Permutations iterator;

  /**
   * Sets up a Permutations instance with default input "abcd".
   */
  @Before
  public void setUp() {
    iterator = new Permutations("abcd");
  }

  /**
   * Test the two-argument constructor for backward traversal.
   */
  @Test
  public void testTwoArgumentConstructorMovingBackward() {
    iterator = new Permutations("abcd", 2);

    // Generate some permutations
    assertEquals("ab", iterator.next());
    assertEquals("ac", iterator.next());
    assertEquals("ad", iterator.next());

    // Check if we can go backward
    assertTrue(iterator.hasPrevious());
    assertEquals("ad", iterator.previous());
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());

    // Ensure we cannot go backward anymore
    assertFalse(iterator.hasPrevious());
  }

  /**
   * Test the two-argument constructor with a starting length of 1.
   */
  @Test
  public void testSmallerStartInTwoArgumentConstructor() {
    iterator = new Permutations("abc", 1);
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("c", iterator.next());
  }

  /**
   * Test the two-argument constructor with a larger starting length.
   */
  @Test
  public void testLargerStartInTwoArgumentConstructor() {
    iterator = new Permutations("abcd", 3);
    assertEquals("abc", iterator.next());
    assertEquals("abd", iterator.next());
    assertEquals("acd", iterator.next());
    assertEquals("bcd", iterator.next());
    assertFalse(iterator.hasNext());
  }

  /**
   * Testing one-argument constructor moving forward.
   */
  @Test
  public void testOneArgumentConstructorMovingForward() {
    iterator = new Permutations("abcd");
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("c", iterator.next());
    assertEquals("d", iterator.next());
    iterator = new Permutations("abcd", 2); // advance to length 2
    assertTrue(iterator.hasNext());
    assertEquals("ab", iterator.next());
  }

  /**
   * Testing moving backward from the middle.
   */
  @Test
  public void testMovingBackwardFromMiddle() {
    iterator = new Permutations("abc", 2);
    iterator.next(); // "ab"
    iterator.next(); // "ac"
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
  }

  /**
   * Test moving forward and backward between permutations.
   */
  @Test
  public void testGoingBackAndForth() {
    iterator = new Permutations("abc", 2);
    assertEquals("ab", iterator.next());
    assertEquals("ac", iterator.next());
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
    assertEquals("ab", iterator.next());
  }

  /**
   * Testing moving forward from the middle of permutations.
   */
  @Test
  public void testMovingForwardFromMiddle() {
    iterator = new Permutations("abc", 2);
    iterator.next(); // "ab"
    assertEquals("ac", iterator.next());
    assertEquals("bc", iterator.next());
  }

  /**
   * Testing invalid characters in one-argument constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCharactersOneArgumentConstructor() {
    new Permutations("ab@cd"); // Invalid character '@'
  }

  /**
   * Testing invalid characters in two-argument constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCharactersTwoArgumentConstructor() {
    new Permutations("ab1cd", 2); // Invalid character '1'
  }

  /**
   * Test previous() when no previous element is available,
   * expecting NoSuchElementException.
   */
  @Test(expected = NoSuchElementException.class)
  public void testPreviousWhenNoPrevious() {
    iterator = new Permutations("abcd", 2);
    iterator.previous(); // should throw NoSuchElementException
  }

  /**
   * Tests that `next()` throws a NoSuchElementException
   * when there are no more elements.
   */
  @Test
  public void testNextWhenNoNext() {
    Permutations iterator = new Permutations("ab");

    // Traverse available elements
    assertEquals("a", iterator.next());  // Expected: "a"
    assertEquals("b", iterator.next()); // Expected: "b"

    // Check hasNext after the last element
    assertFalse("Has next after last element should be false", iterator.hasNext());

    // Confirm NoSuchElementException is thrown when calling next() beyond the last element
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  /**
   * Tests that the Permutations constructor completes initialization within 100 ms
   * without precomputing permutations, ensuring efficient, lazy evaluation.
   */
  @Test(timeout = 100)
  public void testConstructorPerformance() {
    // Creating a Permutations instance with a long string and minimal start length
    new Permutations("abcdefghijklmnop", 1);
  }

  /**
   * Test the single-argument constructor with a valid base string.
   * Verifies it starts with length 1 permutation.
   */
  @Test
  public void testSingleArgumentConstructor() {
    Permutations perm = new Permutations("abcd");
    assertTrue(perm.hasNext());
    assertEquals("a", perm.next());
  }

  /**
   * Test the two-argument constructor
   * with a valid base string and starting length.
   * Verifies it starts with permutations of the specified length.
   */
  @Test
  public void testTwoArgumentConstructorValidStartLength() {
    Permutations perm = new Permutations("abcd", 3);
    assertTrue(perm.hasNext());
    assertEquals("abc", perm.next()); // Starts at the correct length
  }

  /**
   * Test that the two-argument constructor
   * throws IllegalArgumentException
   * when provided with an invalid start length
   * (greater than the base length).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTwoArgumentConstructorInvalidStartLengthTooLarge() {
    // Invalid start length (greater than string length)
    new Permutations("abcd", 5);
  }

  /**
   * Test that the two-argument constructor throws IllegalArgumentException
   * when provided with an invalid start length (less than 1).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTwoArgumentConstructorInvalidStartLengthTooSmall() {
    // Invalid start length (less than 1)
    new Permutations("abcd", 0);
  }

  /**
   * Test that the single-argument constructor
   * throws IllegalArgumentException
   * when provided with an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSingleArgumentConstructorEmptyString() {
    new Permutations(""); // Invalid empty string
  }

  /**
   * Test that the single-argument constructor
   * throws IllegalArgumentException
   * when provided with a null base string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSingleArgumentConstructorNullString() {
    new Permutations(null); // Invalid null string
  }

  /**
   * Test that the constructor throws IllegalArgumentException
   * when the base string contains non-alphabetic characters.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidCharacters() {
    new Permutations("ab1cd"); // Invalid character '1'
  }

  /**
   * Test all one-character permutations of "abcd".
   */
  @Test
  public void testOneCharacterPermutations() {
    assertTrue(iterator.hasNext());
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("c", iterator.next());
    assertEquals("d", iterator.next());
  }

  /**
   * Test all two-character permutations of "abcd".
   */
  @Test
  public void testTwoCharacterPermutations() {
    // Move iterator to length 2
    iterator = new Permutations("abcd", 2);
    assertTrue(iterator.hasNext());
    assertEquals("ab", iterator.next());
    assertEquals("ac", iterator.next());
    assertEquals("ad", iterator.next());
    assertEquals("bc", iterator.next());
    assertEquals("bd", iterator.next());
    assertEquals("cd", iterator.next());
  }

  /**
   * Test all three-character permutations of "abcd".
   */
  @Test
  public void testThreeCharacterPermutations() {
    // Move iterator to length 3
    iterator = new Permutations("abcd", 3);
    assertTrue(iterator.hasNext());
    assertEquals("abc", iterator.next());
    assertEquals("abd", iterator.next());
    assertEquals("acd", iterator.next());
    assertEquals("bcd", iterator.next());
    assertFalse(iterator.hasNext());
  }

  /**
   * Test the only four-character permutation of "abcd" in source order.
   */
  @Test
  public void testFourCharacterPermutations() {
    iterator = new Permutations("abcd", 4);


    assertEquals("abcd", iterator.next());

    // Ensure there are no more permutations left
    assertFalse("Expected no more permutations after 'abcd'",
            iterator.hasNext());

    // Verify exception if `next()` is called after the last permutation
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  /**
   * Test that hasNext() returns false at the end of the permutations
   * and next() throws a NoSuchElementException
   * when no more permutations are available.
   */
  @Test(expected = NoSuchElementException.class)
  public void testNextThrowsExceptionAtEnd() {
    iterator = new Permutations("a");
    iterator.next(); // "a"
    assertFalse(iterator.hasNext());
    iterator.next(); // should throw NoSuchElementException
  }

  /**
   * Test to verify previous() and hasPrevious()
   * functionality for two-argument constructor.
   * Ensures correct reverse traversal.
   */
  @Test
  public void testPreviousWithTwoArgumentConstructor() {
    iterator = new Permutations("abcd", 2);
    iterator.next(); // "ab"
    iterator.next(); // "ac"
    iterator.next(); // "ad"
    assertTrue(iterator.hasPrevious());
    assertEquals("ad", iterator.previous());
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
  }

  /**
   * Test to verify that hasPrevious() returns false
   * at the beginning of the permutations
   * and previous() throws a NoSuchElementException
   * when there are no prior permutations.
   */
  @Test(expected = NoSuchElementException.class)
  public void testPreviousThrowsExceptionAtBeginning() {
    iterator = new Permutations("abc", 2);
    iterator.previous(); // should throw NoSuchElementException
  }

  /**
   * Tests the forward traversal using `next()` on a sequence
   * of permutations with starting length 3 for "abcd".
   */
  @Test
  public void testNextWithAssertions() {
    iterator = new Permutations("abcd", 3);

    // Verify forward traversal for "abcd" starting with length 3
    assertTrue(iterator.hasNext());
    assertEquals("abc", iterator.next());

    assertTrue(iterator.hasNext());
    assertEquals("abd", iterator.next());

    assertTrue(iterator.hasNext());
    assertEquals("acd", iterator.next());

    assertTrue(iterator.hasNext());
    assertEquals("bcd", iterator.next());

    // Confirm end of sequence after expected permutations
    assertFalse("Expected no more permutations", iterator.hasNext());

    // Verify exception is thrown if `next()` is called at the end
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  /**
   * Tests the backward traversal using `previous()` on a sequence
   * of permutations with starting length 3 for "abcd".
   */
  @Test
  public void testPreviousWithAssertions() {
    iterator = new Permutations("abcd", 3);

    // Move forward through all elements first to reach the end of the sequence
    while (iterator.hasNext()) {
      iterator.next();
    }

    // Verify backward traversal for length 3 permutations
    assertTrue(iterator.hasPrevious());
    assertEquals("bcd", iterator.previous());

    assertTrue(iterator.hasPrevious());
    assertEquals("acd", iterator.previous());

    assertTrue(iterator.hasPrevious());
    assertEquals("abd", iterator.previous());

    assertTrue(iterator.hasPrevious());
    assertEquals("abc", iterator.previous());

    // Ensure no more elements in backward traversal
    assertFalse("Expected no previous permutations",
            iterator.hasPrevious());

    // Verify exception is thrown if `previous()` is called at the beginning
    assertThrows(NoSuchElementException.class, iterator::previous);
  }

  /**
   * Tests the next() and previous() methods for the two-argument constructor.
   * Verifies forward traversal through permutations,
   * followed by backward traversal.
   * It checks if the permutations are generated in the correct order
   * when traversing forward and whether the `previous()` method works
   * as expected for backward traversal.
   */
  @Test
  public void testNextAndPreviousForTwoArgumentConstructor() {
    iterator = new Permutations("abcd", 2);
    assertEquals("ab", iterator.next());
    assertEquals("ac", iterator.next());
    assertEquals("ad", iterator.next());

    // Test moving backward
    assertTrue(iterator.hasPrevious());
    assertEquals("ad", iterator.previous());
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
    assertFalse(iterator.hasPrevious());
  }

  /**
   * Tests that permutations are traversed correctly in forward order.
   * Verifies that the first set of permutations are in lexicographical order,
   * and the second set of permutations starts at length 2.
   */
  @Test
  public void testPermutationsInForwardOrder() {
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("c", iterator.next());
    assertEquals("d", iterator.next());
    iterator = new Permutations("abcd", 2); // move to length 2
    assertTrue(iterator.hasNext());
    assertEquals("ab", iterator.next());
  }

  /**
   * Tests backward traversal
   * when starting from the middle of the permutations list.
   * Moves forward through the first two permutations
   * and then moves backward to verify
   * that the `previous()` method works as expected.
   */
  @Test
  public void testBackwardTraversalFromMiddle() {
    iterator = new Permutations("abc", 2);
    iterator.next(); // "ab"
    iterator.next(); // "ac"
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
  }

  /**
   * Verifies that forward and backward traversal
   * can be performed sequentially.
   * After moving forward to the second permutation,
   * it checks if moving backward works correctly,
   * then verifies forward movement again.
   */
  @Test
  public void testGoBackAndForthBetweenPermutations() {
    iterator = new Permutations("abc", 2);
    assertEquals("ab", iterator.next());
    assertEquals("ac", iterator.next());
    assertEquals("ac", iterator.previous());
    assertEquals("ab", iterator.previous());
    assertEquals("ab", iterator.next());
  }

  /**
   * Test to verify IllegalArgumentException
   * is thrown for a two-argument constructor
   * with an invalid starting length
   * (greater than the length of the base string).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartLengthThrowsException() {
    new Permutations("abcd", 5); // invalid starting length
  }

  /**
   * Test to verify that equals() method correctly
   * identifies two equal Permutations objects.
   */
  @Test
  public void testEquals() {
    Permutations perm1 = new Permutations("abc", 2);
    Permutations perm2 = new Permutations("abc", 2);
    assertEquals(perm1, perm2);
  }

  /**
   * Test to verify that equals() method correctly
   * identifies two unequal Permutations objects.
   */
  @Test
  public void testNotEquals() {
    Permutations perm1 = new Permutations("abc", 2);
    Permutations perm2 = new Permutations("abcd", 2);
    assertNotEquals(perm1, perm2);
  }

  /**
   * Test to verify that hashCode() method
   * generates the same hash code for equal objects
   * and different hash codes for unequal objects.
   */
  @Test
  public void testHashCode() {
    Permutations perm1 = new Permutations("abc", 2);
    Permutations perm2 = new Permutations("abc", 2);
    assertEquals(perm1.hashCode(), perm2.hashCode());

    Permutations perm3 = new Permutations("abcd", 2);
    assertNotEquals(perm1.hashCode(), perm3.hashCode());
  }

  /**
   * Test to verify that toString() produces a correct,
   * readable representation of the object.
   */
  @Test
  public void testToString() {
    Permutations perm = new Permutations("abc", 2);
    assertEquals("Permutations(base='abc', currentLength=2)",
            perm.toString());
  }
}