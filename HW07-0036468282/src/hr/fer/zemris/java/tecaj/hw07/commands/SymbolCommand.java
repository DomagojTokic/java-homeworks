package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.MyShellEnviroment;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Changes one of special {@link MyShellEnviroment} symbols. If new symbol isn't
 * specified, it notifies user which symbol is is currently used for given
 * symbol type. Symbol types are: PROMPT - symbol before every new command,
 * MORELINES - notifies shell that current command will be written across
 * multiple lines; it's written at the end of the line, MULTILINE - written for
 * every new line in command written in multiple lines.
 * 
 * @author Domagoj Tokić
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);
		try {
			if (argumentList.isEmpty()) {
				writeCommandDescription(env);
			} else if (argumentList.size() == 1) {
				writeSymbol(argumentList.get(0), env);
			} else if (argumentList.size() == 2) {
				changeSymbol(argumentList.get(0), argumentList.get(1), env);
			} else {
				throw new InvalidOperationException(
						"symbol command can't accept more than 2 arguments.");
			}
		} catch (IOException e) {
			throw new InvalidOperationException("Unable to write to enviroment output.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Writes current symbol to environment output.
	 * 
	 * @param symbolName Symbol name
	 * @param env Environment
	 * @throws IOException if method is unable to write message to environment
	 */
	private void writeSymbol(String symbolName, Environment env) throws IOException {
		switch (symbolName) {
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			break;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			break;
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			break;
		default:
			throw new InvalidOperationException("Unknown symbol " + symbolName);
		}
	}

	/**
	 * Changes current symbol with new and notifies user about the change.
	 * 
	 * @param symbolName Symbol name
	 * @param symbolString New symbol
	 * @param env Environment
	 * @throws IOException if method is unable to write message to environment
	 */
	private void changeSymbol(String symbolName, String symbolString, Environment env)
			throws IOException {
		if (symbolString.length() != 1) {
			throw new InvalidOperationException("Given symbol must be one character");
		}
		if (symbolString.matches("[A-Za-z0-9]")) {
			throw new InvalidOperationException("Given symbol can't be alphabetic");
		}
		Character symbol = symbolString.toCharArray()[0];
		switch (symbolName) {
		case "MORELINES":
			env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '"
					+ symbol + "'");
			env.setMorelinesSymbol(symbol);
			break;
		case "MULTILINE":
			env.writeln("Symbol for PROMPT changed from '" + env.getMultilineSymbol() + "' to '"
					+ symbol + "'");
			env.setMultilineSymbol(symbol);
			break;
		case "PROMPT":
			env.writeln("Symbol for PROMPT changed from '" + env.getMorelinesSymbol() + "' to '"
					+ symbol + "'");
			env.setPromptSymbol(symbol);
			break;
		default:
			throw new InvalidOperationException("Unknown symbol " + symbolName);
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Operation for getting information about and changing specific");
		commandDescription.add("symbol in MyShell. Types are: MORELINES - when command");
		commandDescription.add("is written in multiple lines, every line must end with");
		commandDescription.add("multiline character, PROMPT - signifies beginning of");
		commandDescription.add("every command, MULTILINE - signifies continuation of");
		commandDescription.add("command after line which ended with morelines symbol.\n");
		commandDescription.add("If new character is specified after symbol type argument,");
		commandDescription.add("current symbol will be changed to given:\n");
		commandDescription.add("Example:");
		commandDescription.add("symbol PROMPT -> gives information about current PROMPT character");
		commandDescription.add("symbol PROMPT # -> changes current PROMPT character to '#'");
		return commandDescription;
	}

}
