package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents the perlinBW function in Picasso language
 * 
 * @author Naka Assoumatine
 */

public class PerlinBW extends ExpressionTreeNode {

	private ExpressionTreeNode param1;
	private ExpressionTreeNode param2;

	/**
	 * Create a perlinBw expression tree that takes as a parameter the given expression
	 * 
	 * @param param1 the first expression to perlinBW
	 * @param param2 the second expression to perlinBW
	 */
	public PerlinBW(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		this.param1 = param1;
		this.param2 = param2;
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the perlinBw of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the perlinBw of the expression's parameter
	 */
	

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultExpression1 = param1.evaluate(x, y);
		RGBColor resultExpression2 = param2.evaluate(x, y);
		
		double grey = ImprovedNoise.noise(resultExpression1.getRed() + resultExpression2.getRed(), resultExpression1.getGreen() + resultExpression2.getGreen(),
				resultExpression1.getBlue() + resultExpression2.getBlue());
		return new RGBColor(grey, grey, grey);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof PerlinBW)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		PerlinBW uf = (PerlinBW) o;

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
