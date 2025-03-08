package tests;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.ImageToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.ExpToken;
import picasso.parser.tokens.functions.ImageClipToken;

/**
 * Runs tests on the imageClip function's evaluation, expression tree generation, and semantic analysis 
 * 
 * @author Vincent Ziccardi
 */

public class ImageClipTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		semAnalyzer = SemanticAnalyzer.getInstance();
		parser = new ExpressionTreeGenerator();
	}
	
	@Test
	public void imClipExpressionTreeTests() {
		ExpressionTreeNode e = parser.makeExpression("imageClip(\"vortex.jpg\", x , y)");
		assertEquals(new ImageClip(new Image("vortex.jpg"), new X(), new Y()), e);

		ExpressionTreeNode e2 = parser.makeExpression("imageClip(\"vortex.jpg\", x + x , y");
		assertEquals(new ImageClip(new Image("vortex.jpg"), new Addition(new X(), new X()), new Y()), e2);

	}
	@Test
	public void testimClipEvaluation() {
		ImageClip myTree = new ImageClip(new Image("vortex.jpg"), new X(), new Y());
		Image image = new Image("vortex.jpg");

		// some straightforward tests when expressions are just x and y
		assertEquals(image.evaluate(0, -1), myTree.evaluate(0, -1));
		assertEquals(image.evaluate(.5, -1), myTree.evaluate(.5, -1));
		assertEquals(image.evaluate(.5, 1), myTree.evaluate(.5, 1));
		assertEquals(image.evaluate(-.5, 1), myTree.evaluate(-.5, 1));

		// test the ints; remember that y's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(image.evaluate(i, i), myTree.evaluate(i, i));
			assertEquals(image.evaluate(-i, -i), myTree.evaluate(-i, -i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			RGBColor imOfTestVal = image.evaluate(testVal, testVal);
			assertEquals(imOfTestVal, myTree.evaluate(testVal, testVal));
		}
	}

	@Test
	void testImageClipParse() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new ImageToken("vortex.jpg"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new ImageClipToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new ImageClip(new Image("vortex.jpg"), new X(), new Y()), actual);
	}

}
