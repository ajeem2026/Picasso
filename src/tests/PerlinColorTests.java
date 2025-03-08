package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.PerlinColor;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.PerlinColorToken;

/**
 * Tests for the PerlinColor  function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class PerlinColorTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test PerlinColor Evaluation 
	 * 
	 */
	@Test
	public void testPerlinColorEvaluation() {
		PerlinColor myTree = new PerlinColor(new X(), new Y());
		assertEquals(new RGBColor(0.0227,-0.24566,0.08373), myTree.evaluate(0,0));
		assertEquals(new RGBColor(0.4574,0.0957,0.1967), myTree.evaluate(1, 1));
		assertEquals(new RGBColor(-0.4061,-0.0766,-0.1076), myTree.evaluate(-1, -1));

	}
	
	/**
	 * 
	 * Tests PerlinColor Expression Tree Node
	 * 
	 */

	@Test
	public void PerlinColorETGTests() {
		ExpressionTreeNode e = parser.makeExpression("perlinColor(x,y)");
		assertEquals(new PerlinColor(new X(), new Y()), e);
	}
	

	/**
	 * 
	 * Tests PerlinColor Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParsePerlinColor() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new PerlinColorToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new PerlinColor(new X(), new Y()), actual);
	}

}