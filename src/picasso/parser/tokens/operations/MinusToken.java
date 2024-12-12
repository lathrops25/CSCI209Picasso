package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the minus sign token
 * @author Sarah Lathrop
 */
public class MinusToken extends CharToken implements OperationInterface {
	
	public MinusToken() {
		super(CharConstants.MINUS);
		precedence= Token.ADD_OR_SUBTRACT;
	}
	
	@Override
    public boolean isLeftAssociative() {
        return true; // Subtraction is left-associative
    }
}
