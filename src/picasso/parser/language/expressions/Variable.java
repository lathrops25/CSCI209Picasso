package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a variable
 * 
 * @author Sara Sprenkle, Sarah Lathrop
 * 
 *
 */
public class Variable extends ExpressionTreeNode implements Comparable<Variable>  {

	private String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public RGBColor evaluate(double x, double y) {
		//get the variable's expression
		ExpressionTreeNode expression = gelementsToValue.get(this);
		if (expression == null) {
			throw new EvaluateException("Never assigned variable to a value");
		}
		RGBColor value = expression.evaluate(x, y);
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Variable)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		Variable uf = (Variable) o;

		// check if their parameters are equal
		if (!this.name.equals(uf.name)) {
			return false;
		}
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int compareTo(Variable other) {
        return name.compareTo(other.getName());
	}

}
