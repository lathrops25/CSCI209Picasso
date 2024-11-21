package tests;

import picasso.parser.language.expressions.Clamp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;

public class ClampTest {
	
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
}