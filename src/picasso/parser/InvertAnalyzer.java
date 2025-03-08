package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Invert;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the invert function.
 * 
 * @author Cat Caples
 * 
 */
public class InvertAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the invert token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		// TODO: Need to finish.
		ExpressionTreeNode result = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);

		return new Invert(result);
	}

}
