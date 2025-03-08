package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;

/**
 * Handle an identifier token 
 * 
 * @author Sara Sprenkle
 *
 */
public class IdentifierAnalyzer implements SemanticAnalyzerInterface {

	public static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();

	static {
		// We always have x and y defined.
		idToExpression.put("x", new X());
		idToExpression.put("y", new Y());
	}

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		IdentifierToken t = (IdentifierToken) tokens.pop();
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped != null) {
			return mapped;
		}
    
		// TODO : What should we do if we don't recognize the identifier?
		// Is that an error? Or, could there a valid reason?
		throw new ParseException ("Identifier not recognized.");
	}

}
