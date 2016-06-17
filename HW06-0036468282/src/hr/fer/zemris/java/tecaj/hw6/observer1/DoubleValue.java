package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Prints double of current value of {@link IntegerStorage} on every
 * change on observed subject.
 * 
 * @author Domagoj Tokić
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

	/** Times available for use */
	int timesAvailableForUse;

	/**
	 * Creates instance of {@link DoubleValue}
	 * 
	 * @param i Times available for use
	 */
	public DoubleValue(int i) {
		if (i <= 0) {
			throw new IllegalArgumentException(
					"Number of times available for use must be higher than 0");
		}

		timesAvailableForUse = i;
	}

	/**
	 * Prints double of current value of {@link IntegerStorage}
	 */
	@Override
	public void valueChanged(IntegerStorage integerStorage) {
		if (timesAvailableForUse == 0) {
			integerStorage.removeObserver(this);
			return;
		}

		System.out.println("Double value of integer storage is "
				+ 2 * integerStorage.getValue());
		timesAvailableForUse--;
	}

}
