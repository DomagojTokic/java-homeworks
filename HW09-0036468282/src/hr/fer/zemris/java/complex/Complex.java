package hr.fer.zemris.java.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Complex number class
 * 
 * @author Domagoj
 *
 */
public class Complex {

	/** Real part */
	private double re;

	/** Imaginary part */
	private double im;

	/**
	 * Returns real part
	 * 
	 * @return real part
	 */
	public double getReal() {
		return re;
	}

	/**
	 * Returns imaginary part
	 * 
	 * @return imaginary part
	 */
	public double getImag() {
		return im;
	}

	/** Complex zero */
	public static final Complex ZERO = new Complex(0, 0);

	/** Complex one */
	public static final Complex ONE = new Complex(1, 0);

	/** Complex negative one */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** Complex imaginary one */
	public static final Complex IM = new Complex(0, 1);

	/** Complex imaginary negative one */
	public static final Complex IM_NEG = new Complex(0, -1);

	/** Regex for checking input */
	private static final String INPUT_REGEX = "((-?\\d+([.]\\d+)?)?\\s*)(([+-]?\\s*[i]\\d+([.]\\d+)?)|i|-i)?$";

	/** Regex used to extract double real number */
	private static final String REAL_DOUBLE_REGEX = "^(?!i)\\d+[.]\\d+";

	/** Regex used to extract integer real number */
	private static final String REAL_INT_REGEX = "^(?!i)\\d+";

	/** Regex used to extract double imaginary number */
	private static final String IMAG_DOUBLE_REGEX = "[i]\\d+[.]\\d+";

	/** Regex used to extract integer imaginary number */
	private static final String IMAG_INT_REGEX = "[i]\\d+[^.]";

	/**
	 * Default contructor - creates new complex zero.
	 */
	public Complex() {
		this(0.0, 0.0);
	}

	/**
	 * Creates instance of complex number
	 * 
	 * @param re Real part
	 * @param im Imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Returns this*c
	 * 
	 * @param c Multiplier
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		double real = re * c.getReal() - im * c.getImag();
		double imag = re * c.getImag() + im * c.getReal();

		return new Complex(real, imag);
	}

	/**
	 * Returns this/c
	 * 
	 * @param c Divisor
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		Complex multiplicator = new Complex(c.getReal(), -c.getImag());
		Complex denominator = c.multiply(multiplicator);
		Complex numerator = this.multiply(multiplicator);

		return new Complex(numerator.getReal() / denominator.getReal(),
				numerator.getImag() / denominator.getReal());
	}

	/**
	 * Returns this+c
	 * 
	 * @param c Addition
	 * @return this+c
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.getReal(), im + c.getImag());
	}

	/**
	 * Returns this-c
	 * 
	 * @param c Subtractor
	 * @return this-c
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.getReal(), im - c.getImag());
	}

	/**
	 * Returns -this
	 * 
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Returns this^n, n is non-negative integer
	 * 
	 * @param n power
	 * @return this^n
	 * @throws IllegalArgumentException if power is negative
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Requested power mustn't be negative number");
		}
		if (n == 0) {
			return Complex.ONE;
		}

		double magnitude = this.module();
		double angle = this.getAngle();

		return fromMagnitudeAndAngle(Math.pow(magnitude, n), n * angle);
	}

	/**
	 * Creates new complex number from given magnitude and angle. Angle must be
	 * in radian.
	 * 
	 * @param magnitude
	 * @param angle
	 * @return new complex number
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imag = magnitude * Math.sin(angle);

		return new Complex(real, imag);
	}

	/**
	 * Returns complex number angle in radian in scope of 0 to 2PI.
	 * 
	 * @return angle
	 */
	private double getAngle() {
		double angle = Math.atan2(im, re);

		if (angle < 0) {
			return 2 * Math.PI + angle;
		}

		return angle;
	}

	/**
	 * Returns n-th root of this, n is positive integer
	 * 
	 * @param n root
	 * @return n-th root of this
	 * @throws IllegalArgumentException if root is less than 1
	 */
	public List<Complex> root(int n) {
		List<Complex> roots = new ArrayList<>();

		if (n <= 0) {
			throw new IllegalArgumentException("Requested root must greater than 0");
		}

		for (int i = 0; i < n; i++) {
			double magnitude = Math.pow(this.module(), (double) 1 / n);
			double angle = (this.getAngle() + 2 * Math.PI * i) / n;
			roots.add(fromMagnitudeAndAngle(magnitude, angle));
		}

		return roots;
	}

	/**
	 * Parses string and generates appropriate complex number based on it. Valid
	 * input is in format a+ib, including blank spaces between numbers and
	 * operator.
	 * 
	 * @param string String representation of complex number
	 * @return complex number
	 */
	public static Complex fromString(String string) {
		if (!string.matches(INPUT_REGEX) || string.isEmpty()) {
			return null;
		} else {
			Double real = null;
			Double imag = null;
			boolean realPositive = true;
			boolean imagPositive = true;

			string = string.replaceAll("\\s+", "") + " ";

			if (string.charAt(0) == '-') {
				if (string.charAt(1) != 'i') {
					realPositive = false;
					string = string.substring(1);
				} else {
					imagPositive = false;
					string = string.substring(1);
				}
			}

			if (string.substring(1).contains("-")) {
				imagPositive = false;
			}

			Pattern pattern = Pattern.compile(REAL_DOUBLE_REGEX);
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				String realString = matcher.group();
				string = string.substring(realString.length());
				real = Double.parseDouble((!realPositive ? "-" : "") + realString);
			}

			if (real == null) {
				pattern = Pattern.compile(REAL_INT_REGEX);
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					String realString = matcher.group();
					string = string.substring(realString.length());
					real = Double.parseDouble((!realPositive ? "-" : "") + realString);
				}
			}

			pattern = Pattern.compile(IMAG_DOUBLE_REGEX);
			matcher = pattern.matcher(string);
			if (matcher.find()) {
				String imagString = matcher.group();
				string = string.substring(imagString.length());
				imag = Double.parseDouble((!imagPositive ? "-" : "") + imagString.substring(1));
			}

			if (imag == null) {
				pattern = Pattern.compile(IMAG_INT_REGEX);
				matcher = pattern.matcher(string);
				if (matcher.find()) {
					String imagString = matcher.group();
					string = string.substring(imagString.length());
					imag = Double.parseDouble((!imagPositive ? "-" : "") + imagString.substring(1));
				}
			}

			if (real == null) {
				real = 0.0;
			}

			if (imag == null) {
				if (imagPositive) {
					imag = 1.0;
				} else {
					imag = -1.0;
				}
			}

			return new Complex(real, imag);
		}
	}

	@Override
	public String toString() {
		String string = "";
		if (this == Complex.ZERO || this.getImag() == 0 && this.getReal() == 0) {
			string = "0";
		} else {
			string += re == 0 ? "" : String.format("%.2f", re);
			if (im != 0) {
				if (re != 0) {
					string += String.format("%+.2f", im) + "i";
				} else {
					string += String.format("%.2f", im) + "i";
				}
			}
		}
		return string;
	}
}
