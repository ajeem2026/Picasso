package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents x in a Picasso expression
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class X extends ExpressionTreeNode {

	/**
	 * Returns the color represented by x. The value of y doesn't matter.
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		return new RGBColor(x, x, x);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof X)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns "x", the representation of this variable in Picasso expressions
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "x";
	}

}
