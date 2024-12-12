package picasso.parser.tokens.functions;

/**
 * Represents the cos function token
 * 
 * @author Jonathan
 */
public class CosToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public CosToken() {
		super("Cos Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
