package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.PerlinBW;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.PerlinBWToken;

/**
 * Tests for the PerlinBW  function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class PerlinBWTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test PerlinBW Evaluation 
	 * 
	 */
	@Test
	public void testPerlinBWEvaluation() {
		PerlinBW myTree = new PerlinBW(new X(), new Y());
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(0,0));
		assertEquals(new RGBColor(-0.5,-0.5,-0.5), myTree.evaluate(1, 0.5));
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(-0.5,0.5));

	}
	
	/**
	 * 
	 * Tests PerlinBW Expression Tree Node
	 * 
	 */

	@Test
	public void PerlinBWETGTests() {
		ExpressionTreeNode e = parser.makeExpression("perlinBW(x,y)");
		assertEquals(new PerlinBW(new X(), new Y()), e);
	}
	

	/**
	 * 
	 * Tests PerlinBW Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParsePerlinBW() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new PerlinBWToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new PerlinBW(new X(), new Y()), actual);
	}

}