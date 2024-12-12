package picasso.parser.tokens.functions;

/**
 * Represents the Wrap function token
 * 
 * @author Gabriel Hogan, Allison Hidalgo
 */
public class WrapToken extends FunctionToken {
	public static final int UNARY = 2;

	public WrapToken() {
		super("Wrap Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
