package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * Class for calculation n-th root of complex number. Formula is taken from
 * page:
 * http://www-thphys.physics.ox
 * .ac.uk/people/FrancescoHautmann/Cp4/sl_clx_11_4_cls.pdf
 * 
 * @author Domagoj Tokić
 *
 */

public class Roots {

	/**
	 * Entrance into class functionality.
	 * 
	 * @param args Real part
	 *            Imaginary part
	 *            Roots ordinal number
	 */

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Program needs 3 arguments.");
			System.exit(1);
		}

		double real = Double.parseDouble(args[0]);
		double imag = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);

		double r = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
		double angle = Math.atan2(imag, real);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);

		System.out.println("You requested calculation of " + root
				+ ". roots. Solutions are:");

		for (int k = 0; k < root; k++) {
			double rootReal = Math.pow(r, (double) 1 / root)
					* Math.cos((angle + 2 * Math.PI * k) / root);
			double rootImag = Math.pow(r, (double) 1 / root)
					* Math.sin((angle + 2 * Math.PI * k) / root);

			
			String sign = "+";
			if (Math.signum(rootImag) < 0) {
				sign = "-";
			}

			System.out.println(k + 1 + ") " + df.format(rootReal) + " " + sign
					+ " " + df.format(Math.abs(rootImag)) + "i");
		}
	}

}
