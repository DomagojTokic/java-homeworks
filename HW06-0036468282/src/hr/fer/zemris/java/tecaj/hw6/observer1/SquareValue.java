package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Prints double value of current value of {@link IntegerStorage} on every
 * change of value.
 * 
 * @author Domagoj Tokić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints double value of current value of {@link IntegerStorage}
	 */
	@Override
	public void valueChanged(IntegerStorage integerStorage) {
		System.out.println("Squared value of integer storage is "
				+ Math.pow(integerStorage.getValue(), 2));
	}

}
