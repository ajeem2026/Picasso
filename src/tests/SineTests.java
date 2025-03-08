package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

/**
 * Tests for the sine function in expression trees
 * 
 * @author Abid Jeem
 */
public class SineTests {

	private ExpressionTreeNode testNode;

	@BeforeEach
	public void setUp() throws Exception {
		testNode = new X();
	}

	/**
	 * 
	 * Test boundary and edge cases for sine function
	 * 
	 */
	@Test
	public void testSineEdgeCases() {

		// The domain of sin is -1<= sinx <= 1
		// sin(-1), sin(0), sin(1)

		ExpressionTreeNode sineNode = new Sine(new RGBColor(-1, 0, 1));

		RGBColor result = sineNode.evaluate(0, 0);

		assertEquals(new RGBColor(Math.sin(-1), Math.sin(0), Math.sin(1)), result);
	}

	/**
	 * 
	 * Test sine function applied to constants
	 * 
	 */

	@Test
	public void testSineOfConstant() {
		ExpressionTreeNode sineNode = new Sine(new RGBColor(Math.PI / 2, 0, -Math.PI / 2));

		// sin(pi/2) = 1, sin(0) = 0, sin(-pi/2) = -1
		RGBColor result = sineNode.evaluate(0, 0);
		assertEquals(new RGBColor(1.0, 0.0, -1.0), result);
	}

	/**
	 * 
	 * Test sine function applied to the X variable
	 * 
	 */

	@Test
	public void testSineOfX() {
		ExpressionTreeNode sineNode = new Sine(testNode);

		// Test for x = -pi, -pi/2, 0, pi/2, pi
		double[] testValues = { -Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI };
		double[] expectedValues = { Math.sin(-Math.PI), Math.sin(-Math.PI / 2), Math.sin(0), Math.sin(Math.PI / 2),
				Math.sin(Math.PI) };

		for (int i = 0; i < testValues.length; i++) {
			double testVal = testValues[i];
			double expectedVal = expectedValues[i];
			assertEquals(new RGBColor(expectedVal, expectedVal, expectedVal), sineNode.evaluate(testVal, testVal));
		}
	}

	/**
	 * 
	 * Test sine function applied to nested expressions
	 * 
	 */

	@Test
	public void testSineOfNestedExpressions() {

		// sin(floor(x)) for x = 0.5 -> floor(0.5) = 0, sin(0) = 0

		ExpressionTreeNode sineNode = new Sine(new Floor(testNode));
		assertEquals(new RGBColor(0.0, 0.0, 0.0), sineNode.evaluate(0.5, 0.5));

		// sin(floor(x)) for x = -0.5 -> floor(-0.5) = -1, sin(-1) ≈ -0.841

		assertEquals(new RGBColor(Math.sin(-1), Math.sin(-1), Math.sin(-1)), sineNode.evaluate(-0.5, -0.5));
	}

}
