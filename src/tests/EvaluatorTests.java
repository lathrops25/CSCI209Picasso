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

	// TODO: More tests of evaluation

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
        assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(-5.0, 0));

        // Test values within range
        assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(1.0, 0));
        assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(-1.0, 0));
        assertEquals(new RGBColor(0.0, 0.0, 0.0), myTree.evaluate(0.0, 0));

        // Test zero crossing boundary conditions
        assertEquals(new RGBColor(1.0, 1.0, 1.0), myTree.evaluate(-3.0, 0));
        assertEquals(new RGBColor(-1.0, -1.0, -1.0), myTree.evaluate(3.0, 0));

	}

}
