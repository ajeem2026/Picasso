package picasso.parser.tokens;

/**
 * This represents an end-of-file token. Uses the singleton pattern because
 * there's no reason to have more than one such token. For equals, this compares
 * false to any object other than an EOFToken
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 */
public class EOFToken extends Token {

	private static EOFToken ourInstance;

	/**
	 * @return an EOFToken (singleton)
	 */
	public static EOFToken getInstance() {
		if (ourInstance == null) {
			ourInstance = new EOFToken();
		}
		return ourInstance;
	}

	private EOFToken() {
		super("End of File");
	}

	/**
	 * @return true of o is an EOFToken, else return false
	 */
	public boolean equals(Object o) {
		if (!(o instanceof EOFToken)) {
			return false;
		}
		return true;
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
