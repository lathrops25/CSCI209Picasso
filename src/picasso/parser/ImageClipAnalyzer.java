package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageClip;
import picasso.parser.tokens.StringToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing for ImageClip
 * @author Jonathan
 */
public class ImageClipAnalyzer extends UnaryFunctionAnalyzer{
static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();

	
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the imageClip token
		// the parameters are the next token(s) on the stack.
		// But, it needs to be processed
		
		// deal with y-expression
		ExpressionTreeNode paramETN1 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// deal with x-expression
		ExpressionTreeNode paramETN2 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// final pop off to deal with string, last should be string token
		Token token = tokens.pop();
		if (!(token instanceof StringToken)) {
			throw new IllegalArgumentException("Oops, first input must be a string");
		}
		StringToken t = (StringToken) token;
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped != null) {
			return mapped;
		}
		
		
		return new ImageClip(paramETN1, paramETN2, id);
	}
}
