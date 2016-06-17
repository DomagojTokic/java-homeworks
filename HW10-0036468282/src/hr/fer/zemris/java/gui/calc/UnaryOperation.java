package hr.fer.zemris.java.gui.calc;

import java.util.function.UnaryOperator;

/**
 * Container of basic one number functions used in {@link Calculator}.
 * 
 * @author Domagoj
 *
 */
public class UnaryOperation {

	/** 1/x function. */
	public static final UnaryOperator<Double> INVERT = (x) -> {
		return 1 / x;
	};

	/** Logarithm of 10 function. */
	public static final UnaryOperator<Double> LOG = (x) -> {
		return Math.log10(x);
	};

	/** 10^x function. */
	public static final UnaryOperator<Double> POW10 = (x) -> {
		return Math.pow(10, x);
	};

	/** Natural logarithm function. */
	public static final UnaryOperator<Double> LN = (x) -> {
		return Math.log(x);
	};

	/** e^x function. */
	public static final UnaryOperator<Double> POW_E = (x) -> {
		return Math.pow(Math.E, x);
	};

	/** Sine function. */
	public static final UnaryOperator<Double> SIN = (x) -> {
		return Math.sin(x);
	};

	/** Arc sine function. */
	public static final UnaryOperator<Double> ARCSIN = (x) -> {
		return Math.asin(x);
	};

	/** Cosine function. */
	public static final UnaryOperator<Double> COS = (x) -> {
		return Math.cos(x);
	};

	/** Arc cosine function. */
	public static final UnaryOperator<Double> ARCCOS = (x) -> {
		return Math.acos(x);
	};

	/** Tangent function. */
	public static final UnaryOperator<Double> TAN = (x) -> {
		return Math.tan(x);
	};

	/** Arc tangent function. */
	public static final UnaryOperator<Double> ARCTAN = (x) -> {
		return Math.atan(x);
	};

	/** Cotangent function. */
	public static final UnaryOperator<Double> CTG = (x) -> {
		return Math.cos(x) / Math.sin(x);
	};

	/** Arc cotangent function. */
	public static final UnaryOperator<Double> ARCCTG = (x) -> {
		return Math.PI / 2 - Math.atan(x);
	};
}
