package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;

/**
 * This is the original implementation. Will probably want to refactor to fit
 * within new framework.
 * 
 * Enumeration to evaluate binary operations and methods
 * 
 */
public enum ReferenceForExpressionEvaluations {
	X {
		public RGBColor evaluate(RGBColor left, RGBColor unused) {
			double red = left.getRed();
			double green = left.getGreen();
			double blue = left.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	Y {
		public RGBColor evaluate(RGBColor unused, RGBColor right) {
			double red = right.getRed();
			double green = right.getGreen();
			double blue = right.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	PLUS {
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double red = left.getRed() + right.getRed();
			double green = left.getGreen() + right.getGreen();
			double blue = left.getBlue() + right.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	MINUS {
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double red = left.getRed() - right.getRed();
			double green = left.getGreen() - right.getGreen();
			double blue = left.getBlue() - right.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	TIMES {
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double red = left.getRed() * right.getRed();
			double green = left.getGreen() * right.getGreen();
			double blue = left.getBlue() * right.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	DIVIDE {
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double red = left.getRed() / right.getRed();
			double green = left.getGreen() / right.getGreen();
			double blue = left.getBlue() / right.getBlue();
			return new RGBColor(red, green, blue);
		}
	},
	COS {
		public RGBColor evaluate(RGBColor left, RGBColor ignore) {
			double red = Math.cos(left.getRed());
			double green = Math.cos(left.getGreen());
			double blue = Math.cos(left.getBlue());
			return new RGBColor(red, green, blue);
		}
	},
	PERLINCOLOR {
		/**
		 * Generate Perlin noise based on the given values. Algorithm described in
		 * detail at this site:
		 * http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
		 */
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			return new RGBColor(red, green, blue);
		}
	},
	PERLINBW {
		/**
		 * Generate Perlin noise based on the given values. Algorithm described in
		 * detail at this site:
		 * http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
		 */
		public RGBColor evaluate(RGBColor left, RGBColor right) {
			double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(),
					left.getBlue() + right.getBlue());
			return new RGBColor(grey, grey, grey);
		}
	},
	RGB2YCRCR {
		/**
		 * Convert color from RGB to YUV color space. Details and constants derived from
		 * this site: http://www.answers.com/topic/yuv
		 */
		public RGBColor evaluate(RGBColor c, RGBColor unused) {
			double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
			double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
			double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
			return new RGBColor(red, green, blue);
		}
	},
	YCRC2RGBR {
		/**
		 * Convert color from YUV to RGB color space. Details and constants derived from
		 * this site: http://www.answers.com/topic/yuv
		 */
		public RGBColor evaluate(RGBColor c, RGBColor unused) {
			double red = c.getRed() + c.getBlue() * 1.4022;
			double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
			double blue = c.getRed() + c.getGreen() * 1.7710;
			return new RGBColor(red, green, blue);
		}
	},
	INVERT {
		/**
		 * Convert color from RGB to YUV color space. Details and constants derived from
		 * this site: http://www.answers.com/topic/yuv
		 */
		public RGBColor evaluate(RGBColor c, RGBColor unused) {
			return new RGBColor(-c.getRed(), -c.getGreen(), -c.getBlue());
		}
	};

	// Do arithmetic operation represented by this constant
	abstract public RGBColor evaluate(RGBColor left, RGBColor right);
}
