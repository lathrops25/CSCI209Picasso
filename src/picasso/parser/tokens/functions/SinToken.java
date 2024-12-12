package picasso.parser.tokens.functions;

/**
 * Represents the sin function token
 * 
 * @author Naka Assoumatine
 */

public class SinToken extends FunctionToken{
	public static final int UNARY = 2;

	public SinToken() {
		super("Sin Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
