package picasso.parser.language.expressions;

import java.awt.Color;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Special color class for Picasso. Represents colors by their red, green, and
 * blue components. Each color component is on a scale of [-1,1]. When the color
 * is evaluated in the expression tree, it is the color.
 */
public class RGBColor extends ExpressionTreeNode {
	public static final double COLOR_MIN = -1;
	public static final double COLOR_MAX = 1;
	public static final int JAVA_COLOR_MAX = 255;

	/** for testing */
	public static final double ERROR_TOLERANCE = 0.01;

	private double myRed;
	private double myGreen;
	private double myBlue;

	/**
	 * Create an RGBColor object that has the given amount of red, green, and blue.
	 * All components are on a scale of [-1, 1]
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public RGBColor(double red, double green, double blue) {
		myRed = red;
		myGreen = green;
		myBlue = blue;
	}

	/**
	 * Copy a Java Color object into a Picasso RGBColor object
	 * 
	 * @param javaColor the color to convert into a Picasso RGBColor object
	 */
	public RGBColor(Color javaColor) {
		myRed = toDouble(javaColor.getRed());
		myGreen = toDouble(javaColor.getGreen());
		myBlue = toDouble(javaColor.getBlue());
	}

	/**
	 * Returns the amount of red, between COLOR_MIN and COLOR_MAX
	 * 
	 * @return the amount of red, between COLOR_MIN and COLOR_MAX
	 */
	public double getRed() {
		return myRed;
	}

	/**
	 * Returns the amount of green, between COLOR_MIN and COLOR_MAX
	 * 
	 * @return the amount of green, between COLOR_MIN and COLOR_MAX)
	 */
	public double getGreen() {
		return myGreen;
	}

	/**
	 * Returns the amount of blue, between COLOR_MIN and COLOR_MAX
	 * 
	 * @return the amount of blue, between COLOR_MIN and COLOR_MAX
	 */
	public double getBlue() {
		return myBlue;
	}

	/**
	 * Clamps the color components between COLOR_MIN and COLOR_MAX
	 */
	public void clamp() {
		myRed = clamp(myRed);
		myGreen = clamp(myGreen);
		myBlue = clamp(myBlue);
	}

	/**
	 * Converts to Java's Color class
	 * 
	 * @return this color converted into Java's Color
	 */
	public Color toJavaColor() {
		clamp();
		return new Color(toInt(myRed), toInt(myGreen), toInt(myBlue));
	}

	/**
	 * 
	 * @param o the other object
	 * @return true iff the other object is an RGBColor within ERROR_TOLERANCE for
	 *         each color component
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof RGBColor) {
			RGBColor other = (RGBColor) o;
			return ((Math.abs(myRed - other.myRed) < ERROR_TOLERANCE)
					&& (Math.abs(myGreen - other.myGreen) < ERROR_TOLERANCE)
					&& (Math.abs(myBlue - other.myBlue) < ERROR_TOLERANCE));
		}
		return false;
	}

	/**
	 * Returns the RGBColor as a String in the form "Color: &lt;red&gt;
	 * &lt;green&gt; &lt;blue&gt;"
	 * 
	 * @return the RGBColor as a String in the form "Color: &lt;red&gt;
	 *         &lt;green&gt; &lt;blue&gt;"
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Color: " + getRed() + " " + getGreen() + " " + getBlue();
	}

	/**
	 * Convert the Java color's component value (from [0,255]) to the [-1,1] range
	 * needed for Picasso
	 * 
	 * @param value the color's component value (from [0,255])
	 * @return the value of the color component, scaled from [-1,1]
	 */
	protected static double toDouble(int value) {
		double range = COLOR_MAX - COLOR_MIN;
		return (double) value / JAVA_COLOR_MAX * range + COLOR_MIN;
	}

	/**
	 * Convert the Picasso color's component value (from [-1,1] to the Java color's
	 * component (from [0,255])
	 * 
	 * @param value the color's component value (from [-1,1])
	 * @return the value of the color component, scaled from [0,255]
	 */
	protected static int toInt(double value) {
		double range = COLOR_MAX - COLOR_MIN;
		return (int) ((value - COLOR_MIN) / range * JAVA_COLOR_MAX);
	}

	/**
	 * Clamps the value to between COLOR_MIN and COLOR_MAX
	 * 
	 * @param value to be clamped
	 * @return the value, clamped to between COLOR_MIN and COLOR_MAX
	 */
	protected static double clamp(double value) {
		return Math.max(COLOR_MIN, Math.min(COLOR_MAX, value));
	}

	/**
	 * Returns this color
	 * @param x
	 * @param y
	 * @return this color
	 * @see picasso.parser.language.ExpressionTreeNode#evaluate(double, double)
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		return this;
	}
}
