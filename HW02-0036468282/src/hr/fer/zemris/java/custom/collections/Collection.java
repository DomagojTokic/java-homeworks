package hr.fer.zemris.java.custom.collections;

/**
 * Prototype of custom collection class. Made for inheritance.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class Collection {

	/**
	 * Default constructor
	 */
	protected Collection() {

	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * @return State of emptiness
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * @return Size of collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * @param value Object to be added
	 */
	public void add(Object value) {

	}

	/**
	 * Returns true only if the collection contains given value, as determined
	 * by equals method. Given object can be null.
	 * @param value Value to be checked in collection
	 * @return State of collection (does it contain)
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it (in this class it is not
	 * specified which one).
	 * @param value Object to be removed
	 * @return State of collection (is it removed)
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections,
	 * fills it with collection content and returns the array. This method never
	 * returns null.
	 * @return Array of objects
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException(
				"Operation toArray() is not implemented");
	}

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * The order in which elements will be sent is undefined in this class.
	 * @param processor Processor of objects
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into itself all elements from given collection. This other
	 * collection remains unchanged.
	 * @param other Collection of objects to be added
	 */
	public void addAll(Collection other) {

		/**
		 * Processor for adding object into collection
		 * @author Domagoj Tokić
		 */
		class InnerProcessor extends Processor {

			/**
			 * Method which adds object into collection
			 * @param object Object to be added to collection
			 */
			public void process(Object object) {
				Collection.this.add(object);
			}
		}

		if (other == null) {
			return;
		}

		InnerProcessor processor = new InnerProcessor();
		other.forEach(processor);
	}

	/**
	 * Removes all elements from collection.
	 */
	public void clear() {

	}

}
