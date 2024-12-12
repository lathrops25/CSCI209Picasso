package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JTextField;

import picasso.Main;
import picasso.database.ExpressionDB;
import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.ParseException;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.EvaluateException;
import picasso.util.Command;
import picasso.view.ErrorDialog;

/**
 * Evaluate an expression for each pixel in a image.
 * 
 * @author Robert C Duvall
 * @author Sara Sprenkle
 * @author Jonathan Carranza Cortes
 */
public class Evaluator implements Command<Pixmap> {

	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;

	private static String inputString;
	private JTextField textIn;
	private List<String> history;

	/**
	 * Constructor that takes input from text field and saves it
	 * 
	 * @param text field
	 */
	public Evaluator(JTextField textIn, List<String> history) {
		this.textIn = textIn;
		this.history = history;
	}

	/**
	 * Evaluate an expression for each point in the image.
	 */
	public void execute(Pixmap target) {

		try {
			ExpressionTreeNode expr = createExpression();

			// evaluate it for each pixel
			Dimension size = target.getSize();
			for (int imageY = 0; imageY < size.height; imageY++) {
				double evalY = imageToDomainScale(imageY, size.height);
				for (int imageX = 0; imageX < size.width; imageX++) {
					double evalX = imageToDomainScale(imageX, size.width);
					Color pixelColor = expr.evaluate(evalX, evalY).toJavaColor();
					target.setColor(imageX, imageY, pixelColor);
				}
			}
		} catch (Exception e) {
			ErrorDialog.showDialog("Error in Evaluator: <br/>" + e.getMessage());
			return;
		}

		if (ExpressionDB.dbEnabled) {
			ExpressionDB db = new ExpressionDB();
			long newId = db.insertExpression(inputString);
//			System.out.println("Inserted new expression with ID: " + newId);z
			Main.viewer.loadData();
		}

	}

	/**
	 * Convert from image space to domain space.
	 */
	protected double imageToDomainScale(int value, int bounds) {
		double range = DOMAIN_MAX - DOMAIN_MIN;
		return ((double) value / bounds) * range + DOMAIN_MIN;
	}

	/**
	 * Creates expression based on text in a text field
	 */
	private ExpressionTreeNode createExpression() {
		// Note, when you're testing, you can use the ExpressionTreeGenerator to
		// generate expression trees from strings, or you can create expression
		// objects directly (as in the commented statement below).

		// String test = "x + y";
		// Take the current expression in the text field
		inputString = textIn.getText();
		history.add(inputString);

		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		return expTreeGen.makeExpression(inputString);
	}

}
