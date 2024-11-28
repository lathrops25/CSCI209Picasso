package picasso.parser.language.expressions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import picasso.parser.language.ExpressionTreeNode;


public class StringNode extends ExpressionTreeNode{
	
	public static final Dimension DEFAULT_SIZE = new Dimension(300, 300);
	public static final Color DEFAULT_COLOR = Color.BLACK;
	public static final String DEFAULT_NAME = "Picasso";

	// relative pathname for images in image folders
	private String myFileName = "images/";
	private BufferedImage myImage;
	private Dimension mySize;
	
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	
	public StringNode(String myString) {
		myFileName += myString;
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
	 * Evaluate each pixel in an image
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		int XCord = imageToRegScale(x, mySize.width);
		int YCord = imageToRegScale(y, mySize.height);
		Color RGB = new Color(myImage.getRGB(XCord, YCord));
		
		double red = convert((double)RGB.getRed());
		double green = convert((double)RGB.getGreen());
		double blue = convert((double)RGB.getBlue());

		return new RGBColor(red, green, blue);
	}

}
