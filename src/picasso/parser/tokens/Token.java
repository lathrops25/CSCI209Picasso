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

	private String description;

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
    public abstract int getPrecedence();

}
