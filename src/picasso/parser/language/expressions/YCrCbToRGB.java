package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the yCrCbToRGB function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class YCrCbToRGB extends UnaryFunction {

	/**
	 * Create an YCrCbToRGB expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to YCrCbToRGB
	 */
	public YCrCbToRGB(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Convert color from YUV to RGB color space. Details and constants derived from
	 * this site: http://www.answers.com/topic/yuv
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor c = param.evaluate(x,y);		
		double red = c.getRed() + c.getBlue() * 1.4022;
		double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
		double blue = c.getRed() + c.getGreen() * 1.7710;
		return new RGBColor(red, green, blue);
	}
	
}