package picasso.parser.tokens.functions;

/**
 * Represents the tan function token
 * 
 * @author Jonathan
 */
public class TanToken extends FunctionToken{
	public static final int UNARY = 2;

	public TanToken() {
		super("Tan Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
