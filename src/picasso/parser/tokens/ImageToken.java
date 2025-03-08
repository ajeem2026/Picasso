package picasso.parser.tokens;

/**
 * Represents an identifier (a variable name)
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 * 
 */
public class ImageToken extends Token {

	private final String myName;

	public ImageToken(String value) {
		super("Image Token");
		myName = value;
	}

	public boolean equals(Object o) {
		if( o == this ) {
			return true;
		}
		if (!(o instanceof ImageToken)) {
			return false;
		}
		ImageToken rhs = (ImageToken) o;
		return myName.equals(rhs.myName);
	}

	/**
	 * Returns the identifier's name
	 * @return the identifier's name
	 */
	public String getName() {
		return myName;
	}

	public String toString() {
		return super.toString() + ": " + myName;
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
