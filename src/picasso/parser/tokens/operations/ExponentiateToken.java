package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the exponentiate token
 * 
 * @Author Naka Assoumatine, Allison Hidalgo 
 */
public class ExponentiateToken extends CharToken implements OperationInterface {
    public ExponentiateToken() {
        super(CharConstants.CARET);
        precedence= Token.EXPONENT;
    }
}



