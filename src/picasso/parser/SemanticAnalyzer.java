package picasso.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import picasso.parser.language.BuiltinFunctionsReader;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.TokenFactory;

/**
 * SemanticAnalyzer calls appropriate SemanticAnalyzer for the given token
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 */
public class SemanticAnalyzer implements SemanticAnalyzerInterface {

	private Map<Class<?>, SemanticAnalyzerInterface> tokenToSemAnalyzer;
	private static SemanticAnalyzer ourInstance;

	private static final String PARSER_PACKAGE = "picasso.parser.";
	private static final String OPERATIONS_TOKENS_PACKAGE = PARSER_PACKAGE + "tokens.operations.";
	private static final String TOKENS_PACKAGE_NAME = PARSER_PACKAGE + "tokens.";
	private static final String OPS_FILE = "conf/operations.prop";

	/**
	 * Make sure that there is only one semantic analyzer for the application.
	 * 
	 * @return the semantic analyzer
	 */
	public static SemanticAnalyzer getInstance() {
		if (ourInstance == null) {
			ourInstance = new SemanticAnalyzer();
		}
		return ourInstance;
	}

	/**
	 * 
	 */
	private SemanticAnalyzer() {
		tokenToSemAnalyzer = new HashMap<Class<?>, SemanticAnalyzerInterface>();
		createFunctionParserMappings();
		createOperationMappings();

		// TODO: Probably should put this information into a file
		// that can be read in. Separates business rules/syntax from the code.
		// OR, is there a better alternative?

		// add for constants
		String tokenName = TOKENS_PACKAGE_NAME + "NumberToken";
		String parserName = PARSER_PACKAGE + "ConstantAnalyzer";
		addSemanticAnalyzerMapping(tokenName, parserName);

		// IdentifierToken --> IdentifierAnalyzer
		tokenName = TOKENS_PACKAGE_NAME + "IdentifierToken";
		parserName = PARSER_PACKAGE + "IdentifierAnalyzer";
		addSemanticAnalyzerMapping(tokenName, parserName);

		// Color mapping
		tokenName = TOKENS_PACKAGE_NAME + "ColorToken";
		parserName = PARSER_PACKAGE + "ColorAnalyzer";
		addSemanticAnalyzerMapping(tokenName, parserName);

		// Image mapping
		tokenName = TOKENS_PACKAGE_NAME + "ImageToken";
		parserName = PARSER_PACKAGE + "ImageAnalyzer";
		addSemanticAnalyzerMapping(tokenName, parserName);

		// TODO: Are there any others that should be added?
		// Is there a better way to create this mapping?
	}

	/**
	 * Adds the mapping between the Token class and the SemanticAnalyzer class
	 * 
	 * @param tokenName            the name of the token class
	 * @param semanticAnalyzerName the name of the semantic analyzer class that
	 *                             handles the token
	 */
	private void addSemanticAnalyzerMapping(String tokenName, String semanticAnalyzerName) {
		Class<?> tokenClass = null;
		try {
			tokenClass = Class.forName(tokenName);
		} catch (ClassNotFoundException e) {
			throw new ParseException(tokenName + " not found " + e);
		}

		SemanticAnalyzerInterface p = null;
		try {
			p = (SemanticAnalyzerInterface) Class.forName(semanticAnalyzerName).getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			throw new ParseException(semanticAnalyzerName + " not found " + e);
		} catch (InstantiationException e) {
			throw new ParseException(semanticAnalyzerName + " not instantiated " + e);
		} catch (IllegalAccessException e) {
			throw new ParseException(semanticAnalyzerName + " not creatable " + e);
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new ParseException(semanticAnalyzerName + " not creatable " + e);
		}

		// if we got to here, we're good to go
		tokenToSemAnalyzer.put(tokenClass, p);
	}

	/**
	 * Map each function tokens to its semantic analyzer
	 */
	private void createFunctionParserMappings() {
		List<String> functionsList = BuiltinFunctionsReader.getFunctionsList();

		for (String function : functionsList) {
			String functionClass = TokenFactory.capitalize(function);
			String tokenName = TOKENS_PACKAGE_NAME + "functions." + functionClass + "Token";
			String semanticAnalyzer = PARSER_PACKAGE + functionClass + "Analyzer";
			addSemanticAnalyzerMapping(tokenName, semanticAnalyzer);
		}
	}

	/**
	 * Map each operation token to its semantic analyzer
	 */
	private void createOperationMappings() {

		// TODO: The following exceptions should probably be propagated up to
		// the user.
		Properties opProps = new Properties();
		try {
			opProps.load(new FileReader(OPS_FILE));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (Object op : opProps.keySet()) {
			String opName = (String) opProps.get(op);
			String tokenName = OPERATIONS_TOKENS_PACKAGE + opName + "Token";
			String parserName = PARSER_PACKAGE + opName + "Analyzer";
			addSemanticAnalyzerMapping(tokenName, parserName);
		}
	}

	/**
	 * From a stack of tokens in postfix order, creates an expression tree
	 * 
	 * @param tokens in postfix order
	 * @return the root node of the expression tree.
	 */
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {

		if (tokens.isEmpty()) {
			// XXX: Is this the only reason that the token stack is empty?
			throw new ParseException("Expected another argument.");
		}

		// Find the appropriate semantic analyzer for the token.
		Token t = tokens.peek();
		SemanticAnalyzerInterface analyzer = tokenToSemAnalyzer.get(t.getClass());
		if (analyzer == null) {
			throw new ParseException("No semantic analyzer for " + t.getClass());
		}
		return analyzer.generateExpressionTree(tokens);
	}

}
