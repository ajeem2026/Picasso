package picasso.parser;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.EOFToken;
import picasso.parser.tokens.StringToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.TokenFactory;
import picasso.parser.tokens.functions.RandomToken;

/**
 * A tokenizer for the Picasso language.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 */
public class Tokenizer {

	private StreamTokenizer tokenizer;
	private Token currToken;
	private SemanticAnalyzer semanticParser;

	public Tokenizer() {
		semanticParser = SemanticAnalyzer.getInstance();
	}

	public SemanticAnalyzer getSemanticParser() {
		return semanticParser;
	}

	/**
	 * Parses the given string into a list of Picasso tokens (in order)
	 * 
	 * @param s
	 *            the string to parse; may or may not be in valid format.
	 * @return the list of Picasso tokens (in order) in the string
	 */
	@SuppressWarnings("unlikely-arg-type")
	public List<Token> parseTokens(String s) {

		// - is seen as a numeric character. If we try to parse, e.g., x-y,
		// it is seen as one string "x-y". See StreamTokenizer documentation about
		// handling words. So, our imperfect solution is to always add
		// a space before a minus sign. Negative numbers will still be
		// seen as numbers, and the space will mark the end of a word.
		s = s.replace("-", " -");

		tokenizer = new StreamTokenizer(new StringReader(s));
		tokenizer.quoteChar(CharConstants.QUOTE);

		tokenizer.wordChars('a', 'z');
		tokenizer.wordChars('A', 'Z');
		tokenizer.wordChars('_', '_');

		tokenizer.eolIsSignificant(false);

		// parsing numbers is a good idea, allow it
		tokenizer.parseNumbers();

		tokenizer.ordinaryChar('/');

		tokenizer.slashSlashComments(true);
		tokenizer.slashStarComments(true);
		

		List<Token> tokens = new ArrayList<Token>();

		Token result = nextToken();

		while (true) {

			if (EOFToken.getInstance().equals(result)) {
				break;
			}
//			if (result instanceof NumberToken ) {
//				NumberToken numToken = (NumberToken) result;
//				if ( numToken.value() == 0.0 ) {
//				System.out.println(tokens);
//				}
//			}
			tokens.add(result);
			result = nextToken();
		}

		// System.out.println("Tokens: " + tokens);
		return tokens;
	}

	/**
	 * get the next token and store it for retrieval by getToken()
	 * 
	 * @return the current (just read) token
	 */
	protected Token nextToken() {
		currToken = TokenFactory.parse(tokenizer);
		return currToken;
	}

	/**
	 * @return the currToken
	 */
	public Token getToken() {
		return currToken;
	}

	/**
	 * @param currToken the currToken to set
	 */
	public void setCurrToken(Token currToken) {
		this.currToken = currToken;
	}

	/**
	 * Try to match a token. If unsuccessful throw an exception. Otherwise match
	 * succeeds, and next token is obtained and returned
	 * 
	 * @param rhs
	 *            token being matched
	 * @return the next read token
	 * @throws ParseException
	 *             if match fails
	 */
	public Token match(Token rhs) {
		Token result = getToken();
		if (!result.equals(rhs)) {
			throw new ParseException("expected " + rhs + " got " + result);
		}
		return nextToken();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String expression = "sin(floor(x + y)) //+ [ -1, 1, 1] /* test */";

		Tokenizer tokenizer = new Tokenizer();
		System.out.println("Tokens" + tokenizer.parseTokens(expression));
	}

}
