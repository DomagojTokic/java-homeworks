package hr.fer.zemris.java.cstr;

import java.util.Objects;

/**
 * String class with same internal functionalities as original was before Java 7
 * update 6. It's internal structure consists of immutable char array, offset
 * and length. More instances of CString can point to the same char array. New
 * array is allocated only if new string cannot be represented by current char
 * array (it does not contain it).
 * 
 * @author Domagoj Tokić
 *
 */
public class CString {

	/** Array for string data. Multiple instances may use the one data array. */
	private char[] data;

	/** Offset of string data in data array */
	private int offset;

	/** String length */
	private int length;

	/**
	 * Creates instance of {@link CString}
	 * 
	 * @param data Data array
	 * @param offset Offset of string data in data array
	 * @param length Length of string
	 */
	public CString(char[] data, int offset, int length) {
		if (data == null) {
			throw new NullPointerException("Data array can't be null");
		}
		if (offset < 0 || length < 0) {
			throw new IndexOutOfBoundsException(
					"Offset and length cannot be negative");
		}
		if (data.length < length + offset) {
			throw new IndexOutOfBoundsException(
					"Length together with offset of string can't be greater than length of data array");
		}
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Creates instance of {@link CString} with default offset and with length
	 * of data array.
	 * 
	 * @param data
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}

	/**
	 * Creates new instance of {@link CString} if data array length isn't the
	 * same as length of string.
	 * 
	 * @param original Original instance of {@link CString}
	 */
	public CString(CString original) {
		Objects.requireNonNull(original, "Original instance of CString can't be null");

		if (original.length != original.data.length) {
			char[] array = new char[original.length];
			System.arraycopy(original.data, original.offset, array, 0,
					original.length);

			data = array;
		} else {
			data = original.data;
		}
		offset = 0;
		length = original.length;
	}

	/**
	 * Creates instance of {@link CString} from instance of class String.
	 * 
	 * @param string String for conversion into CString
	 * @return new instance of CString with data of String instance
	 */
	public static CString fromString(String string) {
		Objects.requireNonNull(string, "Argument string can't be null");
		return new CString(string.toCharArray());
	}

	/**
	 * Returns length of string
	 * 
	 * @return length of string
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns character at given index
	 * 
	 * @param index Index of requested character
	 * @return character at given index
	 * @throws IndexOutOfBoundsException if index isn't in scope of 0 to
	 *             length-1
	 */
	public char charAt(int index) {
		if (index >= 0 && index < length) {
			return data[index + offset];
		}
		throw new IndexOutOfBoundsException("Given index is out of scope");
	}

	/**
	 * Returns string in char array representation
	 * 
	 * @return string in char array representation
	 */
	public char[] toCharArray() {
		char[] array = new char[length];
		System.arraycopy(data, offset, array, 0, length);
		return array;
	}

	/**
	 * Returns class String instance of this CString object
	 */
	public String toString() {
		String string = "";
		for (int i = offset; i < offset + length; i++) {
			string += data[i];
		}
		return string;
	}

