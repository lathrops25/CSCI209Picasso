package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents the rgbToYCrCb function in Picasso language
 * 
 * @author Naka Assoumatine
 */

public class RgbToYCrCb extends ExpressionTreeNode {

	private ExpressionTreeNode param;
	// private ExpressionTreeNode param2;

	/**
	 * Create an rgbToYCrCb expression tree that takes as a parameter the given expression
	 * 
	 * @param param1 the first expression to rgbToYCrCb
	 */
	
	public RgbToYCrCb(ExpressionTreeNode param) {
		this.param = param;
		// this.param2 = param2;
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the rgbToYCrCb of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the rgbToYCrCb of the expression's parameter
	 */
	

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultExpression1 = param.evaluate(x, y);
		// RGBColor resultExpression2 = param2.evaluate(x, y); // resultExpression2 is unused 
		
		double red = resultExpression1.getRed() * 0.2989 + resultExpression1.getGreen() * 0.5866 + resultExpression1.getBlue() * 0.1145;
		double green = resultExpression1.getRed() * -0.1687 + resultExpression1.getGreen() * -0.3312 + resultExpression1.getBlue() * 0.5;
		double blue = resultExpression1.getRed() * 0.5000 + resultExpression1.getGreen() * -0.4183 + resultExpression1.getBlue() * -0.0816;
		return new RGBColor(red, green, blue);
	}
}
