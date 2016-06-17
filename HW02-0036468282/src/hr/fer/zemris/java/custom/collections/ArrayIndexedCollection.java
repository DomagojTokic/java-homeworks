package hr.fer.zemris.java.custom.collections;

/**
 * Class which implements collection based on array. Contrary to usual arrays,
 * this implementation has changeable size. Extends Collection class.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Collection size
	 */
	private int size;

	/**
	 * Collection capacity
	 */
	private int capacity;

	/**
	 * Inner array of elements where objects are stored
	 */
	private Object[] elements;

	/**
	 * Constructor with setting initial capacity
	 * @param initialCapacity
	 * @throws IllegalArgumentException if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {

		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Initial capacity must be larger than 0!");
		}

		size = 0;
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Default constructor. Sets initial capacity to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Constructor with setting initial collection
	 * @param initialCollection
	 */
	public ArrayIndexedCollection(Collection initialCollection) {
		this();
		this.addAll(initialCollection);
	}

	/**
	 * Constructor with setting initial collection and capacity
	 * @param initialCollection
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(int initialCapacity,
			Collection initialCollection) {
		this(initialCapacity);
		this.addAll(initialCollection);
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(Object value) {

		if (value != null) {

			for (int i = 0; i < size; i++) {
				if (elements[i].equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. Complexity is O(n).
	 * @param value
	 * @return Index of given value in collection
	 */
	public int indexOf(Object value) {

		if (value != null) {
			for (int i = 0; i < size; i++) {
				if (elements[i].equals(value)) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Returns the object that is stored in collection at position index.
	 * Valid indexes are 0 to size-1. Complexity is O(1).
	 * @param index Index of requested object
	 * @return Requested object
	 * @throws IndexOutOfBoundsException if index isn't between 0 and size-1
	 */
	public Object get(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
					"Index of requested element is out of bounds");
		}

		return elements[index];
	}

	/**
	 * Doubles current array by making a duplicate with same values but double
	 * capacity.
	 */
	private void doubleTheArray() {
		int newCapacity = capacity * 2;
		Object[] newElements = new Object[newCapacity];

		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}

		capacity = newCapacity;
		elements = newElements;

	}

	/**
	 * Adds the given object into this collection. Doubles the capacity if
	 * collection is full. Method has complexity O(1) if collection array isn't
	 * full or O(n) it is.
	 * @param value Object to be added
	 * @throws IllegalArgumentException if given object is null
	 */
	public void add(Object value) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Cannot add null object into collection");
		}

		// If array if full, allocate new with double capacity and copy elements
		if (size == capacity) {
			doubleTheArray();
		}

		elements[size] = value;
		size++;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * array. The legal positions are 0 to size. If given position is less than
	 * size, all objects on positions greater than position will be moved
	 * to one position higher. Complexity is O(n) of position is less than size
	 * O(n) if position equals size.
	 * @param value Object for insertion
	 * @param position Position in collection where object will be inserted
	 * @throws IllegalArgumentException if given object is null
	 * @throws IndexOutOfBoundsException if position isn't between 0 and size
	 */
	public void insert(Object value, int position) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Cannot add null object into collection");
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(
					"Index of requested element is out of bounds");
		}

		if (size == capacity) {
			doubleTheArray();
		}

		size++;

		for (int i = size - 1; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = value;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 * @param index Index of object in collection
	 * @throws IllegalArgumentException if index isn't between 0 and size-1
	 */
	public void remove(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
					"Index of requested element is out of bounds 0 and size-1");
		}

		for (int i = index; i < size; i++) {

			// when loop gets to the end of the array
			if (i == size - 1) {
				elements[i] = null;
				break;
			}

			elements[i] = elements[i + 1];
		}

		size--;
	}

	/**
	 * Removes given object from collection by first finding its index and
	 * calling method for removing by index if it exists. If it existed, returns
	 * true or else false.
	 * @param value Object to be removed
	 * @return Status of removal
	 * @throws IllegalArgumentException if given object is null
	 */
	public boolean remove(Object value) {

		if (value == null) {
			throw new IllegalArgumentException("Given object is null");
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];

		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}

		return array;
	}

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * Order of elements to be processed is from lower to higher indices.
	 * @param processor Processor of objects
	 */
	public void forEach(Processor processor) {

		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Removes all elements from the collection by dereferencing them. The
	 * allocated array is left at current capacity.
	 */
	public void clear() {

		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

}
