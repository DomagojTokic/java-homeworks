package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Wraps value of Integer, Double or String object. Accepts null. Provides
 * methods for basic arithmetic operations: incrementing, decrementing,
 * multiplying, dividing and comparing (less than 0 if smaller, greater than 0
 * if greater or 0 if equal). Arithmetic operations on strings can only occur if
 * string can be interpreted as number. Null is interpreted as Integer value of
 * 0 on arithmetic operations.
 * 
 * @author Domagoj Tokić
 *
 */
public class ValueWrapper {

	/**
	 * Wrapped value
	 */
	Object value;

	/**
	 * Creates instance of {@link ValueWrapper}
	 * 
	 * @param value Value for wrapping
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns value object.
	 * 
	 * @return value object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets value of object.
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Increments current value with value of increment.
	 * 
	 * @param incValue Increment
	 */
	public void increment(Object incValue) {
		setValue(executeOperation(value, incValue, Operation.INCREMENT));
	}

	/**
	 * Decrements current value with value of decrement.
	 * 
	 * @param decValue Decrement
	 */
	public void decrement(Object decValue) {
		setValue(executeOperation(value, decValue, Operation.DECREMENT));
	}

	/**
	 * Multiplies current value with multiplier value.
	 * 
	 * @param mulValue Multiplier
	 */
	public void multiply(Object mulValue) {		
		setValue(executeOperation(value, mulValue, Operation.MULTIPLY));
	}

	/**
	 * Divides current value with divisor value.
	 * 
	 * @param divValue Divisor
	 * @throws ArithmeticException if user tries to divide by 0
	 */
	public void divide(Object divValue) {
		approveValueClass(divValue);
		if(((Number) turnIntoNumber(divValue)).doubleValue() == 0.0) {
			throw new ArithmeticException("Unable to divide by zero");
		}
		
		setValue(executeOperation(value, divValue, Operation.DIVIDE));
	}

	/**
	 * Compares current value with comparator value
	 * 
	 * @param withValue Value for comparison
	 * @return less than 0 if current is lesser, greater than 0 if current is
	 *         greater, 0 if equal
	 */
	public int numCompare(Object withValue) {
		return executeOperation(value, withValue, Operation.COMPARE).intValue();
	}

	/**
	 * Approves type of value object
	 * 
	 * @param value Value object for approval
	 * @throws IllegalArgumentException if class of value is invalid
	 */
	private void approveValueClass(Object value) {
		if (!(value == null || value instanceof Integer
				|| value instanceof Double || value instanceof String)) {
			throw new IllegalArgumentException(
					"Unsupported class operating with ValueWrapper");
		}
	}

	/**
	 * Creates number from string value (Double or Integer depending on format)
	 * or creates Integer(0) if value is null. If it's already a number, returns the
	 * same object.
	 * 
	 * @param value Value for turning into number
	 * @return Double or Integer type object
	 * @throws NumberFormatException if string cannot be turned into number
	 */
	private Object turnIntoNumber(Object value) {
		if (value == null) {
			return Integer.valueOf(0);
		}
		if (value instanceof String) {
			String stringValue = (String) value;
			try {
				if (stringValue.matches("[-]?[0-9]+\\.[0-9]+.*")
						|| stringValue.contains("E")) {
					return Double.valueOf(Double.parseDouble(stringValue));
				
				} else {
					return Integer.valueOf(Integer.parseInt(stringValue));
				}
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"Unable to execute operations with value "
								+ stringValue);
			}

		}
		return value;
	}

	/**
	 * Executes given operation.
	 * 
	 * @param value First operand
	 * @param operValue Second operand
	 * @param operation Operation
	 * @return result of operation
	 */
	private Number executeOperation(Object value, Object operValue,
			Operation operation) {
		
		approveValueClass(value);
		approveValueClass(operValue);
		Object currentValue = turnIntoNumber(value);
		Object validOperValue = turnIntoNumber(operValue);

		Number result = operation.operate((Number) currentValue,
				(Number) validOperValue);

		if (result instanceof Integer || (currentValue instanceof Integer
				&& validOperValue instanceof Integer)) {
			// int value needs to extracted from default double
			return (Integer) result.intValue();
		} else {
			return (Double) result;
		}
	}

	/**
	 * Interface for basic arithmetic operations used in {@link ValueWrapper}
	 *  
	 * @author Domagoj Tokić
	 *
	 */
	private interface Operation {

		/**
		 * Increment operation
		 */
		Operation INCREMENT = (o1, o2) -> o1.doubleValue() + o2.doubleValue();

		/**
		 * Decrement operation
		 */
		Operation DECREMENT = (o1, o2) -> o1.doubleValue() - o2.doubleValue();

		/**
		 * Multiply operation
		 */
		Operation MULTIPLY = (o1, o2) -> o1.doubleValue() * o2.doubleValue();

		/**
		 * Divide operation
		 */
		Operation DIVIDE = (o1, o2) -> o1.doubleValue() / o2.doubleValue();

		/**
		 * Compare operation
		 */
		Operation COMPARE = (o1, o2) -> Double.valueOf(o1.doubleValue())
				.compareTo(o2.doubleValue());

		/**
		 * Operates over two objects type Number and returns value type Number
		 * 
		 * @param o1 First operand
		 * @param o2 Second operand
		 * @return result of operation
		 */
		Number operate(Number o1, Number o2);
	}

}
