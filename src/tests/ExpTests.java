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
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.ExpToken;

/**
 * Runs tests on the exp function's evaluation, expression tree generation, and semantic analysis 
 * 
 * @author Vincent Ziccardi
 */

public class ExpTests {

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
	public void expExpressionTreeTests() {
		ExpressionTreeNode e = parser.makeExpression("exp( x )");
		assertEquals(new Exp(new X()), e);

		e = parser.makeExpression("exp( exp( y ) )");
		assertEquals(new Exp(new Exp(new Y())), e);
	}
	@Test
	public void testExpEvaluation() {
		Exp myTree = new Exp(new X());

		// some straightforward tests
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(Math.exp(1), Math.exp(1), Math.exp(1)), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(Math.exp(-.5), Math.exp(-.5), Math.exp(-.5)), myTree.evaluate(-.5, -1));

		// test the ints; remember that y's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double expOfTestVal = Math.exp(testVal);
			assertEquals(new RGBColor(expOfTestVal, expOfTestVal, expOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(expOfTestVal, expOfTestVal, expOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}

	@Test
	void testParseExp() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new ExpToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Exp(new X()), actual);
	}

}
