package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.YCrCbToRGB;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.YCrCbToRGBToken;

/**
 * Tests for the YCrCbToRGB  function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class YCrCbToRGBTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test YCrCbToRGB Evaluation 
	 * 
	 */
	@Test
	public void testYCrCbToRGBEvaluation() {
		YCrCbToRGB myTree = new YCrCbToRGB(new X());
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(0,0));
		assertEquals(new RGBColor(2.4019,-0.0601,2.771), myTree.evaluate(1, 1));
		assertEquals(new RGBColor(-2.4019,0.0601,-2.771), myTree.evaluate(-1, -1));

	}
	
	/**
	 * 
	 * Tests YCrCbToRGB Expression Tree Node
	 * 
	 */

	@Test
	public void YCrCbToRGBETGTests() {
		ExpressionTreeNode e = parser.makeExpression("yCrCbToRGB(x)");
		assertEquals(new YCrCbToRGB(new X()), e);

		e = parser.makeExpression("yCrCbToRGB(x+x)");
		assertEquals(new YCrCbToRGB(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests YCrCbToRGB Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseYCrCbToRGB() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new YCrCbToRGBToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new YCrCbToRGB(new X()), actual);
	}

}