	/**
	 * Returns index of first occurrence of given char in string. If it doesn't
	 * exist, returns -1
	 * 
	 * @param c Character for index search.
	 * @return index of first occurrence of given char in string, if it doesn't
	 *         exist, returns -1
	 */
	public int indexOf(char c) {
		for (int i = 0; i < length; i++) {
			if (charAt(i) == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns true if string starts with given string, else false
	 * 
	 * @param s String for testing start of string
	 * @return true if string starts with given string, else false
	 * @throws IllegalArgumentException if given string length is greater than
	 *             current.
	 */
	public boolean startsWith(CString s) {
		if (this.length < s.length) {
			throw new IllegalArgumentException(
					"String for comparing mustn't be longer than tested string");
		}
		for (int i = 0; i < s.length; i++) {
			if (s.charAt(i) != this.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if string ends with given string, else false
	 * 
	 * @param s String for testing end of string
	 * @return true if string ends with given string, else false
	 * @throws IllegalArgumentException if given string length is greater than
	 *             current.
	 */
	public boolean endsWith(CString s) {
		if (this.length < s.length) {
			throw new IllegalArgumentException(
					"String for comparing mustn't be longer than tested string");
		}
		for (int i = s.length - 1; i >= 0; i--) {
			if (s.charAt(i) != this.charAt(this.length - s.length + i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Test string if it contains given string.
	 * 
	 * @param string String for testing current
	 * @return True if current contains given, else false
	 * @throws NullPointerException if given string is null
	 */
	public boolean contains(CString string) {
		Objects.requireNonNull(string, "String cannot be tested if it contains null");
		if (string.length() == 0) {
			return true;
		}
		for (int i = 0; i <= data.length - string.length; i++) {
			for (int j = 0; j < string.length; j++) {
				if (data[i + j] != string.charAt(j)) {
					break;
				}
				if (j == string.length - 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Creates new string from startIndex to endIndex-1 of current string.
	 * 
	 * @param startIndex Starting index of new string
	 * @param endIndex Ending index of new string
	 * @return new instance of CString
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex >= 0 && endIndex >= startIndex && endIndex <= length) {
			return new CString(data, offset + startIndex,
					endIndex - startIndex);
		}
		throw new IndexOutOfBoundsException();
	}

	/**
	 * Creates new instance of {@link CString} from first n characters of string
	 * 
	 * @param n Number of character at beginning of string for new string
	 * @return new instance of CString
	 * @throws IndexOutOfBoundsException if n isn't in scope of 0 and length
	 */
	public CString left(int n) {
		if (n >= 0 && n <= length) {
			return new CString(data, offset, n);
		}
		throw new IndexOutOfBoundsException(Integer.toString(n));
	}

	/**
	 * Creates new instance of {@link CString} from last n characters of string
	 * 
	 * @param n Number of character at beginning of string for new string
	 * @return new instance of CString
	 * @throws IndexOutOfBoundsException if n isn't in scope of 0 and length
	 */
	public CString right(int n) {
		if (n >= 0 && n <= length) {
			return new CString(data, offset + length - n, n);
		}
		throw new IndexOutOfBoundsException(Integer.toString(n));
	}

	/**
	 * Returns new instance of {@link CString} with given string added to
	 * it's end. If length of given string is 0, it returns current instance.
	 * 
	 * @param s String for adding to current
	 * @return new instance of CString
	 */
	public CString add(CString s) {
		if (s.length == 0) {
			return this;
		}

		char[] array = new char[this.length + s.length];
		System.arraycopy(this.data, offset, array, 0, length);
		for (int i = this.length; i < this.length + s.length; i++) {
			array[i] = s.charAt(i - this.length);
		}

		return new CString(array);
	}

	/**
	 * Replicates given string and assigns it to new data array. Data array only
	 * contains data of current string.
	 * 
	 * @return new instance of CString
	 */
	private CString replicate() {
		char[] array = new char[this.length];
		System.arraycopy(this.data, this.offset, array, 0, this.length);
		return new CString(array, this.offset, this.length);
	}

	/**
	 * Replaces all occurrences old characters with new characters. Returns new
	 * instance of CString if old isn't the same as new character.
	 * 
	 * @param oldChar Old character
	 * @param newChar New Character
	 * @return new instance of CString
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (oldChar == newChar) {
			return this;
		}
		CString replaced = this.replicate();
		for (int i = 0; i < replaced.data.length; i++) {
			if (replaced.data[i] == oldChar) {
				replaced.data[i] = newChar;
			}
		}
		return replaced;
	}

	/**
	 * Replaces all occurrences old string with new string. Returns new
	 * instance of CString if old isn't the same as new string. If old string is
	 * empty, method inserts new string between every character, including
	 * beginning and the end of string.
	 * 
	 * @param oldStr Old string
	 * @param newStr New string
	 * @return new instance of CString
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		Objects.requireNonNull(oldStr, "String for replacement can't be null.");
		Objects.requireNonNull(newStr, "Can't replace with null object");
		if (oldStr.length == 0) {
			return insertStringBetweenEveryChar(newStr);
		}
		if (oldStr.equals(newStr)) {
			return this;
		}

		int newNumberOfChars = this.length + getNumberOfOccurences(oldStr)
				* (newStr.length - oldStr.length);
		char[] replaced = new char[newNumberOfChars];
		CString original = this.replicate();

		int originalStrIterator = 0;
		int replacedStrIterator = 0;

		while (replacedStrIterator < replaced.length) {
			if (originalStrIterator <= this.length - oldStr.length
					&& original.startsWith(oldStr)) {
				for (int i = 0; i < newStr.length; i++) {
					replaced[replacedStrIterator + i] = newStr.charAt(i);
				}
				originalStrIterator += oldStr.length;
				replacedStrIterator += newStr.length;
				original = right(original.length - oldStr.length);
			} else {
				replaced[replacedStrIterator] = original.data[originalStrIterator];
				originalStrIterator++;
				replacedStrIterator++;
				original = right(original.length - 1);
			}
		}

		return new CString(replaced);
	}

	/**
	 * Inserts new string between every character, including
	 * beginning and the end of string.
	 * 
	 * @param s String for insertion
	 * @return new instance of CString
	 */
	private CString insertStringBetweenEveryChar(CString s) {
		if (s.length() == 0) {
			return this;
		}

		int newNumberOfChars = this.length + (this.length + 1) * s.length;
		char[] inserted = new char[newNumberOfChars];

		for (int i = 0; i <= this.length(); i++) {
			for (int j = 0; j <= s.length(); j++) {
				if (j != s.length()) {
					inserted[i * s.length() + i + j] = s.charAt(j);
				} else if (i != this.length()) {
					inserted[(i + 1) * s.length() + i] = this.charAt(i);
				}
			}
		}

		return new CString(inserted);
	}

	/**
	 * Returns number of occurrences of given string in current.
	 * 
	 * @param s String for counting occurrences
	 * @return Number of occurrences
	 */
	private int getNumberOfOccurences(CString s) {
		CString original = this.replicate();
		int numberOfOccurrences = 0;

		while (original.length >= s.length) {
			if (original.startsWith(s)) {
				original = original.right(original.length - s.length);
				numberOfOccurrences++;
			} else {
				original = original.right(original.length - 1);
			}
		}
		return numberOfOccurrences;
	}

}
