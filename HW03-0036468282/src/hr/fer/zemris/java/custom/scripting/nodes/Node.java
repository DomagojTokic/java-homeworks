package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class representing one whole (function or text) of code. Made for
 * inheritance.
 * @author Domagoj Tokić
 *
 */
public class Node {

	/**
	 * Children of node
	 */
	ArrayIndexedCollection children;

	/**
	 * Adds child node to node
	 * @param child Node to be added as child.
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	/**
	 * Returns number of children.
	 * @return number of children.
	 */
	public int numberOfChildren() {
		if (children != null) {
			return children.size();
		}
		return 0;
	}

	/**
	 * Returns child node at given index.
	 * @param index Index of child node to be retrieved.
	 * @return child node at given index.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}

	/**
	 * Returns string representation of node.
	 * @return string representation of node.
	 */
	public String asText() {
		return "";
	}

}
