package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Node for storing text (outside tags)
 * 
 * @author Domagoj Tokić
 */
public class TextNode extends Node {

	/**
	 * Text of TextNode
	 */
	private String text;

	/**
	 * Creates instance of TextNode
	 * 
	 * @param text Text of TextNode
	 */
	public TextNode(Element text) {
		this.text = ((ElementString) text).getValue();
	}
	
	
	/**
	 * Returns TextNodes text.
	 * @return TextNodes text.
	 */
	public String getText() {
		return text;
	}


	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
