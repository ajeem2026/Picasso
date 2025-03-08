package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the clamp function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class Clamp extends UnaryFunction {

	/**
	 * Create a clamp expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to clamp
	 */
	public Clamp(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the clamp of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the clamp of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.clamp(result.getRed(), -1.0, 1.0);
		double green = Math.clamp(result.getGreen(), -1.0, 1.0);
		double blue = Math.clamp(result.getBlue(), -1.0, 1.0);

		return new RGBColor(red, green, blue);
	}

}