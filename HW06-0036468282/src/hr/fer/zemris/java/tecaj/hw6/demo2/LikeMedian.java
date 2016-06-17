package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

/**
 * Class for calculation median value added into this class. Median value is
 * value of added objects. If there's even number of elements, lesser one is
 * returned.
 * 
 * @author Domagoj Tokić
 *
 * @param <K> Type of value which will be added
 */
public class LikeMedian<K extends Comparable<K>> implements Iterable<K> {

	/** Sorted storage of objects */
	ArrayList<K> storage;

	/**
	 * Constructor for {@link LikeMedian}
	 */
	public LikeMedian() {
		storage = new ArrayList<>();
	}

	/**
	 * Adds object into storage
	 * 
	 * @param object Object to be added
	 */
	public void add(K object) {
		storage.add(object);
	}

	/**
	 * Returns object with median value. If there are two potential median
	 * values, lesser one is returned. If there are no objects stored, method
	 * returns empty class Optional object
	 * 
	 * @return median object
	 */
	public Optional<K> get() {
		if (storage.isEmpty()) {
			return Optional.empty();
		}
		
		ArrayList<K> temp = new ArrayList<>(storage);
		Collections.sort(temp);
		
		if (temp.size() % 2 != 0) {
			return Optional.of(temp.get(temp.size() / 2));
		} else {
			return Optional.of(temp.get(temp.size() / 2 - 1));
		}
	}

	@Override
	public Iterator<K> iterator() {
		return storage.iterator();
	}
}
