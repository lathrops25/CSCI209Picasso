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
	
	/**
	 * Constructor creates a RandomExpression object
	 * @param textIn 
	 */
	
	//class variables (can import these using prop and conf but it was becoming too complicated)
	// add when add new function/operator
	 private static final String[] BINARYOPERATORS = {"+", "*", "^", "/", "%", "-"};
	 private static final String[] UNARYFUNCTIONS = {"!", "tan", "abs", "log", "cos", "sin", "ceil", "floor", "wrap", "atan", "clamp", "exp", "rgbToYCrCb", "yCrCbToRGB"};
	 private static final String[] NODEPTHOPERATORS = {"x", "y", "constant", "random()"}; //TODO: add random()
	 private static final String[] MULTIFUNCTIONS = {"perlinColor", "perlinBW"};
	 private static final String[] ALLOPSFUNC = 
		 {"+", "*", "^", "/", "%", "-", "tan", "abs", "log", "cos", "sin", "ceil", "floor", "wrap", "atan", "clamp", "exp", "rgbToYCrCb", "yCrCbToRGB", "x", "y", "constant", "random()", "!", "perlinColor", "perlinBW"}; //add more when needed
	 

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
		
		
		Random randGen = new Random();
		
		//generate depth random number between 0-9 as the depth of the function
		int depth = randGen.nextInt(10);
		
		//pass in nesting depth and random expression generator
		String randomExpression = build(depth, randGen);
		//sets the text in the text box to the random expression
		textIn.setText(randomExpression); 
	}
	
	/**
	 * Builds a random expression
	 * @param depth- maximum depth of the generated expression
	 * @param randGen- a random generator
	 * @return
	 */
	public static String build(int depth, Random randGen) {
		//initialize the string
		StringBuilder builder = new StringBuilder();
		String parentR = ")";
		String parentL = "(";
		String comma = ",";
		
		//used all nesting depth, should return the String
		if (depth <= 0) {
		
			String choice0 = randomChoice(NODEPTHOPERATORS, randGen);
			if (choice0 == "constant") {
				// add a random number
				return randomNumber(randGen);
			}
			else {
				// add the variable or random()- random color, to the position in expression
				return choice0;
			}
		}
		else {
			String choice = randomChoice(ALLOPSFUNC, randGen);
			
			//if choice is a binary operator
			if(Arrays.asList(BINARYOPERATORS).contains(choice)){
				
				builder.append(parentL);
				builder.append(build(depth-1, randGen));
				builder.append (parentR);
				builder.append(choice);
				builder.append(parentL);
				builder.append(build(depth-1, randGen));
				builder.append(parentR);
			} 
			// if choice is a unary function or unary operator --> 
			//Syntax is "sin" + "(" + build + ")" if unary function or "!" + "(" + build + ")"
			else if (Arrays.asList(UNARYFUNCTIONS).contains(choice)) {
				
				builder.append(choice);
				builder.append(parentL);
				builder.append(build((depth - 1), randGen));
				builder.append(parentR);
			}
			// if choice is a function that takes in 2 parameters
			// syntax is "perlinColor" + "(" + build + "," + build + ")"
			else if (Arrays.asList(MULTIFUNCTIONS).contains(choice)) {
				builder.append(choice);
				builder.append(parentL);
				builder.append(build((depth - 1), randGen));
				builder.append(comma);
				builder.append(build((depth - 1), randGen));
				builder.append(parentR);
				
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
	private static String randomChoice(String[] operators, Random randGen) {
		// generate random number within length of the operators array
		int index = randGen.nextInt(operators.length);
		
		//get the item
		String randChoice = operators[index];
		
		return randChoice;
		
	}
	
	/**
	 * Returns a number between -1 and 1 in String format
	 * @param randGen- a random generator
	 * @return randNum- a random number in String format
	 */
	private static String randomNumber (Random randGen) {
		Random randObj = new Random();
		double rand1 = randObj.nextDouble();
		int negative1 = randObj.nextInt(2);
		if (negative1 == 1) {
			rand1 = -rand1;
		}
		
		String randNum = String.valueOf(rand1);
		return randNum;
	}
}
