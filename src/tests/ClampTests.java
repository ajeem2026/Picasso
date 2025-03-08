package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Clamp;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.ClampToken;

/**
 * Tests for the clamp  function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class ClampTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test clamp Evaluation 
	 * 
	 */
	@Test
	public void testClampEvaluation() {
		Clamp myTree = new Clamp(new X());

		// some straightforward tests, y doesnt matter
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(5, 1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(-1,-1, -1), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(-.5,-.5, -.5), myTree.evaluate(-.5, 1));	
		assertEquals(new RGBColor(-1,-1, -1), myTree.evaluate(-2, 1));	

	}
	
	/**
	 * 
	 * Tests Clamp Expression Tree Node
	 * 
	 */

	@Test
	public void ClampETGTests() {
		ExpressionTreeNode e = parser.makeExpression("clamp(x)");
		assertEquals(new Clamp(new X()), e);

		e = parser.makeExpression("clamp(x+x)");
		assertEquals(new Clamp(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests Clamp Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseClamp() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new ClampToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Clamp(new X()), actual);
	}

}