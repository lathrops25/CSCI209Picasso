package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the minus sign token
 * @author Sarah Lathrop
 */
public class MinusToken extends CharToken implements OperationInterface {
	public static final int ADD_OR_SUBTRACT = 5;
	
	public MinusToken() {
		super(CharConstants.MINUS);
	}
	
	@Override
    public int getPrecedence() {
        return ADD_OR_SUBTRACT;
    }
	
	@Override
    public boolean isLeftAssociative() {
        return true; // Subtraction is left-associative
    }
}
