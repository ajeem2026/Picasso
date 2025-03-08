package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the wrap function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class Wrap extends UnaryFunction {

	/**
	 * Create a wrap expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to wrap
	 */
	public Wrap(ExpressionTreeNode param) {
		super(param);
	}

	public static double wrap(double value, double min, double max) {
	    double range = max - min; 
	    double wrappedValue = (value - min) % range;
	    if (wrappedValue < 0) {
	        wrappedValue += range;
	    }
	    return min + wrappedValue; 
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the wrap of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the wrap of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = wrap(result.getRed(), -1.0, 1.0);
		double green = wrap(result.getGreen(), -1.0, 1.0);
		double blue = wrap(result.getBlue(), -1.0, 1.0);

		return new RGBColor(red, green, blue);
	}
}
