package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Log;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the log function.
 * 
 * @author Cat Caples
 * 
 */
public class LogAnalyzer extends UnaryFunctionAnalyzer {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the log token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Log(paramETN);
	}

}
