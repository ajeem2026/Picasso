package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Wrap;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.WrapToken;

/**
 * Tests for the wrap function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class WrapTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test wrap Evaluation 
	 * 
	 */
	@Test
	public void testWrapEvaluation() {
		Wrap myTree = new Wrap(new X());

		// some straightforward tests, y doesnt matter
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(5, 1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-3, -1));
		assertEquals(new RGBColor(0.5,0.5, 0.5), myTree.evaluate(0.5, 1));
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(2, 1));	

	}
	
	/**
	 * 
	 * Tests wrap Expression Tree Node
	 * 
	 */

	@Test
	public void WrapETGTests() {
		ExpressionTreeNode e = parser.makeExpression("wrap(x)");
		assertEquals(new Wrap(new X()), e);

		e = parser.makeExpression("wrap(x+x)");
		assertEquals(new Wrap(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests wrap Semantic Analyzer
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
		tokens.push(new WrapToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Wrap(new X()), actual);
	}

}