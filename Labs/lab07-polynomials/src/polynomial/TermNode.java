package polynomial;

/**
 * The TermNode class represents a non-zero term in the polynomial.
 */
public class TermNode implements PolynomialNode {
  private int coefficient;
  private int power;
  private PolynomialNode next;

  /**
   * Constructs a TermNode with a given coefficient, power, and link to the next node.
   *
   * @param coefficient the coefficient of this term
   * @param power       the power of this term
   * @param next        the next node in the polynomial
   */
  public TermNode(int coefficient, int power, PolynomialNode next) {
    this.coefficient = coefficient;
    this.power = power;
    this.next = next;
  }

  /**
   * Returns the power (exponent) of this term.
   *
   * @return the power of the term
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Retrieves the subsequent node in the polynomial's linked list.
   * This represents the next term in the polynomial, allowing traversal
   * of the polynomial structure.
   *
   * @return the next {@code PolynomialNode} in the linked list,
    or {@code null} if this is the last node.
   */
  public PolynomialNode getNext() {
    return this.next;
  }

  @Override
  public PolynomialNode add(PolynomialNode other) {
    if (other instanceof EmptyNode) {
      return this; // If other is empty, return the current node
    }

    TermNode otherTerm = (TermNode) other;

    if (this.power == otherTerm.power) {
      // If the powers are equal,
      // combine the coefficients and proceed to the next terms
      int newCoefficient = this.coefficient + otherTerm.coefficient;
      if (newCoefficient == 0) {
        // Skip this term if coefficient becomes 0
        return this.next.add(otherTerm.next);
      } else {
        return new TermNode(newCoefficient, this.power,
                this.next.add(otherTerm.next));
      }
    } else if (this.power > otherTerm.power) {
      // If this term's power is greater,
      // keep it and move to the next in this polynomial
      return new TermNode(this.coefficient, this.power,
              this.next.add(otherTerm));
    } else {
      // If other's term power is greater,
      // keep other's term and move to the next in other
      return new TermNode(otherTerm.coefficient, otherTerm.power,
              this.add(otherTerm.next));
    }
  }

  @Override
  public PolynomialNode addTerm(int coefficient, int power) {
    if (this.power == power) {
      int newCoeff = this.coefficient + coefficient;
      return (newCoeff == 0) ? this.next : new TermNode(newCoeff,
              this.power, this.next);
    } else if (this.power > power) {
      return new TermNode(this.coefficient, this.power,
              this.next.addTerm(coefficient, power));
    } else {
      return new TermNode(coefficient, power, this);
    }
  }

  @Override
  public boolean isSame(PolynomialNode other) {
    if (!(other instanceof TermNode)) {
      return false;
    }
    TermNode that = (TermNode) other;
    return this.coefficient == that.coefficient
            && this.power == that.power
            && this.next.isSame(that.next);
  }

  @Override
  public double evaluate(double x) {
    return this.coefficient
            * Math.pow(x, this.power)
            + this.next.evaluate(x);
  }

  /**
   * Returns the coefficient of this term.
   *
   * @return the coefficient of the term
   */
  public int getCoefficient() {
    return this.coefficient;
  }

  @Override
  public int getCoefficient(int power) {
    return this.power == power ? this.coefficient : this.next.getCoefficient(power);
  }

  @Override
  public int getDegree() {
    // Return the current power if next node is EmptyNode,
    // otherwise recurse.
    if (this.next instanceof EmptyNode) {
      return this.power;
    }
    return Math.max(this.power, this.next.getDegree());
  }

  @Override
  public String toString() {
    StringBuilder term = new StringBuilder();

    // First term handling for sign
    if (coefficient < 0) {
      term.append("-");
    }

    if (Math.abs(coefficient) != 1 || power == 0) {
      term.append(Math.abs(coefficient));
    }

    if (power > 0) {
      term.append("x");
      if (power > 1) {
        term.append("^").append(power);
      }
    }
    return term.toString();
  }
}