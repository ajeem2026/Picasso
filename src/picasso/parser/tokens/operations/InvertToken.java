package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the invert token "!"
 * 
 * @author Cat Caples
 */
public class InvertToken extends CharToken implements OperationInterface {

	public InvertToken() {
		super(CharConstants.BANG);
	}

}
