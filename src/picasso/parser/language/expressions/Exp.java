package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the exp function in the Picasso language.
 * 
 * @author Vincent Ziccardi
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class Exp extends UnaryFunction {

	/**
	 * Create a exp expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression e^
	 */
	public Exp(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating e^ of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the e^ of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.exp(result.getRed());
		double green = Math.exp(result.getGreen());
		double blue = Math.exp(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
