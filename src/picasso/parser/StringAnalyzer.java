package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.StringNode;
import picasso.parser.language.expressions.Variable;
import picasso.parser.tokens.StringToken;
import picasso.parser.tokens.Token;

/**
 *  Handles parsing the quotations
 *  @author Jonathan
 */
public class StringAnalyzer implements SemanticAnalyzerInterface{
	
	static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();

	
	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		StringToken t = (StringToken) tokens.pop();
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped == null) {
			idToExpression.put(id, new Variable (id));
			mapped = idToExpression.get(id);
		}

		return new StringNode(id);
	}
}
