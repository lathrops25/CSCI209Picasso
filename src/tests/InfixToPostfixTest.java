package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.tokens.Token;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.ParseException;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.operations.*;

/**
 * Test that checks for proper infix to postfix conversion
 * 
 * @author Sarah Lathrop
 */

class InfixToPostfixTest {
	Stack<Token> postfix;
	ExpressionTreeGenerator expressionTree;

	
	@BeforeEach
	public void setUp() throws Exception {
		expressionTree = new ExpressionTreeGenerator();
	}
	
	@Test
	public void testPostfixAssignmentExpression () {
		String expression = "a = y";
		postfix = expressionTree.infixToPostfix (expression);
		assertEquals (new AssignmentToken(), postfix.pop());
		assertEquals (new IdentifierToken("y"), postfix.pop());
		assertEquals (new IdentifierToken("a"), postfix.pop());
	}
	

	@Test
	void testInfixToPostfixConversion() {
	    String expression1 = "x + y * x";
	    postfix = expressionTree.infixToPostfix (expression1);
	    assertEquals(new PlusToken(), postfix.pop());
	    assertEquals (new MultiplicationToken(), postfix.pop());
	    assertEquals (new IdentifierToken("x"), postfix.pop());
		assertEquals (new IdentifierToken("y"), postfix.pop());
		assertEquals (new IdentifierToken("x"), postfix.pop());
		
		
	    String expression2 = "(x + y) * x";
	    postfix = expressionTree.infixToPostfix (expression2);
	    assertEquals (new MultiplicationToken(), postfix.pop());
	    assertEquals (new IdentifierToken("x"), postfix.pop());
	    assertEquals(new PlusToken(), postfix.pop());
		assertEquals (new IdentifierToken("y"), postfix.pop());
		assertEquals (new IdentifierToken("x"), postfix.pop());
	}
	
	@Test
	void testInvalidToken () {
		String expression = "&";
		assertThrows(ParseException.class, () -> {
			expressionTree.infixToPostfix (expression);
		});
	}
}
