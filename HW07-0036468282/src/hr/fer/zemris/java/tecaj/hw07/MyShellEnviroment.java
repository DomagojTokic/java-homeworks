package hr.fer.zemris.java.tecaj.hw07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.commands.CatCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.CharsetsCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.CopyCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.HexdumpCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.LsCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.MkdirCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.SymbolCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.TreeCommand;

/**
 * Storage of output and input connection, special meaning symbols and instances
 * of shell operations. Implements read and write operations.
 * 
 * @author Domagoj Tokić
 *
 */
public class MyShellEnviroment implements Environment {

	/**
	 * Multiline symbol
	 */
	private char multilineSymbol = '|';

	/**
	 * Prompt symbol
	 */
	private char promptSymbol = '>';

	/**
	 * More lines symbol
	 */
	private char morelinesSymbol = '\\';

	/**
	 * Environment reader
	 */
	BufferedReader reader;

	/**
	 * Environment writer
	 */
	BufferedWriter writer;

	/**
	 * Commands map
	 */
	Map<String, ShellCommand> commands;

	/**
	 * Creates instance of {@link MyShellEnviroment} using
	 * 
	 * @param reader Environment reader (input)
	 * @param writer Environment writer (output)
	 */
	public MyShellEnviroment(BufferedReader reader, BufferedWriter writer) {
		this.reader = reader;
		this.writer = writer;
		commands = getCommands();
	}

	@Override
	public String readLine() throws IOException {
		return reader.readLine();
	}

	@Override
	public void write(String text) throws IOException {
		writer.write(text);
		writer.flush();
	}

	@Override
	public void writeln(String text) throws IOException {
		writer.write(text + "\n");
		writer.flush();
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return commands.values();
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

	/**
	 * Returns map containing all commands supported within
	 * {@link MyShellEnviroment}
	 * 
	 * @return map containing all commands supported within
	 *         {@link MyShellEnviroment}
	 */
	private static Map<String, ShellCommand> getCommands() {
		Map<String, ShellCommand> commands = new HashMap<>();
		commands.put("symbol", new SymbolCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("cat", new CatCommand());
		commands.put("ls", new LsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("copy", new CopyCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("exit", new ExitShellCommand());

		return commands;
	}

}
