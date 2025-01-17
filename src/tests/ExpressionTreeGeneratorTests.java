package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.ParseException;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.*;

/**
 * Tests of creating an expression tree from a string expression. Will have
 * compiler errors until some code is created.
 * 
 * @author Sara Sprenkle, Sarah Lathrop, Naka Assoumatine, Jonathan Carranza Cortes
 * 
 */
public class ExpressionTreeGeneratorTests {

	private ExpressionTreeGenerator parser;

	@BeforeEach 
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	@Test
	public void constantExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("[1,-1, 1]");
		assertEquals(new RGBColor(1, -1, 1), e);
	}

	@Test
	public void variableExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x");
		assertEquals(new X(), e);
	}

	@Test
	public void additionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x + y");
		assertEquals(new Addition(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x+y");
		assertEquals(new Addition(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] + y");
		assertEquals(new Addition(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x + y + [ -.51, 0, 1]");
		assertEquals(new Addition(new Addition(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void subtractionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x - y");
		assertEquals(new Subtraction(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x-y");
		assertEquals(new Subtraction(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] - y");
		assertEquals(new Subtraction(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x - y - [ -.51, 0, 1]");
		assertEquals(new Subtraction(new Subtraction(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}

	@Test
	public void parenthesesExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("( x + y )");
		assertEquals(new Addition(new X(), new Y()), e);

		e = parser.makeExpression("( x + (y + [ 1, 1, 1] ) )");
		assertEquals(new Addition(new X(), new Addition(new Y(), new RGBColor(1, 1, 1))), e);
	}
	
	@Test
	public void parenthesesExpressionTestsSub() {
		ExpressionTreeNode e = parser.makeExpression("( x - y )");
		assertEquals(new Subtraction(new X(), new Y()), e);

		e = parser.makeExpression("( x - (y - [ 1, 1, 1] ) )");
		assertEquals(new Subtraction(new X(), new Subtraction(new Y(), new RGBColor(1, 1, 1))), e);
	}

	@Test
	public void arithmeticStackTests() {
		Stack<Token> stack = parser.infixToPostfix("x + y * x");

		Stack<Token> expected = new Stack<>();
		expected.push(new IdentifierToken("x"));
		expected.push(new IdentifierToken("y"));
		expected.push(new IdentifierToken("x"));
		expected.push(new MultiplicationToken());
		expected.push(new PlusToken());

		assertEquals(expected, stack);
		
		Stack<Token> stack2 = parser.infixToPostfix("x - y * x");

		Stack<Token> expected2 = new Stack<>();
		expected2.push(new IdentifierToken("x"));
		expected2.push(new IdentifierToken("y"));
		expected2.push(new IdentifierToken("x"));
		expected2.push(new MultiplicationToken());
		expected2.push(new MinusToken());

		assertEquals(expected2, stack2);
	}
	
	

	@Test
	public void floorFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("floor( x )");
		assertEquals(new Floor(new X()), e);

		e = parser.makeExpression("floor( x + y )");
		assertEquals(new Floor(new Addition(new X(), new Y())), e);
	}

	@Test
	public void clampFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("clamp( x )");
		assertEquals(new Clamp(new X()), e);

		e = parser.makeExpression("clamp( x + x )");
		assertEquals(new Clamp(new Addition(new X(), new X())), e);
	}

	@Test
	public void wrapFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("wrap( x )");
		assertEquals(new Wrap(new X()), e);

		e = parser.makeExpression("wrap( x + x )");
		assertEquals(new Wrap(new Addition(new X(), new X())), e);
	}

	@Test
	public void sinFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("sin( x )");
		assertEquals(new Sin(new X()), e);

		e = parser.makeExpression("sin( x + y )");
		assertEquals(new Sin(new Addition(new X(), new Y())), e);
	}
  
  @Test
	public void cosFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("cos( x )");
		assertEquals(new Cos(new X()), e);

		e = parser.makeExpression("cos( x + y )");
		assertEquals(new Cos(new Addition(new X(), new Y())), e);
	}
  
	@Test
	public void TanFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("tan( x )");
		assertEquals(new Tan(new X()), e);

		e = parser.makeExpression("tan( x + y )");
		assertEquals(new Tan(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void AtanFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("atan( x )");
		assertEquals(new Atan(new X()), e);

		e = parser.makeExpression("atan( x + y )");
		assertEquals(new Atan(new Addition(new X(), new Y())), e);
  }

	@Test
	public void absFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("abs( x )");
		assertEquals(new Abs(new X()), e);

		e = parser.makeExpression("abs( x + y )");
		assertEquals(new Abs(new Addition(new X(), new Y())), e);

	}

	@Test
	public void ceilFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("ceil( x )");
		assertEquals(new Ceil(new X()), e);

		e = parser.makeExpression("ceil( x + y )");
		assertEquals(new Ceil(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void logFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("log( x )");
		assertEquals(new Log(new X()), e);
		
		e = parser.makeExpression("log( x + x )");
		assertEquals(new Log(new Addition( new X(), new X())), e);
	}
	
	@Test
	public void imageWrapFunctionTest() {
		ExpressionTreeNode e = parser.makeExpression("imageWrap( \"vortex.jpg\", x+x, y )");
		assertEquals(new ImageWrap(new Y(), new Addition(new X(), new X()), "vortex.jpg"), e);
		
		ExpressionTreeNode e1 = parser.makeExpression("imageWrap(\"foo.jpg\", x, y + y)");
		assertEquals(new ImageWrap(new Addition(new Y(), new Y()), new X(), "foo.jpg"), e1);
	}
	
	@Test
	public void imageClipFunctionTest() {
		ExpressionTreeNode e = parser.makeExpression("imageClip( \"vortex.jpg\", x+x, y )");
		assertEquals(new ImageClip(new Y(), new Addition(new X(), new X()), "vortex.jpg"), e);
		
		ExpressionTreeNode e1 = parser.makeExpression("imageClip(\"foo.jpg\", x, y + y)");
		assertEquals(new ImageClip(new Addition(new Y(), new Y()), new X(), "foo.jpg"), e1);
	}
	
	@Test
	public void stringNodeTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			new StringNode("test");
		});
		
	}
	
	@Test 
	public void stringNodeFunctionTest() {
		ExpressionTreeNode e = parser.makeExpression("\"vortex.jpg\"");
		assertEquals(new StringNode("vortex.jpg"), e);

		ExpressionTreeNode e1 = parser.makeExpression("\"images/vortex.jpg\"");
		assertEquals(new StringNode("images/vortex.jpg"), e1);
	}
	
	
	@Test
	public void assignmentTests() {
		ExpressionTreeNode e = parser.makeExpression("a = x");
		assertEquals (new Assignment(new Variable("a"), new X()), e);
	}
	
	@Test
	public void assignmentTestsIllegalLH() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("a + y = x");
		});
	}
	
	@Test
	public void assignmentTestsIllegalLHY() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("y = x");
		});
	}
	
	@Test
	public void multiplicationExpressionTests() {
	    // Basic test for "x * y"
	    ExpressionTreeNode e = parser.makeExpression("x * y");
	    assertEquals(new Multiplication(new X(), new Y()), e);

	    // Test with no spaces
	    e = parser.makeExpression("x*y");
	    assertEquals(new Multiplication(new X(), new Y()), e);

	    // Test with a color constant and multiplication
	    e = parser.makeExpression("[1,.3,-1] * y");
	    assertEquals(new Multiplication(new RGBColor(1, .3, -1), new Y()), e);

	    // Test chained multiplication
	    e = parser.makeExpression("x * y * [ -.51, 0, 1]");
	    assertEquals(new Multiplication(new Multiplication(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	 @Test
	public void moduloExpressionTests() {
	    // Basic test for "x % y"
	    ExpressionTreeNode e = parser.makeExpression("x % y");
	    assertEquals(new Modulo(new X(), new Y()), e);
	    e = parser.makeExpression("x%y");
	    assertEquals(new Modulo(new X(), new Y()), e);

	    // Test with a color constant and modulo
	    e = parser.makeExpression("[1,.3,-1] % y");
	    assertEquals(new Modulo(new RGBColor(1, .3, -1), new Y()), e);
	} 
	
	@Test
	public void perlinBWExpressionTests() {
	    // Basic test for "PerlinBW(x, y)"
	    ExpressionTreeNode e = parser.makeExpression("perlinBW(x, y)");
	    assertEquals(new PerlinBW(new X(), new Y()), e);
	    e = parser.makeExpression("perlinBW(x,y)");
	    assertEquals(new PerlinBW(new X(), new Y()), e);

	    // Test with a color constant and PerlinBW
	    e = parser.makeExpression("perlinBW([1,.3,-1], y)");
	    assertEquals(new PerlinBW(new RGBColor(1, .3, -1), new Y()), e);

	}
	    

	@Test
	public void yCrCbToRGBExpressionTests() {
	    // Basic test for "rgbToYCrCb(x)"
	    ExpressionTreeNode e = parser.makeExpression("yCrCbToRGB(x)");
	    assertEquals(new YCrCbToRGB(new X()), e);
	    
	    // Test with a color constant and rgbToYCrCb
	    e = parser.makeExpression("yCrCbToRGB([1,.3,-1])");
	    assertEquals(new YCrCbToRGB(new RGBColor(1, .3, -1)), e);
	}
	
	@Test
	public void perlinColornExpressionTests() {
	    // Basic test for "x * y"
	    ExpressionTreeNode e = parser.makeExpression("perlinColor(x, y)");
	    assertEquals(new PerlinColor(new X(), new Y()), e);

	    // Test with no spaces
	    e = parser.makeExpression("perlinColor(x,y)");
	    assertEquals(new PerlinColor(new X(), new Y()), e);

	    // Test with a color constant and PerlinColor
	    e = parser.makeExpression("perlinColor([1,.3,-1], y)");
	    assertEquals(new PerlinColor(new RGBColor(1, .3, -1), new Y()), e);    
	}
	
	

	@Test
	public void divisionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x / y");
		assertEquals(new Division(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x/y");
		assertEquals(new Division(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] / y");
		assertEquals(new Division(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x / y / [ -.51, 0, 1]");
		assertEquals(new Division(new Division(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	
	@Test
	public void rgbToYCrCbExpressionTests() {
	    // Basic test for "rgbToYCrCb(x)"
	    ExpressionTreeNode e = parser.makeExpression("rgbToYCrCb(x)");
	    assertEquals(new RgbToYCrCb(new X()), e);
	}

	@Test
	public void negationTest() {
		ExpressionTreeNode e = parser.makeExpression("!x");
		assertEquals(new Negation(new X()), e);
	}
	
	@Test
	public void expExpressionTest() {
		ExpressionTreeNode e = parser.makeExpression("exp(x)");
		assertEquals(new Exp(new X()), e);
		
	}

	@Test
	public void RGbTOYCrCbTest() {
	    // Test with a color constant and rgbToYCrCb
	    ExpressionTreeNode e = parser.makeExpression("rgbToYCrCb([1,.3,-1])");
	    assertEquals(new RgbToYCrCb(new RGBColor(1, .3, -1)), e);
	}
	
	@Test
	public void exponentiateTest() {
		ExpressionTreeNode e = parser.makeExpression("x^x");
		assertEquals(new Exponentiate(new X(), new X()), e);
		
		ExpressionTreeNode e2 = parser.makeExpression("x ^ y");
		assertEquals(new Exponentiate(new X(), new Y()), e2);
		
	}
	
}
