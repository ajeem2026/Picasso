/**
 * 
 */
package picasso.parser.tokens;

/**
 * Represents a number (using double values). Using equals, a NumberToken object
 * compares as true only to another NumberToken object with the same value
 * <P>
 * a NumberToken is immutable, once created it doesn't change
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 */
public class NumberToken extends Token {

	private final double myValue;

	/**
	 * Constructs a token representing value
	 * 
	 * @param value the value of this number token
	 */
	public NumberToken(double value) {
		super("Number Token");
		myValue = value;
	}

	/**
	 * @return true iff o is a NumberToken with same value
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof NumberToken)) {
			return false;
		}
		NumberToken other = (NumberToken) o;
		return myValue == other.myValue;
	}

	/**
	 * @return the value of this token
	 */
	public double value() {
		return myValue;
	}

	public String toString() {
		return super.toString() + ": " + myValue;
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
