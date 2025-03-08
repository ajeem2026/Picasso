package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Ceil;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.CeilToken;

/**
 * Tests for the ceil value function in the Picasso language.
 * 
 * @author Medaly Cardenas
 * 
 */

public class CeilTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test Ceil Value Evaluation 
	 * 
	 */
	@Test
	public void testCeilEvaluation() {
		Ceil myTree = new Ceil(new X());

		// some straightforward tests
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.4, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.999, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-.7, -1));

		// test the ints; remember that y's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double ceilOfTestVal = Math.ceil(testVal);
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
	
	/**
	 * 
	 * Tests Ceil Value Expression Tree Node
	 * 
	 */

	@Test
	public void CeilETGTests() {
		ExpressionTreeNode e = parser.makeExpression("ceil(x)");
		assertEquals(new Ceil(new X()), e);

		e = parser.makeExpression("ceil( x + y )");
		assertEquals(new Ceil(new Addition(new X(), new Y())), e);
	}
	

	/**
	 * 
	 * Tests Ceil Value Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseCeil() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new CeilToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Ceil(new X()), actual);
	}



}
