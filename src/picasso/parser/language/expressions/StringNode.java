package picasso.parser.language.expressions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the String operation
 * 
 * @author Jonathan Carranza Cortes
 */

public class StringNode extends ExpressionTreeNode{
	
	public static final Dimension DEFAULT_SIZE = new Dimension(300, 300);
	public static final Color DEFAULT_COLOR = Color.BLACK;
	public static final String DEFAULT_NAME = "Picasso";

	// relative pathname for images in image folders
	private String preFix = "images/";
	private String myFileName;
	private BufferedImage myImage;
	private Dimension mySize;
	
	// Used to check if string is an image
	private String[] validExtensions = {".jpg", ".png"};
	
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	
	/**
	 * Constructor
	 * @param file name as string
	 */
	public StringNode(String myString) {
		// check if image has valid extension
		if (!fileExtension(myString)) {
			throw new IllegalArgumentException("File is not a .jpg or .png, OR missing set of quote");
		}
		
		// checks if partial path is stated
		if (myString.contains(preFix)) {
			myFileName = myString;
		}
		// adds prefix to string to get complete path
		else {
			myFileName = preFix + myString;
		}
		getLocalPic(myFileName);
	}

	/**
	 * Create a copy pixmap from the given local file
	 * @param fileName complete pathname of local file
	 */
	public void getLocalPic(String fileName) {
		if (fileName == null) {
			createImage(DEFAULT_SIZE.width, DEFAULT_SIZE.height, DEFAULT_COLOR);
		} else {
			read(fileName);
		}
	}	
	
	
	private void createImage(int width, int height, Color color) {
		myFileName = DEFAULT_NAME;
		myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		mySize = new Dimension(width, height);
		for( int i=0; i < width; i++ ) {
			for(int j=0; j<width; j++ ) {
				setColor(i, j, color);
			}
		}
	}
	
	/**
	 * Read the image named by fileName
	 * 
	 * @param fileName the name of the image file to be read in
	 */
	public void read(String fileName) {
		try {
			myFileName = fileName;
			myImage = ImageIO.read(new File(myFileName));
			mySize = new Dimension(myImage.getWidth(), myImage.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setColor(int x, int y, Color value) {
		if (isInBounds(x, y)) {
			myImage.setRGB(x, y, value.getRGB());
		}
	}
	
	/**
	 * Determine if the given (x,y) coordinate is within the bounds of this image.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if the given (x,y) coordinate is within the bounds of this
	 *         image.
	 */
	public boolean isInBounds(int x, int y) {
		return (0 <= x && x < mySize.width) && (0 <= y && y < mySize.height);
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
		
		int XCord = imageToRegScale(x, mySize.width);
		int YCord = imageToRegScale(y, mySize.height);
		
		if (!isInBounds(XCord, YCord)) {
			if (XCord >= mySize.width) {
				XCord = mySize.width-1;
			}
			if (YCord >= mySize.width) {
				YCord = mySize.height-1;
			}
		}
		Color RGB = new Color(myImage.getRGB(XCord, YCord));
		
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
		if (!this.myFileName.equals(uf.myFileName)) {
			return false;
		}
		if (!this.myImage.equals(uf.myImage)) {
			return false;
		}
		if (!this.mySize.equals(uf.mySize)) {
			return false;
		}
		return true;
	}

}
