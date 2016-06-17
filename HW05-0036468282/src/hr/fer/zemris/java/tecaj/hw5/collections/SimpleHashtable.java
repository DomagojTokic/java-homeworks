package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class defining hash table (also known as Map) parameterized with type K and
 * V. K is the type of key and V is the type of value. The rule of hash table is
 * that there cannot be tow entries with the same key. If user puts entry with
 * the same key as one already in hash table, previous value will be
 * overwritten. Key cannot be null value, but Value can. Class also provides
 * iterator for iteration over hash table entries.
 * 
 * @author Domagoj Tokić
 *
 * @param <K> Type of hash table key
 * @param <V> Type of hash table value
 */
public class SimpleHashtable<K, V>
		implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Hash table entry class. It's structured as one directional list so hash
	 * table can work with overflow (multiple values stored into the same slot).
	 * 
	 * @author Domagoj Tokić
	 *
	 * @param <K> Type of table entry key
	 * @param <V> Type of table entry value
	 */
	public static class TableEntry<K, V> {

		/** Entry key */
		private K key;

		/** Entry value */
		private V value;

		/** Next entry in same slot as current */
		private TableEntry<K, V> next;

		/**
		 * Creates instance of TableEntry
		 * 
		 * @param key Entry key
		 * @param value Entry value
		 * @param next Next entry in same slot as current
		 */
		private TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns entry value
		 * 
		 * @return entry value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets entry value
		 * 
		 * @param value Entry value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns entry key
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns string representation of entry in format "key=value"
		 */
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}

	/** Array of hash table entries */
	TableEntry<K, V>[] tableEntries;

	/** Number of stored entries */
	int size;

	/** Number of filled slots in tableEntries */
	int filledSlots;

	/**
	 * Counter of modifications of hash table structure (doesn't count changes
	 * on already existing entries)
	 */
	int modificationCount;

	/** Default size of array for entries */
	private static final int DEAFULT_ENTRY_NUM = 16;

	/**
	 * Percentage of filled map capacity after which it's capacity will double
	 */
	private static final double FILLED_TRESHOLD = 0.75;

	/**
	 * Creates instance of {@link SimpleHashtable} with given size of
	 * tableEntries array. If given value isn't power of number two, constructor
	 * uses next greater value which is power of two.
	 * 
	 * @param numberOfEntries
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int numberOfEntries) {
		double power = Math.ceil(Math.log10(numberOfEntries) / Math.log10(2));
		int realNumberOfEntries = (int) Math.pow(2, power);
		tableEntries = (TableEntry<K, V>[]) new TableEntry[realNumberOfEntries];
	}

	/**
	 * Default constructor with default size of array for entries.
	 */
	public SimpleHashtable() {
		this(DEAFULT_ENTRY_NUM);
	}

	/**
	 * Returns index of slot in entries table for given key.
	 * 
	 * @param key Entry key
	 * @return index of slot for given key.
	 */
	private int getSlot(Object key) {
		return Math.abs(key.hashCode()) % tableEntries.length;
	}

	/**
	 * Returns table entry for given key.
	 * 
	 * @param key Entry key
	 * @return table entry containing given key. If entry doesn't exist, returns
	 *         value null.
	 */
	private TableEntry<K, V> getTableEntry(Object key) {
		if (key == null) {
			return null;
		}
		TableEntry<K, V> temp = tableEntries[getSlot(key)];
		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return temp;
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * Returns last entry in slot which fits given key. If slot is empty,
	 * returns null.
	 * 
	 * @param key Entry key
	 * @return Last entry in slot or null if slot is empty.
	 */
	private TableEntry<K, V> getLastEntryInSlot(K key) {
		TableEntry<K, V> temp = tableEntries[getSlot(key)];
		while (temp != null && temp.next != null) {
			temp = temp.next;
		}
		return temp;
	}

	/**
	 * Doubles the capacity of table of entries for current instance of
	 * {@link SimpleHashtable}.
	 */
	@SuppressWarnings("unchecked")
	private void doubleEntryTableCapacity() {
		TableEntry<K, V>[] oldTableEntries = tableEntries;

		tableEntries = (TableEntry<K, V>[]) new TableEntry[tableEntries.length
				* 2];
		size = 0;
		filledSlots = 0;
		modificationCount = 0;

		TableEntry<K, V> temp;
		for (int i = 0; i < oldTableEntries.length; i++) {
			temp = oldTableEntries[i];
			while (temp != null) {
				put(temp.getKey(), temp.getValue());
				temp = temp.next;
			}
		}
	}

	/**
	 * Puts new entry into hash table.
	 * 
	 * @param key Entry key
	 * @param value Entry value
	 * @throws IllegalArgumentException if given key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException(
					"Unable to put null as key into hash table.");
		}

		if (containsKey(key)) {
			getTableEntry(key).setValue(value);
			return;
		}

		TableEntry<K, V> entry = new TableEntry<>(key, value, null);
		size++;
		modificationCount++;

		TableEntry<K, V> lastEntry = getLastEntryInSlot(key);
		if (lastEntry != null) {
			lastEntry.next = entry;
			return;
		}

		tableEntries[getSlot(key)] = entry;
		filledSlots++;
		if (filledSlots >= FILLED_TRESHOLD * tableEntries.length) {
			doubleEntryTableCapacity();
		}
	}

	/**
	 * Returns value of entry with given key. If entry with given key doesn't
	 * exits, returns null.
	 * 
	 * @param key Entry key
	 * @return Value of entry with given key or value null of entry doesn't
	 *         exist.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		TableEntry<K, V> entry = getTableEntry(key);

		if (entry != null) {
			return entry.getValue();
		}
		return null;
	}

	/**
	 * Returns number of entries in hash table.
	 * 
	 * @return number of entries in hash table
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if entry with given key exists, else false.
	 * 
	 * @param key Entry key
	 * @return true if entry with given key exists, else false.
	 */
	public boolean containsKey(Object key) {
		if (getTableEntry(key) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if entry with given value exists, else false.
	 * 
	 * @param value Entry value
	 * @return true if entry with given value exists, else false.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> temp;
		for (int i = 0; i < tableEntries.length; i++) {
			temp = tableEntries[i];
			while (temp != null) {
				if (value != null) {
					if (temp.getValue() != null && temp.getValue().equals(value)) {
						return true;
					}
				} else {
					if (temp.getValue() == value) {
						return true;
					}
				}
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * Removes entry with given key (if it exists).
	 * 
	 * @param key Entry key
	 */
	public void remove(Object key) {
		TableEntry<K, V> toBeRemoved = getTableEntry(key);

		if (toBeRemoved != null) {
			size--;
			modificationCount++;

			int slot = getSlot(key);
			TableEntry<K, V> slotEntry = tableEntries[slot];

			// If table entry is at beginning of slot
			if (slotEntry == toBeRemoved) {
				tableEntries[slot] = toBeRemoved.next;
				return;
			}

			while (slotEntry.next != toBeRemoved) {
				slotEntry = slotEntry.next;
			}
			slotEntry.next = slotEntry.next.next;

		}
	}

	/**
	 * Clears whole hash table and sets internal variables to initial values.
	 */
	public void clear() {
		for (int i = 0; i < tableEntries.length; i++) {
			tableEntries[i] = null;
		}
		size = 0;
		modificationCount = 0;
		filledSlots = 0;
	}

	/**
	 * Checks if hash table is empty.
	 * 
	 * @return true if hash table is empty, else false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns String representation of hash table.
	 * 
	 * @return String representation of hash table
	 */
	public String toString() {
		String string = "";
		TableEntry<K, V> temp;
		for (int i = 0; i < tableEntries.length; i++) {
			string += "[ ";
			temp = tableEntries[i];
			while (temp != null) {
				string += temp.toString();
				temp = temp.next;
				if (temp != null) {
					string += ", ";
				} else {
					break;
				}
			}
			string += " ]\n";
		}
		return string;
	}

	/**
	 * Returns iterator for iterating over hash table entries.
	 * 
	 * @return iterator for iterating over hash table entries
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();
	}

	/**
	 * Iterator for iterating over hash table entries. Provides method for
	 * removing entries. Structural changes on hash table outside iterator,
	 * after iterator creation, makes iterator unusable - throws
	 * ConcurrentModificationException on call of all its methods.
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private class SimpleHashtableIterator
			implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Number of modifications made before iterator creation plus
		 * modifications made by iterator.
		 */
		int itModificationCount;

		/**
		 * Last slot returned by iterator.
		 */
		int currentSlot;

		/**
		 * Number of returned elements
		 */
		int returnedElements;

		/**
		 * Number of entries before creation of iterator.
		 */
		int initialSize;

		/**
		 * Flag for checking if iterator is allowed to make operations over
		 * current element
		 */
		boolean validState;

		/**
		 * Last hash table entry returned by method next()
		 */
		TableEntry<K, V> current;

		/**
		 * Creates new instance of {@link SimpleHashtableIterator}
		 */
		public SimpleHashtableIterator() {
			super();
			itModificationCount = modificationCount;
			initialSize = size;
			currentSlot = 0;
			validState = false;
		}

		/**
		 * Checks if iterator has more entries to return from hash table.
		 * 
		 * @return true if iterator has more entries to return from hash table,
		 *         else false.
		 * @throws ConcurrentModificationException if structural modifications
		 *             were made on hash table outside iterator.
		 */
		@Override
		public boolean hasNext() {
			if (itModificationCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Can't iterate after modification");
			}
			return returnedElements != initialSize;
		}

		/**
		 * Returns next entry of hash table (after current).
		 * 
		 * @return next entry of hash table (after current)
		 * @throws ConcurrentModificationException if structural modifications
		 *             were made on hash table outside iterator.
		 * @throws NoSuchElementException if requested element is after last.
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public SimpleHashtable.TableEntry next() {
			if (itModificationCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Can't iterate after modification");
			}

			if (!hasNext()) {
				throw new NoSuchElementException("Next element does not exist");
			}

			validState = true;

			// If next element is first element
			if (current == null) {
				current = tableEntries[0];
				if (current != null) {
					returnedElements++;
					return current;
				}
			}

			while (true) {
				if (current != null && current.next != null) {
					current = current.next;
					returnedElements++;
					return current;
				} else {
					currentSlot++;
					current = tableEntries[currentSlot];
					if (current != null) {
						returnedElements++;
						return current;
					}
				}
			}
		}

		/**
		 * Removes last entry returned by next() method.
		 * 
		 * @throws ConcurrentModificationException if structural modifications
		 *             were made on hash table outside iterator.
		 * @throws IllegalStateException if iterator already made remove
		 *             operation on current entry
		 */
		public void remove() {
			if (itModificationCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Unable to remove after outer modifications");
			}
			if (!validState) {
				throw new IllegalStateException(
						"Can't remove before requesting next element");
			}

			itModificationCount++;
			SimpleHashtable.this.remove(current.getKey());
			validState = false;
		}
	}

}
