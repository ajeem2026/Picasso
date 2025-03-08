/**
 * 
 */
package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.ColorToken;
import picasso.parser.tokens.Token;

/**
 * SemanticAnalyzer for an RGB Color
 * 
 * @author Sara Sprenkle
 * 
 */
public class ColorAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		Token t = tokens.pop();
		if( ! ( t instanceof ColorToken )) {
			// XXX: Look into this more.  Is this even possible?
			throw new ParseException("Expected a color");
		}
		ColorToken ct = (ColorToken) t;
		return new RGBColor( ct.getRed(), ct.getGreen(), ct.getBlue());
	}
}
