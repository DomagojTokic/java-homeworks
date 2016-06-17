package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Lexer for generating elements for {@link QueryParser} and
 * {@link IndexedQueryParser}
 * 
 * @author Domagoj Tokić
 *
 */
public class QueryLexer {

	/**
	 * Data array for storing query
	 */
	private char[] data;

	/**
	 * Index which points to slot in data array which is currently analyzed for
	 * generating token.
	 */
	private int currentIndex;

	/**
	 * Last generated token.
	 */
	private Token current;

	/**
	 * Creates instance of {@link QueryLexer}
	 * 
	 * @param query Query conditions
	 */
	public QueryLexer(String query) {
		query = query.replaceAll("\\s+", " ").trim();
		query += "\n";
		data = query.toCharArray();
	}

	/**
	 * Returns next token in query string.
	 * 
	 * @return next token in query string
	 * @throws QueryLexerException if lexer is unable to generate token from
	 *             current character.
	 */
	public Token nextToken() {

		if (current != null && current.getType() == TokenType.END) {
			throw new QueryLexerException("end of query");
		}

		if (data[currentIndex] == ' ') {
			currentIndex++;
		}

		if (data[currentIndex] == '\n') {
			return new Token(null, TokenType.END);
		}

		if (data[currentIndex] == '"') {
			return generateString();
		}

		if (data.length - currentIndex > 4 && isLikeOperator()) {
			currentIndex += 4;
			return new Token("LIKE", TokenType.CONDITION);
		}

		if (Character.isAlphabetic(data[currentIndex])) {
			return generateAttribute();
		}

		if (Character.toString(data[currentIndex]).matches("[=!<>]")) {
			return generateSymbolCondition();
		}

		throw new QueryLexerException(
				"'" + Character.toString(data[currentIndex]) + "'");

	}

	/**
	 * Generates type STRING token
	 * 
	 * @return type STRING token
	 */
	private Token generateString() {
		currentIndex++;
		String string = "";
		while (data[currentIndex] != '"') {
			if (data[currentIndex] == '\n') {
				if (string.isEmpty()) {
					throw new QueryLexerException("empty string");
				}
				throw new QueryLexerException(string);
			}

			string += data[currentIndex];
			currentIndex++;
		}
		currentIndex++;

		return new Token(string, TokenType.STRING);
	}

	/**
	 * Generates type ATTRIBUTE token
	 * 
	 * @return type ATTRIBUTE token
	 */
	private Token generateAttribute() {
		String attribute = "";
		while (Character.isAlphabetic(data[currentIndex])
				|| Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {
			attribute += data[currentIndex];
			currentIndex++;
		}

		return new Token(attribute, TokenType.ATTRIBUTE);
	}

	/**
	 * Checks if next token is condition 'LIKE'
	 * 
	 * @return true if next token is condition 'LIKE', else false
	 */
	private boolean isLikeOperator() {
		return data[currentIndex] == 'L' && data[currentIndex + 1] == 'I'
				&& data[currentIndex + 2] == 'K'
				&& data[currentIndex + 3] == 'E'
				&& !Character.isAlphabetic(data[currentIndex + 4]);
	}

	/**
	 * Generates new symbol condition token.
	 * 
	 * @return new symbol condition token
	 */
	private Token generateSymbolCondition() {
		String condition = "";
		while (Character.toString(data[currentIndex]).matches("[=!<>]")) {
			condition += data[currentIndex];
			currentIndex++;
		}
		return new Token(condition, TokenType.CONDITION);
	}

}
