package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Multiplication;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the multiplication operator ("*").
 * @author allisonhidalgo
 */
public class MultiplicationAnalyzer implements SemanticAnalyzerInterface {

    @Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        tokens.pop(); // Remove the multiplication token
        // Process the operands (right first, then left)
        ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        
        return new Multiplication(paramETNLeft, paramETNRight);
    }
}
