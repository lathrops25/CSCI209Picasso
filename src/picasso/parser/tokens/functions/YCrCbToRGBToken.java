package picasso.parser.tokens.functions;

/**
 * Represents the YCrCbToRGB function token
 * 
 * @author Naka ASsoumatine, Allison Hidalgo
 */
public class YCrCbToRGBToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public YCrCbToRGBToken() {
		super("YCrCbToRGB Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
