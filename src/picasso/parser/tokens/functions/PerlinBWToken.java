package picasso.parser.tokens.functions;

/**
 * Represents the PerlinBW function token
 * 
 * @author Naka Assoumatine
 */

public class PerlinBWToken extends FunctionToken {
	public static final int UNARY = 2;

	public PerlinBWToken() {
		super("PerlinBW Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
