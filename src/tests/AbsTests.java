package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Abs;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.AbsToken;

/**
 * Tests for the absolute value function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class AbsTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test Absolute Value Evaluation 
	 * 
	 */
	@Test
	public void testAbsEvaluation() {
		Abs myTree = new Abs(new X());

		// some straightforward tests
		assertEquals(new RGBColor(4, 4, 4), myTree.evaluate(-4, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(.7,.7, .7), myTree.evaluate(-.7, 1));

		// test the ints; remember that y's value doesn't matter
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(-.5, 1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, 1));
		


		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double absOfTestVal = Math.abs(testVal);
			assertEquals(new RGBColor(absOfTestVal, absOfTestVal, absOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(absOfTestVal, absOfTestVal, absOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
	
	/**
	 * 
	 * Tests Absolute Value Expression Tree Node
	 * 
	 */

	@Test
	public void AbsETGTests() {
		ExpressionTreeNode e = parser.makeExpression("abs(x)");
		assertEquals(new Abs(new X()), e);

		e = parser.makeExpression("abs( x + y )");
		assertEquals(new Abs(new Addition(new X(), new Y())), e);
	}
	

	/**
	 * 
	 * Tests Absolute Value Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseAbs() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new AbsToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Abs(new X()), actual);
	}

}

