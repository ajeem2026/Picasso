package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;

/**
 * The interface that all Semantic Analyzers must implement
 * 
 * @author Sara Sprenkle
 * 
 */
public interface SemanticAnalyzerInterface {

	/**
	 * 
	 * @param tokens in postfix order
	 * @return the root node for the expression represented by the top token
	 */
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens);
}
