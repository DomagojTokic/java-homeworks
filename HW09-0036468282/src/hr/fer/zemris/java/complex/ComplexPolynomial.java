package hr.fer.zemris.java.complex;

/**
 * Complex (number) polynomial function class
 * 
 * @author Domagoj
 *
 */
public class ComplexPolynomial {

	/** Polynomial function factors */
	Complex[] factors;

	/**
	 * Creates new instance of {@link ComplexPolynomial}
	 * 
	 * @param factors Function factors in order from lower to higher factors;
	 *            eg. 1, 2+i(z), 9(z^2),...
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * 
	 * @return order of this polynom
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Computes a new polynomial this*p
	 * 
	 * @param p Multiplier
	 * @return new polynomial this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = new Complex[order() + p.order() + 1];

		for (int i = 0; i < mulFactors.length; i++) {
			mulFactors[i] = Complex.ZERO;
		}

		for (int i = 0; i <= this.order(); i++) {
			for (int j = 0; j <= p.order(); j++) {
				mulFactors[i + j] = mulFactors[i + j].add(getFactor(i).multiply(p.getFactor(j)));
			}
		}

		return new ComplexPolynomial(mulFactors);
	}

	/**
	 * Returns factor from given place in polynomial
	 * 
	 * @param index Place in polynomial; power of variable z
	 * @return factor
	 */
	private Complex getFactor(int index) {
		return factors[index];
	}

	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return first derivative of polynomial
	 */
	public ComplexPolynomial derive() {
		if (order() == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		} else {
			Complex[] derFactors = new Complex[factors.length - 1];

			for (int i = 1; i <= order(); i++) {
				derFactors[i - 1] = new Complex(this.getFactor(i).getReal() * i,
						this.getFactor(i).getImag() * i);
			}
			return new ComplexPolynomial(derFactors);
		}
	}

	/**
	 * computes polynomial value at given point z
	 * @param z Point for calculation
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;

		for (int i = 0; i <= order(); i++) {
			result = result.add(z.power(i).multiply(getFactor(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		String string = "";

		for (int i = order(); i >= 0; i--) {
			String factorString = getFactor(i).toString();
			if (!factorString.equals("0")) {
				if (factorString.contains("+") || factorString.contains("-")) {
					string += "(" + factorString + ")";
				} else {
					string += factorString;
				}

				if (i != 0)
					string += "z";
				if (i > 1)
					string += "^" + i;
				string += " + ";
			}
		}
		// Removing extra " + "
		string = string.substring(0, string.length() - 3);

		return string;
	}

}
