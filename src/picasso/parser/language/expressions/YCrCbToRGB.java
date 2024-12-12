package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents the YCrCbToRGB function in Picasso language
 * 
 * @author Naka Assoumatine
 */

public class YCrCbToRGB extends ExpressionTreeNode {

	private ExpressionTreeNode param;

	/**
	 * Create an YCrCbToRGB expression tree that takes as a parameter the given expression
	 * 
	 * @param the expression to YCrCbToRGB
	 */
	
	public YCrCbToRGB(ExpressionTreeNode param) {
		this.param = param;
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the YCrCbToRGB of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the YCrCbToRGB of the expression's parameter
	 */
	

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultExpression = param.evaluate(x, y);
		
		double red = resultExpression.getRed() + resultExpression.getBlue() * 1.4022;
		double green = resultExpression.getRed() + resultExpression.getGreen() * -0.3456 + resultExpression.getBlue() * -0.7145;
		double blue = resultExpression.getRed() + resultExpression.getGreen() * 1.7710;
		return new RGBColor(red, green, blue);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof YCrCbToRGB)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		YCrCbToRGB uf = (YCrCbToRGB) o;

		// check if their parameters are equal
		
		if (!this.param.equals(uf.param)) {
			return false;
		}
		return true;
	}
}
