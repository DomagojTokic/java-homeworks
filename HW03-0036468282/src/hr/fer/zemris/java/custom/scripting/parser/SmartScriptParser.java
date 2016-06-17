package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Class for parsing elements generated from SmartScriptLexer.
 * @author Domagoj
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer for generating text elements
	 */
	SmartScriptLexer lexer;

	/**
	 * Document node
	 */
	DocumentNode document;

	/**
	 * Object stack for parse method
	 */
	ObjectStack stack;

	/**
	 * Current element
	 */
	Element currentElement;

	/**
	 * For loop tag
	 */
	private static String forLoop = "FOR";

	/**
	 * End of for loop tag
	 */
	private static String endOfForLoop = "END";

	/**
	 * Echo tag
	 */
	private static String echo = "=";

	/**
	 * Returns document node
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}

	/**
	 * Creates instance of SmartScriptParser and parses input.
	 * @param docBody Document body which will be parsed
	 */
	public SmartScriptParser(String docBody) {
		lexer = new SmartScriptLexer(docBody);
		document = new DocumentNode();
		stack = new ObjectStack();
		stack.push(document);
		this.parse();
	}

	/**
	 * Method for parsing given document body 
	 */
	private void parse() {
		try {
			currentElement = lexer.nextElement();

			while (currentElement.getType() != TokenType.EOF) {
				currentElement = lexer.getElement();
				if (currentElement.getType() == TokenType.TEXT) {
					TextNode node = new TextNode(currentElement);
					((Node) stack.peek()).addChildNode(node);
					currentElement = lexer.nextElement();
					continue;

				}
				if (currentElement.getType() == TokenType.TAG_NAME) {
					if (currentElement.asText().toUpperCase().equals(forLoop)) {
						ForLoopNode node = generateForLoopNode();
						((Node) stack.peek()).addChildNode(node);
						stack.push(node);
						continue;

					} else if (currentElement.asText().toUpperCase()
							.equals(endOfForLoop)) {
						if (stack.peek() instanceof ForLoopNode) {
							stack.pop();
							currentElement = lexer.nextElement();
						} else {
							throw new SmartScriptParserException();
						}
						continue;

					} else if (currentElement.asText().equals(echo)) {
						EchoNode node = generateEchoNode();
						((Node) stack.peek()).addChildNode(node);
						continue;
					} else {
						throw new SmartScriptParserException("Unable to parse");
					}
				}

			}
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException("Unable to parse");
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Unable to parse");
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Unable to parse");
		}
	}

	/**
	 * Checks if current element could be for loop node member
	 * @param element Element to be checked
	 * @return true if elements could be for loop node member, else false
	 */
	private boolean isForLoopMember(Element element) {
		TokenType type = element.getType();
		return type == TokenType.NUMBER || type == TokenType.VARIABLE
				|| type == TokenType.STRING;
	}

	/**
	 * Checks if current element could be echo node member
	 * @param element Element to be checked
	 * @return true if elements could be echo node member, else false
	 */
	private boolean isEchoNodeMember(Element element) {
		TokenType type = element.getType();
		return type == TokenType.NUMBER || type == TokenType.STRING
				|| type == TokenType.VARIABLE
				|| element instanceof ElementOperator
				|| element instanceof ElementFunction;
	}

	/**
	 * Generates for loop node.
	 * @return new instance of ForLoopNode
	 */
	private ForLoopNode generateForLoopNode() {
		ElementVariable variable = null;
		Element startExpression = null;
		Element endExpression = null;
		Element stepExpression = null;

		// First must be variable
		currentElement = lexer.nextElement();
		if (currentElement.getType() == TokenType.VARIABLE) {
			variable = (ElementVariable) currentElement;
		} else {
			throw new SmartScriptParserException();
		}
		// Start expression
		currentElement = lexer.nextElement();
		if (isForLoopMember(currentElement)) {
			startExpression = currentElement;
		} else {
			throw new SmartScriptParserException();
		}
		// End expression
		currentElement = lexer.nextElement();
		if (isForLoopMember(currentElement)) {
			endExpression = currentElement;
		} else {
			throw new SmartScriptParserException();
		}
		// Optional step expression (if it doesn't exist, it must be element
		// that isn't echo member)
		currentElement = lexer.nextElement();
		if (isForLoopMember(currentElement)) {
			stepExpression = currentElement;
			currentElement = lexer.nextElement();
		} else if (isEchoNodeMember(currentElement)) {
			throw new SmartScriptParserException();
		}
		// After (optional) step element must come something that isn't echo tag
		// member
		if (isEchoNodeMember(currentElement)) {
			throw new SmartScriptParserException();
		}
		try {
			return new ForLoopNode(variable, startExpression, endExpression,
					stepExpression);
		} catch (IllegalArgumentException e) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Generates echo node.
	 * @return new instance of EchoNode
	 */
	private EchoNode generateEchoNode() {
		ArrayIndexedCollection storage = new ArrayIndexedCollection();
		Element[] elements;
		currentElement = lexer.nextElement();

		while (isEchoNodeMember(currentElement)) {
			storage.add(currentElement);
			currentElement = lexer.nextElement();
		}

		elements = new Element[storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			elements[i] = (Element) storage.get(i);
		}

		return new EchoNode(elements);
	}
}
