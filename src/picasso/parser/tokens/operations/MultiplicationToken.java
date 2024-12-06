package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication token
 * 
 * @Author Allison Hidalgo
 */
public class MultiplicationToken extends CharToken implements OperationInterface {
    public MultiplicationToken() {
        super(CharConstants.STAR);
    }
}
