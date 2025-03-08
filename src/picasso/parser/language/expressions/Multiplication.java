package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the multiplication function in the Picasso language.
 * 
 * @author Cat Caples
 * @author Claire Hamlet
 * 
 */
public class Multiplication extends BinaryOperator {

	/**
	 * Create an addition expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to multiplication
	 */
	public Multiplication(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		super(param1, param2);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the multiplication of
	 * the function's parameters.
	 * 
	 * @return the color from evaluating the multiplication of the expression's parameters
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultLeft = param1.evaluate(x, y);
		RGBColor resultRight = param2.evaluate(x, y);
		double red = resultLeft.getRed() * resultRight.getRed();
		double green = resultLeft.getGreen() * resultRight.getGreen();
		double blue = resultLeft.getBlue() * resultRight.getBlue();
		return new RGBColor(red, green, blue);
	}

}
