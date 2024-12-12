package picasso.parser.tokens.functions;

/**
 * Represents the imageClip function token
 * @author Jonathan
 */
public class ImageClipToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public ImageClipToken() {
		super("ImageClipo Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
