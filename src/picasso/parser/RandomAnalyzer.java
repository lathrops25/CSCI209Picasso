package picasso.parser;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RandomFunction;
import picasso.parser.tokens.Token;

/**
 * Handle parsing the random function
 * 
 * @author Sarah Lathrop
 */
public class RandomAnalyzer implements SemanticAnalyzerInterface {
	
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); 
		if (!tokens.isEmpty()) {
			throw new ParseException("Opps, can't pass in a parameter to random");
		}
		return new RandomFunction();
	}

}