package picasso.parser.tokens.functions;

/**
 *  Represents the ceil function token
 *  
 *  @author Jonathan C, Allison Hidalgo
 */

public class CeilToken extends FunctionToken{
	public static final int UNARY = 2;

	public CeilToken() {
		super("Ceil Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
