package picasso.parser.language.expressions;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the Assignment operator
 * 
 * @author Sarah Lathrop
 */
public class Assignment extends ExpressionTreeNode {
	private Variable left;
	private ExpressionTreeNode right;
	
	/**
	 * Constructor 
	 * @param left
	 * @param right
	 */
	public Assignment (Variable left, ExpressionTreeNode right) {
		this.left = left;
		this.right = right;
		//map the variable to right (don't map the evaluation)
		gelementsToValue.put(left, right);
	}
	
	@Override
	public RGBColor evaluate (double x, double y) {
		// get the name of the variable and map this to the variable in the idToExpression map
		RGBColor result = right.evaluate(x, y);
		return result;
	}
		
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Assignment)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		Assignment uf = (Assignment) o;

		// check if their parameters are equal
		if (!this.left.equals(uf.left)) {
			return false;
		}
		if (!this.right.equals(uf.right)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + left + "=" + right + ")";
	}

}
