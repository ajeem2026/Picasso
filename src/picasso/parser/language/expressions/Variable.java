package picasso.parser.language.expressions;

import picasso.parser.IdentifierAnalyzer;
import picasso.parser.language.ExpressionTreeNode;


/**
 * Represents a variable
 * 
 * @author Sara Sprenkle
 * @author Cat Caples
 *
 */
public class Variable extends ExpressionTreeNode {

	private String name;
	private ExpressionTreeNode rhs;

	public Variable(String name, ExpressionTreeNode rhs) {
		this.name = name;
		this.rhs = rhs;
	}

	@Override
	public RGBColor evaluate(double x, double y) {
		//tell identifier analyzer to save expression
		IdentifierAnalyzer.idToExpression.put(name, rhs);
		RGBColor result = rhs.evaluate(x, y);
		return result;
	}
	

	/**
	 * @param o
	 * @return boolean if equals
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Variable)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		Variable uf = (Variable) o;

		// check if their parameter 1 are equal
		if (!this.name.equals(uf.name)) {
			return false;
		}
		// check if their parameter 2 are equal
		if (!this.rhs.equals(uf.rhs)) {
			return false;
		}
		return true;
	}
	
	public String getName() {
		return name;
	}

}
