package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the cos function in the Picasso language
 * 
 * @author Jonathan
 */
public class Cos extends UnaryFunction{
	/**
	 * Creates a cos expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to cos
	 */
	public Cos(ExpressionTreeNode param) {
		super(param);
	}
	
	/**
	 * Evaluates this expression at the given x, y point by evaluating the function's parameter
	 * 
	 * @return the color from evaluating the cos of the expression's parameter
	 */
	@Override 
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.cos(result.getRed());
		double green = Math.cos(result.getGreen());
		double blue = Math.cos(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
