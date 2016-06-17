package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Creates new directory or tree of directories.
 * 
 * @author Domagoj Tokić
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			writeCommandDescription(env);
		} else if (argumentList.size() == 1) {
			File file = new File(argumentList.get(0));

			if (!file.exists()) {
				file.mkdirs();
				if (!file.exists()) {
					throw new InvalidOperationException(
							"You do not have permission to create directory with given path.");
				}
			} else {
				throw new InvalidOperationException(
						"Directory or file with given name already exists.");
			}
		} else {
			throw new InvalidOperationException("mkdir command must have one path argument.");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Creates new directory or tree of directories.");
		return commandDescription;
	}

}
