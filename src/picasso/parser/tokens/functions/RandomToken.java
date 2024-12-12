package picasso.parser.tokens.functions;

/** 
 * Represents the random function token
 * @author Sarah Lathrop, Allison Hidalgo
 */
public class RandomToken extends FunctionToken {
	public static final int UNARY = 2;
	
	public RandomToken() {
		super("Random Function Token");
	}
	
	@Override
    public int getPrecedence() {
        return UNARY;
    }
}
