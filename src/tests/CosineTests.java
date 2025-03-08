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
import picasso.parser.tokens.functions.CosToken;


/**
 * Tests for the cosine function in expression trees
 * 
 * @author Cat Caples
 */
public class CosineTests {

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
	 * Test boundary and edge cases for cosine function
	 * 
	 */
	@Test
	public void testCosineEdgeCases() {
		ExpressionTreeNode cosNode = new Cosine(new RGBColor(-1, 0, 1));
		RGBColor result = cosNode.evaluate(0, 0);
		assertEquals(new RGBColor(Math.cos(-1), Math.cos(0), Math.cos(1)), result);
	}

	/**
	 * 
	 * Test cosine function applied to constants
	 * 
	 */

	@Test
	public void testCosineOfConstant() {
		ExpressionTreeNode cosineNode = new Cosine(new RGBColor(Math.PI / 2, 0, -Math.PI / 2));

		// cos(pi/2) = 0, cos(0) = 1, cos(-pi/2) = 0
		RGBColor result = cosineNode.evaluate(0, 0);
		assertEquals(new RGBColor(0.0, 1.0, 0.0), result);
	}

	/**
	 * 
	 * Test cosine function applied to the X variable
	 * 
	 */

	@Test
	public void testCosineOfX() {
		ExpressionTreeNode cosineNode = new Cosine(testNode);

		// Test for x = -pi, -pi/2, 0, pi/2, pi
		double[] testValues = { -Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI };
		double[] expectedValues = { Math.cos(-Math.PI), Math.cos(-Math.PI / 2), Math.cos(0), Math.cos(Math.PI / 2),
				Math.cos(Math.PI) };

		for (int i = 0; i < testValues.length; i++) {
			double testVal = testValues[i];
			double expectedVal = expectedValues[i];
			assertEquals(new RGBColor(expectedVal, expectedVal, expectedVal), cosineNode.evaluate(testVal, testVal));
		}
	}

	/**
	 * 
	 * Tests cosine Expression Tree Node
	 * 
	 */

	@Test
	public void CosETGTests() {
		ExpressionTreeNode e = parser.makeExpression("cos(x)");
		assertEquals(new Cosine(new X()), e);

		e = parser.makeExpression("cos(x+x)");
		assertEquals(new Cosine(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests cos Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseCosine() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new CosToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Cosine(new X()), actual);
	}

}