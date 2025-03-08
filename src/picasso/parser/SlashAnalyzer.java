package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Division;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the slash or "division function".
 * 
 * @author Claire Hamlet
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class SlashAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the star token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		// TODO: Need to finish.
		ExpressionTreeNode right = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode left = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Division(left, right);
	}

}
