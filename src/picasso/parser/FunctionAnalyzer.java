package picasso.parser;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;

/**
 * Abstract class that parses a function that takes no expressions as a parameter (random())
 * @author Claire Hamlet
 * 
 */
public abstract class FunctionAnalyzer implements SemanticAnalyzerInterface {
	@Override
		public abstract ExpressionTreeNode generateExpressionTree(
				Stack<Token> tokens);
}



