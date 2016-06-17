package hr.fer.zemris.java.tecaj.hw07;

/**
 * Return value of commands which signify what is next action for shell when
 * command is done.
 * 
 * @author Domagoj
 *
 */
public enum ShellStatus {

	/**
	 * Message for shell to continue reading commands
	 */
	CONTINUE,

	/**
	 * Message for shell to terminate program
	 */
	TERMINATE

}
