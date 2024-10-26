import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import polynomial.Polynomial;
import polynomial.PolynomialImpl;

/**
 * Unit tests for PolynomialImpl class., covering various edge cases
 * such as valid and invalid constructors, addition of terms,
 * duplicate terms, unique power validation,
 * and general polynomial operations.
 */
public class PolynomialImplTest {

  private Polynomial emptyPoly;
  private Polynomial poly1;
  private Polynomial poly2;
  private Polynomial poly3;

  /**
   * Sets up PolynomialImpl instances for use in test cases.
   */
  @Before
  public void setUp() {
    emptyPoly = new PolynomialImpl();
    poly1 = new PolynomialImpl("4x^3 +3x^1 -5");
    poly2 = new PolynomialImpl("-2x^5 +3x^4 +11x^1 -5");
    poly3 = new PolynomialImpl("7");
  }

  /**
   * Test that an invalid polynomial string format throws an IllegalArgumentException.
   * This test specifically checks for invalid consecutive signs (e.g., "+-" or "-+").
   */
  @Test
  public void testInvalidPolynomialCreationByString() {
    String invalidPolyStr = "-3x^3 +-2x^5 -3 +12x^1";

    // Expect IllegalArgumentException due to invalid formatting
    assertThrows(IllegalArgumentException.class, () -> {
      Polynomial poly = new PolynomialImpl(invalidPolyStr);
    });
  }

  /**
   * Tests the default constructor to ensure it initializes as an empty polynomial.
   * Expected output: "0" for an empty polynomial.
   */
  @Test
  public void testDefaultConstructorCreatesEmptyPolynomial() {
    assertEquals("0", emptyPoly.toString());
  }

  /**
   * Tests the string constructor with an empty string.
   * Expected output: Initializes as an empty polynomial.
   */
  @Test
  public void testEmptyStringConstructor() {
    Polynomial poly = new PolynomialImpl("");
    assertEquals("0", poly.toString());
  }

  /**
   * Tests an empty polynomial's behavior.
   * Verifies its string representation is ""
   * Confirms getDegree() throws IllegalStateException
   */
  @Test
  public void testEmptyPolynomialThrowIllegalStateException() {
    Polynomial poly = new PolynomialImpl();
    assertEquals("0", poly.toString());
    assertThrows(IllegalStateException.class, poly::getDegree);
  }

  /**
   * Tests the single-term constructor.
   * Verifies that the string representation is "5x^3"
   * and the coefficient for power 3 is 5
   */
  @Test
  public void testSingleTermConstructor() {
    Polynomial poly = new PolynomialImpl("5x^3");
    assertEquals("5x^3", poly.toString());
    assertEquals(5, poly.getCoefficient(3));
  }

  /**
   * Tests the string constructor with a valid single constant term.
   * Expected output: Polynomial initialized to a constant.
   */
  @Test
  public void testSingleConstantTerm() {
    Polynomial poly = new PolynomialImpl("5");
    assertEquals("5", poly.toString());
  }

  /**
   * Tests the string constructor with a valid single variable term.
   * Expected output: Polynomial with a single x term, "x".
   */
  @Test
  public void testSingleVariableTerm() {
    Polynomial poly = new PolynomialImpl("x");
    assertEquals("x^1", poly.toString());
  }

  /**
   * Tests parsing of terms with positive, negative, and missing coefficients.
   * Expected output: Correctly formatted terms.
   */
  @Test
  public void testTermsWithDifferentCoefficients() {
    Polynomial poly = new PolynomialImpl("+3x^4 -2x^3 +4x -7");
    assertEquals("3x^4 -2x^3 +4x^1 -7", poly.toString());
  }

  /**
   * Tests the string constructor with multiple terms,
   * including zero coefficient.
   * Expected output: Zero coefficient terms are ignored.
   */
  @Test
  public void testZeroCoefficientTerms() {
    Polynomial poly = new PolynomialImpl("0x^5 +4x^3 -5");
    assertEquals("4x^3 -5", poly.toString());
  }

  /**
   * Tests the string constructor with invalid term (negative power).
   * Expected output: IllegalArgumentException thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativePowerThrowsException() {
    new PolynomialImpl("3x^-2");
  }

  /**
   * Tests the string constructor with invalid characters in term.
   * Expected output: IllegalArgumentException thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCharactersThrowsException() {
    new PolynomialImpl("4x^2 + abx +5");
  }

  /**
   * Tests parsing terms with both positive and negative powers in one input.
   * Expected output: IllegalArgumentException thrown due to invalid negative power.
   */
  @Test
  public void testMixedPositiveAndNegativePowers() {
    assertThrows(IllegalArgumentException
            .class, () -> new PolynomialImpl("3x^3 -2x^-2 +5"));
  }

  /**
   * Tests a polynomial string with terms of very large powers.
   * Expected output: Polynomial with terms of large powers in correct format.
   */
  @Test
  public void testLargePowerTerms() {
    Polynomial poly = new PolynomialImpl("2x^100 -3x^50 +5");
    assertEquals("2x^100 -3x^50 +5", poly.toString());
  }

  /**
   * Tests polynomial with terms in descending order of powers.
   * Expected output: Polynomial maintains the descending order in output.
   */
  @Test
  public void testDescendingOrderTerms() {
    Polynomial poly = new PolynomialImpl("5x^3 +3x^2 +2x +1");
    assertEquals("5x^3 +3x^2 +2x^1 +1", poly.toString());
  }

  /**
   * Tests polynomial with terms out of order in input.
   * Expected output: Polynomial terms ordered by descending powers in output.
   */
  @Test
  public void testOutOfOrderTerms() {
    Polynomial poly = new PolynomialImpl("2x^1 +4x^5 -3x^3 +5");
    assertEquals("4x^5 -3x^3 +2x^1 +5", poly.toString());
  }

