package picasso.parser.tokens.operations;

/**
 * A marker interface
 * 
 */
public interface OperationInterface {
	/**
     * Gets the precedence of the operation.
     * 
     * @return precedence level
     */
    int getPrecedence();
	
	/**
     * Determines if the operation is left-associative.
     * 
     * @return true if the operation is left-associative
     */
    default boolean isLeftAssociative() {
        return true;
    }

}
