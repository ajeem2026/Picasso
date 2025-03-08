package picasso.parser.language;

import picasso.parser.language.expressions.RGBColor;

/**
 * Classes that implement this interface can be evaluated for the image.
 * 
 * @author Sara Sprenkle
 * 
 */
public interface EvaluatableExpression {
	public RGBColor evaluate(double x, double y);

}
