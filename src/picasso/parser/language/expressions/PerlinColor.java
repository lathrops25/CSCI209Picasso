package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents the abs function in Picasso language
 * 
 * @author Sarah Lathrop
 */

public class PerlinColor extends ExpressionTreeNode {

	private ExpressionTreeNode param1;
	private ExpressionTreeNode param2;

	/**
	 * Create a absolute value expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to absolute value
	 */
	public PerlinColor(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		this.param1 = param1;
		this.param2 = param2;
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the absolute value of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the absolute value of the expression's parameter
	 */
	

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultExpression1 = param1.evaluate(x, y);
		RGBColor resultExpression2 = param2.evaluate(x, y);
		
		double red = ImprovedNoise.noise(resultExpression1.getRed() + 0.3, resultExpression2.getRed() + 0.3, 0);
		double blue = ImprovedNoise.noise(resultExpression1.getBlue() + 0.1, resultExpression2.getBlue() + 0.1, 0);
		double green = ImprovedNoise.noise(resultExpression1.getGreen() - 0.8, resultExpression2.getGreen() - 0.8, 0);
		return new RGBColor(red, green, blue);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof PerlinColor)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		PerlinColor uf = (PerlinColor) o;

		// check if their parameters are equal
		
		if (!this.param1.equals(uf.param1)) {
			return false;
		}
		if (!this.param2.equals(uf.param2)) {
			return false;
		}
		return true;
	}
}
