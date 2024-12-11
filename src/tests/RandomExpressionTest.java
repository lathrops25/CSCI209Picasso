package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.tokens.Token;
import picasso.view.commands.RandomExpression;

/**
 * Tests the RandomExpression class
 * 
 * @author Sarah Lathrop
 */

class RandomExpressionTest {

	Random randGen;
	Stack<Token> postfix;
	ExpressionTreeGenerator expressionTree;
	
	@BeforeEach
	void setUp() throws Exception {
		int seed = 1234;
		randGen = new Random(seed);
		expressionTree = new ExpressionTreeGenerator();
	}

	@Test
	void buildTest() {
		//get string, tokenize it to postfix, check that is within a certain size rage
		// the maximum size of an expression for depth i is 2^0 + 2^1 + ... + 2^i
		// the minimum size of an expression for depth i is i+1
		for (int i=0; i<=10; i++) {
		String expression = RandomExpression.build(i, randGen);
		System.out.println(expression);
		postfix = expressionTree.infixToPostfix (expression);
		System.out.println(postfix);
		double sizeExpected = postfix.size();
		double sizeMin = i + 1;
		double sizeMax = 0;
			for (int j=0; j<=i; j++) {
				sizeMax = sizeMax + Math.pow(2, i);
			}
		assertTrue((sizeExpected >= sizeMin) && (sizeExpected <= sizeMax));
		}
	}
	

}
