package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import picasso.parser.ParseException;
import picasso.parser.ExpressionTreeGenerator;

/**
 * Tests of incorrect parsing of strings by the ExpressionTreeGenerator
 * 
 * @author Sara Sprenkle
 * 
 */
public class ErrorParsedEvaluatedTests {

	private ExpressionTreeGenerator parser;

	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	@Test
	public void errorConstantExpressionTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("- .7");
		});
	}

	@Test
	public void errorUnrecognizedInputTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("a");
		});
	}

	@Test
	public void errorTooFewArgsTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("( .7 + )");
		});
	}

	@Test
	public void errorExtraOperandTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("( .7 + .3 .5)");
		});
	}

	@Test
	public void errorExtraOperandTest2() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("( .7 * .3 ) .5");
		});
	}

	@Test
	public void errorMissingRightParenTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("( .7 * .3");
		});
	}

	@Test
	public void errorMissingLeftParenTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression(".7 * .3 )");
		});
	}
	
	@Test
	public void errorMissingFunctionParenTest() {
		assertThrows(ParseException.class, () -> {
			parser.makeExpression("floor(x");
		});
	}

}
