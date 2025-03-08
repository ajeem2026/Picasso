package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the invert function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class Invert extends UnaryFunction {

	/**
	 * Create an invert expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to Negate
	 */
	public Invert(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the invert of
	 * the function's parameters.
	 * 
	 * @return the color from evaluating the Negate of the expression's parameters
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		return new RGBColor(-result.getRed(), -result.getGreen(), -result.getBlue());
	}
}