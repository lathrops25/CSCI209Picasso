package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents the ImageWrap function in the Picasso language
 * @author Jonathan Carranza Cortes
 */
//public class ImageWrap extends UnaryFunction{
public class ImageWrap extends ExpressionTreeNode{

	private ExpressionTreeNode xExp;
	private ExpressionTreeNode yExp;
	private StringNode thisOne;

	
	public ImageWrap(ExpressionTreeNode yExp, ExpressionTreeNode xExp, String imageName) {

		// Saving expression nodes to evaluate later
		this.yExp = yExp;
		this.xExp = xExp;
		
		// Used for the image
		thisOne = new StringNode(imageName);
	}
	
	@Override
	public RGBColor evaluate(double x, double y) {
		
		// evaluate the x and y expressions
		RGBColor xRGB = xExp.evaluate(x, y);
		RGBColor yRGB = yExp.evaluate(x, y);

		// normalize and wrap to a double
		// thought process is using getRed() and getGreen() to get coordinate since
		// its already in a range from [-1, 1]
		double newX = Wrap.wrap(xRGB.getRed());
		double newY = Wrap.wrap(yRGB.getGreen());
		
		// get the RGB color of image pixel at wrapped coordinates
		RGBColor stringExp = thisOne.evaluate(newX, newY); 
		
		return stringExp;
	}
	
	
	
}
	
