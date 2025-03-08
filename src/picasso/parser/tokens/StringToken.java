package picasso.parser.tokens;

/**
 * Represents a token for a string (e.g., an expression loaded from a file).
 * Using equals, a StringToken object compares as true only to another
 * StringToken object with the same string value.
 * 
 * A StringToken is immutable, once created it doesn't change.
 * 
 * @author Your Name
 */
public class StringToken extends Token {

    private final String myValue;

    /**
     * Constructs a token representing the given string.
     * 
     * @param value the value of this string token
     */
    public StringToken(String value) {
        super("String Token");
        myValue = value;
    }

    /**
     * @return true iff o is a StringToken with the same value
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StringToken)) {
            return false;
        }
        StringToken other = (StringToken) o;
        return myValue.equals(other.myValue);
    }

    /**
     * @return the value of this token
     */
    public String value() {
        return myValue;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + myValue;
    }

    @Override
    public boolean isConstant() {
        return false; // A string token is not a numeric constant
    }

    @Override
    public boolean isFunction() {
        return false; // A string token is not a function
    }
}
