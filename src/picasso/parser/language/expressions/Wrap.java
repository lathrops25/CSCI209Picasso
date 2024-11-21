package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the Wrap function in the Picasso language.
 * 
 * @author Gabriel Hogan
 * 
 */
public class Wrap extends UnaryFunction {

	/**
	 * Create a Wrap expression tree that takes as a parameter the given expression
	 * wrap results around [-1, 1] (e.g., 1.5 -> -0.5)
	 * 
	 * @param param the expression to Wrap
	 */
	public Wrap(ExpressionTreeNode param) {
		super(param);
	}

	private static double wrap(double val) {
		// Use modulo to keep the value in the range [-2, 2]
		double wrappedVal = val % 2;

		// Adjust for values outside [-1, 1]
		if (wrappedVal > 1) {
			return wrappedVal - 2;
		} else if (wrappedVal < -1) {
			return wrappedVal + 2;
		} else {
			return wrappedVal; // Already in the range [-1, 1]
		}
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the Wrap of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the Wrap of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);

		// Wrap the result around [-1, 1]
		double red = wrap(result.getRed());
		double green = wrap(result.getGreen());
		double blue = wrap(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
