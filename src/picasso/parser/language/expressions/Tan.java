package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the tan function in the Picasso language
 * 
 * @author Jonathan
 */
public class Tan extends UnaryFunction{

	/**
	 * Creates a tan expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to tan
	 */
	
	public Tan(ExpressionTreeNode param) {
		super(param);
	}
	/**
	 * Evaluates this expression at the given x,y point by evaluating tan of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the tan of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.tan(result.getRed());
		double green = Math.tan(result.getGreen());
		double blue = Math.tan(result.getBlue());

		return new RGBColor(red, green, blue);
	}
}
