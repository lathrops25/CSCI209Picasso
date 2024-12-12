package picasso.parser.language.expressions;
//addition extends tree node

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the addition function that takes two arguments.
 * 
 * @author Sarah Lathrop
 *
 */
public class Division extends ExpressionTreeNode {
	private ExpressionTreeNode left;
	private ExpressionTreeNode right;
	/**
	 * 
	 * @param left
	 * @param right
	 */


	public Division(ExpressionTreeNode left, ExpressionTreeNode right) {
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

		if (!(o instanceof Division)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		Division uf = (Division) o;

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
		double red;
		double green;
		double blue;
		//can't divide by 0
		if (resultRight.getRed() == 0) {
			red = -1;
		} else {
			red = resultLeft.getRed() / resultRight.getRed();
		}
		
		if (resultRight.getGreen() == 0) {
			green = -1;
		} else {
			green = resultLeft.getGreen() / resultRight.getGreen();
		}
		
		if (resultRight.getBlue() == 0) {
			blue = -1;
		} else {
			blue = resultLeft.getBlue() / resultRight.getBlue();
		}
		
		return new RGBColor(red, green, blue);
	}

}
