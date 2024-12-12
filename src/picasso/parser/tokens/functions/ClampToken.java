package picasso.parser.tokens.functions;

/**
 * This represents the clamp function token 
 * 
 * @author Allison Hidalgo
 */
public class ClampToken extends FunctionToken {
	public static final int UNARY = 2;

	public ClampToken() {
		super("Clamp Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}

