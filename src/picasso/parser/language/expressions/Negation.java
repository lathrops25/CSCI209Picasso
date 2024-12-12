package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the negation function that takes two arguments.
 * 
 * @author Naka Assoumatine
 */
public class Negation extends ExpressionTreeNode {
    ExpressionTreeNode expression;

    /**
     * Constructor for the negation operation.
     * 
     * @param expresion the expression
     */
    public Negation(ExpressionTreeNode expression) {
        this.expression = expression;
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
        return classname.substring(classname.lastIndexOf(".") + 1) + "(" + expression + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Negation)) {
            return false;
        }

        // Make sure the objects are the same type
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Negation uf = (Negation) o;

        // check if their parameters are equal
        if (!this.expression.equals(uf.expression)) {
            return false;
        }
        return true;
    }

    @Override
    public RGBColor evaluate(double x, double y) {
        RGBColor resultExpression = expression.evaluate(x, y);
        return new RGBColor(-resultExpression.getRed(), -resultExpression.getGreen(), -resultExpression.getBlue());
    }
}
