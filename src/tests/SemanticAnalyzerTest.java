/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ParseException;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.tokens.*;
import picasso.parser.tokens.functions.*;
import picasso.parser.tokens.operations.*;

/**
 * Test the parsing from the Stack (not as easy as using a String as input, but
 * helps to isolate where the problem is)
 * 
 * @author Sara Sprenkle, Sarah Lathrop, Allison Hidalgo 
 *
 */
class SemanticAnalyzerTest {

	private SemanticAnalyzer semAnalyzer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		semAnalyzer = SemanticAnalyzer.getInstance();
	}

	@Test
	void testParseMultiplication() {

	    // Set up the token stack for "x * y"
	    Stack<Token> tokens = new Stack<>();
	    tokens.push(new IdentifierToken("x")); // Push the left operand
	    tokens.push(new IdentifierToken("y")); // Push the right operand
	    tokens.push(new MultiplicationToken());     // Push the multiplication operator

	    // Generate the expression tree
	    ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

	    // Expected: Multiplication(x, y)
	    assertEquals(new Multiplication(new X(), new Y()), actual);
	}
	
	@Test
	void testParseAddition() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new PlusToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Addition(new X(), new Y()), actual);
	}
	
	@Test
	void testParseSubtaction() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new MinusToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Subtraction(new X(), new Y()), actual);
	}
	
	@Test 
	void testParseLog(){
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new LogToken());
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Log(new X()), actual);
		
	}
	
	@Test
	void testParseCos() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new CosToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Cos(new X()), actual);
	}
	@Test
	void testParseTan() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new TanToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Tan(new X()), actual);
	}
	
	@Test
	void testParseAtan() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new AtanToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Atan(new X()), actual);
	}
	
	@Test
	void testParseImageWrap() {
		
		Stack<Token> tokens = new Stack<>();
		tokens.push(new StringToken("vortex.jpg"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new PlusToken());
		tokens.push(new IdentifierToken("y"));
		tokens.push(new ImageWrapToken());
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		
		assertEquals(new ImageWrap(new Y(), new Addition(new X(), new X() ), "vortex.jpg"), actual);
		
	}
	
	@Test
	void testParseImageClip() {
		
		Stack<Token> tokens = new Stack<>();
		tokens.push(new StringToken("vortex.jpg"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new PlusToken());
		tokens.push(new IdentifierToken("y"));
		tokens.push(new ImageClipToken());
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		
		assertEquals(new ImageClip(new Y(), new Addition(new X(), new X() ), "vortex.jpg"), actual);
		
	}
	
	@Test 
	void testParseStringNode() {
		
		Stack<Token> tokens = new Stack<>();
		tokens.push(new StringToken("vortex.jpg"));
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		
		assertEquals(new StringNode("vortex.jpg"), actual);
	}

	@Test
	void testParseAssignment() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("a"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new AssignmentToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		assertEquals (new Assignment (new Variable ("a"), new Y()), actual);
	}
	
	@Test 
	void testParseAssignmentFailureId() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new AssignmentToken());
		assertThrows(ParseException.class, () -> {
			semAnalyzer.generateExpressionTree(tokens);
		});
	}
	
	@Test 
	/**
	 * Tests that an expression a + x = y (postfix a x + y = )should throw an exception
	 */
	void testParseAssignmentFailureLHExpression() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("a"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new PlusToken());
		tokens.push(new IdentifierToken("y"));
		tokens.push(new AssignmentToken());
		assertThrows(ParseException.class, () -> {
			semAnalyzer.generateExpressionTree(tokens);
		});
	}
	
	@Test
	void testParseyCrCbToRGB() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new YCrCbToRGBToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new YCrCbToRGB(new X()), actual);
	}

	@Test
	void testParsergbToYCrCb() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new RgbToYCrCbToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new RgbToYCrCb(new X()), actual);
	}

	@Test
	void testNegation() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new NegationToken());
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Negation(new X()), actual);
	}
	
	@Test
	void testParseDivision() {

	    // Set up the token stack for "x * y"
	    Stack<Token> tokens = new Stack<>();
	    tokens.push(new IdentifierToken("x")); // Push the left operand
	    tokens.push(new IdentifierToken("y")); // Push the right operand
	    tokens.push(new DivisionToken());     // Push the multiplication operator

	    // Generate the expression tree
	    ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

	    // Expected: Multiplication(x, y)
	    assertEquals(new Division(new X(), new Y()), actual);
	}
	
	@Test
	void testExp() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new ExpToken());
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		assertEquals(new Exp(new X()), actual);
	}
	
	@Test
	void testExponentiate() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new ExponentiateToken());
	
		
		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		assertEquals(new Exponentiate(new X(), new X()), actual);
	}
}
