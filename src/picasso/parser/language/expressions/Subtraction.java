package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the subtraction function in the Picasso language.
 * 
 * @author Claire Hamlet
 * @author Cat Caples
 * 
 */
public class Subtraction extends BinaryOperator {

	/**
	 * Create an subtraction expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to subtraction
	 */
	public Subtraction(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		super(param1, param2);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the subtraction of
	 * the function's parameters.
	 * 
	 * @return the color from evaluating the subtraction of the expression's parameters
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor resultLeft = param1.evaluate(x, y);
		RGBColor resultRight = param2.evaluate(x, y);
		double red = resultLeft.getRed() - resultRight.getRed();
		double green = resultLeft.getGreen() - resultRight.getGreen();
		double blue = resultLeft.getBlue() - resultRight.getBlue();
		return new RGBColor(red, green, blue);
	}

}