  /**
   * Tests that the polynomial retains the order of terms in the input string.
   */
  @Test
  public void testTermOrderRetention() {
    Polynomial orderPoly = new PolynomialImpl("5 +4x -3x^2 +x^3 -2x^3");
    assertEquals("-x^3 -3x^2 +4x^1 +5", orderPoly.toString());
  }

  /**
   * Tests polynomial with a constant negative term as the first term.
   * Expected output: The negative sign is directly adjacent to the coefficient.
   */
  @Test
  public void testLeadingNegativeConstantTerm() {
    Polynomial poly = new PolynomialImpl("-7");
    assertEquals("-7", poly.toString());
  }

  /**
   * Tests polynomial with a negative variable term.
   * Expected output: Polynomial maintains negative sign with variable.
   */
  @Test
  public void testNegativeVariableTerm() {
    Polynomial poly = new PolynomialImpl("-x^3");
    assertEquals("-x^3", poly.toString());
  }

  /**
   * Tests both constructors: empty and parsing string input.
   */
  @Test
  public void testConstructors() {
    assertEquals("0", emptyPoly.toString());
    assertEquals("4x^3 +3x^1 -5", poly1.toString());
    assertEquals("-2x^5 +3x^4 +11x^1 -5", poly2.toString());
  }

  /**
   * Tests constructor validation to ensure an exception is thrown
   * for invalid input (negative power).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsException() {
    new PolynomialImpl("3x^-1 +2x^2");
  }

  /**
   * Tests the constructor with a valid polynomial string.
   */
  @Test
  public void testStringConstructorValidInput() {
    assertEquals("4x^3 +3x^1 -5", poly1.toString());
    assertEquals("-2x^5 +3x^4 +11x^1 -5", poly2.toString());
    assertEquals("7", poly3.toString());
  }

