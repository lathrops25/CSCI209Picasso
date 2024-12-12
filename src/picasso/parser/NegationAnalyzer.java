package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Negation;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the negation operator ("!").
 * 
 * @author Naka Assoumatine
 */
public class NegationAnalyzer implements SemanticAnalyzerInterface {

    @Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        tokens.pop(); // Remove the negation token
        // Process the operands (right first, then left)
        ExpressionTreeNode paramETNExpression = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        
        return new Negation(paramETNExpression);
    }
}
