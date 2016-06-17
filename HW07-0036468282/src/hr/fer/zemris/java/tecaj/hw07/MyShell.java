package hr.fer.zemris.java.tecaj.hw07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hr.fer.zemris.java.tecaj.hw07.commands.*;

/**
 * Shell (command prompt) which implements functionalities: charsets, cat, ls,
 * tree, copy, mkdir, hexdump. For changing special meaning symbols within
 * shell, there's command "symbol". For exiting shell, write "exit". Details of
 * commands are given on environment output by writing command name without
 * arguments.
 * 
 * @author Domagoj Tokić
 *
 */
public class MyShell {

	/**
	 * Entrance into {@link MyShell}
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		Environment environment = new MyShellEnviroment(reader, writer);
		MyShellParser parser = new MyShellParser(environment);
		ShellStatus status = ShellStatus.CONTINUE;

		System.out.println("Welcome to MyShell v 1.0");

		while (status != ShellStatus.TERMINATE) {
			try {

				parser.parse();

				ShellCommand command = null;
				for (ShellCommand com : environment.commands()) {
					if (parser.getCommand().equals(com.getCommandName())) {
						command = com;
					}
				}

				if (command != null) {
					status = command.executeCommand(environment, parser.getArguments());

				} else if (parser.getCommand().equals("")) {
					for (ShellCommand com : environment.commands()) {
						environment.writeln(com.getCommandName());
					}
				} else {
					environment.writeln("Command " + parser.getCommand() + " does not exist!");
				}

			} catch (InvalidOperationException e) {
				try {
					environment.writeln(e.getMessage());
				} catch (IOException e1) {
					System.err.println("Unable to produce error message");
					System.exit(1);
				}
			} catch (IOException e) {
				System.err.println("Unable write command list to output");
				System.exit(1);
			}

		}
	}

}
