package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the PerlinBW function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */
public class PerlinBW extends PerlinParent {


	/**
	 * Create an PerlinColor expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to PerlinColor
	 */
	public PerlinBW(ExpressionTreeNode param1, ExpressionTreeNode param2) {
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
		double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(),
				left.getBlue() + right.getBlue());
		return new RGBColor(grey, grey, grey);
	}

}