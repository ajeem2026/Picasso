package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the log function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class Log extends UnaryFunction {

	/**
	 * Create an absolute value expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to Abs
	 */
	public Log(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the log of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the log of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(Math.abs(x), Math.abs(y));
		double red = Math.log(result.getRed());
		double green = Math.log(result.getGreen());
		double blue = Math.log(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}