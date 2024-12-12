package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the modulo function that takes two arguments.
 * @author : Naka Assoumatine
 */
public class Modulo extends ExpressionTreeNode {
    ExpressionTreeNode left;
    ExpressionTreeNode right;

    /**
     * Constructor for the modulo operation.
     * 
     * @param left  the left operand
     * @param right the right operand
     */
    public Modulo(ExpressionTreeNode left, ExpressionTreeNode right) {
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

        if (!(o instanceof Modulo)) {
            return false;
        }

        // Make sure the objects are the same type
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Modulo uf = (Modulo) o;

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
    	// Can't divide by 0
        if (resultRight.getRed() == 0) {
        	red = -1;
        } else {
        	red = resultLeft.getRed() % resultRight.getRed();
        }
        
        if (resultRight.getGreen() == 0) {
        	green = -1;
        } else {
        	green = resultLeft.getGreen() % resultRight.getGreen();
        }
        
        if (resultRight.getBlue() == 0) {
        	blue = -1;
        } else {
        	blue = resultLeft.getBlue() % resultRight.getBlue();
        }
        
        return new RGBColor(red, green, blue);

    }
    
    
}