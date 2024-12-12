
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the negation token
 * 
 * @Author Naka Assoumatine
 */
public class NegationToken extends CharToken implements OperationInterface {
    public NegationToken() {
        super(CharConstants.BANG);
    }
}
