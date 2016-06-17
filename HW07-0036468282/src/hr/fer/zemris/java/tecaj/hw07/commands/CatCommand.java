package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Writes data from file path which is given as first argument. Second argument
 * is optional and it defines charset which will be used to encode data from
 * file. If second argument isn't defined, system's default charset will be
 * used.
 * 
 * @author Domagoj Tokić
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			writeCommandDescription(env);
		} else if (argumentList.size() > 2) {
			throw new InvalidOperationException("Command 'cat' mustn't have more than 2 arguments");

		} else {
			Charset charset;

			if (argumentList.size() == 1) {
				charset = Charset.defaultCharset();
			} else {
				try {
					charset = Charset.forName(argumentList.get(1));
				} catch (UnsupportedCharsetException e) {
					throw new InvalidOperationException(
							"Given charset does not exits. Use command 'charsets' to see list of valid charsets.");
				}
			}

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(argumentList.get(0)), charset))) {
				String line;

				while ((line = reader.readLine()) != null)
					env.writeln(line);

			} catch (IOException e) {
				throw new InvalidOperationException("Invalid file path " + argumentList.get(0));
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add(
				"Writes data from file path which is given as first argument. Second argument");
		commandDescription
				.add("is optional and it defines charset which will be used to encode data from");
		commandDescription
				.add("file. If second argument isn't defined, system's default charset will be");
		commandDescription.add("used.");
		return commandDescription;
	}

}
