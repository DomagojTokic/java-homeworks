package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Lists available charsets for interpreting data.
 * 
 * @author Domagoj Tokić
 *
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.trim().isEmpty()) {
			for (Charset charset : Charset.availableCharsets().values()) {
				try {
					env.writeln(charset.name());
				} catch (IOException e) {
					throw new InvalidOperationException("Unable to write charsets");
				}
			}
		} else {
			throw new InvalidOperationException("Charset operation must not have arguments!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Currently missing description");
		return commandDescription;
	}

}
