package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a function that takes two arguments.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 *
 */
public abstract class BinaryOperator extends ExpressionTreeNode {

	ExpressionTreeNode param1;
	ExpressionTreeNode param2;

	/**
	 * 
	 * @param param
	 */
	public BinaryOperator(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		this.param1 = param1;
		this.param2 = param2;
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
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + param1 + ")" + ", (" + param2 + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof BinaryOperator)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		BinaryOperator uf = (BinaryOperator) o;

		// check if their parameter 1 are equal
		if (!this.param1.equals(uf.param1)) {
			return false;
		}
		// check if their parameter 2 are equal
		if (!this.param2.equals(uf.param2)) {
			return false;
		}
		return true;
	}

}
