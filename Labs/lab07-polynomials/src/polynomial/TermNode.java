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
   * Returns the coefficient of this term.
   *
   * @return the coefficient of the term
   */
  public int getCoefficient() {
    return this.coefficient;
  }

  /**
   * Retrieves the subsequent node in the polynomial's linked list.
   * This represents the next term in the polynomial, allowing traversal
   * of the polynomial structure.
   *
   * @return the next {@code PolynomialNode} in the linked list, or {@code null} if this is the last node.
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
    StringBuilder result = new StringBuilder();

    PolynomialNode currentNode = this;
    boolean isFirstTerm = true;

    while (!(currentNode instanceof EmptyNode)) {
      if (currentNode instanceof TermNode) {
        TermNode termNode = (TermNode) currentNode; // Cast to TermNode
        int coefficient = termNode.coefficient; // Access coefficient directly
        int power = termNode.power; // Access power directly

        if (coefficient != 0) { // Skip terms with a zero coefficient
          if (isFirstTerm) {
            // Format the first term without leading space if it's negative
            if (coefficient < 0) {
              result.append("-").append(Math.abs(coefficient));
            } else {
              result.append(coefficient);
            }
            isFirstTerm = false;
          } else {
            // For subsequent terms, add " + " or " - " before the coefficient
            if (coefficient > 0) {
              result.append(" + ").append(coefficient);
            } else {
              result.append(" - ").append(Math.abs(coefficient));
            }
          }

          // Append 'x' and exponent if power > 0
          if (power > 0) {
            result.append("x");
            if (power > 1) {
              result.append("^").append(power);
            }
          }
        }
        currentNode = termNode.next; // Move to the next node without getNext()
      }
    }

    // Return "0" if there are no terms or all terms have a zero coefficient
    return result.length() > 0 ? result.toString().trim() : "0";
  }
}