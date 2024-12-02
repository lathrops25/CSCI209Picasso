package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.StringNode;
import picasso.parser.language.expressions.Variable;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;

/**
 * Handle an identifier token 
 * 
 * @author Sara Sprenkle, Sarah Lathrop
 *
 */
public class IdentifierAnalyzer implements SemanticAnalyzerInterface {

	// Used to check if string is an image
	private String[] validExtensions = {".jpg", ".png"};
    
	static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();

	static {
		// We always have x and y defined.
		idToExpression.put("x", new X());
		idToExpression.put("y", new Y());
	}

	
	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		IdentifierToken t = (IdentifierToken) tokens.pop();
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped == null) {
			idToExpression.put(id, new Variable (id));
			mapped = idToExpression.get(id);
		}

		// TODO : What should we do if we don't recognize the identifier?
		// Is that an error? Or, could there a valid reason?
//		if (fileExtension(id)) {
//			return new StringNode(id);
//		}
		
		return mapped;
	}

	/**
	 * helper function that checks for a file extension
	 * used to check if something is an image
	 * @return true
	 */
	private boolean fileExtension(String fileEx) {
		
		for (int i=0; i<= validExtensions.length; i++) {
			if (fileEx.contains(validExtensions[i])) {
				return true;
			}
		}
		
		return false;
	}
}
