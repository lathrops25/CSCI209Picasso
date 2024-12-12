/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.ImageClip;
import picasso.parser.language.expressions.ImageWrap;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;

/**
 * Test equals methods for ImageWrap and ImageClip
 * 
 * @author Jonathan Carranza Cortes
 *
 */

public class EqualsTests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testImageWrapEquals() {
		String fileName = "images/testImage.jpg";
		ImageWrap myTree = new ImageWrap(new Y(), new Addition(new X(), new X()), fileName);
		ImageWrap sameTree = new ImageWrap(new Y(), new Addition(new X(), new X()), fileName);
		ImageWrap otherTree = new ImageWrap(new Y(), new X(), fileName);
		ImageClip aTree = new ImageClip(new Y(), new Addition(new X(), new X()), fileName);

		ImageWrap testTree = myTree;
		assertTrue(myTree.equals(testTree));
		assertFalse(myTree.equals(aTree));
		assertTrue(myTree.equals(sameTree));
		assertFalse(myTree.equals(otherTree));

	}

	@Test
	public void testImageClipEquals() {
		String fileName = "images/testImage.jpg";
		ImageClip myTree = new ImageClip(new Y(), new Addition(new X(), new X()), fileName);
		ImageClip sameTree = new ImageClip(new Y(), new Addition(new X(), new X()), fileName);
		ImageClip otherTree = new ImageClip(new Y(), new X(), fileName);
		ImageWrap aTree = new ImageWrap(new Y(), new Addition(new X(), new X()), fileName);

		ImageClip testTree = myTree;
		assertTrue(myTree.equals(testTree));
		assertFalse(myTree.equals(aTree));
		assertTrue(myTree.equals(sameTree));
		assertFalse(myTree.equals(otherTree));
	}
}
