package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Invert;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.InvertToken;

/**
 * Tests for the Invert function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class InvertTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test Invert Evaluation 
	 * 
	 */
	@Test
	public void testAbsEvaluation() {
		Invert myTree = new Invert(new X());

		// some straightforward tests
		assertEquals(new RGBColor(4, 4, 4), myTree.evaluate(-4, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(.7,.7, .7), myTree.evaluate(-.7, 1));

		// test the ints; remember that y's value doesn't matter
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(-.5, 1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, 1));
		

	}
	
	/**
	 * 
	 * Tests Invert Expression Tree Node
	 * 
	 */

	@Test
	public void InvertETGTests() {
		ExpressionTreeNode e = parser.makeExpression("!x");
		assertEquals(new Invert(new X()), e);

		e = parser.makeExpression("!(x+y)");
		assertEquals(new Invert(new Addition(new X(), new Y())), e);
	}
	

	/**
	 * 
	 * Tests Invert Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseInvert() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new InvertToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Invert(new X()), actual);
	}

}