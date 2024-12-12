/**
 * 
 */
package picasso.parser.tokens;

/**
 * Represents tokens in the Picasso programming language
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle, Allison Hidalgo
 *
 */
public abstract class Token {
	public static final int CONSTANT = 0;
	public static final int GROUPING = 1; // parentheses 
	public static final int UNARY = 2; 
	public static final int EXPONENT = 3; 
	public static final int MULTIPLY_OR_DIVIDE = 4;
	public static final int ADD_OR_SUBTRACT = 5;
	public static final int COMMA = 6;

	private String description;
	protected int precedence = CONSTANT;

	/**
	 * Creates a token with the given description
	 * 
	 * @param description the token's description
	 */
	public Token(String description) {
		this.description = description;
	}

	/**
	 * Represents the token by its description
	 * 
	 * @return the token's description
	 */
	@Override
	public String toString() {
		return description;
	}

	/**
	 * Returns true if this token represents a constant, false otherwise
	 * 
	 * @return true iff this Token represents a constant
	 */
	public abstract boolean isConstant();

	/**
	 * Returns true if this token represents a function, false otherwise
	 * 
	 * @return true iff this Token represents a function
	 */
	public abstract boolean isFunction();

	/**
	 * Returns false unless token is an operand
	 * @param token
	 * @return false
	 */
	public boolean isOperand() {
		return false;
	}
	
	/**
     * Gets the precedence of the token
     * @return the precedence level of the token
     */
    public int getPrecedence() {
        return precedence;
    }

}
