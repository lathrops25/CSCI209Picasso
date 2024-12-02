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

	public static double wrap(double value) {
		while (value > 1) {
			value -= 2;
		}
		while (value < -1) {
			value += 2;
		}
		return value;
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
