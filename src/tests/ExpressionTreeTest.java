package tests;

import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.ParseException;
import picasso.parser.Tokenizer;
import picasso.parser.tokens.Token;

import java.util.List;
import java.util.Stack;

public class ExpressionTreeTest {

    private ExpressionTreeGenerator generator = new ExpressionTreeGenerator();

    @Test
    public void testSimpleArithmetic() {
        String input = "3 + 5";
        Stack<Token> result = generator.infixToPostfix(input);
        assertEquals("3 5 +", stackToString(result));
    }

    private void assertEquals(String string, String stackToString) {
		// TODO Auto-generated method stub
	}

	@Test
    public void testOperatorPrecedence() {
        String input = "3 + 5 * 2";
        Stack<Token> result = generator.infixToPostfix(input);
        assertEquals("3 5 2 * +", stackToString(result));
    }

    @Test
    public void testParentheses() {
        String input = "3 * (2 + 4)";
        Stack<Token> result = generator.infixToPostfix(input);
        assertEquals("3 2 4 + *", stackToString(result));
    }

    @Test
    public void testNestedParentheses() {
        String input = "(3 + 2) * (4 - 1)";
        Stack<Token> result = generator.infixToPostfix(input);
        assertEquals("3 2 + 4 1 - *", stackToString(result));
    }

    @Test
    public void testDivisionAndSubtraction() {
        String input = "10 / (5 - 3)";
        Stack<Token> result = generator.infixToPostfix(input);
        assertEquals("10 5 3 - /", stackToString(result));
    }


    @Test
    public void testInvalidInputMismatchedParentheses() {
        String input = "3 + (4 * 2";
        assertThrows(ParseException.class, () -> {
            generator.infixToPostfix(input);
        });
    }

    @Test
    public void testTokenizer() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.parseTokens("3 + 5 * 2");
        assertEquals("[NumberToken: 3, PlusToken: +, NumberToken: 5, MultiplyToken: * , NumberToken: 2]", tokens.toString());
    }

    @Test
    public void testInfixToPostfix() {
        ExpressionTreeGenerator generator = new ExpressionTreeGenerator();
        Stack<Token> postfix = generator.infixToPostfix("3 + 5 * 2");
        assertEquals("3 5 2 * +", stackToString(postfix));
    }    


    private String stackToString(Stack<Token> stack) {
        StringBuilder sb = new StringBuilder();
        for (Token token : stack) {
            sb.append(token.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}
