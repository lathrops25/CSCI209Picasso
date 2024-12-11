package picasso.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.*;
import picasso.parser.tokens.chars.*;
import picasso.parser.tokens.functions.*;
import picasso.parser.tokens.operations.*;

/**
 * Parses a string into an expression tree based on rules for arithmetic.
 * 
 * @author former student solution
 * @author Robert C. Duvall (added comments, exceptions)
 * @author Sara Sprenkle modified for Picasso
 */
public class ExpressionTreeGenerator {

	// TODO: Do these belong here?
	private static final int CONSTANT = 0;
	private static final int GROUPING = 1; // parentheses
	private static final int UNARY = 2;
	private static final int EXPONENT = 3;
	private static final int MULTIPLY_OR_DIVIDE = 4;
	private static final int ADD_OR_SUBTRACT = 5;
	private static final int COMMA = 6;

	/**
	 * Converts the given string into expression tree for easier manipulation.
	 * 
	 * @param infix - a non-empty expression to parse.
	 * 
	 * @return ExpressionTreeNode representing the root node of the given infix
	 *         formula
	 */
	public ExpressionTreeNode makeExpression(String infix) {
		Stack<Token> postfix = infixToPostfix(infix);

		if (postfix.isEmpty()) {
			return null;
		}

		// System.out.println("Process postfix expression");
		SemanticAnalyzer semAnalyzer = SemanticAnalyzer.getInstance();

		ExpressionTreeNode root = semAnalyzer.generateExpressionTree(postfix);

		// Is this the best place to put this check? 
			// it makes sure the tree is complete but then it doesn't detect the error until the tree is generated 
		if (!postfix.isEmpty()) {
			throw new ParseException("Extra operands without operators or functions");
		}
		return root;
	}

	/**
	 * This method converts the String infix expression to a Stack of tokens, which
	 * are in postfix.
	 * 
	 * @param infix the String to parse, as we would typically write it
	 * @return a stack of tokens, in postfix order
	 */
	public Stack<Token> infixToPostfix(String infix) {

		Tokenizer tokenizer = new Tokenizer();
		List<Token> tokens = tokenizer.parseTokens(infix);

		return infixToPostfix(tokens);
	}

	/**
	 * This method converts the List of tokens (in infix order) to a Stack of
	 * tokens, which are in postfix.
	 * 
	 * @param tokens the Tokens, in infix order
	 * @return a stack of tokens, in postfix order
	 */
	private Stack<Token> infixToPostfix(List<Token> tokens) {
		// Algorithm for converting infix to postfix was adapted from
		// http://en.wikipedia.org/wiki/Shunting_yard_algorithm
		// Needed to handle identifiers and colors, which aren't in the original
		// algorithm.
		// May need to modify/update further...

		Stack<Token> operators = new Stack<Token>();
		Stack<Token> postfixResult = new Stack<Token>();

		//Iterator<Token> iter = tokens.iterator();

	//These are questions related to the old code 
		// TO DISCUSS: Is this the correct way to design this code?
			//the while loop makes it difficult to test since its responsible for determining the token and what to do with it 
		// What is the code smell? What is the alternative?
			// code smell is the repeated if statemnts, it makes it difficult to maintain and update 
		
		
		//each token is being handeled explicitly and itll be easier to extend 
		for(Token token : tokens) {
			if (isOperand(token)) {
				postfixResult.push(token);
			} else if (token instanceof FunctionToken || token instanceof LeftParenToken) {
				operators.push(token);
			} else if (token instanceof RightParenToken) {
				handelRightParenthesis(operators, postfixResult);
			} else if (token instanceof OperationInterface) {
                handelOperator(token, operators, postfixResult);
            } else if (token instanceof CommaToken){
            	handleCommaToken(operators, postfixResult);
            } else {
                throw new ParseException("Invalid token: " + token);
            }
        }
				
				
				/*
				 * while there is an operator, o2, at the top of the stack (this excludes left
				 * parenthesis), and either
				 * 
				 * o1 is left-associative and its precedence is less than (lower precedence) or
				 * equal to that of o2, or o1 is right-associative and its precedence is less
				 * than (lower precedence) that of o2 (SS: second case is not reflected in below
				 * code),
				 * 
				 * pop o2 off the stack, onto the output queue;
				 */

				// While there are operators on the stack:
				// - Pop operators with higher or equal precedence (for left-associative operators)
				// - Stop at left parentheses or lower precedence operators
			while (!operators.isEmpty()) {
				Token top = operators.pop();
				if(top instanceof LeftParenToken || top instanceof RightParenToken) {
					throw new ParseException("Mismatch Parenthesis");
				}
				postfixResult.push(top);
			}
			return postfixResult;
	}
				// Until the token at the top of the stack is a left
				// parenthesis, pop operators off the stack onto the output
				// queue.
	
	/**
	 * This checks if the token is an Number, color, identifier, or string
	 * 
	 * @param token
	 * @return true if token is an operand
	 */
	private boolean isOperand(Token token) {
        return token instanceof NumberToken || 
               token instanceof ColorToken || 
               token instanceof IdentifierToken || 
               token instanceof StringToken;
    }
	
	/**
	 * This handles the right parenthesis by popping the operators until a left parenthesis is found
	 * 
	 * @param operator
	 */
	private void handelRightParenthesis(Stack<Token>operators, Stack<Token> postfixResult) {
		while (!operators.isEmpty() && !(operators.peek() instanceof LeftParenToken)) {
			postfixResult.push(operators.pop());
		}
		
		if(operators.isEmpty() || !(operators.peek() instanceof LeftParenToken)) {
			throw new ParseException("Mismatched Parenthesis");
		}
		
		operators.pop();
		
		if(!operators.isEmpty() && operators.peek() instanceof FunctionToken) {
			postfixResult.push(operators.pop());
		}
	}
	
	/**
	 * This handles the operator token
	 */
	private void handelOperator(Token token, Stack<Token> operators, Stack<Token> postfixResults) {
		while (!operators.isEmpty() && !(operators.peek() instanceof LeftParenToken) && orderOfOperation(token) >= orderOfOperation(operators.peek())) {
			postfixResults.push(operators.pop());
		}
		operators.push(token);
	}
	
	/**
	 * This handles the comma token 
	 */
	private void handleCommaToken(Stack<Token> operators, Stack<Token> postfixResult) {
		while (!operators.isEmpty() && !(operators.peek() instanceof LeftParenToken)) {
			postfixResult.push(operators.pop());
		}
		if (operators.isEmpty()) {
			throw new ParseException("Misplaed comma or mismatched parenthesis");
		}
	}

	/**
	 * Determines precedent level of given operator tokens 
	 * @param token
	 * @return precedence level
	 */
	private int orderOfOperation(Token token) {
        if (token instanceof CommaToken) {
            return COMMA;
        } else if (token instanceof PlusToken || token instanceof MinusToken) {
            return ADD_OR_SUBTRACT;
        } else if (token instanceof MultiplyToken || token instanceof DivideToken || token instanceof ModulusToken) {
            return MULTIPLY_OR_DIVIDE;
        } else if (token instanceof ExponentiateToken) {
            return EXPONENT;
        } else if (token instanceof UnaryOperatorToken) {
            return UNARY;
        } else {
            return CONSTANT;
        }
    }
}
