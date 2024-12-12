package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.PerlinBW;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the "perlinBW function".
 * 
= * @author Naka Assoumatine
 * 
 */
public class PerlinBWAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the PerlinBW token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		
		ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		return new PerlinBW(paramETNLeft, paramETNRight);
	}

}
