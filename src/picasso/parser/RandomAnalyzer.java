package picasso.parser;

import java.util.Stack;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RandomFunction;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.RandomToken;

/**
 * Analyzer for random()
 * @Author Claire Hamlet
 */
public class RandomAnalyzer extends FunctionAnalyzer{
    /**
     * @param tokens stack in postfix
     * @return root node for expression tree
     */
	@Override
    public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
        // pop from stack
        Token token = tokens.pop();

        // check token is RandomToken
        if (!(token instanceof RandomToken)) {
            throw new IllegalArgumentException("Expected a RandomToken but got: " + token.getClass().getName());
        }

        return new RandomFunction();
    }
}
