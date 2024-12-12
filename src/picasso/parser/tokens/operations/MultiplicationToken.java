package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
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
	
	@Override
    public int getPrecedence() {
        return Token.MULTIPLY_OR_DIVIDE;  // Return the precedence for multiplication/division
    }
	
	/**
     * Multiplication is left-associative.
     */
    @Override
    public boolean isLeftAssociative() {
        return true;
    }
}
