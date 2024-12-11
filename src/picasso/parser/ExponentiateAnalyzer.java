package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Exponentiate;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the exponentiate operator ("^").
 * @author Naka Assoumatine
 */
public class ExponentiateAnalyzer implements SemanticAnalyzerInterface {

    @Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        tokens.pop(); // Remove the exponentiate token
        // Process the operands (right first, then left)
        ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        
        return new Exponentiate(paramETNLeft, paramETNRight);
    }
}
