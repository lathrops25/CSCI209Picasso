package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the modulo token
 * 
 * @Author NAka Assoumatine
 */
public class ModuloToken extends CharToken implements OperationInterface {
    public ModuloToken() {
        super(CharConstants.MOD);
        precedence = Token.MULTIPLY_OR_DIVIDE;
    }
}
