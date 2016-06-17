package hr.fer.zemris.java.gui.calc;

import java.util.function.BinaryOperator;

/**
 * Container and executor of basic two number functions used in
 * {@link Calculator}.
 * 
 * @author Domagoj
 *
 */
public class DualOperation {

	/** Plus function. */
	public static final BinaryOperator<Double> PLUS = (x, y) -> {
		return x + y;
	};

	/** Minus function. */
	public static final BinaryOperator<Double> MINUS = (x, y) -> {
		return x - y;
	};

	/** Multiply function. */
	public static final BinaryOperator<Double> MULTIPLY = (x, y) -> {
		return x * y;
	};

	/** Divide function. */
	public static final BinaryOperator<Double> DIVIDE = (x, y) -> {
		return x / y;
	};

	/** x^y function. */
	public static final BinaryOperator<Double> POWER = (x, y) -> {
		return Math.pow(x, y);
	};

	/** y-th root of x function. */
	public static final BinaryOperator<Double> ROOT = (x, y) -> {
		if (x < 0) {
			return Double.NaN;
		}
		return Math.pow(x, 1 / y);
	};

}
