package picasso.parser.tokens;

/**
 * Represents an identifier (a variable name)
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 * 
 */
public class IdentifierToken extends Token {

	private final String myName;

	public IdentifierToken(String value) {
		super("Variable Token");
		myName = value;
	}

	public boolean equals(Object o) {
		if( o == this ) {
			return true;
		}
		if (!(o instanceof IdentifierToken)) {
			return false;
		}
		IdentifierToken rhs = (IdentifierToken) o;
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
