/**
 * 
 */
package picasso.parser.tokens.chars;

import picasso.parser.tokens.Token;

/**
 * A token represented by a single character, e.g., <tt>'*', ';', '['</tt> and
 * so on; the method equals compares a CharToken as equal to another CharToken
 * encapsulating the same character.
 * <P>
 * Client code should use CharTokenFactory to get a CharToken object, the
 * constructor is package access only
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 */
public class CharToken extends Token {

	private final int myValue;

	/**
	 * Represents the given single-character token
	 */
	public CharToken(int ch) {
		super("Character: ");
		myValue = ch;
	}

	/**
	 * @return true iff o is a CharToken with same value
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CharToken)) {
			return false;
		}

		// Make sure the objects are the same type
		if (o.getClass() != this.getClass()) {
			return false;
		}

		CharToken other = (CharToken) o;
		return myValue == other.myValue;
	}

	/**
	 * @return the encapsulated value
	 */
	public int value() {
		return myValue;
	}

	@Override
	public String toString() {
		return super.toString() + (char) myValue;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return false;
	}
}
