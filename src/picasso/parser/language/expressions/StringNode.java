package picasso.parser.language.expressions;

import java.awt.Color;

import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the String operation
 * 
 * @author Jonathan Carranza Cortes
 */

public class StringNode extends ExpressionTreeNode{
	
	private Pixmap testImage;
	private int imageWidth;
	private int imageHeight;

	// relative pathname for images in image folders
	private String preFix = "images/";
	private String myFileName;
	
	//instance variable
	private String myString;
	
	// Used to check if string is an image
	private String[] validExtensions = {".jpg", ".png"};
	
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	
	/**
	 * Constructor
	 * @param file name as string
	 */
	public StringNode(String myString) {
		this.myString = myString;
		
		// check if image has valid extension
		if (!fileExtension(myString)) {
			throw new IllegalArgumentException("File is not a .jpg or .png. Check to make sure its a valid extension");
		}
		
		// checks if partial path is stated
		if (myString.contains(preFix)) {
			myFileName = myString;
		}
		// adds prefix to string to get complete path
		else {
			myFileName = preFix + myString;
		}
		
		
		testImage = new Pixmap(myFileName);
		imageWidth = testImage.mySize.width;
		imageHeight = testImage.mySize.height;
	}
	
	/**
	 * Converting from domain space to image space
	 */
	public int imageToRegScale(double value, int bounds) {
		double range = DOMAIN_MAX - DOMAIN_MIN;
		return (int) (((value - DOMAIN_MIN) / range) * bounds);
	}
	/**
	 * Converting to a double within [-1, 1] range
	 */
	public double convert(double xColor) {
		return (xColor / 255) * 2.0 - 1.0;
	}
	
	/**
	 * helper function that checks for a file extension
	 * used to check if something is an image
	 * @return true
	 */
	private boolean fileExtension(String fileEx) {
		
		for (int i=0; i < validExtensions.length; i++) {
			if (fileEx.contains(validExtensions[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Evaluate each pixel in an image
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		
		int XCord = imageToRegScale(x, imageWidth);
		int YCord = imageToRegScale(y, testImage.mySize.height);
		
		if (!testImage.isInBounds(XCord, YCord)) {
			if (XCord >= imageWidth) {
				XCord = imageWidth-1;
			}
			if (YCord >= imageHeight) {
				YCord = imageHeight-1;
			}
		}
		Color RGB = new Color(testImage.myImage.getRGB(XCord, YCord));
		
		double red = convert((double)RGB.getRed());
		double green = convert((double)RGB.getGreen());
		double blue = convert((double)RGB.getBlue());

		return new RGBColor(red, green, blue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof StringNode)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		StringNode uf = (StringNode) o;

		// check if their parameters are equal
		if (!this.myString.equals(uf.myString)) {
			return false;
		}
	
		return true;
	}

}
