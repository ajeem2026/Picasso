package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.PerlinBW;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the PerlinBW function.
 * 
 * @author Cat Caples
 * 
 */
public class PerlinBWAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the PerlinColor token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ExpressionTreeNode param2 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode param1 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new PerlinBW(param1, param2);
	}

}
