package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Log;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.X;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.LogToken;

/**
 * Tests for the log function in the Picasso language.
 * 
 * @author Cat Caples
 * 
 */

public class LogTests {

	private ExpressionTreeGenerator parser;
	private SemanticAnalyzer semAnalyzer;
		
	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	
	/**
	 * 
	 * Test log Evaluation 
	 * 
	 */
	@Test
	public void testLogEvaluation() {
		Log myTree = new Log(new X());

		// some straightforward tests, y doesnt matter
		assertEquals(new RGBColor(0,0,0), myTree.evaluate(1, 1));
		assertEquals(new RGBColor(2.3026,2.3026,2.3026), myTree.evaluate(10, 1));

	}
	
	/**
	 * 
	 * Tests log Expression Tree Node
	 * 
	 */

	@Test
	public void LogETGTests() {
		ExpressionTreeNode e = parser.makeExpression("log(x)");
		assertEquals(new Log(new X()), e);

		e = parser.makeExpression("log(x+x)");
		assertEquals(new Log(new Addition(new X(), new X())), e);
	}
	

	/**
	 * 
	 * Tests Log Semantic Analyzer
	 * 
	 */
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	void testParseLog() {

		semAnalyzer = SemanticAnalyzer.getInstance();

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new LogToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Log(new X()), actual);
	}

}