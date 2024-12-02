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
import picasso.parser.tokens.functions.ImageWrapToken;
import picasso.parser.tokens.operations.*;

/**
 * Test the parsing from the Stack (not as easy as using a String as input, but
 * helps to isolate where the problem is)
 * 
 * @author Sara Sprenkle, Sarah Lathrop
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
	void testParseAddition() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new PlusToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Addition(new X(), new Y()), actual);
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
		
		assertEquals(new ImageWrap(new Addition(new X(), new X() ), new Y(), "vortex.jpg"), actual);
		
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
}
