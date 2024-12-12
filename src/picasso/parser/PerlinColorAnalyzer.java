package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.PerlinColor;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the plus or "perlinColor function".
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * @author Naka Assoumatine
 * 
 */
public class PerlinColorAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the perlinColor token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		
		ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		return new PerlinColor(paramETNLeft, paramETNRight);
	}

}
