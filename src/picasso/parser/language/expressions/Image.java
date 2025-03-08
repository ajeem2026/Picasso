package picasso.parser.language.expressions;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import picasso.parser.language.ExpressionTreeNode;
import picasso.view.commands.Evaluator;

/**
 * Class for manipulating graphics images, originally developed in C++.
 * 
 * <p>
 * This class represents an image that supports manipulation, i.e., reflection,
 * expansion, inversion, etc. It has an analog in C++, for comparison between
 * the two languages, although there is more support in Java for images than
 * there is in C++.
 * </p>
 * 
 * <p>
 * Creating a pixmap requires a filename that should be a gif or jpg image (or
 * others if getImage() supports them). Currently the filename represents a
 * local image, but changing the URL to support network retrievable images
 * should be straightforward.
 * 
 * <P>
 * Revision history for C++ version: <br/>
 * Modified: 3/21/94 11/29/94 4/13/95
 * <P>
 * Ported: 10/16/1996 to Java (Syam Gadde) re-implemented, ported to 1.1 6/1/97
 * (Owen Astrachan)
 * 
 * @author Robert C. Duvall
 * @author Owen Astrachan
 * @author Syam Gadde
 */
public class Image extends ExpressionTreeNode {
	public static final Dimension DEFAULT_SIZE = new Dimension(300, 300);
	public static final Color DEFAULT_COLOR = Color.PINK;
	public static final String DEFAULT_NAME = "Picasso";

	private String myFileName;
	private BufferedImage myImage;
	private Dimension mySize;

//	/**
//	 * Create a default pixmap (300x300 black)
//	 */
//	public Image() {
//		this(DEFAULT_SIZE.width, DEFAULT_SIZE.height, DEFAULT_COLOR);
//	}
//
//	/**
//	 * Create a black pixmap with given size
//	 */
//	public Image(Dimension size) {
//		this(size.width, size.height, DEFAULT_COLOR);
//	}
//
//	/**
//	 * Create a black pixmap with given width and height
//	 */
//	public Image(int width, int height) {
//		this(width, height, DEFAULT_COLOR);
//	}
//
//	/**
//	 * Create a pixmap with given width and height and filled with given initial
//	 * color
//	 */
//	public Image(int width, int height, Color color) {
//		createImage(width, height, color);
//	}
//
//	/**
//	 * Create this image as a copy of the given image
//	 */
//	public Image(Image other) {
//		myFileName = other.myFileName;
//		mySize = other.getSize();
//		myImage = copyImage(mySize, mySize, other.myImage);
//	}

	/**
	 * Create a pixmap from the given local file
	 * 
	 * @param fileName complete pathname of local file
	 */
	public Image(String fileName) {
		if (fileName == null) {
			createImage(DEFAULT_SIZE.width, DEFAULT_SIZE.height, DEFAULT_COLOR);
		} else {
			read(fileName);
		}
	}

	/**
	 * Returns the name of the image
	 * 
	 * @return the name of the image
	 */
	public String getName() {
		int index = myFileName.lastIndexOf(File.separator);
		if (index >= 0)
			return myFileName.substring(index + 1);
		else
			return myFileName;
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

	public Dimension getSize() {
		return new Dimension(mySize);
	}

	/**
	 * Returns the color of the pixel at the given (x,y) coordinate if the
	 * coordinate is within the bounds of the image; otherwise returns the default
	 * color
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the color of the pixel at the given (x,y) coordinate if the
	 *         coordinate is within the bounds of the image; otherwise returns the
	 *         default color
	 */
	public Color getColor(int x, int y) {
		if (isInBounds(x, y))
			return new Color(myImage.getRGB(x, y));
		else
			return DEFAULT_COLOR;
	}

	public void setColor(int x, int y, Color value) {
		if (isInBounds(x, y)) {
			myImage.setRGB(x, y, value.getRGB());
		}
	}

	public void setSize(Dimension size) {
		setSize(size.width, size.height);
	}

	/**
	 * Changes the size of the image to the given width and height
	 * 
	 * @param width  the new width of the image
	 * @param height the new height of the image
	 */
	public void setSize(int width, int height) {
		if (width != mySize.width || height != mySize.height) {
			Dimension newSize = new Dimension(width, height);
			if (width > mySize.width || height > mySize.height) {
				myImage = copyImage(mySize, newSize, myImage);
			} else {
				// TODO: BUGBUG: scale image down instead?
				myImage = myImage.getSubimage(0, 0, width, height);
			}
			mySize = newSize;
		}
	}

	/**
	 * Read the image named by fileName
	 * 
	 * @param fileName the name of the image file to be read in
	 */
	public void read(String fileName) {
		try {
			myFileName = "images/" + fileName;
			String temp = new File(myFileName).getAbsolutePath(); //TODO remove this line and next, were for debugging
			System.out.println(temp);
			myImage = ImageIO.read(new File(myFileName));
			mySize = new Dimension(myImage.getWidth(), myImage.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write the current image to a file as a jpg.
	 * 
	 * @param fileName the name of the file to write the image to
	 */
	public void write(String fileName) {
		try {
			ImageIO.write(myImage, "jpg", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics pen) {
		pen.drawImage(myImage, 0, 0, mySize.width, mySize.height, null);
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

	private BufferedImage copyImage(Dimension from, Dimension to, BufferedImage original) {
		int[] data = new int[from.width * from.height];
		original.getRGB(0, 0, from.width, from.height, data, 0, from.width);

		BufferedImage result = new BufferedImage(to.width, to.height, BufferedImage.TYPE_INT_RGB);
		result.setRGB(0, 0, from.width, from.height, data, 0, from.width);
		return result;
	}

	/**
	 * Convert from domain space to image space.
	 */
	protected int domainToImageScale(double value, int bounds) {
		int range = bounds;
		return (int) (((value + 1) / (Evaluator.DOMAIN_MAX - Evaluator.DOMAIN_MIN)) * range);
	}
	
	@Override
	public RGBColor evaluate(double x, double y) {
		int evalX = domainToImageScale(x, myImage.getWidth()-1);
		int evalY = domainToImageScale(y, myImage.getHeight()-1);
		return new RGBColor(this.getColor(evalX, evalY));
	}
}
