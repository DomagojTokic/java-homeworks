package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.ArgumentSplitter;
import hr.fer.zemris.java.tecaj.hw07.Environment;
import hr.fer.zemris.java.tecaj.hw07.InvalidOperationException;
import hr.fer.zemris.java.tecaj.hw07.ShellStatus;

/**
 * Writes hexadecimal representation of file, together with hexadecimal block
 * number and simple text representation.
 * 
 * @author Domagoj Tokić
 *
 */
public class HexdumpCommand implements ShellCommand {

	/**
	 * Maximum number of block allowed for writing in environment
	 */
	private final long MAX_BLOCK_OUTPUT = 0x0ffffffffL;

	/**
	 * Default block size
	 */
	private final int DEFAULT_BLOCK_SIZE = 16;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentSplitter().split(arguments);

		if (argumentList.isEmpty()) {
			writeCommandDescription(env);
		} else if (argumentList.size() > 1) {
			throw new InvalidOperationException("Command hexdump accepts only one path argument");
		} else {
			try (FileInputStream is = new FileInputStream(argumentList.get(0))) {

				byte[] buffer = new byte[DEFAULT_BLOCK_SIZE];
				int bytesRead;
				long blockNumber = 0;

				while ((bytesRead = is.read(buffer)) > 1 && blockNumber < MAX_BLOCK_OUTPUT) {
					printBlock(buffer, bytesRead, blockNumber, env);
					blockNumber += 1;
				}

			} catch (IOException e) {
				throw new InvalidOperationException("Unable to open file " + argumentList.get(0));
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints one block information to environment output.
	 * 
	 * @param block Array of block bytes
	 * @param blockSize Size of block
	 * @param blockNumber Number of block
	 * @param env Environment
	 */
	private void printBlock(byte[] block, int blockSize, long blockNumber, Environment env) {
		try {
			env.write(String.format("%08X: ", (blockNumber * DEFAULT_BLOCK_SIZE + blockSize)));

			// writing block values
			if (blockSize == DEFAULT_BLOCK_SIZE) {
				env.write(String.format(
						"%02X %02X %02X %02X %02X %02X %02X %02X|%02X %02X %02X %02X %02X %02X %02X %02X | ", block[0],
						block[1], block[2], block[3], block[4], block[5], block[6], block[7], block[8], block[9],
						block[10], block[11], block[12], block[13], block[14], block[15]));

			} else {
				String blockList = "";
				for (int i = 0; i < blockSize; i++) {
					if (i == 7) {
						blockList += String.format("%02X|", block[i]);
					} else {
						blockList += String.format("%02X ", block[i]);
					}
				}
				for (int i = blockSize; i < DEFAULT_BLOCK_SIZE; i++) {
					if (i == 7) {
						blockList += "  |";
					} else {
						blockList += String.format("   ", block[i]);
					}
				}

				env.write(blockList + "| ");
			}

			String textRepresentation = "";
			for (int i = 0; i < blockSize; i++) {
				if (32 <= block[i] && block[i] <= 127) {
					textRepresentation += (char) block[i];
				} else {
					textRepresentation += ".";
				}
			}
			for (int i = blockSize; i < DEFAULT_BLOCK_SIZE; i++) {
				textRepresentation += " ";
			}

			env.writeln(textRepresentation);

		} catch (IOException e) {
			throw new InvalidOperationException("Unable to write to enviroment");
		}
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Writes hexadecimal representation of file, together with");
		commandDescription.add("hexadecimal block number and simple text representation.");
		return commandDescription;
	}

}
