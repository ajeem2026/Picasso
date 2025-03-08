package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * @author Claire Hamlet
 * Represents the division function token
 * 
 * */

public class SlashToken extends CharToken implements OperationInterface {
	
	public SlashToken() {
		super(CharConstants.SLASH);
	}
}

