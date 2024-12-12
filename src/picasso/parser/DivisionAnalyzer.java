package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Division;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the multiplication operator ("*").
 * @author Sarah Lathrop
 */
public class DivisionAnalyzer implements SemanticAnalyzerInterface {

    @Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        tokens.pop(); // Remove the division token
        
        // Process the operands (right first, then left)
        ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        
        return new Division(paramETNLeft, paramETNRight);
    }
}
