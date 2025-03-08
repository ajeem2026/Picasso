package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Exp;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the exp function.
 * 
 * @author Vincent Ziccardi
 * @author Sara Sprenkle
 * 
 */
public class ExpAnalyzer extends UnaryFunctionAnalyzer {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the exp token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Exp(paramETN);
	}

}
