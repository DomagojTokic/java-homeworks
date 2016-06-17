package hr.fer.zemris.java.tecaj.hw5.db.query;

/**
 * Parser for indexed query. Checks if condition is in form 'jmbag = "example"'
 * and stores jmbag value.
 * 
 * @author Domagoj Tokić
 *
 */
public class IndexedQueryParser {

	/**
	 * Indexed attribute
	 */
	public static String INDEXED_ATTRIBUTE = "jmbag";

	/**
	 * Allowed operator
	 */
	public static String ALLOWED_OPERATOR = "=";

	/**
	 * Requested indexed value
	 */
	private String value;

	/**
	 * Creates instance of IndexedQueryParser parses input query.
	 * 
	 * @param query Query condition
	 */
	public IndexedQueryParser(String query) {
		if (query.isEmpty()) {
			throw new QueryParserException("Query conditions must be defined");
		}
		parse(query);
	}

	/**
	 * Returns requested indexed value.
	 * 
	 * @return requested indexed value
	 */
	public String getIndexedAttributeValue() {
		return value;
	}

	/**
	 * Method for parsing indexed query condition.
	 * 
	 * @param query Query condition
	 */
	private void parse(String query) {
		QueryLexer lexer = new QueryLexer(query);
		Token current = new Token("beginning of query", null);
		try {

			current = lexer.nextToken();
			checkAttribute(current);

			current = lexer.nextToken();
			checkCondition(current);

			current = lexer.nextToken();
			checkValue(current);

			if (lexer.nextToken().getType() != TokenType.END) {
				throw new QueryParserException(
						"Indexed query can only have one condition");
			}

			value = current.getValue();

		} catch (QueryLexerException e) {
			throw new QueryParserException(
					"Failed to process " + e.getMessage());
		}
	}

	/**
	 * Checks if given token is valid attribute for indexed query.
	 * 
	 * @param token Token for checking
	 */
	private void checkAttribute(Token token) {
		if (token.getType() != TokenType.ATTRIBUTE
				|| !token.getValue().equals(INDEXED_ATTRIBUTE)) {
			throw new QueryParserException(
					"Beginning of indexed query must be indexed attribute '"
							+ INDEXED_ATTRIBUTE + "'");
		}
	}

	/**
	 * Checks if given token is valid operation for indexed query.
	 * 
	 * @param token Token for checking
	 */
	private void checkCondition(Token token) {
		if (token.getType() != TokenType.CONDITION
				&& !token.getValue().equals(ALLOWED_OPERATOR)) {
			throw new QueryParserException(
					"After indexed attribute can come only allowed operator '"
							+ ALLOWED_OPERATOR + "'");
		}
	}

	/**
	 * Checks if given token is valid value for indexed query.
	 * 
	 * @param token Token for checking
	 */
	private void checkValue(Token token) {
		if (token.getType() != TokenType.STRING) {
			throw new QueryParserException(
					"After condition operator must come valid value, not '"
							+ token.getValue() + "'");
		}
	}

}
