package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication token
 * 
 * @Author Sarah Lathrop
 */
public class DivisionToken extends CharToken implements OperationInterface {
	public static final int MULTIPLY_OR_DIVIDE = 4;
   
	public DivisionToken() {
        super(CharConstants.SLASH);
    }
	
	@Override
    public int getPrecedence() {
        return MULTIPLY_OR_DIVIDE;
    }
	
}
