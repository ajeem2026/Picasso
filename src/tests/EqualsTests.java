package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.IdentifierAnalyzer;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.Tokenizer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.Variable;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.EqualsToken;

/**
 * Testing for the Variable Class, EqualsToken, and EqualsAnalyzer
 * 
 * @author Cat Caples
 */

public class EqualsTests {

	Tokenizer tokenizer;
	List<Token> tokens;
	SemanticAnalyzer semAnalyzer;
	ExpressionTreeGenerator parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		tokenizer = new Tokenizer();
		semAnalyzer = SemanticAnalyzer.getInstance();
		parser = new ExpressionTreeGenerator();
	}
	/**
	 * Tests Equals Tokenization
	 */
	@Test
	public void testEqualsToken() {
		String expression = "a=x";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new IdentifierToken("a"), tokens.get(0));
		assertEquals(new EqualsToken(), tokens.get(1));
		assertEquals(new IdentifierToken("x"), tokens.get(2));
	}
	/**
	 * Tests Equals Semantic Analyzer
	 */
	@Test
	void testParseEquals() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("a"));
		tokens.push(new IdentifierToken("x"));
		tokens.push(new EqualsToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);

		assertEquals(new Variable("a", new X() ), actual);
		
	}
	
	/**
	 * Tests that the variable is an identifier token
	 */
	@Test
	void testIDToken() {
		String expression = "a=x";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(IdentifierToken.class, tokens.get(0).getClass());
		
		String expression2 = "b=x+y";
		tokens = tokenizer.parseTokens(expression2);
		assertEquals(IdentifierToken.class, tokens.get(0).getClass());
	}
	
	/**
	 * Tests Equals Expression Tree Generator
	 */
	@Test
	public void equalsExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("a=x+y");
		assertEquals(new Variable("a", new Addition(new X(), new Y())), e);

		e = parser.makeExpression("a=x");
		assertEquals(new Variable("a", new X()), e);
	}
	
	/**
	 * Tests that identifierAnalyzer correctly maps the variable to the rhs
	 */

	@Test
	public void testVariableMapping() {
		ExpressionTreeNode testRHS = new X(); 
		Variable var = new Variable("testVar", testRHS);
		var.evaluate(0, 0); 
		assertEquals(testRHS, IdentifierAnalyzer.idToExpression.get("testVar"));
	}
}
