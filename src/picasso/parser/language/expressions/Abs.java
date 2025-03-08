package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the absolute value function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class Abs extends UnaryFunction {

	/**
	 * Create an absolute value expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to Abs
	 */
	public Abs(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the abs of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the abs of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.abs(result.getRed());
		double green = Math.abs(result.getGreen());
		double blue = Math.abs(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
