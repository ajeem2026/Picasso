package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageClip;
import picasso.parser.language.expressions.ImageWrap;
import picasso.parser.tokens.ImageToken;
import picasso.parser.tokens.Token;
import picasso.parser.language.expressions.Image;

/**
 * Handles evaluation of image wrap
 * 
 * @author Vincent Ziccardi
 * @author Sara Sprenkle
 * 
 */
public class ImageWrapAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // get rid of ImageWrapToken
		
		ExpressionTreeNode param2 = SemanticAnalyzer.getInstance().generateExpressionTree(tokens); // eval y expression
		ExpressionTreeNode param1 = SemanticAnalyzer.getInstance().generateExpressionTree(tokens); // eval x expression

		Token token = tokens.pop(); // capture ImageToken
		ImageToken it = (ImageToken) token; // turn it to right type
		Image image = new Image(it.getName()); //create image object
		
		return new ImageWrap(image, param1, param2);
	}

}
