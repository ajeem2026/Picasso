package picasso.parser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.*;
import picasso.parser.tokens.chars.*;
import picasso.parser.tokens.functions.*;
import picasso.parser.tokens.operations.*;

/**
 * Parses a string into an expression tree based on rules for arithmetic.
 * 
 * @author Claire Hamlet
 * @author former student solution
 * @author Robert C. Duvall (added comments, exceptions)
 * @author Sara Sprenkle modified for Picasso
 */
public class ExpressionTreeGenerator {

	// TODO: Do these belong here?
	//Swapped order to match PEMDAS, correct order of operations
	private static final int CONSTANT = 0;
	private static final int ADD_OR_SUBTRACT = 1; 
	private static final int MULTIPLY_OR_DIVIDE = 2;
	private static final int FUNCTIONS = 3; //
	private static final int GROUPING = 4; //parentheses

	/**
	 * Converts the given string into expression tree for easier manipulation.
	 * 
	 * @param infix - a non-empty expression to parse.
	 * 
	 * @return ExpressionTreeNode representing the root node of the given infix
	 *         formula
	 */
	public ExpressionTreeNode makeExpression(String infix) {
		Stack<Token> postfix = infixToPostfix(infix);

		if (postfix.isEmpty()) {
			return null;
		}

		// System.out.println("Process postfix expression");
		SemanticAnalyzer semAnalyzer = SemanticAnalyzer.getInstance();

		ExpressionTreeNode root = semAnalyzer.generateExpressionTree(postfix);

		// Is this the best place to put this check?
		if (!postfix.isEmpty()) {
		    Token token = postfix.peek();
		    if (!(token instanceof FunctionToken)) {
		        throw new ParseException("Extra operands without operators or functions");
		    }
			return root;
		}
		return root;
	}
		

	/**
	 * This method converts the String infix expression to a Stack of tokens, which
	 * are in postfix.
	 * 
	 * @param infix the String to parse, as we would typically write it
	 * @return a stack of tokens, in postfix order
	 */
	public Stack<Token> infixToPostfix(String infix) {

		Tokenizer tokenizer = new Tokenizer();
		List<Token> tokens = tokenizer.parseTokens(infix);
		
		return infixToPostfix(tokens);
	}

	/**
	 * This method converts the List of tokens (in infix order) to a Stack of
	 * tokens, which are in postfix.
	 * 
	 * @param tokens the Tokens, in infix order
	 * @return a stack of tokens, in postfix order
	 */
	private Stack<Token> infixToPostfix(List<Token> tokens) {
		// Algorithm for converting infix to postfix was adapted from
		// http://en.wikipedia.org/wiki/Shunting_yard_algorithm
		// Needed to handle identifiers and colors, which aren't in the original
		// algorithm.
		// May need to modify/update further...

		Stack<Token> operators = new Stack<Token>();
		Stack<Token> postfixResult = new Stack<Token>();

		Iterator<Token> iter = tokens.iterator();

		// TO DISCUSS: Is this the correct way to design this code?
		// What is the code smell? What is the alternative?

		while (iter.hasNext()) {
			Token token = iter.next();
		    if (token instanceof NumberToken || token instanceof ColorToken || token instanceof IdentifierToken || token instanceof ImageToken) {
		        // Numbers, variables, or constants go directly to the postfix result
		        postfixResult.push(token);
		    } else if (token instanceof FunctionToken) {
		        // Functions are pushed to the operator stack
		        operators.push(token);
			} else if (token instanceof OperationInterface) {
				/*
				 * while there is an operator, o2, at the top of the stack (this excludes left
				 * parenthesis), and either
				 * 
				 * o1 is left-associative and its precedence is less than (lower precedence) or
				 * equal to that of o2, or o1 is right-associative and its precedence is less
				 * than (lower precedence) that of o2 (SS: second case is not reflected in below
				 * code),
				 * 
				 * pop o2 off the stack, onto the output queue;
				 */
				while (!operators.isEmpty() && !(operators.peek() instanceof LeftParenToken)
						&& orderOfOperation(token) <= orderOfOperation(operators.peek())) {
					postfixResult.push(operators.pop());
				}

				operators.push(token);

			} else if (token instanceof CommaToken) {
				// Until the token at the top of the stack is a left
				// parenthesis, pop operators off the stack onto the output
				// queue.

				while (!operators.isEmpty() && !(operators.peek() instanceof LeftParenToken)) {
					postfixResult.push(operators.pop());
				}

				// If no left parentheses are encountered, either the
				// separator was misplaced or parentheses were mismatched.
				if (operators.isEmpty() || !(operators.peek() instanceof LeftParenToken)) {
					throw new ParseException("Parentheses were mismatched.");
				}

			} else if (token instanceof LeftParenToken) {
				operators.push(token);
			} else if (token instanceof RightParenToken) {
				// Until the token at the top of the stack is a left
				// parenthesis, pop operators off the stack onto the output
				// queue.
				while (operators.size() > 0 && !(operators.peek() instanceof LeftParenToken)) {
					postfixResult.push(operators.pop());
				}

				// Pop the left parenthesis from the stack, but not onto the
				// output queue.
				if (operators.isEmpty()) {
					throw new ParseException("Missing (");
				}
				operators.pop();

				// If the token at the top of the stack is a function token, pop
				// it onto the output queue.
				if (operators.size() > 0 && operators.peek() instanceof FunctionToken) {
					postfixResult.push(operators.pop());
				}

			} else {
				System.out.println("ERROR: No match: " + token);
			}
			// System.out.println("Postfix: " + postfixResult);
		}

		while (!operators.isEmpty()) {

			// If the operator token on the top of the stack is a parenthesis,
			// then there are mismatched parentheses.

			Token top = operators.peek();

			if (top.equals(CharTokenFactory.getToken('(')) || top.equals(CharTokenFactory.getToken(')'))) {
				throw new ParseException("Mismatched Parentheses");
			}
			postfixResult.push(operators.pop());
		}
		System.out.println(postfixResult);
		postfixResult = simplifyExpression(postfixResult);
		System.out.println(postfixResult);
		return postfixResult;
	}

