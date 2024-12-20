package polynomial;

import java.util.Scanner;

/**
 * This class represents a polynomial implemented as a recursive structure.
 * Each polynomial consists of multiple terms,
 * where each term has an integer coefficient and a non-negative power.
 * This class supports various operations on polynomials such as
 * addition, term parsing, evaluation, and formatting to a string representation.
 */
public class PolynomialImpl implements Polynomial {
  private PolynomialNode head;

  /**
   * Default constructor that initializes the polynomial
   * as an empty polynomial (equivalent to 0).
   */
  public PolynomialImpl() {
    this.head = new EmptyNode();
  }

  /**
   * Constructor that parses a polynomial string to initialize the polynomial.
   *
   * @param polyStr the polynomial string to parse
   * @throws IllegalArgumentException if the term format is invalid
    or contains negative powers
   */
  public PolynomialImpl(String polyStr) throws IllegalArgumentException {
    this.head = new EmptyNode();
    Scanner scanner = new Scanner(polyStr);

    while (scanner.hasNext()) {
      String term = scanner.next().trim();

      // Check for invalid combinations of symbols like "+-" or "-+" in the term
      if (term.matches(".*[+-]{2,}.*")) {
        throw new IllegalArgumentException("Invalid term format "
                + "with consecutive symbols: " + term);
      }

      // Skip isolated "+" or "-" symbols resulting from extra spaces
      if (("+").equals(term) || ("-").equals(term)) {
        if (scanner.hasNext()) {
          term += scanner.next().trim();  // Append the next part to form a valid term
        } else {
          continue;  // Ignore isolated symbol if no term follows
        }
      }

      // Parse and add term to polynomial
      parseTerm(term);
    }
    scanner.close();
  }

  /**
   * Parses a polynomial term in the form of a string
   * and adds it to the polynomial.
   * Expected term formats include "4x^3", "+x", "-5", "x", or constants.
   *
   * @param term the string representation of the term
   * @throws IllegalArgumentException if the term format is invalid
    or contains an isolated "+" or "-"
   */
  private void parseTerm(String term) {
    term = term.trim();

    if (term.isEmpty() || ("+").equals(term) || ("-").equals(term)) {
      throw new IllegalArgumentException("Invalid term format: " + term);
    }

    int coefficient = 1;
    int power = 0;

    // Check for leading signs
    if (term.startsWith("+")) {
      term = term.substring(1).trim();
    } else if (term.startsWith("-")) {
      coefficient = -1;
      term = term.substring(1).trim();
    }

    // Check for presence of 'x' and set coefficient/power accordingly
    int indexOfX = term.indexOf('x');
    if (indexOfX == -1) {
      // Constant term
      coefficient *= Integer.parseInt(term);
    } else {
      String coeffStr = term.substring(0, indexOfX).trim();
      if (!coeffStr.isEmpty()) {
        coefficient *= Integer.parseInt(coeffStr);
      }
      // Check for power notation
      if (indexOfX + 1 < term.length() && term.charAt(indexOfX + 1) == '^') {
        power = Integer.parseInt(term.substring(indexOfX + 2).trim());
      } else {
        power = 1;
      }
    }

    if (power < 0) {
      throw new IllegalArgumentException("Negative powers are not allowed.");
    }

    addTerm(coefficient, power);
  }

  @Override
  public Polynomial add(Polynomial other) {
    if (!(other instanceof PolynomialImpl)) {
      throw new IllegalArgumentException("Incompatible polynomial type.");
    }
    PolynomialImpl result = new PolynomialImpl();
    result.head = this.head.add(((PolynomialImpl) other).head);
    return result;
  }

  @Override
  public void addTerm(int coefficient, int power) {
    if (power < 0) {
      throw new IllegalArgumentException("Power cannot be negative.");
    }
    if (coefficient != 0) {
      this.head = this.head.addTerm(coefficient, power);
    }
  }

  @Override
  public boolean isSame(Polynomial poly) {
    if (!(poly instanceof PolynomialImpl)) {
      return false;
    }
    return this.head.isSame(((PolynomialImpl) poly).head);
  }

  @Override
  public double evaluate(double x) {
    return this.head.evaluate(x);
  }

  @Override
  public int getCoefficient(int power) {
    return this.head.getCoefficient(power);
  }

  @Override
  public int getDegree() {
    // If the polynomial is empty, represented by EmptyNode, throw an exception
    if (this.head instanceof EmptyNode) {
      throw new IllegalStateException("Degree of an empty polynomial is undefined.");
    }
    return this.head.getDegree();
  }

  /**
   * Returns a string representation of the polynomial
   * in a simplified mathematical format.
   * Terms are arranged in descending order by power,
   * with appropriate signs between terms.
   * Formatting rules:
   * Terms with a coefficient of 1 or -1 omit the coefficient
   * if the term is non-constant.
   * Power notation (e.g., "^1") is omitted for terms with a power of 1.
   * Terms are preceded by " + " or " - " based on the sign.
   * Terms with zero coefficients are omitted.
   *
   * @return the polynomial as a string in mathematical format.
    Returns "0" if there are no terms.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    PolynomialNode currentNode = this.head;
    boolean isFirstTerm = true;

    while (!(currentNode instanceof EmptyNode)) {
      if (currentNode instanceof TermNode) {
        TermNode termNode = (TermNode) currentNode;
        int coefficient = termNode.getCoefficient();
        int power = termNode.getPower();

        if (coefficient != 0) { // Skip zero coefficients
          if (isFirstTerm) {
            if (coefficient == -1 && power > 0) {
              result.append("-");
            } else if (coefficient != 1 || power == 0) {
              result.append(coefficient);
            }
            isFirstTerm = false;
          } else {
            result.append(coefficient > 0 ? " +" : " -");
            if (Math.abs(coefficient) != 1 || power == 0) {
              result.append(Math.abs(coefficient));
            }
          }

          // Append "x" and power
          if (power > 0) {
            result.append("x");
            if (power > 1 || power == 1) { // Always append "^1" for linear terms
              result.append("^").append(power);
            }
          }
        }
      }
      currentNode = currentNode.getNext();
    }

    return result.length() > 0 ? result.toString() : "0";
  }
}
