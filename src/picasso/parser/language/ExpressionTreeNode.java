package picasso.parser.language;

import java.util.Map;
import java.util.TreeMap;

import picasso.parser.language.expressions.RGBColor;

/**
 * All elements of the language (e.g., that make up ExpressionTree) should
 * extend ExpressionTreeNode.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle, Allison Hidalgo
 */
public abstract class ExpressionTreeNode implements EvaluatableExpression {

	/**
	 * Evaluate this expression, given x and y
	 * 
	 * @param x the value of x
	 * @param y the value of y
	 * 
	 * @return the result of evaluating the expression for the given values of x and
	 *         y
	 */
	public abstract RGBColor evaluate(double x, double y);

	// TODO: Not being utilized yet. Why would it be useful?
	// keep a mapping of the element to its value.
	static protected Map<ExpressionTreeNode, ExpressionTreeNode> gelementsToValue = new TreeMap<ExpressionTreeNode, ExpressionTreeNode>();
	
	/**
     * Returns the precedence of this node
     * 
     * This method can be overridden by subclasses to return specific precedence
     * 
     * @return a default precedence value (can be overridden)
     */
    public int getPrecedence() {
        return Integer.MAX_VALUE; // Default precedence (highest possible value)
    }
}
