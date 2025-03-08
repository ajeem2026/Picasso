/**
 * 
 */
package picasso.parser.tokens;

/**
 * Represents a color (using double values).
 * A ColorToken is immutable: once created it doesn't change.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 */
public class ColorToken extends Token {

	private final double red;
	private final double green;
	private final double blue;

	/**
	 * Constructs a token representing the value
	 * 
	 * @param red amount of red within range [-1,1]
	 * @param green amount of green within range [-1,1]
	 * @param blue amount of blue within range [-1,1]
	 */
	public ColorToken(double red, double green, double blue) {
		super("Color Token");
		boolean error = false;
		String errorMsg = "";
		if (red < -1 || red > 1) {
			error = true;
			errorMsg += "Red must be within range [-1,1]. ";
		}
		if (green < -1 || green > 1) {
			error = true;
			errorMsg += "Green must be within range [-1,1]. ";
		}
		if (blue < -1 || blue > 1) {
			error = true;
			errorMsg += "Blue must be within range [-1,1].";
		}

		if (error) {
			throw new IllegalArgumentException(errorMsg);
		}

		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public static boolean isValidValue(double value) {
		return (value >= -1 && value <= 1);
	}

	/**
	 * @return true iff o is a NumberToken with same value
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ColorToken)) {
			return false;
		}
		ColorToken other = (ColorToken) o;
		return red == other.red && green == other.green && blue == other.blue;
	}

	public String toString() {
		return super.toString() + ": " + red + "," + green + "," + blue;
	}

	/**
	 * @return the red
	 */
	public double getRed() {
		return red;
	}

	/**
	 * @return the green
	 */
	public double getGreen() {
		return green;
	}

	/**
	 * @return the blue
	 */
	public double getBlue() {
		return blue;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

}
