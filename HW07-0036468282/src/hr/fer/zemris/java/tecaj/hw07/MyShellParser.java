package hr.fer.zemris.java.tecaj.hw07;

import java.io.IOException;

/**
 * Parser for reading and parsing user commands. First word is command and
 * everything else is considered an argument.
 * 
 * @author Domagoj Tokić
 *
 */
public class MyShellParser {

	/**
	 * Command of current user input
	 */
	String command;

	/**
	 * Arguments of current command
	 */
	String arguments;

	/**
	 * Environment from which parser reads user input
	 */
	Environment environment;

	/**
	 * Creates instance of {@link MyShellParser} linked to given environment.
	 * 
	 * @param environment Environment to which is parser connected
	 */
	public MyShellParser(Environment environment) {
		this.environment = environment;
		command = "";
		arguments = "";
	}

	/**
	 * Parses user input by separating command name from command arguments.
	 * Supports multiple inputs for arguments if user uses multiline symbol.
	 */
	public void parse() {
		command = "";
		arguments = "";

		try {
			environment.write(environment.getPromptSymbol() + " ");
			String line = environment.readLine().trim();

			if (line.isEmpty()) {
				return;
			}
			if (!line.split("\\s+")[0].matches("[A-Za-z_0-9]+")) {
				throw new InvalidOperationException("Invalid operation: " + line.split(" ")[0]);
			}

			command = line.split("\\s+")[0];

			line = line.substring(command.length(), line.length());

			while (line.endsWith(" " + environment.getMorelinesSymbol().toString())) {
				arguments += line.substring(0, line.length() - 2) + " ";
				environment.write("" + environment.getMultilineSymbol() + " ");
				line = environment.readLine();
			}

			arguments += line;

		} catch (IOException e) {
			System.err.println("Unable to read from input");
			System.exit(1);
		}

	}

	/**
	 * Returns command
	 * 
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Returns string containing all arguments
	 * 
	 * @return string containing all arguments
	 */
	public String getArguments() {
		return arguments;
	}

}
