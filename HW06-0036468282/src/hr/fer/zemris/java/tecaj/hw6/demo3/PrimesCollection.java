package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for iteration over prime numbers.
 * 
 * @author Domagoj Tokić
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/** Number of prime numbers which are accessible */
	private int numOfPrimeNum;

	/**
	 * Creates instance of {@link PrimesCollection}
	 * 
	 * @param numOfPrimeNum Number of prime numbers which are accessible
	 */
	public PrimesCollection(int numOfPrimeNum) {
		this.numOfPrimeNum = numOfPrimeNum;
	}

	/**
	 * Returns iterator for iteration over prime numbers
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator(numOfPrimeNum);
	}

	/**
	 * Iterator for iteration over prime numbers
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private class PrimeIterator implements Iterator<Integer> {

		/** First prime number */
		private static final int FIRST_PRIME = 2;

		/** Last prime number returned */
		private int currentPrime;

		/** Number of prime numbers returned */
		private int counter;

		/** Number of prime numbers which are accessible */
		private int numOfPrimeNum;

		/**
		 * Creates instance of {@link PrimeIterator}
		 * 
		 * @param numOfPrimeNum Number of prime numbers which are accessible
		 */
		public PrimeIterator(int numOfPrimeNum) {
			currentPrime = FIRST_PRIME;
			this.numOfPrimeNum = numOfPrimeNum;
		}

		@Override
		public boolean hasNext() {
			return counter < numOfPrimeNum;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException(
						"Number of elements is exceeded");
			}

			if (counter == 0) {
				currentPrime = 2;
			} else if (counter == 1) {
				currentPrime = 3;
			
			} else {
				while (true) {
					currentPrime += 2;
					boolean isPrime = true;
					
					for (int i = 2, sqrt = (int) Math.sqrt(currentPrime); i <= sqrt; i++) {
						if (currentPrime % i == 0) {
							isPrime = false;
						}
					}
					
					if (isPrime) {
						break;
					}
				}
			}
			
			counter++;
			return currentPrime;
		}

	}

}
