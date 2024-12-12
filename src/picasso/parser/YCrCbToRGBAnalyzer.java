package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.YCrCbToRGB;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the YCrCbToRGB function.
 * 
 * @author Naka Assoumatine
 */
public class YCrCbToRGBAnalyzer extends UnaryFunctionAnalyzer{

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove YCrCbToRGB token
		// the parameter is the next token(s) on the stack
		// But, it needs to be processed
		
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		return new YCrCbToRGB(paramETN);
	}

}
