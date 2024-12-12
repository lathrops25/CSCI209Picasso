package picasso.view.commands;

import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.Command;

import java.util.Random;
import java.util.Arrays;



/**
 * Class that generates a random expression for the Picasso application to evaluate
 * 
 * @author Sarah Lathrop
 */

public class RandomExpression implements Command<Pixmap>{

	private JTextField textIn;
	private static Random RANDGEN = new Random();
	
	//class variables (can import these using prop and conf but it was becoming too complicated)
	// add when add new function/operator
	 private static final String[] BINARYOPERATORS = {"+", "*", "^", "/", "%", "-"};
	 private static final String[] UNARYFUNCTIONS = {"!", "tan", "abs", "log", "cos", "sin", "ceil", "floor", "wrap", "atan", "clamp", "exp", "rgbToYCrCb", "yCrCbToRGB"};
	 private static final String[] NODEPTHOPERATORS = {"x", "y", "constant", "random()"};
	 private static final String[] MULTIFUNCTIONS = {"perlinColor", "perlinBW"};
	 private static final String[] ALLOPSFUNC = {"+", "*", "^", "/", "%", "-", "!", "tan", "abs", "log", "cos", "sin", "ceil", "floor", "wrap", "atan", "clamp", "exp", "rgbToYCrCb", "yCrCbToRGB", "perlinColor", "perlinBW"}; //add more when needed
	 private static final String PARENTR = ")";
	 private static final String PARENTL = "(";
	 private static final String COMMA = ",";


	/**
	 * Constructor, creates a RandoExpression object
	 * @param textIn- the text that is entered into the text field on the GUI
	 */
	public RandomExpression(JTextField textIn) {
		this.textIn = textIn;
	}
	
	/**
	 * Random expression execute method. Occurs when "Random" is clicked on the GUI
	 * @param target- Pixmap target to display
	 * @see picasso.util.Command#execute(java.lang.Object)
	 */
	public void execute (Pixmap target) {
	
		
		//generate depth random number between 0-9 as the depth of the function
		int depth = RANDGEN.nextInt(10);
		//change of it being 0 is 1/9, so chance of it being any no depth operator is 1/9*1/4 = 1/36, which is very high.
		// the chance if it being any given unary function is 1/9 * 1/14 (14 unary operators) * 1/4 (4 no depth) = 1/504
		// Want to lower the probability of a no depth operator to similar probability of a given unary function, so we will do 1/9 * 1/9 * 1/4 = 1/324
		// still much higher but lower than the probability before.
		// so, if a 0 is generated the first time, generate another random number (could still be 0)
		
		if (depth == 0) {
			depth = RANDGEN.nextInt(10); 
		}
		// now if it is a 0 that is okay (probability still higher for no depth operator, but close enough for us)
		
		//pass in nesting depth and random expression generator
		String randomExpression = build(depth);
		//sets the text in the text box to the random expression
		textIn.setText(randomExpression); 
	}
	
	/**
	 * Builds a random expression
	 * @param depth- maximum depth of the generated expression
	 * @param randGen- a random generator
	 * @return
	 */
	public static String build(int depth) {
		//initialize the string
		StringBuilder builder = new StringBuilder();
		
		//used all nesting depth, should return the String
		if (depth <= 0) {
		
			String choice0 = randomChoice(NODEPTHOPERATORS);
			if (choice0.equals("constant")) {
				// add a random number
				return randomNumber();
			}
			else {
				// add the variable or random()- random color, to the position in expression
				return choice0;
			}
		}
		else {
			String choice = randomChoice(ALLOPSFUNC);
			
			//if choice is a binary operator
			if(Arrays.asList(BINARYOPERATORS).contains(choice)){
				
				builder.append(PARENTL);
				builder.append(build(depth-1));
				builder.append (PARENTR);
				builder.append(choice);
				builder.append(PARENTL);
				builder.append(build(depth-1));
				builder.append(PARENTR);
			} 
			// if choice is a unary function or unary operator --> 
			//Syntax is "sin" + "(" + build + ")" if unary function or "!" + "(" + build + ")"
			else if (Arrays.asList(UNARYFUNCTIONS).contains(choice)) {
				
				builder.append(choice);
				builder.append(PARENTL);
				builder.append(build(depth - 1));
				builder.append(PARENTR);
			}
			// if choice is a function that takes in 2 parameters
			// syntax is "perlinColor" + "(" + build + "," + build + ")"
			else if (Arrays.asList(MULTIFUNCTIONS).contains(choice)) {
				builder.append(choice);
				builder.append(PARENTL);
				builder.append(build(depth - 1));
				builder.append(COMMA);
				builder.append(build(depth - 1));
				builder.append(PARENTR);
				
			}
		}
		
		String randomExpression = builder.toString();
		return randomExpression;
	}
	
	/**
	 * Generates a random object from a array. 
	 * Specifically, in the Picasso project, generates a random function or operator, depending on the list
	 * @param operators- a array
	 * @param randGen- a random generator
	 * @return randChoice- a random element in the array
	 */
	private static String randomChoice(String[] operators) {
		// generate random number within length of the operators array
		int index = RANDGEN.nextInt(operators.length);
		
		//get the item
		String randChoice = operators[index];
		
		return randChoice;
		
	}
	
	/**
	 * Returns a number between -1 and 1 in String format
	 * @param randGen- a random generator
	 * @return randNum- a random number in String format
	 */
	private static String randomNumber () {
		double rand1 = RANDGEN.nextDouble();
		int negative1 = RANDGEN.nextInt(2);
		if (negative1 == 1) {
			rand1 = -rand1;
		}
		
		String randNum = String.valueOf(rand1);
		return randNum;
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i <= 5; i++) {
			System.out.println(build(i));
		}
	}
}
