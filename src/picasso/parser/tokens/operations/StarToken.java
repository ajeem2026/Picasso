package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication function token
 * @author Claire Hamlet
 */
	public class StarToken extends CharToken implements OperationInterface {
	
		public StarToken() {
			super(CharConstants.STAR);
		}
	}

