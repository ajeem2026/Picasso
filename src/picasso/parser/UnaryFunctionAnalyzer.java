package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;

/**
 * Abstract class that parses a function that takes one expression as a parameter.
 * 
 * @author Sara Sprenkle
 * 
 */
public abstract class UnaryFunctionAnalyzer implements SemanticAnalyzerInterface {

	// TODO: This class isn't useful yet, but seems like it has an opportunity
	// to be useful because of the amount of duplicate code in the analyzers
	// What would be helpful for this class to do?
	 
	@Override
	public abstract ExpressionTreeNode generateExpressionTree(
			Stack<Token> tokens);

}
