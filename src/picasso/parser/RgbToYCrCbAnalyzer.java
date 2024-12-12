package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RgbToYCrCb;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the RgbToYCrCb function.
 * 
 * @author Naka Assoumatine
 */
public class RgbToYCrCbAnalyzer extends UnaryFunctionAnalyzer{

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove RgbToYCrCb token
		// the parameter is the next token(s) on the stack
		// But, it needs to be processed
		
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		return new RgbToYCrCb(paramETN);
	}

}
