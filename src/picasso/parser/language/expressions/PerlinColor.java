package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the PerlinColor function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class PerlinColor extends PerlinParent {

	/**
	 * Create an PerlinColor expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to PerlinColor
	 */
	public PerlinColor(ExpressionTreeNode param1, ExpressionTreeNode param2) {
		super(param1, param2);
	}
	
	/**
	 * Generate Perlin noise based on the given values. Algorithm described in
	 * detail at this site:
	 * http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
	 */
	public RGBColor evaluate(double x, double y) {
		RGBColor left = param1.evaluate(x,y);
		RGBColor right = param2.evaluate(x,y);
		double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
		double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
		double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
		return new RGBColor(red, green, blue);
	}

}