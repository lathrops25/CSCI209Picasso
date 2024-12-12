package picasso.parser.tokens.functions;

/**
 * Represents the atan function token
 * 
 * @author Jonathan
 */

public class AtanToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public AtanToken() {
		super("Atan Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
