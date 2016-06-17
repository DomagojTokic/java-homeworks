package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node for storing echo function
 * @author Domagoj Tokić
 */
public class EchoNode extends Node {

	/**
	 * Elements of echo function
	 */
	Element[] elements;
	
	/**
	 * Creates an instance of EchoNode
	 * @param elements
	 */
	public EchoNode(Element[] elements){
		this.elements = elements;
	}

	/**
	 * Returns echo node elements
	 * @return echo node elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		String text = "=";
		
		for(int i = 0; i < elements.length; i++){
			text += " " + elements[i].asText();
		}
		
		return text;
	}
}
