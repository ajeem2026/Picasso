package picasso.parser.tokens;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.*;
import picasso.parser.tokens.operations.*;

/**
 * Factory for generating CharToken objects ensuring that only one of any
 * specific CharToken object is created (enforcing CharToken singleton-ness per
 * char value)
 * 
 * @author Owen Astrachan
 * @author Sara Sprenkle
 * @author Claire Hamlet
 */
public class CharTokenFactory {

	private static CharToken[] tokenList = new CharToken[256];

	/**
	 * 
	 * @param ch
	 * @return the token represented by this character
	 */
	public static CharToken getToken(int ch) {
		if (tokenList[ch] == null) {
			tokenList[ch] = new CharToken(ch);
		}
		return tokenList[ch];
	}

	static {
		tokenList[CharConstants.LEFT_PAREN] = new LeftParenToken();
		tokenList[CharConstants.RIGHT_PAREN] = new RightParenToken();
		tokenList[CharConstants.LEFT_BRACKET] = new LeftBracketToken();
		tokenList[CharConstants.RIGHT_BRACKET] = new RightBracketToken();
		tokenList[CharConstants.EQUAL] = new EqualsToken();
		tokenList[CharConstants.COMMA] = new CommaToken();
		tokenList[CharConstants.PLUS] = new PlusToken();
		tokenList[CharConstants.MINUS] = new MinusToken();
		tokenList[CharConstants.STAR] = new StarToken();
		tokenList[CharConstants.SLASH] = new SlashToken();
		tokenList[CharConstants.QUOTE] = new QuoteToken();
		tokenList[CharConstants.BANG] = new InvertToken();


		// TODO: What other tokens should be in here?
		// TODO: Need to create the associated Token classes too.

	}
}
