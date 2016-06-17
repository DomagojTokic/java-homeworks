package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Prints all files and directories from given path.
 * 
 * @author Domagoj Tokić
 *
 */
public class TreeCommand implements ShellCommand {

	/**
	 * Printer of file tree
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private static class TreePrinter implements FileVisitor<Path> {

		/**
		 * Level of directory
		 */
		private int level;

		/**
		 * Environment
		 */
		Environment env;

		/**
		 * Creates instance of {@link TreePrinter}
		 * 
		 * @param env Environment
		 */
		public TreePrinter(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException {
			print(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints whole path if path is root, else it prints file name with
		 * appropriate indentation.
		 * 
		 * @param f Current file
		 * @throws IOException if method is unable to write to environment
		 */
		public void print(Path f) throws IOException {
			if (level == 0) {
				env.writeln(f.normalize().toAbsolutePath().toString());
			} else {
				env.writeln(String.format("%" + (2 * level) + "s%s", " ", f.getFileName()));
			}
		}

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			writeCommandDescription(env);
		} else if (argumentList.size() == 1) {
			Path root = Paths.get(argumentList.get(0));
			try {
				Files.walkFileTree(root, new TreePrinter(env));
			} catch (IOException e) {
				throw new InvalidOperationException("Unable to write to output");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Prints all files and directories from given path.");
		return commandDescription;
	}

}
