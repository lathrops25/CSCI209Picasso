package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
import java.util.Random;


/**
 * Represents the Random function in the Picasso language
 * @author Sarah Lathrop
 */

public class RandomFunction extends ExpressionTreeNode{

	double rand1;
	double rand2;
	double rand3;
	
	public RandomFunction() {
		Random randObj = new Random();
		double rand1 = randObj.nextDouble();
		int negative1 = randObj.nextInt(2);
		if (negative1 == 0) {
			this.rand1 = rand1;
		} else {
			this.rand1 = -rand1;
		}
		
		double rand2 = randObj.nextDouble();
		int negative2 = randObj.nextInt(2);
		if (negative2 == 0) {
			this.rand2 = rand2;
		} else {
			this.rand2 = -rand2;
		}
		
		double rand3 = randObj.nextDouble();
		int negative3 = randObj.nextInt(2);
		if (negative3 == 0) {
			this.rand3 = rand3;
		} else {
			this.rand3 = -rand3;
		}
	}
	
	@Override
	public RGBColor evaluate(double x, double y) {
		
		//RGB color will always be the three random numbers
		RGBColor randomColor= new RGBColor (rand1, rand2, rand3);
		return randomColor;
	}
	
	// the three methods below will be helpful for testing
	/**
	 * Method that returns the first random number
	 * 
	 */
	public Double getRand1() {
		return this.rand1;
	}
	
	/**
	 * Method that returns the second random number
	 * 
	 */
	public Double getRand2() {
		return this.rand2;
	}
	
	/**
	 * Method that returns the third random number
	 * 
	 */
	public Double getRand3() {
		return this.rand3;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof ImageWrap)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		RandomFunction uf = (RandomFunction) o;

		// check if their parameters are equal
		if (!(this.rand1 == uf.rand1)) {
			return false;
		}
		if (!(this.rand2 == uf.rand2)) {
			return false;
		}
		if (!(this.rand3==uf.rand3)) {
			return false;
		}
		return true;
	}
	
	
}