package polynomial;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a polynomial as a recursive structure.
 * Each polynomial consists of terms,
 * with each term having an integer coefficient and a non-negative power.
 */
public class PolynomialImpl implements Polynomial {
  private PolynomialNode head;

  /**
   * Default constructor that creates an empty polynomial (0).
   */
  public PolynomialImpl() {
    this.head = new EmptyNode();
  }

  /**
   * Constructor that parses a polynomial string to initialize the polynomial.
   *
   * @param polyStr the polynomial string to parse
   * @throws IllegalArgumentException if the term format is invalid or contains negative powers
   */
  public PolynomialImpl(String polyStr) throws IllegalArgumentException {
    this.head = new EmptyNode();
    Scanner scanner = new Scanner(polyStr);

    while (scanner.hasNext()) {
      String term = scanner.next();
      int[] parsedTerm = parseTerm(term);
      int coefficient = parsedTerm[0];
      int power = parsedTerm[1];

      if (power < 0) {
        scanner.close();
        throw new IllegalArgumentException("Negative powers are not allowed.");
      }
      addTerm(coefficient, power);
    }
    scanner.close();
  }

  /**
   * Parses a polynomial term in the form of a string
   * and returns an array containing the coefficient and power.
   * The input term is expected to be in formats
   * like "4x^3", "+x", "-5", "x", or constants.
   *
   * @param term the string representation of the term
   * @return an int array where the first element is the coefficient
    and the second is the power
   * @throws IllegalArgumentException if the term cannot be parsed
   */
  private int[] parseTerm(String term) {
    term = term.trim();
    if (term.isEmpty()) {
      throw new IllegalArgumentException("Term cannot be empty.");
    }

    int coefficient;
    int power;

    try {
      if (term.equals("x") || term.equals("+x")) {
        // Term is "+x", meaning coefficient is 1 and power is 1
        coefficient = 1;
        power = 1;
      } else if (term.equals("-x")) {
        // Term is "-x", meaning coefficient is -1 and power is 1
        coefficient = -1;
        power = 1;
      } else if (term.contains("x")) {
        // General case where the term contains "x"
        int xIndex = term.indexOf('x');
        String coeffPart = term.substring(0, xIndex).trim();
        coefficient = coeffPart.isEmpty() || coeffPart.equals("+") ? 1 :
                coeffPart.equals("-") ? -1 : Integer.parseInt(coeffPart);

        if (term.contains("^")) {
          power = Integer.parseInt(term.substring(term.indexOf('^') + 1).trim());
        } else {
          power = 1;
        }
      } else {
        // Term is a constant without 'x'
        coefficient = Integer.parseInt(term);
        power = 0;
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing term: " + term, e);
    }

    return new int[]{coefficient, power};
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


  @Override
  public String toString() {
    String result = head.toString().trim();
    return result.isEmpty() ? "0" : result;
  }
}
