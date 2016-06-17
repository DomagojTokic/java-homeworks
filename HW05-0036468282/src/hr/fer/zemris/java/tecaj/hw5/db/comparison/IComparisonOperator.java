package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Interface for methods which compare strings. Defines simple comparison
 * functions.
 * 
 * @author Domagoj
 *
 */
public interface IComparisonOperator {

	/**
	 * Checks if two strings are equal.
	 */
	public IComparisonOperator EqualOperator = (value1, value2) -> value1
			.equals(value2);

	/**
	 * Checks if two strings aren't equal.
	 */
	public IComparisonOperator NotEqualOperator = (value1,
			value2) -> !value1.equals(value2);

	/**
	 * Checks if first string is greater than second.
	 */
	public IComparisonOperator GreaterThanOperator = (value1,
			value2) -> value1.compareTo(value2) > 0;

	/**
	 * Checks if first string is less than second.
	 */
	public IComparisonOperator LessThanOperator = (value1,
			value2) -> value1.compareTo(value2) < 0;

	/**
	 * Checks if first string is greater or equal to second.
	 */
	public IComparisonOperator GreaterOrEqualOperator = (value1,
			value2) -> value1.compareTo(value2) >= 0;
	/**
	 * Checks if first string is less or equal to second.
	 */
	public IComparisonOperator LessOrEqualOperator = (value1,
			value2) -> value1.compareTo(value2) <= 0;

	/**
	 * Checks if string matches given regex.
	 */
	public IComparisonOperator LikeOperator = (value1, regex) -> value1
			.matches(regex);

	/**
	 * Checks if strings satisfy condition defined in implementation of
	 * this method.
	 * 
	 * @param value1 First string
	 * @param value2 Second string
	 * @return true if condition is satisfied, else false.
	 */
	public boolean satisfied(String value1, String value2);

}
