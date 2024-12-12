package picasso.parser.tokens.functions;

/** 
 * Represents the log function token
 * 
 * @author Jonathan
 */
public class LogToken extends FunctionToken{
	public static final int UNARY = 2;
	
	public LogToken() {
		super("Sin Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }

}
