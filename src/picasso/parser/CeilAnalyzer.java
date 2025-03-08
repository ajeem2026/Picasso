package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Ceil;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the ceil function.
 * 
 * @author Medaly Cardenas
 * 
 */
public class CeilAnalyzer extends UnaryFunctionAnalyzer {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the ceil token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Ceil(paramETN);
	}

}