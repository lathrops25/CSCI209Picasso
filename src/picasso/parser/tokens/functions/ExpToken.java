package picasso.parser.tokens.functions;

/**
 * Represents the Exponential function token
 * 
 * @author Naka Assoumatine, Allison Hidalgo
 */

public class ExpToken extends FunctionToken {
	public static final int EXPONENT = 3;
	
	public ExpToken() {
		super("Exp Function Token");
	}
	
	/**
     * Returns the precedence of the exponent token
     * 
     * @return precedence value for exponentiation
     */
    @Override
    public int getPrecedence() {
        return EXPONENT;
    }

}
