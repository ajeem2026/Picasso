package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents y in a Picasso expression
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class Y extends ExpressionTreeNode {

	/**
	 * Returns the color represented by y. The value of x doesn't matter.
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		return new RGBColor(y, y, y);
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
		if (!(obj instanceof Y)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns "y", the representation of this variable in Picasso expressions
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "y";
	}
}
