package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Records change of {@link IntegerStorage}. It's beginning and new value
 *
 * @author Domagoj Tokić
 *
 */
public class IntegerStorageChange {

	/** Integer Storage */
	private IntegerStorage storage;
	
	/** Beginning value */
	private int oldValue;
	
	/** Current value */
	private int currentValue;

	
	/**
	 * Creates instance of {@link IntegerStorageChange}
	 * @param storage Integer storage
	 * @param beginningValue Beginning value
	 * @param currentValue Current value
	 */
	public IntegerStorageChange(IntegerStorage storage, int beginningValue,
			int currentValue) {
		super();
		this.storage = storage;
		this.oldValue = beginningValue;
		this.currentValue = currentValue;
	}

	/**
	 * Returns integer storage
	 * @return integer storage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Returns beginning value
	 * @return beginning value
	 */
	public int getBeginningValue() {
		return oldValue;
	}

	/**
	 * Returns current value
	 * @return current value
	 */
	public int getCurrentValue() {
		return currentValue;
	}
	
}
