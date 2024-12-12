package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication token
 * 
 * @Author Sarah Lathrop
 */
public class DivisionToken extends CharToken implements OperationInterface {
   
	public DivisionToken() {
        super(CharConstants.SLASH);
    }
	
}
