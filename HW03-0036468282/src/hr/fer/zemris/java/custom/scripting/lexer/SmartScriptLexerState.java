package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * State of lexer.
 * @author Domagoj Tokić
 */
public enum SmartScriptLexerState {

	/**
	 * Mode of work where lexer creates valid text nodes
	 */
	TEXT,
	
	/**
	 * Mode of work where lexer creates valid tag nodes
	 */
	TAG;
	
}
