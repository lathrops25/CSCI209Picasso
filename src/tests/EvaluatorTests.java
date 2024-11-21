/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

/**
 * Tests of the evaluation of expression trees
 * 
 * @author Sara Sprenkle
 * @author Naka Assoumatine
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

	// TODO: More tests of evaluation
	@Test
	public void testSinEvaluation() {
		Sin myTree = new Sin(new X());
		
		// some straightforward tests
		assertEquals(new RGBColor(Math.sin(0), Math.sin(0), Math.sin(0)), myTree.evaluate(0, 0));
		assertEquals(new RGBColor(Math.sin(1), Math.sin(1), Math.sin(1)), myTree.evaluate(1, 0)); 
		assertEquals(new RGBColor(Math.sin(-1),Math.sin(-1), Math.sin(-1)), myTree.evaluate(-1, 0));
		
		assertEquals(new RGBColor(Math.sin(-.4), Math.sin(-.4), Math.sin(-.4)), myTree.evaluate(-.4, 0.9)); 
		
		// test the ints; remember that u's value doesn't matter
		for (int i = -1; i <= 1; i++ ) {
			assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)),myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)),myTree.evaluate(i, i));
		}
	
		double[] tests = {-.7, -.00001, .000001, .5};
		
		for (double testVal : tests) {
			double sinOfTestVal = Math.sin(testVal);
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal), myTree.evaluate(testVal, -1));//////
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal), 
					myTree.evaluate(testVal, testVal));
		}
	}
}
