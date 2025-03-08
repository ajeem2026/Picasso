package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

public abstract class PerlinParent extends ExpressionTreeNode {

	protected ExpressionTreeNode param2;
	protected ExpressionTreeNode param1;

	public PerlinParent(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		this.param1 = param1;
		this.param2=param2;
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String classname = this.getClass().getName();
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + param1 + "," + param2 + ")";
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
	
		if (!(o instanceof PerlinParent)) {
			return false;
		}
	
		// Make sure the objects are the same type
	
		if (o.getClass() != this.getClass()) {
			return false;
		}
	
		PerlinParent etn = (PerlinParent) o;
	
		// check if their parameters are equal
		if (!this.param1.equals(etn.param1)) {
			return false;
		}
		if (!this.param2.equals(etn.param2)) {
			return false;
		}
		return true;
	}

}