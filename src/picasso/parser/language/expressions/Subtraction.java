package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the subtraction function that takes two arguments.
 * 
 * @author Sarah Lathrop
 *
 */
public class Subtraction extends ExpressionTreeNode {
	private ExpressionTreeNode left;
	private ExpressionTreeNode right;
	/**
	 * 
	 * @param left
	 * @param right
	 */


	public Subtraction(ExpressionTreeNode left, ExpressionTreeNode right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + left + right + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Subtraction)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		Subtraction uf = (Subtraction) o;

		// check if their parameters are equal
		if ((!this.left.equals(uf.left)) || (!this.right.equals(uf.right))) {
			return false;
		}
		return true;
	}

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultLeft = left.evaluate(x, y);
		RGBColor resultRight = right.evaluate(x, y);
		double red = resultLeft.getRed() - resultRight.getRed();
		double green = resultLeft.getGreen() - resultRight.getGreen();
		double blue = resultLeft.getBlue() - resultRight.getBlue();
		return new RGBColor(red, green, blue);
	}

}