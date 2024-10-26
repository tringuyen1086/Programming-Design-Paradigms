package polynomial;

/**
 * The EmptyNode class represents the absence of terms in the polynomial.
 */
public class EmptyNode implements PolynomialNode {

  /**
   * Constructs an EmptyNode representing a polynomial with no terms.
   * This signifies an empty polynomial, equivalent to the polynomial 0.
   */
  public EmptyNode() {
    // No parameters needed, as this is inherently an empty node.
  }

  @Override
  public PolynomialNode add(PolynomialNode other) {
    // Adding anything to an empty polynomial returns the other polynomial.
    return other;
  }

  @Override
  public PolynomialNode addTerm(int coefficient, int power) {
    // Add a new term node to the polynomial with the given coefficient and power
    if (coefficient != 0) {
      return new TermNode(coefficient, power, this);
    }
    return this; // Adding a term with zero coefficient keeps it empty
  }

  @Override
  public boolean isSame(PolynomialNode other) {
    // An empty node is only the same as another empty node
    return other instanceof EmptyNode;
  }

  @Override
  public double evaluate(double x) {
    // The value of an empty node is always zero
    return 0;
  }

  @Override
  public int getCoefficient(int power) {
    // No terms exist in an empty node, so coefficient for any power is 0
    return 0;
  }

  @Override
  public int getDegree() {
    throw new IllegalStateException("Degree of an empty polynomial is undefined.");
  }

  @Override
  public String toString() {
    // String representation for an empty polynomial.
    return "0";
  }
}