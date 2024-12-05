package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the Assignment operator
 * 
 * @author Allison, Sarah 
 */
public class Clamp extends UnaryFunction {
	public static final double COLOR_MIN = -1;
	public static final double COLOR_MAX = 1;
	/**
	 * Create a clamp expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to clamp
	 */
	public Clamp(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by clamping the values of
	 * the functions parameter.
	 * 
	 * @return the color from evaluating the clamp of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = clamp(result.getRed());
		double green = clamp(result.getGreen());
		double blue = clamp(result.getBlue());

		return new RGBColor(red, green, blue);
	}
	
	/**
     * Helper method to clamp a value to the range [-1, 1].
     * 
     * @param value the value to clamp
     * @return the clamped value
     */
    public static double clamp(double value) {
        return Math.max(COLOR_MIN, Math.min(COLOR_MAX, value));
    }

}
