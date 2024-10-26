package polynomial;

/**
 * The PolynomialNode interface represents a node
 * in the recursive structure of a polynomial. Each node corresponds to a term
 * in the polynomial or an end marker (EmptyNode) to signify the end of the polynomial.
 *
 * <p>Purpose:
 * This interface is essential for implementing a recursive union structure for polynomials,
 * as required by the assignment. The PolynomialNode interface manages the internal structure for
 * individual terms within the polynomial, allowing Polynomial to focus on high-level operations.
 *
 * <p>Importance:
 * PolynomialNode enables a recursive structure where each node can represent either a term
 * (represented by TermNode) or an end marker (represented by EmptyNode).
 * This setup delegates term-specific behavior (such as addition or evaluation) to nodes,
 * simplifying the overall implementation while adhering to recursive design principles.
 *
 * <p>Summary:
 * This interface enables PolynomialImpl to manage polynomial operations while delegating
 * term-specific behaviors to individual nodes. The result is a modular and recursive structure.
 */
interface PolynomialNode {

  /**
   * Adds another polynomial node to this node.
   *
   * @param other the node to add
   * @return the resulting node structure after addition
   */
  PolynomialNode add(PolynomialNode other);

  /**
   * Adds a term with the specified coefficient and power.
   *
   * @param coefficient the term's coefficient
   * @param power the term's power
   * @return the resulting node structure after adding the term
   */
  PolynomialNode addTerm(int coefficient, int power);

  /**
   * Checks if this node is structurally equivalent to another node.
   *
   * @param other the node to compare with
   * @return true if the structures are equivalent, false otherwise
   */
  boolean isSame(PolynomialNode other);

  /**
   * Evaluates this term at the specified value of x.
   *
   * @param x the value of x for evaluation
   * @return the evaluated result of the term
   */
  double evaluate(double x);

  /**
   * Retrieves the coefficient of the term with the specified power.
   *
   * @param power the power of the term to locate
   * @return the coefficient of the term, or 0 if no term with the specified power exists
   */
  int getCoefficient(int power);

  /**
   * Returns the degree (power) of the term with the highest exponent.
   *
   * @return the degree of the highest term
   * @throws IllegalStateException if called on an empty polynomial
   */
  int getDegree();

  /**
   * Returns the string representation of this term.
   *
   * @return the string representation of the polynomial term
   */
  @Override
  String toString();
}