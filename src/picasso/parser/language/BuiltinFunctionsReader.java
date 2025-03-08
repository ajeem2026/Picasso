package picasso.parser.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading the available built-in functions from a file.
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class BuiltinFunctionsReader {

	private static List<String> functionsList;
	private static String FUNCTIONS_CONF_FILE = "conf/functions.conf";

	/**
	 * Get the list of built-in function names
	 * 
	 * @return the list of built-in function names
	 */
	public static List<String> getFunctionsList() {
		if (functionsList == null) {
			readFunctionsFromFile();
		}
		return functionsList;
	}

	/**
	 * Read the functions from the functions config file
	 */
	private static void readFunctionsFromFile() {
		functionsList = new ArrayList<String>();
		Scanner reader;
		try {
			reader = new Scanner(new File(FUNCTIONS_CONF_FILE));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			// TODO: should pass the error up so that the user knows what the
			// problem is.
			return;
		}
		while (reader.hasNextLine()) {
			String function = reader.nextLine();
			function = function.trim();
			// ignore blank lines
			if (!function.isBlank()) {
				functionsList.add(function);
			}
		}
	}

}
