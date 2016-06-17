package hr.fer.zemris.java.tecaj.hw5.db.query;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDB;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.field_getters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.field_getters.IFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.field_getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.field_getters.LastNameGetter;

/**
 * Query parser for standard queries. See {@link StudentDB} for details on
 * supported attributes and operations.
 * 
 * @author Domagoj Tokić
 *
 */
public class QueryParser {

	/**
	 * List of condition operators
	 */
	List<IComparisonOperator> conditions;

	/**
	 * List of attributes for comparison
	 */
	List<IFieldGetter> attributes;

	/**
	 * List of values for comparison
	 */
	List<String> values;

	/**
	 * Creates instance of query parser and parses input query.
	 * 
	 * @param query Query conditions
	 */
	public QueryParser(String query) {
		conditions = new ArrayList<>();
		attributes = new ArrayList<>();
		values = new ArrayList<>();

		if (query.isEmpty()) {
			throw new QueryParserException("Query conditions must be defined");
		}
		parse(query);
	}

	/**
	 * Method for parsing query.
	 * 
	 * @param query Query conditions
	 * @throws QueryParserException if stream of elements aren't in valid order
	 */
	private void parse(String query) {
		QueryLexer lexer = new QueryLexer(query);
		Token current = new Token("beginning of query", null);
		try {
			current = lexer.nextToken();
			while (current.getType() != TokenType.END) {

				if (current.getType() == TokenType.ATTRIBUTE) {
					attributes.add(getStudentAttributeGetter(current));
				} else {
					throw new QueryParserException(
							"First element of condition must be table attribute");
				}

				current = lexer.nextToken();
				if (current.getType() == TokenType.CONDITION) {
					conditions.add(getCondition(current));
				} else {
					throw new QueryParserException(
							"After table attribute must come condition, and not "
									+ current.getValue());
				}

				current = lexer.nextToken();
				if (current.getType() == TokenType.STRING) {
					if (isLikeOperation()) {
						current = processLikeToken(current);
					}
					values.add(current.getValue());
				} else {
					throw new QueryParserException(
							"After condition must come valid value for comparison, got: "
									+ current.getValue());
				}

				current = lexer.nextToken();

				if (current.getType() == TokenType.END) {
					break;
				} else if (!(current.getType() == TokenType.ATTRIBUTE
						&& current.getValue().toUpperCase().equals("AND"))) {
					throw new QueryParserException(
							"Invalid start of query condition "
									+ current.getValue()
									+ ". Did you mean 'AND'?");
				}

				current = lexer.nextToken();
			}
		} catch (QueryLexerException e) {
			throw new QueryParserException(
					"Failed to process " + e.getMessage());
		}

		if (conditions.isEmpty() || attributes.isEmpty() || values.isEmpty()) {
			throw new QueryParserException("Query must define conditions");
		}
	}

	/**
	 * Compares student record with list of condition operators and values.
	 * 
	 * @param record Student record for comparison
	 * @return True if student record satisfies all conditions
	 */
	public boolean compare(StudentRecord record) {
		int size = conditions.size();
		for (int i = 0; i < size; i++) {
			if (!conditions.get(i).satisfied(attributes.get(i).get(record),
					values.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns getter for current attribute token.
	 * 
	 * @param current Attribute token.
	 * @return getter for current attribute token
	 * @throws QueryParserException if attribute name is invalid
	 */
	private IFieldGetter getStudentAttributeGetter(Token current) {
		switch (current.getValue()) {
		case "jmbag":
			return new JmbagGetter();
		case "lastName":
			return new LastNameGetter();
		case "firstName":
			return new FirstNameGetter();
		default:
			throw new QueryParserException(
					"Invalid table attribute name " + current.getValue());
		}
	}

	/**
	 * Returns condition operator for current condition token
	 * 
	 * @param current Condition token
	 * @return condition operator for current condition token
	 * @throws QueryParserException if given comparison operator is not
	 *             supported
	 */
	private IComparisonOperator getCondition(Token current) {
		switch (current.getValue()) {
		case "=":
			return IComparisonOperator.EqualOperator;
		case "!=":
			return IComparisonOperator.NotEqualOperator;
		case ">":
			return IComparisonOperator.GreaterThanOperator;
		case ">=":
			return IComparisonOperator.GreaterOrEqualOperator;
		case "<":
			return IComparisonOperator.LessThanOperator;
		case "<=":
			return IComparisonOperator.LessOrEqualOperator;
		case "LIKE":
			return IComparisonOperator.LikeOperator;
		default:
			throw new QueryParserException(
					"Unsupported operation " + current.getValue());
		}
	}

	/**
	 * Checks if operator at current state is 'LIKE'
	 * 
	 * @return true if operator at current state is 'LIKE', else false
	 */
	private boolean isLikeOperation() {
		return conditions
				.get(conditions.size() - 1) == IComparisonOperator.LikeOperator;
	}

	/**
	 * Checks if value after 'LIKE' operator is valid and returns new token with
	 * value adjusted for java regex.
	 * 
	 * @param current Value token
	 * @return new token with value adjusted for java regex
	 * @throws QueryParserException if value can't be processed by 'LIKE'
	 *             operator.
	 */
	private Token processLikeToken(Token current) {
		if (current.getValue().toLowerCase().matches("^[a-z0-9*šđčćž]*")
				&& (current.getValue().length() - current.getValue()
						.replaceAll("\\*", "").length()) <= 1) {
			return new Token(current.getValue().replace("*", ".*"),
					TokenType.STRING);
		}
		throw new QueryParserException("Invalid argument for LIKE operator");
	}
}
