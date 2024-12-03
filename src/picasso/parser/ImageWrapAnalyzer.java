package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageWrap;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.StringToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing for ImageWrap
 * @author Jonathan Carranza Cortes
 */
public class ImageWrapAnalyzer extends UnaryFunctionAnalyzer{
	
	static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();

	static {
		// We always have x and y defined.
		idToExpression.put("x", new X());
		idToExpression.put("y", new Y());
	}
	
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the imageWrap token
		// the parameters are the next token(s) on the stack.
		// But, it needs to be processed
		
		// deal with y-expression
		ExpressionTreeNode paramETN1 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// deal with x-expression
		ExpressionTreeNode paramETN2 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// final pop off to deal with string, last should be string token
		StringToken t = (StringToken) tokens.pop();
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped != null) {
			return mapped;
		}
		
		
		return new ImageWrap(paramETN1, paramETN2, id);
	}
}