	private Stack<Token> simplifyExpression(Stack<Token> postFixExpression) {
		
		Stack<Token> resultStack = new Stack<>();
		int simplified;
		
		for (Token token: postFixExpression) {
			// reset flag
			simplified = 0;
			// case of addition token
			if (token instanceof PlusToken) {
				// get operands
				Token operand2 = resultStack.pop();
				Token operand1 = resultStack.pop();
//				// if both operands are numbers, do the addition and return it
//				if (operand1 instanceof NumberToken && operand2 instanceof NumberToken) {
//					NumberToken numToken1 = (NumberToken) operand1;
//					NumberToken numToken2 = (NumberToken) operand2;
//					NumberToken total = new NumberToken(numToken1.value() + numToken2.value());
//					resultStack.push(total);
//					simplified = 1;
//				}
				// cases where one operand is 0, just return the other
				if (operand1 instanceof NumberToken) {
					NumberToken numToken = (NumberToken) operand1;
					if ( numToken.value() == 0.0 ) {
						resultStack.push(operand2);
						simplified = 1;
					}
				}
				else if (operand2 instanceof NumberToken) {
					NumberToken numToken = (NumberToken) operand2;
					if ( numToken.value() == 0.0 ) {
						resultStack.push(operand1);
						simplified = 1;
					}
				}
				// there was no way to simplify, put operands and addition back in stack
				if (simplified == 0) { 
					resultStack.push(operand1);
					resultStack.push(operand2);
					resultStack.push(token);
				}
			}
			// case of subtraction token
			else if (token instanceof MinusToken) {
				// get operands
				Token operand2 = resultStack.pop();
				Token operand1 = resultStack.pop();
				// if both operands are numbers, do the subtraction and return it
//				if (operand1 instanceof NumberToken && operand2 instanceof NumberToken) {
//					NumberToken numToken1 = (NumberToken) operand1;
//					NumberToken numToken2 = (NumberToken) operand2;
//					NumberToken total = new NumberToken(numToken1.value() - numToken2.value());
//					resultStack.push(total);
//					simplified = 1;
//				}
				if (operand2 instanceof NumberToken) {
					NumberToken numToken = (NumberToken) operand2;
					if ( numToken.value() == 0.0 ) {
						resultStack.push(operand1);
						simplified = 1;
					}
				}
				// there was no way to simplify, put operands and subtraction back in stack
				if (simplified == 0) { 
					resultStack.push(operand1);
					resultStack.push(operand2);
					resultStack.push(token);
				}
			}
			// case of multiplication token
			else if (token instanceof StarToken) {
				// get operands
				Token operand2 = resultStack.pop();
				Token operand1 = resultStack.pop();
//				// if both operands are numbers, do the multiplication and return it
//				if (operand1 instanceof NumberToken && operand2 instanceof NumberToken) {
//					NumberToken numToken1 = (NumberToken) operand1;
//					NumberToken numToken2 = (NumberToken) operand2;
//					NumberToken total = new NumberToken(numToken1.value() * numToken2.value());
//					resultStack.push(total);
//					simplified = 1;
//				}
				// cases where one operand is 1 or 0, return the other operand/return 0
				if (operand1 instanceof NumberToken) {
					NumberToken numToken = (NumberToken) operand1;
					if ( numToken.value() == 1.0 ) {
						resultStack.push(operand2);
						simplified = 1;
					}
					else if (numToken.value() == 0.0) {
						resultStack.push(new NumberToken(0.0));
						simplified = 1;
					}
				}
				else if (operand2 instanceof NumberToken) {
					NumberToken numToken = (NumberToken) operand2;
					if ( numToken.value() == 1.0 ) {
						resultStack.push(operand1);
						simplified = 1;
					}
					else if (numToken.value() == 0.0) {
						resultStack.push(new NumberToken(0.0));
						simplified = 1;
					}
				}
				// there was no way to simplify, put operands and multiplication back in stack
				if (simplified == 0) { 
					resultStack.push(operand1);
					resultStack.push(operand2);
					resultStack.push(token);
				}
			}
			// not a operator token, just add to stack
			else {
				resultStack.push(token);
			}
//			System.out.println(resultStack);
		}
		return resultStack;
	}


	/**
	 * 
	 * @param token
	 * @return
	 */
	private int orderOfOperation(Token token) {
		// TODO: Need to finish with other operators.

		// TODO: DISCUSS: Is it better to have a method in the OperatorToken
		// class that gives the order of operation?

		if (token instanceof LeftParenToken || token instanceof RightParenToken) {
			return GROUPING; //highest precedence (4)
		} else if (token instanceof FunctionToken) {
			return FUNCTIONS; //higher precedence (3)
		} else if (token instanceof StarToken || token instanceof SlashToken) {
			return MULTIPLY_OR_DIVIDE; //lower precedence (2)
		} else if (token instanceof PlusToken || token instanceof MinusToken) {
			return ADD_OR_SUBTRACT; //lower precedence (1)
		} else {
			return CONSTANT; //lowest precedence (0)
		}
	}
}
