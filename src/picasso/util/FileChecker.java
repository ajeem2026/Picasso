package picasso.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.ParseException;

/**
 * Utility class to handle loading and parsing expressions from files
 * 
 * @author Abid Jeem
 */
public class FileChecker {

	private ExpressionTreeGenerator parser = new ExpressionTreeGenerator();

	/**
	 * Reads and validates an expression from a file.
	 *
	 * @param file the file to read from
	 * @return the parsed expression tree
	 * @throws IOException    if the file cannot be read
	 * @throws ParseException if the expression is invalid
	 */

	public ExpressionTreeNode parseExpressionFromFile(File file) throws IOException, ParseException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			// Read the first line as the expression
			String expression = reader.readLine(); 
			
			if (expression == null || expression.trim().isEmpty()) {
				throw new ParseException("The file is empty or contains an invalid expression.");
			}
			return parser.makeExpression(expression);
		}
	}
}
