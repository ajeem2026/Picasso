package picasso.view.commands;

import java.awt.Color;

import java.awt.Dimension;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.ParseException;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

//New imports for file reading functionality

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Evaluate an expression for each pixel in a image.
 * 
 * @author Robert C Duvall
 * @author Sara Sprenkle
 */
public class Evaluator implements Command<Pixmap> {
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;

	// Adding a new field to process user's string
	// This string will be inputed through the input handler in frame.java

	private String inputString;

	// Constructor to make sure my evaluator takes in the input string

	public Evaluator(String input) {
		this.inputString = input;
	}
	
	//Getter method for get input string (needed for HISTORY LOG)
	public String getInputString() {
        return inputString;
    }

	// A setter to change the string with each new input
	public void setInputString(String input) {
		this.inputString = input;
	}

	// New method for reading expression from a file and setting it as the string
	// input

	/**
	 * Reads an expression (only first line) from a file and sets it as the input
	 * string
	 *
	 * @param file The file containing the expression
	 * @throws IOException if the file cannot be read
	 */
	public boolean setInputFromFile(File file) throws IOException {

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			// Read the first line as the expression
			String expression = reader.readLine();

			if (expression == null || expression.trim().isEmpty()) {
				throw new IOException("File is empty or contains an invalid expression.");
			}

			// Validate the expression using ExpressionTreeGenerator
			ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
			try {
				expTreeGen.makeExpression(expression);
			} catch (Exception e) {
				throw new IOException("Invalid expression in the file: " + e.getMessage(), e);
			}

			// Set the validated expression
			setInputString(expression);

			return true;

		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Evaluate an expression for each point in the image.
	 */
	public void execute(Pixmap target) {

		// Create the expression to evaluate just once
		ExpressionTreeNode expr = createExpression();

		// Evaluate for each pixel
		Dimension size = target.getSize();
		for (int imageY = 0; imageY < size.height; imageY++) {
			double evalY = imageToDomainScale(imageY, size.height);
			for (int imageX = 0; imageX < size.width; imageX++) {
				double evalX = imageToDomainScale(imageX, size.width);
				Color pixelColor = expr.evaluate(evalX, evalY).toJavaColor();
				target.setColor(imageX, imageY, pixelColor);
			}
		}
	
	}

	/**
	 * Convert from image space to domain space.
	 */
	protected double imageToDomainScale(int value, int bounds) {
		double range = DOMAIN_MAX - DOMAIN_MIN;
		return ((double) value / bounds) * range + DOMAIN_MIN;
	}

	/**
	 * 
	 * A place holder for a more interesting way to build the expression.
	 */
	private ExpressionTreeNode createExpression() {
		// Note, when you're testing, you can use the ExpressionTreeGenerator to
		// generate expression trees from strings, or you can create expression
		// objects directly (as in the commented statement below).
		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		// Expression is now created based on input string
		return expTreeGen.makeExpression(inputString);
	

	
	}
	

}
