package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

/**
 * Tests for the tangent function in expression trees
 * 
 * @author Claire Hamlet
 */

public class TangentTests {

	private ExpressionTreeNode testNode;

	@BeforeEach
	public void setUp() throws Exception {
		testNode = new X();
	}

	/**
	 * Tests tangent of X
	 */
    @Test
    public void testTangentOfX() {
        ExpressionTreeNode tangentNode = new Tangent(testNode);
        double[] testValues = { -Math.PI / 4, 0, Math.PI / 4 };
        double[] expectedValues = { Math.tan(-Math.PI / 4), Math.tan(0), Math.tan(Math.PI / 4) };

        for (int i = 0; i < testValues.length; i++) {
            double testVal = testValues[i];
            double expectedVal = expectedValues[i];
            assertEquals(new RGBColor(expectedVal, expectedVal, expectedVal), tangentNode.evaluate(testVal, testVal));
        }
    }
	
	/**
	 * Tests edge cases for tangent function
	 */
	@Test
	public void testTangentEdgeCases() {
		// tangent asymptotes at π/2 + k*π
		ExpressionTreeNode tangentNode = new Tangent(new RGBColor(Math.PI / 2, 0, -Math.PI / 2));
		RGBColor result = tangentNode.evaluate(0, 0);
		assertEquals(new RGBColor(Math.tan(Math.PI / 2), Math.tan(0), Math.tan(Math.PI / 2)), result);
	}

	/**
	 * Test tangent function of constants
	 */
	@Test
	public void testTangentOfConstant() {
		ExpressionTreeNode tangentNode = new Tangent(new RGBColor(0, Math.PI / 4, -Math.PI / 4));

		// tan(0) = 0, tan(pi/4) = 1, tan(-pi/4) = -1
		RGBColor result = tangentNode.evaluate(0, 0);
		assertEquals(new RGBColor(0.0, 1.0, -1.0), result);
	}

	/**
	 * Tests tangent function applied to nested expressions
	 */
    @Test
    public void testTangentOfNestedExpressions() {
        // tan(floor(x)) for x = 1.5 -> floor(1.5) = 1, tan(1) ≈ 1.557
        ExpressionTreeNode tangentNode = new Tangent(new Floor(testNode));
        assertEquals(new RGBColor(Math.tan(1), Math.tan(1), Math.tan(1)), tangentNode.evaluate(1.5, 1.5));
    }

}