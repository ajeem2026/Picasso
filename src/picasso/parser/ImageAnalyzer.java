package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Image;
import picasso.parser.tokens.ImageToken;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the Image object.
 * 
 * @author Vincent Ziccardi
 * @author Sara Sprenkle
 * 
 */
public class ImageAnalyzer extends UnaryFunctionAnalyzer {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		Token plain_token = tokens.pop(); // Need to remove the exp token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ImageToken image_token = (ImageToken) plain_token;
	
		return new Image( image_token.getName());
	}

}
