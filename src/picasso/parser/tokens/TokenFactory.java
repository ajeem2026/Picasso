package picasso.parser.tokens;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import picasso.parser.ParseException;
import picasso.parser.language.BuiltinFunctionsReader;
import picasso.parser.tokens.chars.CommaToken;
import picasso.parser.tokens.chars.LeftBracketToken;
import picasso.parser.tokens.chars.RightBracketToken;

/**
 * Looks at a generic token and creates the appropriate token type
 */
public class TokenFactory {
	private static Map<String, Token> tokenNameToToken = new HashMap<String, Token>();

	static {
		initBuiltinFunctionTokenMappings();
	}

	public static Token parse(StreamTokenizer tokenizer) {
		try {
			int result = tokenizer.nextToken();

			switch (result) {
			case StreamTokenizer.TT_EOF:
				return EOFToken.getInstance();
			case StreamTokenizer.TT_NUMBER:
				return new NumberToken(tokenizer.nval);
			case StreamTokenizer.TT_WORD:

				Token t = tokenNameToToken.get(tokenizer.sval);

				// If there is no token with a function name, the token must be
				// an identifier
				if (t == null) {
					return new IdentifierToken(tokenizer.sval);
				}
				return t;
			case '[':
				// parse a color token if it starts with a [
				return parseColorToken(tokenizer);
				
			case '"' :
				Token st = new ImageToken(tokenizer.sval);
				return st; 
				
					
			default:
				Token ct = CharTokenFactory.getToken(result);

				return ct;
			
			// TODO: Handle quoted strings
			// Others?
			}

		} catch (IOException io) {
			throw new ParseException("io problem " + io);
		}
	}

	/**
	 * Parse a ColorToken
	 * 
	 * @param tokenizer
	 * @return
	 */
	private static ColorToken parseColorToken(StreamTokenizer tokenizer) {
		Token r = parse(tokenizer);
		if (!(r instanceof NumberToken)) {
			throw new ParseException("Error parsing color, expected number");
		}

		Token comma = parse(tokenizer);
		if (!(comma instanceof CommaToken)) {
			throw new ParseException("Error parsing color, expected ,");
		}

		Token g = parse(tokenizer);
		if (!(g instanceof NumberToken)) {
			throw new ParseException("Error parsing color, expected number");
		}
		comma = parse(tokenizer);
		if (!(comma instanceof CommaToken)) {
			throw new ParseException("Error parsing color, expected ,");
		}

		Token b = parse(tokenizer);
		if (!(b instanceof NumberToken)) {
			throw new ParseException("Error parsing color, expected number");
		}

		Token rightBracket = parse(tokenizer);
		if (!(rightBracket instanceof RightBracketToken)) {
			throw new ParseException("Error parsing color, expected ] got " + rightBracket);
		}

		NumberToken red = (NumberToken) r;
		NumberToken green = (NumberToken) g;
		NumberToken blue = (NumberToken) b;

		boolean error = false;
		String errorMsg = "";

		if (!ColorToken.isValidValue(red.value())) {
			error = true;
			errorMsg += "Red must be within range [-1,1]. ";
		}
		if (!ColorToken.isValidValue(green.value())) {
			error = true;
			errorMsg += "Green must be within range [-1,1]. ";
		}
		if (!ColorToken.isValidValue(blue.value())) {
			error = true;
			errorMsg += "Blue must be within range [-1,1].";
		}
		if (error) {
			throw new ParseException(errorMsg);
		}

		return new ColorToken(red.value(), green.value(), blue.value());
	}

	/**
	 * Add the built-in functions as tokens
	 */
	private static void initBuiltinFunctionTokenMappings() {
		String functionTokenPackage = "picasso.parser.tokens.functions.";

		// Maybe not necessary. Maybe the function is just an identifier

		List<String> functionsList = BuiltinFunctionsReader.getFunctionsList();

		for (String function : functionsList) {
			// System.out.println("Function: " + function);
			String functionForToken = capitalize(function);
			String tokenName = functionTokenPackage + functionForToken + "Token";
			Class<?> tokenClass = null;
			Token t = null;
			// System.out.println(tokenName);
			try {
				tokenClass = Class.forName(tokenName);
				t = (Token) tokenClass.getDeclaredConstructor().newInstance();
				tokenNameToToken.put(function, t);
			} catch (ClassNotFoundException e) {
				throw new ParseException(tokenName + " not found " + e);
			} catch (InstantiationException e) {
				throw new ParseException(tokenName + " not instantiated " + e);
			} catch (IllegalAccessException e) {
				throw new ParseException(tokenName + " not createable " + e);
			} catch (IllegalArgumentException e) {
				throw new ParseException(tokenName + " not createable " + e);
			} catch (InvocationTargetException e) {
				throw new ParseException(tokenName + " not createable " + e);
			} catch (NoSuchMethodException e) {
				throw new ParseException(tokenName + " not createable " + e);
			} catch (SecurityException e) {
				throw new ParseException(tokenName + " not createable " + e);
			}
		}

	}

	/**
	 * Capitalizes the first letter in the string
	 * 
	 * @param s - string to be capitalized
	 * @return the string capitalized
	 */
	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
