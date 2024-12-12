package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the plus sign token
 * 
 */
public class PlusToken extends CharToken implements OperationInterface {
	public static final int ADD_OR_SUBTRACT = 5;
	
	public PlusToken() {
		super(CharConstants.PLUS);
	}
	
	@Override
    public int getPrecedence() {
        return ADD_OR_SUBTRACT;
    }
	
	@Override
    public boolean isLeftAssociative() {
        return true; // Addition is left-associative
    }
}
