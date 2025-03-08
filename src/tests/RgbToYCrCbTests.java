package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RgbToYCrCb;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.RgbToYCrCbToken;

/**
 * Tests for the RgbToYCrCb  function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class RgbToYCrCbTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test RgbToYCrCb Evaluation 
	 * 
	 */
	@Test
	public void testRgbToYCrCbEvaluation() {
		RgbToYCrCb myTree = new RgbToYCrCb(new X());
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(0,0));
		assertEquals(new RGBColor(1,.000001,.000001), myTree.evaluate(1, 1));
		assertEquals(new RGBColor(-1,-.000001,-.000001), myTree.evaluate(-1, -1));
	}
	
	/**
	 * 
	 * Tests RgbToYCrCb Expression Tree Node
	 * 
	 */

	@Test
	public void RgbToYCrCbETGTests() {
		ExpressionTreeNode e = parser.makeExpression("rgbToYCrCb(x)");
		assertEquals(new RgbToYCrCb(new X()), e);

		e = parser.makeExpression("rgbToYCrCb(x+x)");
		assertEquals(new RgbToYCrCb(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests RgbToYCrCb Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseRgbToYCrCb() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new RgbToYCrCbToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new RgbToYCrCb(new X()), actual);
	}

}