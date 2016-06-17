package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Definition of methods which every shell operation has to implement.
 * 
 * @author Domagoj Tokić
 *
 */
public interface ShellCommand {

	/**
	 * Executes command
	 * 
	 * @param env Environment
	 * @param arguments String containing all arguments
	 * @return Shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Returns command description
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();

	/**
	 * Writes command description on environment output.
	 * 
	 * @param env Environment
	 */
	default void writeCommandDescription(Environment env) {
		for (String line : this.getCommandDescription()) {
			try {
				env.writeln(line);
			} catch (IOException e) {
				throw new InvalidOperationException("Unable to read command description");
			}

		}
	}

}
