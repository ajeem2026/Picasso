package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Variable;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the assignment tree
 * 
 * @author Cat Caples
 * 
 */
public class EqualsAnalyzer implements SemanticAnalyzerInterface {

	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the assignment token
		ExpressionTreeNode right = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);

		Token leftToken = tokens.pop();
		if (leftToken instanceof IdentifierToken) {
			IdentifierToken identifierToken = (IdentifierToken) leftToken;

			if ((identifierToken.getName().equals("x")) | (identifierToken.getName().equals("y"))) {
				throw new ParseException("values cannot be assigned to x and y");

			} else {
				return new Variable(identifierToken.getName(), right);
			}
		} else {
			throw new ParseException("Variable is not valid.");
		}

	}
}
