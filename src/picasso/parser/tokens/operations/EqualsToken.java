package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the assignment token "="
 * 
 * @author Cat Caples
 */
public class EqualsToken extends CharToken implements OperationInterface {

	public EqualsToken() {
		super(CharConstants.EQUAL);
	}

}
