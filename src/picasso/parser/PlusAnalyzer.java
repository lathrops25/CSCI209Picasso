package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Addition;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the plus or "addition function".
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * @author Naka Assoumatine
 * 
 */
public class PlusAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the plus token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		
		ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		return new Addition(paramETNLeft, paramETNRight);
	}

}
