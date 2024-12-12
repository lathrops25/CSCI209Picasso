

package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Modulo;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the modulo operator ("%").
 * @author Naka Assoumatine
 */
public class ModuloAnalyzer implements SemanticAnalyzerInterface {

    @Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        tokens.pop(); // Remove the modulo token
        // Process the operands (right first, then left)
        ExpressionTreeNode paramETNRight = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
        ExpressionTreeNode paramETNLeft = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
                
        return new Modulo(paramETNLeft, paramETNRight);
    }
}
