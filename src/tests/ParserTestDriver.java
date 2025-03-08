package tests;

import java.io.InputStreamReader;
import java.util.Scanner;

import picasso.parser.ParseException;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Repeatedly prompts the user for expressions to parse and evaluate.
 * 
 * This style of interaction is called the read-eval-print loop and forms the
 * basis of most interactive programming environments.
 * 
 * Note: this code has an error in it.
 * 
 * @author former student solution
 * @author Robert C. Duvall (added comments, constants)
 * @author Sara Sprenkle - modified for testing purposes
 */
public class ParserTestDriver {
	public static final String EXIT_COMMAND = ".";
	public static final String PROMPT = "-> ";

	public static void main(String[] args) {
		Scanner input = new Scanner(new InputStreamReader(System.in));
		ExpressionTreeGenerator parser = new ExpressionTreeGenerator();
		int numExpressions = 1;

		System.out.println("Test parsing expressions");
		System.out.print(numExpressions + PROMPT);

		while (input.hasNext()) {
			String line = input.nextLine();
			if (line == null || line.equals(EXIT_COMMAND)) {
				break;
			} else {
				try {
					ExpressionTreeNode expression = parser.makeExpression(line);
					System.out.println("Created expression: " + expression);
				} catch (ParseException e) {
					System.out.println("Invalid expression: " + line
							+ "\n --- " + e.getMessage());
				}
			}
			numExpressions++;
			System.out.print(numExpressions + PROMPT);
		}
	}


}
