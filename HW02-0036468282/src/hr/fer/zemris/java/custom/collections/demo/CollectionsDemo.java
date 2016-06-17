package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

import java.util.Arrays;

/**
 * Demo class for testing custom collections.
 * @author Domagoj Tokić
 *
 */
public class CollectionsDemo {

	/**
	 * Entrance into demo class
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		ArrayIndexedCollection test = new ArrayIndexedCollection(2);
		test.add("New York");
		test.add("San Francisco");
		test.insert("New York", 0);
		
		ArrayIndexedCollection test2 = new ArrayIndexedCollection(test);
		
		
		System.out.println(Arrays.toString(test2.toArray())); // writes [New York, New York, San Francisco]
		ArrayIndexedCollection col1 = new ArrayIndexedCollection(2);
		col1.add(new Integer(20));
		col1.add("New York");
		col1.add("San Francisco"); // here the internal array is reallocated to
									// 4
		System.out.println(col1.size()); // writes 3
		System.out.println(col1.contains("New York")); // writes: true
		col1.remove(1); // removes "New York"; shifts "San Francisco" to
						// position 1
		System.out.println(col1.get(1)); // writes: "San Francisco"
		System.out.println(col1.size()); // writes: 2
		col1.add("Los Angeles");

		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);

		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;

		System.out.println("col1 elements:");
		col1.forEach(new P()); // writes 20, San Francisco and Los Angeles
								// separated by newlines

		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col1.toArray())); // writes [20, San
																// Francisco,
																// Los Angeles]

		System.out.println("col2 elements:");
		col2.forEach(new P()); // writes 20, San Francisco and Los Angeles
								// separated by newlines

		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray())); // writes [20, San
																// Francisco,
																// Los Angeles]

		System.out.println(col1.contains(col2.get(1))); // true
		System.out.println(col2.contains(col1.get(1))); // true

		col1.remove(new Integer(20)); // removes 20 from collection (at position
										// 0).
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col1.toArray())); // writes [San
																// Francisco,
																// Los Angeles]

		col2.remove("San Francisco");
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray())); // writes [20, Los
																// Angeles]

		col2.remove(0);
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray())); // writes [Los
																// Angeles]

	}

}
