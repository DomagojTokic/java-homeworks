package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enumeration representing state of lexer.
 * @author Domagoj Tokić
 *
 */
public enum LexerState {

	/**
	 * Mode of lexer where it distinguishes words, numbers and symbols
	 */
	BASIC,
	
	/**
	 * Mode of lexer where it sees just words
	 */
	EXTENDED;
}
