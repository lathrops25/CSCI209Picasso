package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
/**
 * Represents the atan function in the Picasso language
 * 
 * @author Jonathan
 */
public class Atan extends UnaryFunction{

	/**
	 * Creates a tan expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to atan
	 */
	public Atan(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating atan of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the atan of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.atan(result.getRed());
		double green = Math.atan(result.getGreen());
		double blue = Math.atan(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
