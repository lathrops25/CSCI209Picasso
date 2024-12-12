package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the exponential function in the Picasso language.
 * 
 * @author Naka Assoumatine
 * 
 */
public class Exp extends UnaryFunction {

	/**
	 * Create a exponential expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to exponential
	 */
	public Exp (ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the exponential of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the exponential of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.exp(result.getRed());
		double green = Math.exp(result.getGreen());
		double blue = Math.exp(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
