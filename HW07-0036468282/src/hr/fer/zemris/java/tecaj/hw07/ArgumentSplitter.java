package hr.fer.zemris.java.tecaj.hw07;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for splitting program arguments, including ones between double quotes.
 * Quote character within quoted argument must be escaped with symbol '\' - '\"'
 * 
 * @author Domagoj Tokić
 *
 */
public class ArgumentSplitter {

	/**
	 * Array of characters from argument string
	 */
	char[] argumentsArray;

	/**
	 * Index of character in current arguments split method
	 */
	int index;

	/**
	 * Splits one string containing multiple arguments into list with separated
	 * arguments. If argument is between double quotes, it's considered as one
	 * argument.
	 * 
	 * @param argumentsString Argument string
	 * @return list of arguments
	 */
	public List<String> split(String argumentsString) {

		List<String> arguments = new ArrayList<>();

		argumentsString = argumentsString.trim();
		argumentsArray = argumentsString.toCharArray();

		while (index < argumentsArray.length) {
			if (argumentsArray[index] == '"') {
				index++;
				arguments.add(quotedArgument());
			} else {
				arguments.add(basicArgument());
			}
		}
		return arguments;
	}

	/**
	 * Returns argument which was between quotes.
	 * 
	 * @return argument which was between quotes
	 */
	private String quotedArgument() {
		String argument = "";
		while (index < argumentsArray.length && argumentsArray[index] != '"') {
			if (argumentsArray[index] == '\\' && argumentsArray.length > index + 1
					&& (argumentsArray[index + 1] == '"' || argumentsArray[index + 1] == '\\')) {
				index++;
				argument += argumentsArray[index];
			} else {
				argument += argumentsArray[index];
			}
			index++;
		}

		if (index >= argumentsArray.length || argumentsArray[index] != '"') {
			throw new InvalidOperationException("Invalid argument " + argument);
		} else {
			index++;
			return argument;
		}
	}

	/**
	 * Returns argument which wasn't between quotes.
	 * 
	 * @return argument which wasn't between quotes
	 */
	private String basicArgument() {
		String argument = "";

		if (Character.toString(argumentsArray[index]).matches("\\s+")) {
			index++;
		}

		while (index < argumentsArray.length
				&& !Character.toString(argumentsArray[index]).matches("\\s")) {
			argument += argumentsArray[index];
			index++;
		}
		index++;
		return argument;
	}

}
