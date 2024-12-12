package picasso.parser.tokens.functions;

/**
 * Represents the floor function token
 * 
 */
public class FloorToken extends FunctionToken {
	public static final int UNARY = 2;

	public FloorToken() {
		super("Floor Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
