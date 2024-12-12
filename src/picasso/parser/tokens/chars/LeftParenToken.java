package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a left parenthesis in the Picasso programming language
 * 
 */
public class LeftParenToken extends CharToken {
	public static final int GROUPING = 1;
	
	public LeftParenToken() {
		super(CharConstants.LEFT_PAREN);
	}
	
	@Override
    public int getPrecedence() {
        return GROUPING;
    }
}
