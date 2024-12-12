package picasso.parser.tokens.functions;

/**
 * Represents the floor function token
 * 
 * @author Sarah Lathrop
 */

public class AbsToken extends FunctionToken {
	public static final int UNARY = 2;
	
	public AbsToken() {
		super("Abs Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
