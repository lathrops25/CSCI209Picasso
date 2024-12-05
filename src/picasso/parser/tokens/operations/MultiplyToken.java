package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication token
 * 
 * @Author Allison Hidalgo
 */
public class MultiplyToken extends CharToken implements OperationInterface {
    public MultiplyToken() {
        super(CharConstants.MULTIPLY);
    }
}
