package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.AtanToken;


/**
 * Tests for the atan function in expression trees
 * 
 * @author Cat Caples
 */
public class ATanTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
	private ExpressionTreeNode testNode;

	@BeforeEach
	public void setUp() throws Exception {
		testNode = new X();
		parser = new ExpressionTreeGenerator();

	}

	/**
	 * 
	 * Test boundary and edge cases for atan function
	 * 
	 */
	@Test
	public void testCosineEdgeCases() {
		ExpressionTreeNode AtanNode = new Atan(new RGBColor(-1, 0, 1));
		RGBColor result = AtanNode.evaluate(0, 0);
		assertEquals(new RGBColor(Math.atan(-1), Math.atan(0), Math.atan(1)), result);
	}

	/**
	 * 
	 * Test atan function applied to constants
	 * 
	 */

	@Test
	public void testAtanOfConstant() {
		ExpressionTreeNode atanNode = new Atan(new RGBColor(0, 1, -1));

		// atan(0) = 0, atan(1) = pi/4, atan(-1) = -pi/4
		RGBColor result = atanNode.evaluate(0, 0);
		assertEquals(new RGBColor(0.0, Math.PI / 4, -Math.PI / 4), result);
	}

	/**
	 * 
	 * Test atan function applied to the X variable
	 * 
	 */

	@Test
	public void testAtanOfX() {
		ExpressionTreeNode atanNode = new Atan(testNode);

		// Test for x = -pi, -pi/2, 0, pi/2, pi
		double[] testValues = { -Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI };
		double[] expectedValues = { Math.atan(-Math.PI), Math.atan(-Math.PI / 2), Math.atan(0), Math.atan(Math.PI / 2),
				Math.atan(Math.PI) };

		for (int i = 0; i < testValues.length; i++) {
			double testVal = testValues[i];
			double expectedVal = expectedValues[i];
			assertEquals(new RGBColor(expectedVal, expectedVal, expectedVal), atanNode.evaluate(testVal, testVal));
		}
	}

	/**
	 * 
	 * Tests atan Expression Tree Node
	 * 
	 */

	@Test
	public void AtanETGTests() {
		ExpressionTreeNode e = parser.makeExpression("atan(x)");
		assertEquals(new Atan(new X()), e);

		e = parser.makeExpression("atan(x+x)");
		assertEquals(new Atan(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests atan Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseAtan() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new AtanToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Atan(new X()), actual);
	}

}