package picasso.parser.language.expressions;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the image wrap function in the Picasso language.
 * 
 * @author Vincent Ziccardi
 * 
 */
public class ImageWrap extends ExpressionTreeNode{

	/**
	 * Create an addition expression tree that takes as a parameter the given expression
	 * 
	 * @param param the expression to addition
	 */
	Image image;
	ExpressionTreeNode xExprTree;
	ExpressionTreeNode yExprTree;
	
	public ImageWrap(Image image, ExpressionTreeNode xExprTree, ExpressionTreeNode yExprTree) {
		this.image = image;
		this.xExprTree = xExprTree;
		this.yExprTree = yExprTree;
	}


	/**
	 * Evaluates this expression at the given x,y point by evaluating the addition of
	 * the function's parameters.
	 * 
	 * @return the color from evaluating the addition of the expression's parameters
	 */
	public RGBColor evaluate(double x, double y) {
		
		Wrap xExpression = new Wrap(xExprTree);
		Wrap yExpression = new Wrap(yExprTree);
		
		RGBColor xcolor = xExpression.evaluate(x,y);
		RGBColor ycolor = yExpression.evaluate(x,y);
		
//		image.evaluate(xcolor.getGreen(),ycolor.getGreen());
//		image.evaluate(xcolor.getBlue(),ycolor.getBlue());
		
		RGBColor finalColor = image.evaluate(xcolor.getRed(), ycolor.getRed());
				
		return finalColor;
	}

}
