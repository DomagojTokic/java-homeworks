package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Copies file. First argument is source, second is destination. If new file
 * name isn't specified, operation will use the same as current. If file
 * with given name already exists, user will be asked to confirm copying.
 * 
 * @author Domagoj Tokić
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			writeCommandDescription(env);
		} else if (argumentList.size() == 2) {

			File src = new File(argumentList.get(0));
			File dest = new File(argumentList.get(1));

			if (!src.isFile()) {
				throw new InvalidOperationException(
						"Invalid source path. Path must be a valid file.");
			}

			if (dest.isDirectory()) {
				dest = new File(dest.getAbsolutePath() + File.separatorChar + src.getName());
			}

			if (dest.isFile()) {
				try {
					env.writeln("File already exists, do you want to overwrite? y/n:");
					String answer = env.readLine();

					if (answer.equals("n")) {
						return ShellStatus.CONTINUE;
					} else if (!answer.equals("y")) {
						System.out.println("Invalid answer, valid format is 'y/n'");
					}
				} catch (IOException e) {
					throw new InvalidOperationException(
							"Unable to read/write on enviroment input/output.");
				}
			}

			try (FileInputStream is = new FileInputStream(src);
					FileOutputStream os = new FileOutputStream(dest)) {
				byte[] buffer = new byte[4096];
				int bytesRead;

				while ((bytesRead = is.read(buffer)) > 0) {
					os.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				throw new InvalidOperationException("Invalid paths are specified");
			}
		} else {
			throw new InvalidOperationException("Operation copy must accept two parameters.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription
				.add("Copies file. First argument is source, second is destination. If new file");
		commandDescription
				.add("name isn't specified, operation will use the same as current. If file");
		commandDescription
				.add("with given name already exists, user will be asked to confirm copying.");
		return commandDescription;
	}

}
