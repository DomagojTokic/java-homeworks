package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Class for splitting strings into tokens. Types are: word, number, symbol and
 * eof (end of file). It has two states: basic - recognizes all types of tokens,
 * extended - recognizes only word and eof tokens.
 * @author Domagoj Tokić
 *
 */
public class Lexer {

	/**
	 * State of Lexer
	 */
	private LexerState state;

	/**
	 * Array of characters which needs to be put in tokens
	 */
	private char[] data;

	/**
	 * Current token (replaced by method nextToken)
	 */
	private Token token;

	/**
	 * Index of current character which is beginning of next token
	 */
	private int currentIndex;

	/**
	 * Creates lexer with array of characters (data) from text. Replaces all
	 * combinations of empty spaces and replaces it with single space between
	 * separated groups of characters.
	 * @param text Text for conversion to tokens
	 */
	public Lexer(String text) {

		if (text == null) {
			throw new IllegalArgumentException("Text can't be null!");
		}
		currentIndex = 0;
		state = LexerState.BASIC;
		text = text.replaceAll("\\s+", " ");
		text = text.trim();
		data = text.toCharArray();

	}

	/**
	 * Sets state of lexer
	 * @param state New state of lexer
	 * @throws IllegalArgumentException if state is null
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException(
					"Can't put lexer state on null value!");
		}

		this.state = state;
	}

	/**
	 * Changes state of lexer to a different one (BASIC -> EXTENDED or EXTENDED
	 * -> BASIC)
	 */
	private void changeState() {
		if (state == LexerState.BASIC) {
			setState(LexerState.EXTENDED);
		} else {
			setState(LexerState.BASIC);
		}
	}

	/**
	 * Returns next token and replaces current one with it.
	 * @return Next token
	 */
	public Token nextToken() {

		if (this.token != null && this.token.getType() == TokenType.EOF) {
			throw new LexerException("Can't request token after EOF!");

		} else if (currentIndex == data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;

		} else if (data[currentIndex] == ' ') {
			currentIndex++;
		} else if (data[currentIndex] == '#') {
			changeState();
		}

		if ((Character.isAlphabetic(data[currentIndex])
				|| data[currentIndex] == '\\' || state != LexerState.BASIC)
				&& data[currentIndex] != '#') {
			return getWordToken();

		} else if (Character.isDigit(data[currentIndex])) {
			return getNumberToken();
		} else {
			currentIndex++;
			return new Token(TokenType.SYMBOL, data[currentIndex - 1]);
		}
	}

	/**
	 * Returns current token
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Returns word token
	 * @return word token
	 */
	private Token getWordToken() {
		String tokenString = "";
		
		do {
			if (Character.isAlphabetic(data[currentIndex])
					|| state != LexerState.BASIC) {
				tokenString += data[currentIndex];
				currentIndex++;

			} else {
				// If escaping numbers
				if (currentIndex + 1 != data.length
						&& (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\')) {
					tokenString += data[currentIndex + 1];
					currentIndex += 2;
				} else {
					throw new LexerException("Unable to escape character!");
				}

			}
		} while (currentIndex != data.length
				&& ((Character.isAlphabetic(data[currentIndex]) || data[currentIndex] == '\\')
						|| state != LexerState.BASIC
						&& (Character.isDigit(data[currentIndex])) || Character
							.isAlphabetic(data[currentIndex])));

		return new Token(TokenType.WORD, tokenString);
	}

	/**
	 * Returns number token
	 * @return number token
	 */
	private Token getNumberToken() {
		String tokenString = "";
		
		do {
			tokenString += data[currentIndex];
			currentIndex++;
		} while (currentIndex != data.length
				&& Character.isDigit(data[currentIndex]));
		try {
			return new Token(TokenType.NUMBER, Long.parseLong(tokenString));
		} catch (NumberFormatException e) {
			throw new LexerException("Unable to parse number!");
		}
	}

}
