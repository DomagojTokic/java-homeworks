package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class for recognizing and generating elements of programming code stored in
 * one string. Programming code is separated to text and tag elements. Start of
 * tag is "{$", end is "$}". First string after start of tag is tag name. After
 * tag names, lexer generates variables (first is alphabetic and other
 * characters can be either alphabetic, numbers or '_'), numbers, strings (uses
 * '"' for start and finish and supports escaping with character '\' - escapes
 * '"', '\' and recognizes special characters \n, \r, \t), functions (start with
 * '@') and operations (+-/*%). Last element generated from input data signifies
 * end of file.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class SmartScriptLexer {

	/**
	 * State of SmartScriptLexer
	 */
	private SmartScriptLexerState state;

	/**
	 * Data from which SmartScriptLexer generates elements
	 */
	private char data[];

	/**
	 * Element generated from previously used nextElement() method
	 */
	private Element element;

	/**
	 * Index of next character which will be analyzed by SmartScriptLexer
	 */
	private int currentIndex;

	/**
	 * Tells if SmartScriptLexer is at the start of tag.
	 */
	private boolean isStartofTagFlag;

	/**
	 * Start of tag
	 */
	public static final String startOfTag = "{$";

	/**
	 * End of tag
	 */
	public static final String endOfTag = "$}";

	/**
	 * Character representing start of function.
	 */
	public static final char functionSymbol = '@';

	/**
	 * Character representing start of string inside tags.
	 */
	public static final char stringSymbol= '"';

	/**
	 * Creates instance of SmartScriptLexer.
	 * @param text Data from which SmartScriptLexer will generate elements
	 * @throws IllegalArgumentException if given text is null
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text can't be null!");
		}
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
		data = text.toCharArray();
	}

	/**
	 * Sets state of SmartScriptLexer
	 * @param state State of SmartScriptLexer
	 * @throws IllegalArgumentException if given SmartScriptLexerState state is
	 *             null
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new IllegalArgumentException(
					"Can't put lexer state on null value!");
		}

		this.state = state;
	}

	/**
	 * Checks if SmartScriptLexer should generate "start of tag" element
	 * @return state if if SmartScriptLexer should generate variable element
	 */
	private boolean isStartOfTag() {
		return currentIndex + startOfTag.length() - 1 < data.length
				&& startOfTag.equals(new String(data, currentIndex, startOfTag
						.length()));
	}

	/**
	 * Checks if SmartScriptLexer should generate "end of tag" element
	 * @return state if if SmartScriptLexer should generate variable element
	 */
	private boolean isEndOfTag() {
		return currentIndex + startOfTag.length() - 1 < data.length
				&& endOfTag.equals(new String(data, currentIndex, endOfTag
						.length()));
	}

	/**
	 * Checks if SmartScriptLexer should generate number element
	 * @return state if SmartScriptLexer should generate number element
	 */
	private boolean isNumber() {
		return Character.isDigit(data[currentIndex])
				|| (currentIndex + 1 < data.length && data[currentIndex] == '-' && Character
						.isDigit(data[currentIndex + 1]));
	}

	/**
	 * Checks if SmartScriptLexerState should generate tag name element
	 * @return state if SmartScriptLexerState should generate tag name element
	 */
	private boolean isTagName() {
		return isStartofTagFlag
				&& (data[currentIndex] == '=' || Character
						.isAlphabetic(data[currentIndex]));
	}

	/**
	 * Checks if SmartScriptLexerState should generate variable element
	 * @return state if SmartScriptLexerState should generate variable element
	 */
	private boolean isVariable() {
		return !isStartofTagFlag && Character.isAlphabetic(data[currentIndex]);
	}

	/**
	 * Checks if SmartScriptLexerState should generate operator element
	 * @return state if SmartScriptLexerState should generate operator element
	 */
	private boolean isOperator() {
		return Character.toString(data[currentIndex]).matches("[-+*/^]")
				&& !isNumber();
	}

	/**
	 * Returns next element in given data.
	 * @return Next element
	 * @throws SmartScriptLexerException if SmartScriptLexerState is unable to
	 *             generate element
	 */
	public Element nextElement() {
		// handling EOF
		if (currentIndex == data.length) {
			if (element == null || element.getType() != TokenType.EOF) {
				element = new Element(TokenType.EOF);
				return element;
			} else {
				throw new SmartScriptLexerException(
						"You can't request element after EOF!");
			}
			// When reading text
		} else if (state == SmartScriptLexerState.TEXT) {
			if (!isStartOfTag()) {
				element = generateText();
				return element;
			} else {
				setState(SmartScriptLexerState.TAG);
				isStartofTagFlag = true;
				currentIndex += startOfTag.length();
				return nextElement();
			}
			// When reading tags
		} else {
			while (data[currentIndex] == ' ') {
				currentIndex++;
			}
			if (isNumber()) {
				element = generateNumber();
				return element;
			} else if (isTagName()) {
				element = generateTag();
				isStartofTagFlag = false;
				return element;
			} else if (isVariable()) {
				element = generateVariable();
				return element;
			} else if (isOperator()) {
				element = new ElementOperator(data[currentIndex]);
				currentIndex++;
				return element;
			} else if (data[currentIndex] == functionSymbol) {
				element = generateFunction();
				return element;
			} else if (data[currentIndex] == stringSymbol) {
				element = generateString();
				return element;
			} else if (isEndOfTag()) {
				currentIndex += endOfTag.length();
				setState(SmartScriptLexerState.TEXT);
				return nextElement();
			}
		}
		throw new SmartScriptLexerException("Unable to generate element!");
	}

	/**
	 * Returns previously generated element.
	 * @return previously generated element.
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Generates Text element (placed outside tags)
	 * @return Text element
	 * @throws SmartScriptLexerException if user tries to escape illegal
	 *             characters.
	 */
	private Element generateText() {
		String text = "";
		while (!(currentIndex == data.length || isStartOfTag())) {
			if (data[currentIndex] == '\\') {
				if (currentIndex != data.length - 1
						&& Character.toString(data[currentIndex + 1]).matches(
								"[\\{]")) {
					text += data[currentIndex + 1];
					currentIndex += 2;
				} else {
					throw new SmartScriptLexerException(
							"Can't escape given character");
				}
			} else {
				text += data[currentIndex];
				currentIndex++;
			}
		}
		element = new ElementString(text, TokenType.TEXT);
		return element;
	}

	/**
	 * Generates number element. If number is whole, returns integer element. If
	 * element has decimal part, returns double element.
	 * @return Integer or double element
	 * @throws SmartScriptLexerException if number has incorrect format.
	 */
	private Element generateNumber() {
		String text = "";
		while (currentIndex != data.length
				&& Character.toString(data[currentIndex]).matches("[-0-9.]")) {
			text += data[currentIndex];
			currentIndex++;
		}
		if (text.contains(".")) {
			try {
				double number = Double.parseDouble(text);
				return new ElementConstantDouble(number);
			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException("Unable to parse " + text
						+ "!");
			}
		} else {
			try {
				int number = Integer.parseInt(text);
				return new ElementConstantInteger(number);
			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException("Unable to parse " + text
						+ "!");
			}
		}
	}

	/**
	 * Generates tag name
	 * @return Tag element
	 */
	private Element generateTag() {
		if (data[currentIndex] == '=') {
			currentIndex++;
			return new ElementVariable("=", TokenType.TAG_NAME);
		} else {
			String text = "";
			// First character is checked in method isTagName()
			while (currentIndex != data.length
					&& (Character.isAlphabetic(data[currentIndex])
							|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
				text += data[currentIndex];
				currentIndex++;
			}
			return new ElementVariable(text, TokenType.TAG_NAME);
		}
	}

	/**
	 * Generates variable element
	 * @return Variable element
	 */
	private Element generateVariable() {
		String text = "";
		// First character is checked in method isVariable()
		while (currentIndex != data.length
				&& Character.toString(data[currentIndex]).matches(
						"[a-zA-Z0-9_]")) {
			text += data[currentIndex];
			currentIndex++;
		}
		return new ElementVariable(text, TokenType.VARIABLE);
	}

	/**
	 * Generates function element
	 * @return Function element
	 */
	private Element generateFunction() {
		currentIndex++;
		String text = "";
		if (currentIndex != data.length
				&& Character.isAlphabetic(data[currentIndex])) {
			text += data[currentIndex];
			currentIndex++;
		} else {
			throw new SmartScriptLexerException("Unable to generate function!");
		}
		while (currentIndex != data.length
				&& Character.toString(data[currentIndex]).matches(
						"[a-zA-Z0-9_]")) {
			text += data[currentIndex];
			currentIndex++;
		}
		return new ElementFunction(text);
	}

	/**
	 * Generates string element
	 * @return String element
	 */
	private Element generateString() {
		currentIndex++;
		String text = "";
		while (data[currentIndex] != stringSymbol) {
			if (data[currentIndex] == '\\') {
				text += data[currentIndex];
				currentIndex++;
				if (currentIndex != data.length
						&& Character.toString(data[currentIndex]).matches(
								"[\\\"nr\\\\t]")) {
					text += data[currentIndex];
					currentIndex++;
				} else {
					throw new SmartScriptLexerException(
							"Unable to escape in string");
				}
			} else {
				text += data[currentIndex];
				currentIndex++;
			}
		}
		currentIndex++;
		text = text.replace("\\n", "\n").replace("\\r", "\r")
				.replace("\\t", "\t");
		return new ElementString(text, TokenType.STRING);
	}
}