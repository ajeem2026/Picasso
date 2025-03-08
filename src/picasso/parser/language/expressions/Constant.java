package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a constant in the Picasso language.
 * 
 * @author Sara Sprenkle
 * 
 */
public class Constant extends ExpressionTreeNode {

	private double value;

	/**
	 * Creates a new Constant object that represents the given value
	 * 
	 * @param d must be in range [-1, 1]
	 */
	public Constant(double d) {
		if (d < -1 || d > 1) {
			throw new IllegalArgumentException("Constant must be in range [-1, 1]");
		}
		value = d;
	}

	/**
	 * Returns the value of the constant
	 * 
	 * @return the value of the constant
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns the constant as a string formatted as "Constant: <value>"
	 */
	@Override
	public String toString() {
		return "Constant: " + value;
	}

	/**
	 * The constant is like an RGB Color with the constant as each component. It is
	 * not affected by the values of x and y.
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		return new RGBColor(value, value, value);
	}

	/**
	 * Determines if two Constants are equivalent (represent the same value.)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Constant)) {
			return false;
		}
		Constant other = (Constant) obj;
		return Double.compare(other.value, value) == 0;
	}
}