  /**
   * Tests that the constructor
   * throws an IllegalArgumentException for an invalid polynomial string.
   */
  @Test
  public void testInvalidInputThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      // Code that should throw the exception
      new PolynomialImpl("invalid input"); // Example invalid input
    });
  }

  /**
   * Tests general case where each term has a unique power.
   */
  @Test
  public void testGeneralCaseUniquePowers() {
    Polynomial uniquePowersPoly = new PolynomialImpl("4x^3 +3x^1 -5");
    assertEquals("4x^3 +3x^1 -5", uniquePowersPoly.toString());
  }

  /**
   * Tests parsing of a single term polynomial with a unique power.
   */
  @Test
  public void testSingleTermUniquePower() {
    Polynomial singlePowerPoly = new PolynomialImpl("6x^3");
    assertEquals("6x^3", singlePowerPoly.toString());
  }

  /**
   * Tests parsing of a polynomial where terms have the same power,
   * ensuring they are combined correctly.
   */
  @Test
  public void testDuplicateTermsSamePower() {
    Polynomial duplicatePowerPoly = new PolynomialImpl("3x^2 -4x^2 +5");
    assertEquals("-x^2 +5", duplicatePowerPoly.toString());
  }

  /**
   * Tests that the polynomial correctly handles cases with more than one term.
   */
  @Test
  public void testMultipleTermsInPoly() {
    Polynomial multiTermPoly = new PolynomialImpl("2x^3 -5x^1 +4");
    assertEquals("2x^3 -5x^1 +4", multiTermPoly.toString());
  }

  /**
   * Tests two different terms with the same power in the input.
   */
  @Test
  public void testTwoDifferentTermsSamePower() {
    Polynomial samePowerPoly = new PolynomialImpl("3x^2 -4x^2");
    assertEquals("-x^2", samePowerPoly.toString());
  }

  /**
   * Tests a complex polynomial with multiple terms including duplicate powers.
   */
  @Test
  public void testDuplicateTermsAndOrder() {
    Polynomial duplicatePoly = new PolynomialImpl("+3x^4 -2x^5 -5 -2x^4 +11x^1");
    assertEquals("-2x^5 +x^4 +11x^1 -5", duplicatePoly.toString());
  }

  /**
   * Ensures that duplicate terms are retained in the correct order based on input.
   */
  @Test
  public void testInputOrderRetentionWithSamePower() {
    Polynomial inputOrderPoly = new PolynomialImpl("+3x^4 -2x^5 -5 -2x^4 +11x^1");
    assertEquals("-2x^5 +x^4 +11x^1 -5", inputOrderPoly.toString());
  }

  /**
   * Tests polynomial addition.
   * Verifies that adding "3x^2 +5x^1 -2" and "2x^2 +3" results in the correct
   * combined polynomial "5x^2 +5x^1 +1".
   */
  @Test
  public void testPolynomialAddition() {
    Polynomial poly1 = new PolynomialImpl("3x^2 +5x^1 -2");
    Polynomial poly2 = new PolynomialImpl("2x^2 +3");
    Polynomial result = poly1.add(poly2);
    assertEquals("5x^2 +5x^1 +1", result.toString());
  }

  /**
   * Tests edge case for adding polynomials with zero coefficients and high powers.
   */
  @Test
  public void testAddHighPowerAndZeroCoefficient() {
    Polynomial highPowerPoly = new PolynomialImpl("100x^100");
    Polynomial zeroPoly = new PolynomialImpl("0x^100");
    Polynomial result = highPowerPoly.add(zeroPoly);
    assertEquals("100x^100", result.toString());
  }

  /**
   * Tests adding an empty polynomial to another polynomial.
   */
  @Test
  public void testAddEmptyPolynomial() {
    Polynomial result = poly1.add(emptyPoly);
    assertEquals(poly1.toString(), result.toString());
  }

  /**
   * Tests adding a polynomial with only zero terms.
   */
  @Test
  public void testAddZeroOnlyPolynomial() {
    Polynomial zeroOnlyPoly = new PolynomialImpl("0x^2 +0x +0");
    Polynomial result = poly1.add(zeroOnlyPoly);
    assertEquals(poly1.toString(), result.toString());
  }

  /**
   * Tests that adding a zero coefficient term does not affect the polynomial.
   */
  @Test
  public void testAddZeroCoefficientTerm() {
    poly1.addTerm(0, 3);
    assertEquals("4x^3 +3x^1 -5", poly1.toString());
  }

  /**
   * Test addition of polynomials with similar and different powers.
   */
  @Test
  public void testAddPolynomialsWithLikePowers() {
    Polynomial poly1 = new PolynomialImpl("3x^2 -4x +7");
    Polynomial poly2 = new PolynomialImpl("5x^2 +3x -3");
    Polynomial result = poly1.add(poly2);
    assertEquals("8x^2 -x^1 +4", result.toString());
  }

  /**
   * Tests adding two polynomials with different powers.
   */
  @Test
  public void testAddPolynomialsDifferentPowers() {
    Polynomial sumPoly = poly1.add(poly2);
    assertEquals("-2x^5 +3x^4 +4x^3 +14x^1 -10", sumPoly.toString());
  }

  /**
   * Tests adding two polynomials with some terms having the same power.
   */
  @Test
  public void testAddPolynomialsSamePowers() {
    Polynomial polySamePowers = new PolynomialImpl("3x^1 +4");
    Polynomial resultPoly = poly1.add(polySamePowers);
    assertEquals("4x^3 +6x^1 -1", resultPoly.toString());
  }

  /**
   * Tests the add method by adding terms with different powers
   * and terms with the same power.
   */
  @Test
  public void testAddMethodDifferentAndSamePowers() {
    Polynomial sumPoly = poly1.add(poly2);
    assertEquals("-2x^5 +3x^4 +4x^3 +14x^1 -10", sumPoly.toString());

    Polynomial samePowerPoly1 = new PolynomialImpl("3x^2");
    Polynomial samePowerPoly2 = new PolynomialImpl("4x^2");
    Polynomial sumSamePower = samePowerPoly1.add(samePowerPoly2);
    assertEquals("7x^2", sumSamePower.toString());
  }

  /**
   * Tests adding an empty polynomial to another polynomial.
   * Expected result: The original polynomial is returned unchanged.
   */
  @Test
  public void testAddEmptyPolynomialToNonEmpty() {
    Polynomial result = poly1.add(emptyPoly);
    assertEquals("4x^3 +3x^1 -5", result.toString());
  }

  /**
   * Tests adding a polynomial to an empty polynomial.
   * Expected result: The non-empty polynomial is returned unchanged.
   */
  @Test
  public void testAddNonEmptyPolynomialToEmpty() {
    Polynomial result = emptyPoly.add(poly1);
    assertEquals("4x^3 +3x^1 -5", result.toString());
  }

  /**
   * Tests adding two empty polynomials.
   * Expected result: An empty polynomial "0".
   */
  @Test
  public void testAddTwoEmptyPolynomials() {
    Polynomial result = emptyPoly.add(emptyPoly);
    assertEquals("0", result.toString());
  }

  /**
   * Tests adding two polynomials with the same powers.
   * Expected result: The coefficients of matching powers are summed.
   */
  @Test
  public void testAddPolynomialsWithSamePowers() {
    Polynomial polyWithSamePowers = new PolynomialImpl("3x^3 -5x^1 +7");
    Polynomial result = poly1.add(polyWithSamePowers);
    assertEquals("7x^3 -2x^1 +2", result.toString());
  }

  /**
   * Tests adding polynomials with terms of different powers.
   * Expected result: The resulting polynomial contains terms
   * from both polynomials in descending order of powers.
   */
  @Test
  public void testAddPolynomialsWithDifferentPowers() {
    Polynomial result = poly1.add(poly2);
    assertEquals("-2x^5 +3x^4 +4x^3 +14x^1 -10", result.toString());
  }

  /**
   * Tests adding polynomials with a mix of matching and non-matching powers.
   * Expected result: Terms with the same power are combined, others are retained.
   */
  @Test
  public void testAddPolynomialsWithMixedPowers() {
    Polynomial result = poly2.add(poly3);
    assertEquals("-2x^5 +3x^4 +11x^1 +2", result.toString());
  }

  /**
   * Tests adding a polynomial that results in zero coefficients for some terms.
   * Expected result: Terms with a zero coefficient are omitted from the result.
   */
  @Test
  public void testAddPolynomialsResultingInZeroCoefficients() {
    Polynomial poly = new PolynomialImpl("-4x^3 -3x +5");
    Polynomial result = poly1.add(poly);
    assertEquals("0", result.toString());
  }

  /**
   * Tests adding polynomials with high powers to ensure correct order.
   * Expected result: All terms are correctly ordered in descending powers.
   */
  @Test
  public void testAddPolynomialsWithHighPowers() {
    Polynomial highPowerPoly = new PolynomialImpl("10x^10 +4x^3 -5x");
    Polynomial result = poly1.add(highPowerPoly);
    assertEquals("10x^10 +8x^3 -2x^1 -5", result.toString());
  }

  /**
   * Tests adding a polynomial with zero coefficients to
   * ensure it does not affect the result.
   * Expected result: The polynomial remains unchanged.
   */
  @Test
  public void testAddPolynomialWithZeroCoefficientTerms() {
    Polynomial zeroCoeffPoly = new PolynomialImpl("0x^5 +0x^3 +0x");
    Polynomial result = poly1.add(zeroCoeffPoly);
    assertEquals("4x^3 +3x^1 -5", result.toString());
  }

  /**
   * Tests adding a polynomial with negative coefficients and varying powers.
   * Expected result: Negative coefficients are correctly summed with matching terms.
   */
  @Test
  public void testAddPolynomialWithNegativeCoefficients() {
    Polynomial negCoeffPoly = new PolynomialImpl("-10x^3 -5x^2 +2x -7");
    Polynomial result = poly1.add(negCoeffPoly);
    assertEquals("-6x^3 -5x^2 +5x^1 -12", result.toString());
  }


  /**
   * Tests adding two polynomials
   * where terms of opposite coefficients result in zero for some terms.
   * Expected result: Terms that sum to zero are omitted.
   */
  @Test
  public void testAddPolynomialsWithOppositeCoefficients() {
    Polynomial oppositeCoeffPoly = new PolynomialImpl("-4x^3 -3x +5");
    Polynomial result = poly1.add(oppositeCoeffPoly);
    assertEquals("0", result.toString());
  }

  /**
   * Tests adding a polynomial with only a constant term to another polynomial.
   * Expected result: The constant term is added to the polynomial as a standalone term.
   */
  @Test
  public void testAddConstantOnlyPolynomial() {
    Polynomial constantPoly = new PolynomialImpl("7");
    Polynomial result = poly1.add(constantPoly);
    assertEquals("4x^3 +3x^1 +2", result.toString());
  }


  /**
   * Tests that adding terms with negative powers in the addTerm method
   * throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTermNegativePowerThrowsException() {
    poly1.addTerm(3, -1);
  }

  /**
   * Tests adding a term with a positive power to an empty polynomial.
   * Expected result: The polynomial represents only the added term.
   */
  @Test
  public void testAddTermToEmptyPolynomial() {
    emptyPoly.addTerm(3, 2);
    assertEquals("3x^2", emptyPoly.toString());
  }


  /**
   * Tests adding a term with an existing power to a polynomial.
   * Expected result: The coefficients of terms with matching powers are combined.
   */
  @Test
  public void testAddTermWithExistingPower() {
    poly1.addTerm(2, 3); // Adds 2x^3 to an existing 4x^3
    assertEquals("6x^3 +3x^1 -5", poly1.toString());
  }

  /**
   * Tests adding a term with a new, higher power to a polynomial.
   * Expected result: The new term is added in the correct order by descending powers.
   */
  @Test
  public void testAddTermWithNewHigherPower() {
    poly1.addTerm(1, 6); // Adds 1x^6
    assertEquals("x^6 +4x^3 +3x^1 -5", poly1.toString());
  }

  /**
   * Tests adding a term with a new, lower power than any existing term.
   * Expected result: The new term is appended to the polynomial in the correct order.
   */
  @Test
  public void testAddTermWithNewLowerPower() {
    poly1.addTerm(8, 0); // Adds constant term 8
    assertEquals("4x^3 +3x^1 +3", poly1.toString());
  }

  /**
   * Tests adding a negative coefficient term to an empty polynomial.
   * Expected result: The polynomial contains the negative term as the only term.
   */
  @Test
  public void testAddNegativeCoefficientTermToEmpty() {
    emptyPoly.addTerm(-5, 2);
    assertEquals("-5x^2", emptyPoly.toString());
  }

  /**
   * Tests adding a term with a new power to a polynomial containing a constant.
   * Expected result: The new term is added with its respective power.
   */
  @Test
  public void testAddTermWithNewPowerToConstantPolynomial() {
    poly3.addTerm(4, 1); // Adds 4x^1 to constant term 7
    assertEquals("4x^1 +7", poly3.toString());
  }

  /**
   * Tests adding a term that cancels out an existing term with the same power.
   * Expected result: The resulting polynomial
   * does not contain the term after cancellation.
   */
  @Test
  public void testAddTermCancelsExistingTerm() {
    // Adds 2x^5 to -2x^5, resulting in zero coefficient for x^5
    poly2.addTerm(2, 5);
    assertEquals("3x^4 +11x^1 -5", poly2.toString());
  }

  /**
   * Tests adding multiple terms in succession to an empty polynomial.
   * Expected result: The polynomial reflects all added terms
   * in correct descending order.
   */
  @Test
  public void testAddMultipleTermsToEmptyPolynomial() {
    emptyPoly.addTerm(2, 4);
    emptyPoly.addTerm(-3, 3);
    emptyPoly.addTerm(5, 2);
    assertEquals("2x^4 -3x^3 +5x^2", emptyPoly.toString());
  }

  /**
   * Tests adding a term to a polynomial
   * that has a similar term with an opposite coefficient.
   * Expected result: The term with opposite coefficients is omitted.
   */
  @Test
  public void testAddTermWithOppositeCoefficient() {
    // Adds -4x^3 to 4x^3, resulting in zero coefficient for x^3
    poly1.addTerm(-4, 3);
    assertEquals("3x^1 -5", poly1.toString());
  }

  /**
   * Tests adding a high-power term to a polynomial with significantly lower powers.
   * Expected result: The high-power term is correctly placed as the leading term.
   */
  @Test
  public void testAddHighPowerTerm() {
    poly1.addTerm(6, 10); // Adds 6x^10
    assertEquals("6x^10 +4x^3 +3x^1 -5", poly1.toString());
  }

  /**
   * Tests adding a term that results in zero for the entire polynomial.
   * Expected result: The polynomial is reduced to "0".
   */
  @Test
  public void testAddTermResultsInZeroPolynomial() {
    Polynomial poly = new PolynomialImpl("5x^2");
    // Adds -5x^2 to 5x^2, resulting in zero polynomial
    poly.addTerm(-5, 2);
    assertEquals("0", poly.toString());
  }

  @Test
  public void testIsSame() {
    Polynomial poly4 = new PolynomialImpl("-5 +4x^3 +3x^1");
    assertTrue(poly1.isSame(poly4));
  }

  /**
   * Tests the isSame method by comparing polynomials with different powers, same powers,
   * and invalid cases.
   */
  @Test
  public void testIsSameMethodWithDifferentAndSamePowers() {
    Polynomial samePoly1 = new PolynomialImpl("3x^2 +5");
    Polynomial samePoly2 = new PolynomialImpl("3x^2 +5");
    assertTrue(samePoly1.isSame(samePoly2));

    Polynomial diffPoly = new PolynomialImpl("3x^2 +4");
    assertFalse(samePoly1.isSame(diffPoly));
  }

  /**
   * Tests that the isSame method returns true
   * for polynomials with identical terms and structure.
   */
  @Test
  public void testIsSamePolynomialsIdentical() {
    Polynomial samePoly = new PolynomialImpl("4x^3 +3x^1 -5");
    assertTrue(poly1.isSame(samePoly));
  }

  /**
   * Tests that the isSame method returns false for polynomials with different terms.
   */
  @Test
  public void testIsSamePolynomialsDifferent() {
    Polynomial diffPoly = new PolynomialImpl("4x^3 +2x^1 -5");
    assertFalse(poly1.isSame(diffPoly));
  }

  /**
   * Tests that isSame returns true
   * for comparing an empty polynomial to another empty polynomial.
   */
  @Test
  public void testIsSameWithTwoEmptyPolynomials() {
    Polynomial anotherEmptyPoly = new PolynomialImpl();
    assertTrue(emptyPoly.isSame(anotherEmptyPoly));
  }

  /**
   * Tests that isSame returns false
   * when comparing an empty polynomial to a non-empty polynomial.
   */
  @Test
  public void testIsSameEmptyAndNonEmptyPolynomial() {
    assertFalse(emptyPoly.isSame(poly1));
  }

  /**
   * Tests that isSame returns true for
   * polynomials with identical terms and structure.
   */
  @Test
  public void testIsSameIdenticalPolynomials() {
    Polynomial identicalPoly = new PolynomialImpl("4x^3 +3x^1 -5");
    assertTrue(poly1.isSame(identicalPoly));
  }

  /**
   * Tests that isSame returns false
   * for polynomials with the same terms in a different order.
   */
  @Test
  public void testIsSamePolynomialsDifferentOrder() {
    Polynomial differentOrderPoly = new PolynomialImpl("3x^1 -5 +4x^3");
    assertTrue(poly1.isSame(differentOrderPoly));
  }

  /**
   * Tests that isSame returns false for polynomials
   * with the same terms but different coefficients.
   */
  @Test
  public void testIsSamePolynomialsDifferentCoefficients() {
    Polynomial differentCoefficientsPoly = new PolynomialImpl("5x^3 +3x^1 -5");
    assertFalse(poly1.isSame(differentCoefficientsPoly));
  }

  /**
   * Tests that isSame returns true for polynomials
   * with different constant terms only.
   */
  @Test
  public void testIsSamePolynomialsDifferentConstants() {
    Polynomial differentConstantPoly = new PolynomialImpl("4x^3 +3x^1 -7");
    assertFalse(poly1.isSame(differentConstantPoly));
  }

  /**
   * Tests that isSame returns true
   * when comparing two polynomials with only constant terms.
   */
  @Test
  public void testIsSamePolynomialsOnlyConstantTerms() {
    Polynomial constantPoly1 = new PolynomialImpl("5");
    Polynomial constantPoly2 = new PolynomialImpl("5");
    assertTrue(constantPoly1.isSame(constantPoly2));
  }

  /**
   * Tests that isSame returns false when comparing two polynomials
   * with only constants but different values.
   */
  @Test
  public void testIsSamePolynomialsDifferentConstantValues() {
    Polynomial constantPoly1 = new PolynomialImpl("5");
    Polynomial constantPoly2 = new PolynomialImpl("7");
    assertFalse(constantPoly1.isSame(constantPoly2));
  }

  /**
   * Tests that isSame returns false for polynomials with the same terms,
   * where one has an extra zero coefficient term.
   */
  @Test
  public void testIsSameWithExtraZeroCoefficientTerm() {
    Polynomial polyWithZeroTerm = new PolynomialImpl("4x^3 +3x^1 -5 +0x^2");
    assertTrue(poly1.isSame(polyWithZeroTerm));
  }

  /**
   * Tests that isSame returns true for two polynomials
   * with the same terms but one represented by more terms.
   * Checks the ability to handle equivalent polynomials of
   * varying structural representations.
   */
  @Test
  public void testIsSameWithDifferentStructureSameResult() {
    Polynomial equivalentPoly = new PolynomialImpl("4x^3 +0x^2 +3x^1 -5");
    assertTrue(poly1.isSame(equivalentPoly));
  }

  /**
   * Tests that isSame returns false when comparing polynomials
   * with identical terms but an incompatible polynomial type.
   */
  @Test
  public void testIsSameWithIncompatiblePolynomialType() {
    Polynomial incompatiblePoly = new Polynomial() {
      @Override
      public Polynomial add(Polynomial poly) {
        return null;
      }

      @Override
      public void addTerm(int coefficient, int power) {}

      @Override
      public boolean isSame(Polynomial poly) {
        return false;
      }

      @Override
      public double evaluate(double x) {
        return 0;
      }

      @Override
      public int getCoefficient(int power) {
        return 0;
      }

      @Override
      public int getDegree() {
        return 0;
      }

      @Override
      public String toString() {
        return "4x^3 +3x -5";
      }
    };
    assertFalse(poly1.isSame(incompatiblePoly));
  }

  /**
   * Tests the evaluate method,
   * checking the value of the polynomial at specific values of x.
   */
  @Test
  public void testEvaluate() {
    // Evaluates 4(2^3) + 3(2) - 5 = 33
    assertEquals(33.0, poly1.evaluate(2), 0.001);
    // Constant polynomial, always returns 7
    assertEquals(7.0, poly3.evaluate(3), 0.001);
    // Empty polynomial, evaluates to 0
    assertEquals(0.0, emptyPoly.evaluate(3), 0.001);
  }

  /**
   * Tests evaluating an empty polynomial to ensure it returns 0 for any value of x.
   */
  @Test
  public void testEvaluateEmptyPolynomial() {
    assertEquals(0.0, emptyPoly.evaluate(1), 0.001);
    assertEquals(0.0, emptyPoly.evaluate(-10), 0.001);
    assertEquals(0.0, emptyPoly.evaluate(0), 0.001);
  }

  /**
   * Tests evaluating a constant polynomial,
   * which should return the constant value for any x.
   */
  @Test
  public void testEvaluateConstantPolynomial() {
    assertEquals(7.0, poly3.evaluate(10), 0.001);
    assertEquals(7.0, poly3.evaluate(-100), 0.001);
    assertEquals(7.0, poly3.evaluate(0), 0.001);
  }

  /**
   * Tests evaluating a polynomial
   * with both positive and negative terms at various x values.
   */
  @Test
  public void testEvaluatePositiveAndNegativeTerms() {
    // Evaluates 4(2^3) + 3(2) - 5 = 33
    assertEquals(33.0, poly1.evaluate(2), 0.001);

    // Evaluates -2(1^5) + 3(1^4) + 11(1) - 5 = 7
    assertEquals(7.0, poly2.evaluate(1), 0.001);

    // Evaluates -2(-1^5) + 3(-1^4) + 11(-1) - 5 = -11
    assertEquals(-11.0, poly2.evaluate(-1), 0.001);
  }

  /**
   * Tests evaluation at x = 0, ensuring all terms except constants are nullified.
   */
  @Test
  public void testEvaluateAtZero() {
    assertEquals(-5.0, poly1.evaluate(0), 0.001);
    assertEquals(-5.0, poly2.evaluate(0), 0.001);
    assertEquals(7.0, poly3.evaluate(0), 0.001);
  }

  /**
   * Tests evaluating a polynomial with only high-degree terms at specific values of x.
   */
  @Test
  public void testEvaluateHighDegreePolynomial() {
    Polynomial highDegreePoly
            = new PolynomialImpl("100x^100 -x^50 +3x^10 -5");
    assertEquals(-5.0, highDegreePoly.evaluate(0), 0.001);
    assertEquals(97.0, highDegreePoly.evaluate(1), 0.001);
  }

  /**
   * Tests evaluating polynomials
   * with both positive and negative terms at fractional values of x.
   */
  @Test
  public void testEvaluateWithFractionalValues() {
    // Evaluates 4(0.5^3) + 3(0.5) - 5
    assertEquals(-3, poly1.evaluate(0.5), 0.001);
    // Evaluates -2(0.5^5) + 3(0.5^4) + 11(0.5) - 5
    assertEquals(0.625, poly2.evaluate(0.5), 0.001);
  }

  /**
   * Tests evaluation at negative fractional values of x,
   * to ensure correct behavior with sign changes.
   */
  @Test
  public void testEvaluateNegativeFractionalValues() {
    // Evaluates 4(-0.5^3) + 3(-0.5) - 5
    assertEquals(-7.0, poly1.evaluate(-0.5), 0.001);
    // Evaluates -2(-0.5^5) + 3(-0.5^4) + 11(-0.5) - 5
    assertEquals(-10.25, poly2.evaluate(-0.5), 0.001);
  }

  /**
   * Tests the getCoefficient method for terms with various powers.
   */
  @Test
  public void testGetCoefficient() {
    assertEquals(4, poly1.getCoefficient(3));
    assertEquals(3, poly1.getCoefficient(1));
    assertEquals(-5, poly1.getCoefficient(0));
    // Term with power 2 does not exist
    assertEquals(0, poly1.getCoefficient(2));
  }

  /**
   * Tests getCoefficient on an empty polynomial.
   * Since there are no terms, all coefficients should return 0.
   */
  @Test
  public void testGetCoefficientEmptyPolynomial() {
    assertEquals(0, emptyPoly.getCoefficient(0));
    assertEquals(0, emptyPoly.getCoefficient(1));
    assertEquals(0, emptyPoly.getCoefficient(10));
  }

  /**
   * Tests getCoefficient for a polynomial with only a constant term.
   * Checks that the coefficient for power 0 is the constant value,
   * and others are 0.
   */
  @Test
  public void testGetCoefficientConstantPolynomial() {
    assertEquals(7, poly3.getCoefficient(0)); // Constant term
    assertEquals(0, poly3.getCoefficient(1)); // No term with power 1
    assertEquals(0, poly3.getCoefficient(5)); // No term with power 5
  }

  /**
   * Tests getCoefficient for terms with positive powers.
   * Ensures correct coefficients are returned for existing terms.
   */
  @Test
  public void testGetCoefficientPositivePowers() {
    assertEquals(4, poly1.getCoefficient(3));  // Term 4x^3
    assertEquals(3, poly1.getCoefficient(1));  // Term 3x^1
    assertEquals(-5, poly1.getCoefficient(0)); // Constant term -5
  }

  /**
   * Tests getCoefficient for terms with high and low powers.
   * Ensures correct coefficients are returned for terms with high degrees.
   */
  @Test
  public void testGetCoefficientHighAndLowPowers() {
    assertEquals(-2, poly2.getCoefficient(5)); // Term -2x^5
    assertEquals(3, poly2.getCoefficient(4));  // Term 3x^4
    assertEquals(11, poly2.getCoefficient(1)); // Term 11x^1
  }

  /**
   * Tests getCoefficient for non-existent powers to ensure it returns 0.
   */
  @Test
  public void testGetCoefficientNonExistentPower() {
    assertEquals(0, poly1.getCoefficient(2));
    assertEquals(0, poly2.getCoefficient(3));
    assertEquals(0, poly1.getCoefficient(10));
  }

  /**
   * Tests getCoefficient for terms with mixed positive and negative coefficients.
   */
  @Test
  public void testGetCoefficientMixedCoefficients() {
    Polynomial mixedPoly = new PolynomialImpl("5x^4 -3x^2 +6x -9");
    assertEquals(5, mixedPoly.getCoefficient(4));
    assertEquals(-3, mixedPoly.getCoefficient(2));
    assertEquals(6, mixedPoly.getCoefficient(1));
    assertEquals(-9, mixedPoly.getCoefficient(0));
  }

  /**
   * Tests getCoefficient for terms with maximum and minimum possible power values.
   */
  @Test
  public void testGetCoefficientBoundaryPowerValues() {
    Polynomial boundaryPoly = new PolynomialImpl("9999x^100 -9999x^50");
    assertEquals(9999, boundaryPoly.getCoefficient(100));
    assertEquals(-9999, boundaryPoly.getCoefficient(50));
    assertEquals(0, boundaryPoly.getCoefficient(101));
    assertEquals(0, boundaryPoly.getCoefficient(0));
  }

  /**
   * Tests getCoefficient with negative powers
   * to ensure IllegalArgumentException is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCoefficientNegativePowerThrowsException() {
    Polynomial poly = new PolynomialImpl("4x^3 +3x^-1 -5");
    poly.getCoefficient(3);  // Negative power should throw an exception
  }

  /**
   * Tests the getDegree method,
   * ensuring it returns the highest power of the polynomial.
   */
  @Test
  public void testGetDegree() {
    assertEquals(3, poly1.getDegree());
    assertEquals(5, poly2.getDegree());
    //poly3 represents a constant (7x^0), so degree should be 0
    assertEquals(0, poly3.getDegree());
  }

  /**
   * Tests that calling getDegree on an empty polynomial
   * Throws an IllegalStateException with the expected message.
   */
  @Test
  public void testGetDegreeEmptyPolynomialThrowsException() {
    try {
      emptyPoly.getDegree();
      fail("Expected an IllegalStateException to be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Degree of an empty polynomial is undefined.", e.getMessage());
    }
  }

  /**
   * Tests getDegree on a constant polynomial with a single term.
   * The degree of a constant polynomial should be 0.
   */
  @Test
  public void testGetDegreeConstantPolynomial() {
    assertEquals(0, poly3.getDegree());
  }

  /**
   * Tests getDegree for a polynomial with terms of varying degrees.
   * Ensures the method returns the degree of the highest power term.
   */
  @Test
  public void testGetDegreePolynomialWithTerms() {
    assertEquals(3, poly1.getDegree()); // Highest degree is 3
    assertEquals(5, poly2.getDegree()); // Highest degree is 5
  }

  /**
   * Tests getDegree for a polynomial with only one term of high degree.
   * Ensures that the degree returned is the power of the single term.
   */
  @Test
  public void testGetDegreeSingleHighDegreeTerm() {
    Polynomial highDegreePoly = new PolynomialImpl("9x^10");
    assertEquals(10, highDegreePoly.getDegree());
  }

  /**
   * Tests getDegree for a polynomial
   * with mixed positive and negative coefficients.
   * Ensures the highest power is correctly identified despite coefficient signs.
   */
  @Test
  public void testGetDegreeMixedCoefficients() {
    Polynomial mixedPoly = new PolynomialImpl("-5x^7 +4x^3 -2x^1 +6");
    assertEquals(7, mixedPoly.getDegree());
  }

  /**
   * Tests getDegree for a polynomial
   * with terms in descending order of powers.
   * Ensures that the highest degree
   * is correctly returned as the first term's power.
   */
  @Test
  public void testGetDegreeDescendingOrder() {
    Polynomial descendingPoly = new PolynomialImpl("8x^6 -3x^4 +2x -7");
    assertEquals(6, descendingPoly.getDegree());
  }

  /**
   * Tests getDegree for a polynomial with terms in random order.
   * Ensures that the method identifies the correct highest degree term.
   */
  @Test
  public void testGetDegreeRandomOrderTerms() {
    Polynomial randomOrderPoly = new PolynomialImpl("3x^2 -7x^8 +5x^1 +9x^5");
    assertEquals(8, randomOrderPoly.getDegree());
  }

  /**
   * Tests getDegree for a polynomial with duplicate powers.
   * Ensures the method correctly identifies the highest degree.
   */
  @Test
  public void testGetDegreeWithDuplicatePowers() {
    Polynomial duplicatePowerPoly
            = new PolynomialImpl("4x^3 +5x^3 -6x^2 +x -5");
    assertEquals(3, duplicatePowerPoly.getDegree());
  }

  /**
   * Tests getDegree on a polynomial
   * with very high and low terms to ensure it returns
   * the highest degree regardless of term order or spacing.
   */
  @Test
  public void testGetDegreeExtremeHighAndLowTerms() {
    Polynomial extremePoly
            = new PolynomialImpl("9999x^100 +5x^0 -8x^1 +12x^3 -2x^99");
    assertEquals(100, extremePoly.getDegree());
  }


  /**
   * Tests the toString method to ensure it formats polynomials correctly.
   */
  @Test
  public void testToStringMethod() {
    assertEquals("4x^3 +3x^1 -5", poly1.toString());
    assertEquals("-2x^5 +3x^4 +11x^1 -5", poly2.toString());
    assertEquals("7", poly3.toString());
    assertEquals("0", emptyPoly.toString());
  }

  /**
   * Test toString for ensuring correct sign handling.
   */
  @Test
  public void testToStringSignHandling() {
    Polynomial poly = new PolynomialImpl("2x^3 -5x^2 +4");
    assertEquals("2x^3 -5x^2 +4", poly.toString());
  }

  /**
   * Test toString for consecutive signs output.
   */
  @Test
  public void testToStringConsecutiveSigns() {
    Polynomial poly = new PolynomialImpl("-2x^5 +3x^4 +11x^1 -5");
    assertEquals("-2x^5 +3x^4 +11x^1 -5", poly.toString());
  }

  /**
   * Tests the toString method on an empty polynomial.
   * Ensures the result is "0" when no terms are present.
   */
  @Test
  public void testToStringEmptyPolynomial() {
    assertEquals("0", emptyPoly.toString());
  }

  /**
   * Tests the toString method for a constant polynomial with a single term.
   * Verifies the correct display of a constant term.
   */
  @Test
  public void testToStringConstantPolynomial() {
    assertEquals("7", poly3.toString());
  }

  /**
   * Tests the toString method for a polynomial
   * with positive and negative coefficients.
   * Ensures that terms are displayed
   * in descending order of powers, with correct spacing.
   */
  @Test
  public void testToStringMixedCoefficients() {
    assertEquals("4x^3 +3x^1 -5", poly1.toString());
    assertEquals("-2x^5 +3x^4 +11x^1 -5", poly2.toString());
  }

  /**
   * Tests the toString method for a polynomial with multiple terms.
   * Ensures correct formatting for terms with unique powers.
   */
  @Test
  public void testToStringMultipleTermsUniquePowers() {
    Polynomial multiTermPoly = new PolynomialImpl("6x^4 - 3x^2 + 5x + 10");
    assertEquals("6x^4 -3x^2 +5x^1 +10", multiTermPoly.toString());
  }

  /**
   * Tests the toString method for terms with the same power.
   * Ensures that terms are combined correctly
   * and output reflects the combined result.
   */
  @Test
  public void testToStringWithDuplicatePowers() {
    Polynomial duplicatePowerPoly = new PolynomialImpl("3x^2 -2x^2 +5");
    assertEquals("x^2 +5", duplicatePowerPoly.toString());
  }

  /**
   * Tests the toString method when terms are provided in descending order.
   * Ensures the output matches the input format with correct spacing and order.
   */
  @Test
  public void testToStringDescendingOrderTerms() {
    Polynomial descendingPoly = new PolynomialImpl("9x^3 +5x^2 -3x +1");
    assertEquals("9x^3 +5x^2 -3x^1 +1", descendingPoly.toString());
  }

  /**
   * Tests the toString method when terms are provided in ascending order.
   * Ensures the output displays terms in descending order by power.
   */
  @Test
  public void testToStringAscendingOrderTerms() {
    Polynomial ascendingPoly = new PolynomialImpl("2 +4x +6x^2");
    assertEquals("6x^2 +4x^1 +2", ascendingPoly.toString());
  }

  /**
   * Tests the toString method on a polynomial with large power terms.
   * Ensures the polynomial correctly formats high-degree terms.
   */
  @Test
  public void testToStringHighDegreeTerms() {
    Polynomial highDegreePoly = new PolynomialImpl("5x^100 -3x^50 +7");
    assertEquals("5x^100 -3x^50 +7", highDegreePoly.toString());
  }

  /**
   * Tests toString for a polynomial with only one term,
   * which is a high degree term.
   * Ensures it displays the term without any extra formatting.
   */
  @Test
  public void testToStringSingleHighDegreeTerm() {
    Polynomial singleTermPoly = new PolynomialImpl("8x^10");
    assertEquals("8x^10", singleTermPoly.toString());
  }

  /**
   * Tests the toString method for a polynomial
   * that contains a zero coefficient term.
   * Ensures the zero coefficient term is not displayed in the output.
   */
  @Test
  public void testToStringZeroCoefficientTerm() {
    Polynomial zeroCoeffPoly = new PolynomialImpl("4x^3 +0x^2 -5");
    assertEquals("4x^3 -5", zeroCoeffPoly.toString());
  }

  /**
   * Tests toString for the proper handling of
   * positive and negative coefficients.
   * Ensures each term has the correct sign and spacing in the output.
   */
  @Test
  public void testToStringPositiveAndNegativeCoefficients() {
    Polynomial mixedPoly = new PolynomialImpl("5x^3 -3x^2 +2x -1");
    assertEquals("5x^3 -3x^2 +2x^1 -1", mixedPoly.toString());
  }

  /**
   * Tests the {@code toString} method for a polynomial with extra spaces in the input.
   * Ensures that extra spaces between terms do not affect the final formatted output.
   */
  @Test
  public void testToStringWithExtraSpaces() {
    Polynomial poly = new PolynomialImpl("3x^2    +   4x^1 -   2");

    // Expected output should match the standard format without any extra spaces.
    assertEquals("3x^2 +4x^1 -2", poly.toString());
  }

  /**
   * Tests the {@code toString} method for edge cases.
   * <ul>
   *   <li>A polynomial with a single term.</li>
   *   <li>A polynomial with a constant term only.</li>
   *   <li>An empty polynomial, which should return "0".</li>
   * </ul>
   */
  @Test
  public void testToStringEdgeCases() {
    Polynomial singleTermPoly = new PolynomialImpl("3x^2");
    assertEquals("3x^2", singleTermPoly.toString());

    Polynomial constantPoly = new PolynomialImpl("-5");
    assertEquals("-5", constantPoly.toString());

    Polynomial emptyPoly = new PolynomialImpl();
    assertEquals("0", emptyPoly.toString());
  }

  @Test
  public void testPolynomialString() {
    Polynomial poly = new PolynomialImpl("3x^2 + 4x^1 - 2");
    assertEquals("3x^2 +4x^1 -2", poly.toString());
  }
}