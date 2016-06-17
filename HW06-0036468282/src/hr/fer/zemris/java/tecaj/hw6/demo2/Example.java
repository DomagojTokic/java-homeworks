package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Example class of {@link LikeMedian}
 * 
 * @author Domagoj Tokić
 *
 */
public class Example {

	/**
	 * Entrance into example class
	 * 
	 * @param args No argument
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(4));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result);
		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}

		LikeMedian<String> likeMedian2 = new LikeMedian<String>();
		likeMedian2.add("Joe");
		likeMedian2.add("Jane");
		likeMedian2.add("Adam");
		likeMedian2.add("Zed");
		Optional<String> result2 = likeMedian2.get();
		System.out.println(result2); // Writes: Jane
	}

}
