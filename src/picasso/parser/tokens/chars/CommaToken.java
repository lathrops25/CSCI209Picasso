/**
 * 
 */
package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a comma in the Picasso programming language
 */
public class CommaToken extends CharToken {
	public static final int COMMA = 6;

	public CommaToken() {
		super(CharConstants.COMMA);
	}
	
	@Override
    public int getPrecedence() {
        return COMMA;
    }

}
