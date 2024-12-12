package picasso.parser.tokens.functions;

/**
 * Represents the PerlinColor function token
 * 
 * @author Naka Assoumatine
 */

public class PerlinColorToken extends FunctionToken {
	public static final int UNARY = 2;

	public PerlinColorToken() {
		super("PerlinColor Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
