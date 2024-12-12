package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the plus sign token
 * 
 */
public class PlusToken extends CharToken implements OperationInterface {
	
	public PlusToken() {
		super(CharConstants.PLUS);
		precedence= Token.ADD_OR_SUBTRACT;
	}
	
	
	@Override
    public boolean isLeftAssociative() {
        return true; // Addition is left-associative
    }
}
