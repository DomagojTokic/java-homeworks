package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.MyShell;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Lists details of files and folders from given directory path. First column
 * meaning: 'd' - directory, 'r' - file is readable, 'w' - writable, x -
 * executable. Second column is file size. If file size is -1, it means that
 * {@link MyShell} doesn't have permission to read file size or if it's
 * directory, size of at least one file in that directory. Third column id time
 * of creation. Fourth is file name.
 * 
 * @author Domagoj Tokić
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			this.writeCommandDescription(env);
		} else if (argumentList.size() == 1) {

			File file = new File(argumentList.get(0));
			if (file.isDirectory()) {
				try {
					writeDirectoryFiles(file, env);
				} catch (IOException e) {
					throw new InvalidOperationException(
							"Unable to write result of 'ls' to output.");
				}
			} else {
				throw new InvalidOperationException(
						"Invalid directory path " + argumentList.get(0));
			}
		} else {
			throw new InvalidOperationException("Operation only accepts one path argument");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Lists details of files and folders from given directory path. Details are
	 * written in class description.
	 * 
	 * @param file Directory containing files for listing
	 * @param env Environment
	 * @throws IOException if method is unable to write to environment.
	 */
	private void writeDirectoryFiles(File file, Environment env) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (File child : file.listFiles()) {
			String characteristics = "";
			characteristics += child.isDirectory() ? "d" : "-";
			characteristics += child.canRead() ? "r" : "-";
			characteristics += child.canWrite() ? "w" : "-";
			characteristics += child.canExecute() ? "x" : "-";

			Path path = Paths.get(child.getPath());

			BasicFileAttributeView faView = Files.getFileAttributeView(path,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

			long size;
			try {
				size = Files.walk(path).mapToLong(p -> p.toFile().length()).sum();
			} catch (IOException e) {
				size = -1;
			} catch (UncheckedIOException e) {
				size = -1;
			}

			env.writeln(String.format(
					characteristics + " %10d " + formattedDateTime + " " + child.getName(), size));
		}

	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription
				.add("Lists details of files and folders from given directory path. First column");
		commandDescription
				.add("meaning: 'd' - directory, 'r' - file is readable, 'w' - writable, x -");
		commandDescription
				.add("executable. Second column is file size. If file size is -1, it means that");
		commandDescription.add("MyShell doesn't have permission to read file size or if it's");
		commandDescription.add(
				"directory, size of at least one file in that directory. Third column id time");
		commandDescription.add("of creation. Fourth is file name.");
		return commandDescription;
	}

}
