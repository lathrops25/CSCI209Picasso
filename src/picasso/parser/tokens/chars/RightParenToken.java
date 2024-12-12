package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a right parenthesis in the Picasso programming language
 */
public class RightParenToken extends CharToken {
	public static final int GROUPING = 1;

	public RightParenToken() {
		super(CharConstants.RIGHT_PAREN);
	}
	
	@Override
    public int getPrecedence() {
        return GROUPING;
    }
}
