package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a function that takes one argument.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 *
 */
public abstract class UnaryFunction extends ExpressionTreeNode {

	ExpressionTreeNode param;

	/**
	 * 
	 * @param param
	 */
	public UnaryFunction(ExpressionTreeNode param) {
		this.param = param;
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
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + param + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof UnaryFunction)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		UnaryFunction uf = (UnaryFunction) o;

		// check if their parameters are equal
		if (!this.param.equals(uf.param)) {
			return false;
		}
		return true;
	}

}
