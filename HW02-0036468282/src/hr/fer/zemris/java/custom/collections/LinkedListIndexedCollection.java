package hr.fer.zemris.java.custom.collections;

/**
 * Class which implements linked list-backed collection.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Class representing linked list node.
	 * @author Domagoj Tokić
	 * @version 1.0
	 */
	private static class ListNode {
		/**
		 * Reference to next node
		 */
		ListNode next;
		/**
		 * Reference to previous node
		 */
		ListNode previous;
		/**
		 * Value of node
		 */
		Object value;
	}

	/**
	 * Size of list
	 */
	private int size;

	/**
	 * First node in list
	 */
	private ListNode first;

	/**
	 * Last node in list
	 */
	private ListNode last;

	/**
	 * Default constructor
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Constructor which build list with initial collection
	 * @param initialCollection
	 */
	public LinkedListIndexedCollection(Collection initialCollection) {
		this();
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

			if (this.indexOf(value) != -1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. Uses equals method for
	 * comparison. Complexity is O(n).
	 * @param value
	 * @return Index of value
	 */
	public int indexOf(Object value) {
		ListNode tempNode = first;
		int index = 0;

		while (tempNode != null) {

			if (tempNode.value.equals(value)) {
				return index;
			} else {
				tempNode = tempNode.next;
				index++;
			}
		}

		return -1;
	}

	/**
	 * Returns the object that is stored in linked list at position index. Valid
	 * indexes are 0 to size-1. If index is invalid, method throws
	 * IndexOutOfBoundsException.
	 * @param index Index of requested object
	 * @return value on position index
	 */
	public Object get(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException(
					"Index of requested element is out of bounds");
		}

		ListNode tempNode;

		if (index <= size / 2) {
			tempNode = first;

			for (int i = 0; i < index; i++) {
				tempNode = tempNode.next;
			}
		} else {
			tempNode = last;

			for (int i = size - 1; i > index; i--) {
				tempNode = tempNode.previous;
			}
		}

		return tempNode.value;
	}

	/**
	 * Adds the given object into this collection at the end of collection;
	 * newly added element becomes the element at the biggest index. Complexity
	 * is O(1).
	 * @param value Object to be added
	 */
	public void add(Object value) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Cannot add null object into collection");
		}

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (this.isEmpty()) {
			first = newNode;
			last = newNode;
		} else {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		}

		size++;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * linked-list. Elements starting from this position are shifted one
	 * position. The legal positions are 0 to size. If given value is null,
	 * method throws IllegalArgumentException. If position is invalid, method
	 * throws IndexOutOfBoundsException
	 * @param value Object to be inserted
	 * @param position Position where value should be inserted
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

		if (position == size) {
			this.add(value);
			return;
		}

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (position == 0) {
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
			size++;
			return;
		}

		ListNode tempNode = first;
		ListNode nextNode;

		for (int i = 0; i < position - 1; i++) {
			tempNode = tempNode.next;
		}

		nextNode = tempNode.next;
		tempNode.next = newNode;
		newNode.previous = tempNode;
		newNode.next = nextNode;
		nextNode.previous = newNode;
		size++;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index, method
	 * throws IndexOutOfBoundsException.
	 * @param index Index of object to be removed.
	 */
	public void remove(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
					"Index of requested element is out of bounds");
		}

		if (size == 1) {
			clear();
		}

		if (index == 0) {
			first = first.next;
			first.previous = null;
			size--;
			return;
		}

		if (index == size - 1) {
			last = last.previous;
			last.next = null;
			size--;
			return;
		}

		ListNode tempNode = first;

		for (int i = 0; i < index - 1; i++) {
			tempNode = tempNode.next;
		}

		tempNode.next = tempNode.next.next;
		tempNode.next.previous = tempNode;
		size--;
	}

	/**
	 * Removes given object from collection. If it existed, returns
	 * true or else false.
	 * @param value Object to be removed
	 * @return Status of removal
	 */
	public boolean remove(Object value) {
		if (value != null) {
			int index = this.indexOf(value);

			if (index != -1) {
				this.remove(index);
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
		ListNode tempNode = first;
		int index = 0;

		while (tempNode != null) {
			array[index] = tempNode.value;
			index++;
			tempNode = tempNode.next;
		}

		return array;
	}

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * Order of elements to be processed is from lower to higher indices.
	 * @param processor Processor of objects
	 */
	public void forEach(Processor processor) {
		ListNode tempNode = first;

		while (tempNode != null) {
			processor.process(tempNode.value);
			tempNode = tempNode.next;
		}
	}

	/**
	 * Clears list by dereferencing first and last node and setting size to 0.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
}
