package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Example class for {@link PrimesCollection}
 * 
 * @author Domagoj Tokić
 *
 */
public class PrimesDemo2 {

	/**
	 * Entrance into PrimesDemo2 class
	 * 
	 * @param args No parameters
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
