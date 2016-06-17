package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for integer value. Has ability to hold observers which are notified
 * on every change of value.
 * 
 * @author Domagoj Tokić
 *
 */
public class IntegerStorage {

	/** Storage value */
	private int value;

	/** List of observers */
	private List<IntegerStorageObserver> observers;

	/** Observers marked for removal */
	private List<IntegerStorageObserver> toBeRemoved;

	/**
	 * Creates instance of {@link IntegerStorage}
	 * 
	 * @param initialValue Initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
		toBeRemoved = new ArrayList<>();
	}

	/**
	 * Adds observer into list of observers
	 * 
	 * @param observer Observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes observer from observers list
	 * 
	 * @param observer Observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		toBeRemoved.add(observer);
	}

	/**
	 * Clears list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns value of storage
	 * 
	 * @return value of storage
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets value of storage and notifies observers if value is different from
	 * current
	 * 
	 * @param value New value of storage
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Remove obsolete observers
			for (IntegerStorageObserver observer : toBeRemoved) {
				observers.remove(observer);
			}
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
