package hr.fer.zemris.java.tecaj.hw07;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw07.commands.ShellCommand;

/**
 * Interface for classes which store connection with output and input and
 * implement basic operations of reading and writing from/to them.
 * Implementations are supposed to store special symbols and commands.
 * 
 * @author Domagoj Tokić
 *
 */
public interface Environment {

	/**
	 * Reads line from class input reader
	 * 
	 * @return String line
	 * @throws IOException if method is unable to read form input
	 */
	String readLine() throws IOException;

	/**
	 * Writes to output
	 * 
	 * @param text Text for writing to output
	 * @throws IOException if method is unable to write to output
	 */
	void write(String text) throws IOException;

	/**
	 * Writes to output and ends with newline symbol
	 * 
	 * @param text Text for writing to output
	 * @throws IOException if method is unable to write to output
	 */
	void writeln(String text) throws IOException;

	/**
	 * Returns iterable list of environment commands
	 * 
	 * @return list of environment commands
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns MULTILINE symbol
	 * 
	 * @return MULTILINE symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets MULTILINE symbol
	 * 
	 * @param symbol New MULTILINE symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns PROMPT symbol
	 * 
	 * @return PROMPT symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets PROMPT symbol
	 * 
	 * @param symbol New PROMPT symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns MORELINES symbol
	 * 
	 * @return MORELINES symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets MORELINES symbol
	 * 
	 * @param symbol New MORELINES symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
