package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Prints double value of current and recent value stored in
 * {@link IntegerStorage} on every change of observed subject.
 * 
 * @author Domagoj Tokić
 * @version 1.1
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
	 * Prints double value of current and recent value of {@link IntegerStorage}
	 */
	@Override
	public void valueChanged(IntegerStorageChange integerStorageChange) {
		if (timesAvailableForUse == 0) {
			integerStorageChange.getStorage().removeObserver(this);
			return;
		}

		System.out.println("Double value of integer storage is "
				+ 2 * integerStorageChange.getCurrentValue()
				+ " and before it was "
				+ 2 * integerStorageChange.getBeginningValue());
		timesAvailableForUse--;
	}

}
