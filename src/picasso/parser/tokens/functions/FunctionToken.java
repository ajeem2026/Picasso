/**
 * 
 */
package picasso.parser.tokens.functions;

import picasso.parser.tokens.Token;

/**
 * Parent class for Tokens that represent functions.
 * 
 */
public abstract class FunctionToken extends Token {

	public FunctionToken(String name) {
		super(name);
	}

	/**
	 * @see picasso.parser.tokens.Token#isConstant()
	 */
	@Override
	public boolean isConstant() {
		return false;
	}

	/**
	 * @see picasso.parser.tokens.Token#isFunction()
	 */
	@Override
	public boolean isFunction() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		return obj.getClass().equals(this.getClass());
	}

}
