package picasso.parser.tokens.functions;

/**
 * Represents the RgbToYCrCb function token
 * 
 * @author Naka ASsoumatine, Allison Hidalgo
 */
public class RgbToYCrCbToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public RgbToYCrCbToken() {
		super("RgbToYCrCb Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
