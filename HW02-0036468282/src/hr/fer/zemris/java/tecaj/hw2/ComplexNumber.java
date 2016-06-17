package hr.fer.zemris.java.tecaj.hw2;

import java.text.DecimalFormat;

/**
 * Class which represents an unmodifiable complex number.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class ComplexNumber {

	/**
	 * Real part of complex number
	 */
	private double real;
	
	/**
	 * Imaginary part of complex number
	 */
	private double imaginary;

	/**
	 * Constructor which defines real and imaginary parts.
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Constructor which defines real part and sets imaginary part to 0.
	 * @param real
	 * @return new complex number
	 */
	public ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Constructor which defines imaginary part and sets real part to 0.
	 * @param imaginary
	 * @return new complex number
	 */
	public ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns complex number magnitude.
	 * @return magnitude
	 */
	public double getMagnitude() {
		
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns complex number angle in radian in scope of 0 to 2PI.
	 * @return angle
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		
		if (angle < 0) {
			return 2 * Math.PI + angle;
		}
		
		return angle;
	}

	/**
	 * Creates new complex number from given magnitude and angle. Angle must be
	 * in radian.
	 * @param magnitude
	 * @param angle
	 * @return new complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Returns new complex number which is result of adding given onto current
	 * complex number.
	 * @param c Complex number to be added to current
	 * @return new complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		
		return new ComplexNumber(real + c.getReal(), imaginary
				+ c.getImaginary());
	}

	/**
	 * Returns new complex number which is result of subtracting given from
	 * current complex number.
	 * @param c Complex number to be subtracted from current
	 * @return new complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		
		return new ComplexNumber(real - c.getReal(), imaginary
				- c.getImaginary());
	}

	/**
	 * Returns new complex number which is result of multiplying given with
	 * current complex number.
	 * @param c Complex number to multiplied with current
	 * @return new complex number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		
		double newReal = real * c.getReal() - imaginary * c.getImaginary();
		double newImaginary = real * c.getImaginary() + imaginary * c.getReal();
		
		return new ComplexNumber(newReal, newImaginary);
	}

	/**
	 * Returns new complex number which is result of dividing current with
	 * given complex number.
	 * @param c Complex number which divides current
	 * @return new complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		
		ComplexNumber multiplicator = new ComplexNumber(c.getReal(),
				-c.getImaginary());
		ComplexNumber denominator = c.mul(multiplicator);
		ComplexNumber numerator = this.mul(multiplicator);
		
		return new ComplexNumber(numerator.getReal() / denominator.getReal(),
				numerator.getImaginary() / denominator.getReal());
	}

	/**
	 * Returns new complex number which is nth power of current complex number.
	 * @param n power
	 * @return new complex number
	 * @throws IllegalArgumentException if power is negative number.
	 */
	public ComplexNumber power(int n) {

		if (n < 0) {
			throw new IllegalArgumentException(
					"Requested power mustn't be negative number");
		}

		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		return fromMagnitudeAndAngle(Math.pow(magnitude, n), n * angle);
	}

	/**
	 * Returns array of complex numbers which are nth roots of current complex
	 * number.
	 * @param n root
	 * @return array of complex numbers
	 * @throws IllegalArgumentException if root is less than 1.
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] complexNumberArray = new ComplexNumber[n];

		if (n <= 0) {
			throw new IllegalArgumentException(
					"Requested root must greater than 0");
		}

		for (int i = 0; i < n; i++) {
			double magnitude = Math.pow(this.getMagnitude(), (double) 1 / n);
			double angle = (this.getAngle() + 2 * Math.PI * i) / n;
			complexNumberArray[i] = fromMagnitudeAndAngle(magnitude, angle);
		}

		return complexNumberArray;
	}

	/**
	 * Turns ComplexNumber type object into formatted string. Precision is
	 * 10^(-5).
	 * @return Formatted complex number string
	 */
	public String toString() {
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(5);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);

		String sign = "";
		if (this.getImaginary() >= 0) {
			sign = "+";
		}

		return df.format(this.getReal()) + sign
				+ df.format(this.getImaginary()) + "i";
	}

	/**
	 * Turns input string into complex number. Input has format
	 * real+/-imaginary'i' or if one of them is 0, it does not have to be in
	 * input string. If numbers are written incorrectly or in incorrect order,
	 * method writes message.
	 * Example: "3.51", "-3.17", "-2.71i", "i", "1","-2.71-3.15i"
	 * @param s
	 * @return new complex number
	 * @throws IllegalArgumentException if string for parsing contains illegal
	 *             characters.
	 * @throws NumberFormatException if given complex number string isn't in
	 *             correct format or numbers are in invalid form.
	 */
	public static ComplexNumber parse(String s) {

		if (s.matches("[0-9i+-.]+")) {

			String[] numbers;

			// Extracting sign of first number for valid splitting
			String firstNumberSign = "";
			if (s.charAt(0) == '-') {
				firstNumberSign = "-";
				s = s.substring(1);
			} else {
				if (s.charAt(0) == '+') {
					s = s.substring(1);
				}
			}

			// Splitting complex number
			if (s.contains("+")) {
				numbers = s.split("\\+");
				numbers[0] = firstNumberSign + numbers[0];
			} else {
				if (s.contains("-")) {
					numbers = s.split("-");
					numbers[0] = firstNumberSign + numbers[0];
					numbers[1] = "-" + numbers[1];
				} else {
					numbers = new String[1];
					numbers[0] = s;
				}
			}

			// Complex number cannot have more than two parts (real and
			// imaginary)
			if (numbers.length > 2) {
				throw new IllegalArgumentException("Input is in invalid form");
			}

			try {
				if (numbers.length == 1) {

					// Input contains only imaginary number
					if (numbers[0].contains("i")) {
						String imaginaryString;

						if (numbers[0].length() > 1) {
							imaginaryString = numbers[0].substring(0,
									numbers[0].length() - 1);
						} else {
							imaginaryString = "1";
						}

						double imaginary = Double.parseDouble(imaginaryString);
						return new ComplexNumber(0, imaginary);
					}
					// Input contains only real number
					else {
						double real = Double.parseDouble(numbers[0]);
						return new ComplexNumber(real, 0);
					}
				} else {

					double real = Double.parseDouble(numbers[0]);
					String imaginaryString = numbers[1].substring(0,
							numbers[1].length() - 1);
					double imaginary = Double.parseDouble(imaginaryString);
					return new ComplexNumber(real, imaginary);
				}
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Input is in invalid form");
			}
		}

		throw new IllegalArgumentException(
				"String contains illegal characters or input is empty.");
	}
}
