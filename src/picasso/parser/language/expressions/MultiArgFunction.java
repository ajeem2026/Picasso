package picasso.parser.language.expressions;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a function that takes two arguments.
 * 
 * @author Vincent Ziccardi
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 *
 */
public abstract class MultiArgFunction extends ExpressionTreeNode {

	String param1;
	ExpressionTreeNode param2;
	ExpressionTreeNode param3;

	/**
	 * 
	 * @param param1, param2, param3
	 */
	public MultiArgFunction(String param1, ExpressionTreeNode param2, ExpressionTreeNode param3) {
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		try {
			String myFileName = param1;
			BufferedImage myImage = ImageIO.read(new File(myFileName));
			Dimension mySize = new Dimension(myImage.getWidth(), myImage.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + param1 + ")" + ", (" + param2 + ")" + ", (" + param3 + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof MultiArgFunction)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		MultiArgFunction uf = (MultiArgFunction) o;

		// check if their parameter 1 are equal
		if (!this.param1.equals(uf.param1)) {
			return false;
		}
		// check if their parameter 2 are equal
		if (!this.param2.equals(uf.param2)) {
			return false;
		}
		// check if their parameter 3 are equal
		if (!this.param3.equals(uf.param3)) {
			return false;
		}
		return true;
	}

}
