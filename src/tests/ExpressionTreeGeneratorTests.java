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
 * @author Sara Sprenkle, Sarah Lathrop, Naka Assoumatine
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
	public void parenthesesExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("( x + y )");
		assertEquals(new Addition(new X(), new Y()), e);

		e = parser.makeExpression("( x + (y + [ 1, 1, 1] ) )");
		assertEquals(new Addition(new X(), new Addition(new Y(), new RGBColor(1, 1, 1))), e);
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
	public void stringNodeTest() {
		ExpressionTreeNode e = parser.makeExpression("test");
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
	

	// TODO: more tests
}
