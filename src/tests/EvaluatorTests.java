/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picasso.model.Pixmap;
import picasso.model.ImprovedNoise;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

/**
 * Tests of the evaluation of expression trees
 *
 * @author Sara Sprenkle, Sarah Lathrop, Naka Assoumatine, Jonathan Carranza Cortes
 * 
 */
public class EvaluatorTests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testConstantEvaluation() {
		ExpressionTreeNode e = new RGBColor(1, -1, 1);
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(1, -1, 1), e.evaluate(i, i));
		}
	}

	@Test
	public void testXEvaluation() {
		X x = new X();
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), x.evaluate(i, i));
		}
	}

	@Test
	public void testFloorEvaluation() {
		Floor myTree = new Floor(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(.4, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(.999, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-.7, -1));

		// test the ints; remember that y's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double floorOfTestVal = Math.floor(testVal);
			assertEquals(new RGBColor(floorOfTestVal, floorOfTestVal, floorOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(floorOfTestVal, floorOfTestVal, floorOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}

	@Test
	public void testAbsEvaluation() {
		Abs absTree = new Abs(new X());
		// y values don't matter here

		// straightforward tests
		assertEquals(new RGBColor(0, 0, 0), absTree.evaluate(0, .5));
		assertEquals(new RGBColor(1, 1, 1), absTree.evaluate(-1, 0));
		assertEquals(new RGBColor(1, 1, 1), absTree.evaluate(1, .25));

		// more tests
		double[] tests = { -0.6, -.00001, .00001, .8 };

		for (double testVal : tests) {
			double absTestVal = Math.abs(testVal);
			assertEquals(new RGBColor(absTestVal, absTestVal, absTestVal), absTree.evaluate(testVal, 1));
			assertEquals(new RGBColor(absTestVal, absTestVal, absTestVal),
					absTree.evaluate(testVal, testVal));
		}

	}

	@Test
	public void testCeilEvaluation() {
		Ceil myTree = new Ceil(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-.3, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -.8));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.7, .999));

		// test the ints
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double ceilOfTestVal = Math.ceil(testVal);
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
		
	}

	@Test
    public void testClamp() {
		Clamp clamp = new Clamp(new X());
		
		//Values beyond -1,1
		assertEquals(new RGBColor(1, 1, 1), clamp.evaluate(2, 0));
		assertEquals(new RGBColor(-1, -1, -1), clamp.evaluate(-2, 0));
		
		//Boundary values
		assertEquals(new RGBColor(1, 1, 1), clamp.evaluate(1, 0));
		assertEquals(new RGBColor(-1, -1, -1), clamp.evaluate(-1, 0));
		
		//Values in range of -1,1
		assertEquals(new RGBColor(0, 0, 0), clamp.evaluate(0, 0));
		assertEquals(new RGBColor(0.5, 0.5, 0.5), clamp.evaluate(0.5, 0));
		assertEquals(new RGBColor(-0.5, -0.5, -0.5), clamp.evaluate(-0.5, 0));		
	}
	
	@Test
	public void testAdditionEvaluation() {
		Addition myTree = new Addition(new X(), new Y());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(0.5, 0.5, 0.5), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i - i, i - i, i - i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i + i, i + i, i + i), myTree.evaluate(i, i));
		}

		// test a range of integers
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				assertEquals(new RGBColor(i + j, i + j, i + j), myTree.evaluate(i, j));
			}
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testLeftVal : tests) {
			for (double testRightVal : tests) {
				double additionOfTestVal = testLeftVal + testRightVal;
				assertEquals(new RGBColor(additionOfTestVal, additionOfTestVal, additionOfTestVal),
						myTree.evaluate(testLeftVal, testRightVal));
			}
		}
	}
	
	@Test
	public void testSubtractionEvaluation() {
		Subtraction myTree = new Subtraction(new X(), new Y());
		
		//straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(-.5, -.5, -.5), myTree.evaluate(-.75, -.25));
		assertEquals(new RGBColor(.75, .75, .75), myTree.evaluate(1, .25));
		assertEquals(new RGBColor(.50, .50, .50), myTree.evaluate(.25, -.25));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-.4, .6));
		// test a range of integers
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				assertEquals(new RGBColor(i - j, i - j, i - j), myTree.evaluate(i, j));
			}
		}
		
		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testLeftVal : tests) {
			for (double testRightVal : tests) {
				double subtractionOfTestVal = testLeftVal - testRightVal;
				assertEquals(new RGBColor(subtractionOfTestVal, subtractionOfTestVal, subtractionOfTestVal),
						myTree.evaluate(testLeftVal, testRightVal));
			}
		}
		
	}
	
	
	
	@Test
	public void testWrapEvaluation() {
		Wrap myTree = new Wrap(new X());

		// Test positive values that wrap around
		assertEquals(new RGBColor(-0.5, -0.5, -0.5), myTree.evaluate(1.5, 0));
		assertEquals(new RGBColor(0.5, 0.5, 0.5), myTree.evaluate(2.5, 0));
		assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(3.0, 0));

		// Test negative values that wrap around
		assertEquals(new RGBColor(0.5, 0.5, 0.5), myTree.evaluate(-1.5, 0));
		assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(-3.0, 0));
		assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(-5.0, 0));

		// Test values within range
		assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(1.0, 0));
		assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(-1.0, 0));
		assertEquals(new RGBColor(0.0, 0.0, 0.0), myTree.evaluate(0.0, 0));

		// Test zero crossing boundary conditions
		assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(-3.0, 0));
		assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(3.0, 0));

	}

	@Test
	public void testSinEvaluation() {
		Sin myTree = new Sin(new X());

		// some straightforward tests
		assertEquals(new RGBColor(Math.sin(0), Math.sin(0), Math.sin(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.sin(1), Math.sin(1), Math.sin(1)), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(Math.sin(-1), Math.sin(-1), Math.sin(-1)), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(Math.sin(-.4), Math.sin(-.4), Math.sin(-.4)), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double sinOfTestVal = Math.sin(testVal);
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
	
   @Test
  public void testCosEvaluation() {
		Cos myTree = new Cos(new X());

		// some straightforward tests
		assertEquals(new RGBColor(Math.cos(0), Math.cos(0), Math.cos(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.cos(1), Math.cos(1), Math.cos(1)), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(Math.cos(-1), Math.cos(-1), Math.cos(-1)), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(Math.cos(-.4), Math.cos(-.4), Math.cos(-.4)), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.cos(i), Math.cos(i), Math.cos(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.cos(i), Math.cos(i), Math.cos(i)), myTree.evaluate(i, i));
    }
    double[] tests = { -.7, -.00001, .000001, .5 };
  	for (double testVal : tests) {
  	  double cosOfTestVal = Math.cos(testVal);
			assertEquals(new RGBColor(cosOfTestVal, cosOfTestVal, cosOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(cosOfTestVal, cosOfTestVal, cosOfTestVal),
      myTree.evaluate(testVal, testVal));
      }
  }
      
      
	@Test
	public void testTanEvaluation() {
		Tan myTree = new Tan(new X());

		// some straightforward tests
		assertEquals(new RGBColor(Math.tan(0), Math.tan(0), Math.tan(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.tan(1), Math.tan(1), Math.tan(1)), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(Math.tan(-1), Math.tan(-1), Math.tan(-1)), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(Math.tan(-.4), Math.tan(-.4), Math.tan(-.4)), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.tan(i), Math.tan(i), Math.tan(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.tan(i), Math.tan(i), Math.tan(i)), myTree.evaluate(i, i));
    }
  	double[] tests = { -.7, -.00001, .000001, .5 };
  	for (double testVal : tests) {
			double tanOfTestVal = Math.tan(testVal);
			assertEquals(new RGBColor(tanOfTestVal, tanOfTestVal, tanOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(tanOfTestVal, tanOfTestVal, tanOfTestVal),
      myTree.evaluate(testVal, testVal));
      }
  }
	
	@Test
	public void testAtanEvaluation() {
		Atan myTree = new Atan(new X());

		// some straightforward tests
		assertEquals(new RGBColor(Math.atan(0), Math.atan(0), Math.atan(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.atan(1), Math.atan(1), Math.atan(1)), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(Math.atan(-1), Math.atan(-1), Math.atan(-1)), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(Math.atan(-.4), Math.atan(-.4), Math.atan(-.4)), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.atan(i), Math.atan(i), Math.atan(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.atan(i), Math.atan(i), Math.atan(i)), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double atanOfTestVal = Math.atan(testVal);
			assertEquals(new RGBColor(atanOfTestVal, atanOfTestVal, atanOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(atanOfTestVal, atanOfTestVal, atanOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
                   
  @Test
	public void testLogEvaluation() {
		Log myTree = new Log(new X());
		
		// some straightforward tests
		assertEquals(new RGBColor(Math.log(2), Math.log(2), Math.log(2)), myTree.evaluate(2, 0));
		assertEquals(new RGBColor(Math.log(4), Math.log(4), Math.log(4)), myTree.evaluate(4, 0));
		assertEquals(new RGBColor(Math.log(8), Math.log(8), Math.log(8)), myTree.evaluate(8, 0));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			// absolute value is for parameters, log can't be negative
			i = Math.abs(i);
			assertEquals(new RGBColor(Math.log(i), Math.log(i), Math.log(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.log(i), Math.log(i), Math.log(i)), myTree.evaluate(i, i));
		}
		
		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double logOfTestVal = Math.log(Math.abs(testVal));
			assertEquals(new RGBColor(logOfTestVal, logOfTestVal, logOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(logOfTestVal, logOfTestVal, logOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
		
	}
	@Test
	public void testAssignmentEvaluation() {
		Assignment myTree = new Assignment (new Variable("a"), new X());
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}
		Assignment myTree2 = new Assignment (new Variable("b"), new Addition (new X(), new Y()));
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				assertEquals(new RGBColor(i + j, i + j, i + j), myTree2.evaluate(i, j));
			}
		}
	}
	
	/**
	 * Testing that the evaluation of "a = y", "a", "a = b", and then "b" is all equal to y
	 */
	@Test
	public void testEvaluationWithSimpleAssignment() {
		String testExpression1 = "a = y";
		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		ExpressionTreeNode node1= expTreeGen.makeExpression(testExpression1);
		Y y = new Y();
		for (int i = -1; i <= 1; i++) {
			assertEquals(y.evaluate(i, i), node1.evaluate(i, i));
		}
		
		String testExpression2 = "a";
		ExpressionTreeGenerator expTreeGen2 = new ExpressionTreeGenerator();
		ExpressionTreeNode node2= expTreeGen2.makeExpression(testExpression2);
		for (int i = -1; i <= 1; i++) {
			assertEquals(y.evaluate(i, i), node2.evaluate(i, i));
		}
		
		String testExpression3 = "b = a";
		ExpressionTreeGenerator expTreeGen3 = new ExpressionTreeGenerator();
		ExpressionTreeNode node3= expTreeGen3.makeExpression(testExpression3);
		for (int i = -1; i <= 1; i++) {
			assertEquals(y.evaluate(i, i), node3.evaluate(i, i));
		}
		
		String testExpression4 = "b = a";
		ExpressionTreeGenerator expTreeGen4 = new ExpressionTreeGenerator();
		ExpressionTreeNode node4= expTreeGen4.makeExpression(testExpression4);
		for (int i = -1; i <= 1; i++) {
			assertEquals(y.evaluate(i, i), node4.evaluate(i, i));
		}
	}
	
	// Tests illegal of evaluation of assignment since never assigned
	@Test
	public void errorNoEvaluationTest() {
		Variable a = new Variable("testVar");
		assertThrows(EvaluateException.class, () -> {
			a.evaluate(1,1);
		});
		
		String test = "z";
		ExpressionTreeGenerator TreeGen = new ExpressionTreeGenerator();
		ExpressionTreeNode node= TreeGen.makeExpression(test);
		assertThrows(EvaluateException.class, () -> {
			node.evaluate(0,0);
		});
	}
	
	@Test
	public void testMultiplicationEvaluation() {
	    Multiplication myTree = new Multiplication(new X(), new Y());

	    // Test straightforward cases
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, 1));
	    assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, 1));

	    // Test cases with fractional values
	    assertEquals(new RGBColor(-0.4 * 0.9, -0.4 * 0.9, -0.4 * 0.9), myTree.evaluate(-0.4, 0.9));

	    // Test the multiplication of integers
	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            double result = i * j;
	            assertEquals(new RGBColor(result, result, result), myTree.evaluate(i, j));
	        }
	    }

	    // Test a range of floating-point values
	    double[] tests = { -.7, -.00001, .000001, .5 };

	    for (double testLeftVal : tests) {
	        for (double testRightVal : tests) {
	            double multiplicationResult = testLeftVal * testRightVal;
	            assertEquals(new RGBColor(multiplicationResult, multiplicationResult, multiplicationResult),
	                    myTree.evaluate(testLeftVal, testRightVal));
	        }
	    }
	}

	
	@Test
	public void testYCrCbToRGBEvaluation() {
		YCrCbToRGB myTree = new YCrCbToRGB(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0 + 0*1.4022, 0 + 0*-0.3456 + 0*-0.7145, 0 + 0*1.7710), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(1 + 1*1.4022, 1 + 1*-0.3456 + 1*-0.7145, 1 + 1*1.7710), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(-1 + -1*1.4022, -1 + -1*-0.3456 + -1*-0.7145, -1 + -1*1.7710), myTree.evaluate(-1, 0));
		
		assertEquals(new RGBColor(-.4 + -.4*1.4022, -.4 + -.4*-0.3456 + -.4*-0.7145, -.4 + -.4*1.7710), myTree.evaluate(-.4, 0.9));


		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i + i*1.4022, i + i*-0.3456 + i*-0.7145, i + i*1.7710), myTree.evaluate(i, i));
			assertEquals(new RGBColor(-i + -i*1.4022, -i + -i*-0.3456 + -i*-0.7145, -i + -i*1.7710), myTree.evaluate(-i, i));

		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			assertEquals(new RGBColor(testVal + testVal*1.4022, testVal + testVal*-0.3456 + testVal*-0.7145, testVal + testVal*1.7710), myTree.evaluate(testVal, -1));
					myTree.evaluate(testVal, testVal);
		}
	}




	@Test
	public void testDivisionEvaluation() {
		Division myTree = new Division(new X(), new Y());

		// some straightforward tests
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, 0));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, 1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, -1));

		assertEquals(new RGBColor(-0.444444, -0.444444, -0.444444), myTree.evaluate(-.4, 0.9));

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testLeftVal : tests) {
			for (double testRightVal : tests) {
				double divisionOfTestVal = testLeftVal / testRightVal;
				assertEquals(new RGBColor(divisionOfTestVal, divisionOfTestVal, divisionOfTestVal),
						myTree.evaluate(testLeftVal, testRightVal));
			}
		}
	}
	
	@Test
	public void testNegationEvaluation() {
	    Negation myTree = new Negation(new X());

	    // Test straightforward cases
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, 0));
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, 1));

	    // Test cases with fractional values
	    assertEquals(new RGBColor(0.4, 0.4, 0.4), myTree.evaluate(-0.4, 0.9));

	    // Test the multiplication of integers
	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            double result = -i;
	            assertEquals(new RGBColor(result, result, result), myTree.evaluate(i, j));
	        }
	    }

	    // Test a range of floating-point values
	    double[] tests = { -.7, -.00001, .000001, .5 };

	    for (double testLeftVal : tests) {
	        for (double testRightVal : tests) {
	            double negationResult = -testLeftVal;
	            assertEquals(new RGBColor(negationResult, negationResult, negationResult),
	                    myTree.evaluate(testLeftVal, testRightVal));
	        }
	    }
	}
	
	@Test
	public void testExpEvaluation() {
		Exp myTree = new Exp(new X());

		// some straightforward tests
		assertEquals(new RGBColor(Math.exp(0), Math.exp(0), Math.exp(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.exp(1), Math.exp(1), Math.exp(1)), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(Math.exp(-1), Math.exp(-1), Math.exp(-1)), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(Math.exp(-.4), Math.exp(-.4), Math.exp(-.4)), myTree.evaluate(-.4, 0.9));

		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double expOfTestVal = Math.exp(testVal);
			assertEquals(new RGBColor(expOfTestVal, expOfTestVal, expOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(expOfTestVal, expOfTestVal, expOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
	
	
	@Test
	public void testStringNodeEvaluation() {
		Pixmap image = new Pixmap("images/foo.jpg");
		StringNode myTree = new StringNode("images/foo.jpg");
		
		// evaluate it for each pixel
		Dimension size = image.getSize();
		for (int imageY = 0; imageY < size.height; imageY++) {
			for (int imageX = 0; imageX < size.width; imageX++) {
				// get evaluated color
				Color RGB_Ex = myTree.evaluate(imageX, imageY).toJavaColor();
				int eRed = RGB_Ex.getRed();
				int eGreen = RGB_Ex.getGreen();
				int eBlue = RGB_Ex.getBlue();
				// get actual color from image
				Color RGB_Ac = new Color(image.myImage.getRGB(imageX, imageY));
				int aRed = RGB_Ac.getRed();
				int aGreen = RGB_Ac.getGreen();
				int aBlue = RGB_Ac.getBlue();
				// compare both colors with an error margin of 1
				// this is probably something with the conversion from RGBColor to Color
				assertEquals(eRed, aRed, 1);
				assertEquals(eGreen, aGreen, 1);
				assertEquals(eBlue, aBlue, 1);
				}
			}
	}
	
	@Test
	public void testImageWrapEvaluation() {
		// testImage.jpg uses floor(x) as an expression so any -x is black, any +x is gray
		String fileName = "images/testImage.jpg";
		// using string node because it already has an evaluate method unlike Pixmap
		StringNode Tree = new StringNode(fileName);
		ImageWrap myTree = new ImageWrap(new Y(), new Addition(new X(), new X() ), fileName);
		// evaluate it for each pixel
	
		Color RGB_Ex = myTree.evaluate(0.0, 0.0).toJavaColor();
		Color RGB_Ac = Tree.evaluate(0.0, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
		
		RGB_Ex = myTree.evaluate(0.75, 0.0).toJavaColor();
		RGB_Ac = Tree.evaluate(-0.5, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
		
		RGB_Ex = myTree.evaluate(-0.75, 0.0).toJavaColor();
		RGB_Ac = Tree.evaluate(0.5, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
	}
	
	@Test
	public void testImageClipEvaluation() {
		// testImage.jpg uses floor(x) as an expression so any -x is black, any +x is gray
		String fileName = "images/testImage.jpg";
		// using string node because it already has an evaluate method unlike Pixmap
		StringNode Tree = new StringNode(fileName);
		ImageClip myTree = new ImageClip(new Y(), new Addition(new X(), new X() ), fileName);
		// evaluate it for each pixel
	
		Color RGB_Ex = myTree.evaluate(0.0, 0.0).toJavaColor();
		Color RGB_Ac = Tree.evaluate(0.0, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
		
		RGB_Ex = myTree.evaluate(0.75, 0.0).toJavaColor();
		RGB_Ac = Tree.evaluate(1.0, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
		
		RGB_Ex = myTree.evaluate(-0.75, 0.0).toJavaColor();
		RGB_Ac = Tree.evaluate(-1.0, 0.0).toJavaColor();
		assertEquals(RGB_Ac, RGB_Ex);
	}

	
	@Test
	public void testExponentialEvaluation() {
	    Exponentiate myTree = new Exponentiate(new X(), new Y());

	    // Test straightforward cases
	    assertEquals(new RGBColor(Math.pow(0, 0), Math.pow(0, 0), Math.pow(0, 0)), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(Math.pow(1, 1), Math.pow(1,1), Math.pow(1, 1)), myTree.evaluate(1, 1));
	    assertEquals(new RGBColor(Math.pow(-1, 1), Math.pow(-1,1), Math.pow(-1, 1)), myTree.evaluate(-1, 1));
	    // assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, 0.5));

	    // Test cases with fractional values
	    assertEquals(new RGBColor(Math.pow(-0.4, 0.9), Math.pow(-0.4, 0.9), Math.pow(-0.4, 0.9)), myTree.evaluate(-0.4, 0.9));

	     //Test a range of floating-point values
	    double[] tests = { -.7, -.00001, .000001, .5 };

	    for (double testLeftVal : tests) {
	        for (double testRightVal : tests) {
	            double exponentiateResult = Math.pow(testLeftVal, testRightVal);
	            assertEquals(new RGBColor(exponentiateResult, exponentiateResult, exponentiateResult),
	                    myTree.evaluate(testLeftVal, testRightVal));
	        }
	    }
	}
	
	@Test
	public void testRandomEvaluation() {
		RandomFunction myTree = new RandomFunction(); 
		double color1 = myTree.getRand1();
		double color2 = myTree.getRand2();
		double color3 = myTree.getRand3();
		for (int i = -1; i <=1; i++) {
			for (int j = -1; j <= 1; j++) {
				
			assertEquals(new RGBColor (color1, color2, color3), myTree.evaluate(i, j));
			}
		}
		//testing twice to try to cover more options
		RandomFunction myTree2 = new RandomFunction(); 
		double color1b = myTree2.getRand1();
		double color2b = myTree2.getRand2();
		double color3b = myTree2.getRand3();
		for (int a = -1; a <=1; a++) {
			for (int b = -1; b <= 1; b++) {
				assertEquals(new RGBColor (color1b, color2b, color3b), myTree2.evaluate(a, b));
				}
		}
	}
	
	
	
	
	@Test
	public void testRgbToYCrCbEvaluation() {
		RgbToYCrCb myTree = new RgbToYCrCb(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0*0.2989 + 0*0.5866 + 0*0.1145, 0*-0.1687 + 0*-0.3312 + 0*0.5, 0*0.5000 + 0*-0.4183 + 0*-0.0816), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(1*0.2989 + 1*0.5866 + 1*0.1145, 1*-0.1687 + 1*-0.3312 + 1*0.5, 1*0.5000 + 1*-0.4183 + 1*-0.0816), myTree.evaluate(1, 0));
		assertEquals(new RGBColor(-1*0.2989 + -1*0.5866 + -1*0.1145, -1*-0.1687 + -1*-0.3312 + -1*0.5, -1*0.5000 + -1*-0.4183 + -1*-0.0816), myTree.evaluate(-1, 0));

		assertEquals(new RGBColor(-.4*0.2989 + -.4*0.5866 + -.4*0.1145, -.4*-0.1687 + -.4* -0.3312 + -.4*0.5, -.4*0.5000 + -.4*-0.4183 + -.4*-0.0816), myTree.evaluate(-.4, 0.9));


		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i*0.2989 + i*0.5866 + i*0.1145, i*-0.1687 + i*-0.3312 + i*0.5, i*0.5000 + i*-0.4183 + i*-0.0816), myTree.evaluate(i, i));
			assertEquals(new RGBColor(i*0.2989 + i*0.5866 + i*0.1145, i*-0.1687 + i*-0.3312 + i*0.5, i*0.5000 + i*-0.4183 + i*-0.0816), myTree.evaluate(i, -i));

		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			assertEquals(new RGBColor(testVal*0.2989 + testVal*0.5866 + testVal*0.1145, testVal*-0.1687 + testVal*-0.3312 + testVal*0.5, testVal*0.5000 + testVal*-0.4183 + testVal*-0.0816), myTree.evaluate(testVal, -1));
					myTree.evaluate(testVal, testVal);
		}
	}

	
	@Test
	public void testPerlinColorEvaluation() {
		PerlinColor myTree = new PerlinColor(new X(), new Y());

	    // Test straightforward cases
	    assertEquals(new RGBColor(ImprovedNoise.noise(0+0.3, 0+0.3, 0), ImprovedNoise.noise(0-0.8, 0-0.8, 0), ImprovedNoise.noise(0+0.1, 0+0.1, 0)), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(ImprovedNoise.noise(1+0.3, 1+0.3, 0), ImprovedNoise.noise(1-0.8, 1-0.8, 0), ImprovedNoise.noise(1+0.1, 1+0.1, 0)), myTree.evaluate(1, 1));
	    assertEquals(new RGBColor(ImprovedNoise.noise(-1+0.3, 1+0.3, 0), ImprovedNoise.noise(-1-0.8, 1-0.8, 0), ImprovedNoise.noise(-1+0.1, 1+0.1, 0)), myTree.evaluate(-1, 1));

	    // Test cases with fractional values
	    assertEquals(new RGBColor(ImprovedNoise.noise(-0.4+0.3, 0.9+0.3, 0), ImprovedNoise.noise(-0.4-0.8, 0.9-0.8, 0), ImprovedNoise.noise(-0.4+0.1, 0.9+0.1, 0)), myTree.evaluate(-0.4, 0.9));

	    // Test the perlinColor
	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            assertEquals(new RGBColor(ImprovedNoise.noise(i+0.3, j+0.3, 0), ImprovedNoise.noise(i-0.8, j-0.8, 0), ImprovedNoise.noise(i+0.1, j+0.1, 0)), myTree.evaluate(i, j));
	            
	        }
	    }

	    // Test a range of floating-point values
	    double[] tests = { -.7, -.00001, .000001, .5 };

	    for (double testLeftVal : tests) {
	        for (double testRightVal : tests) {
	            assertEquals(new RGBColor(ImprovedNoise.noise(testLeftVal+0.3, testRightVal+0.3, 0), ImprovedNoise.noise(testLeftVal-0.8, testRightVal-0.8, 0), ImprovedNoise.noise(testLeftVal+0.1, testRightVal+0.1, 0)),
	                    myTree.evaluate(testLeftVal, testRightVal));
	        }
	    }
	}
}
