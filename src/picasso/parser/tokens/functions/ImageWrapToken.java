package picasso.parser.tokens.functions;

/** 
 * Represents the imageWrap function token
 * @author Jonathan Carranza Cortes, Allison Hidalgo
 */
public class ImageWrapToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public ImageWrapToken() {
		super("ImageWrap Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
