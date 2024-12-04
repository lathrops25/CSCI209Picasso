package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.operations.OperationInterface;

/**
 * Represents a quotation mark in the Picasso programming language
 * 
 */
public class QuoteToken extends CharToken implements OperationInterface{
	
	public QuoteToken() {
		super(CharConstants.QUOTE);
	}
}