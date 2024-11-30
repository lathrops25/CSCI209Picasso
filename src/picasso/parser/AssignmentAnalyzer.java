package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Assignment;
import picasso.parser.language.expressions.Variable;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the assignment operator
 * 
 * @author Sarah Lathrop
 */
public class AssignmentAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the assignment token
		// the parameters are the next tokens on the stack.
		// id token is the last on the stack
		
		
		ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		//make sure that this left hand side is an id
		if ((tokens.size() == 1) && (tokens.peek() instanceof IdentifierToken)) {
			ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
			if (paramETNLeft instanceof Variable)
			{
				Variable lhs = (Variable) paramETNLeft;
				Assignment result = new Assignment(lhs, paramETNRight);
				return result;
			}
			else
			{
				throw new ParseException("Left hand side identifier not a variable");
			}
		}
		else {
			throw new ParseException("Left hand side not an identifier");
		}
	}

}
