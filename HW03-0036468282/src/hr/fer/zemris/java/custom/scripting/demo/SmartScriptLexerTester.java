package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;


/**
 * Test class for SmartScriptLexer class
 * @author Domagoj Tokić
 *
 */
public class SmartScriptLexerTester {

	/**
	 * Entrance into test class
	 * @param args No arguments
	 */
	public static void main(String[] args) {

		SmartScriptLexer lexer = new SmartScriptLexer("");
		Element element = lexer.nextElement();
		if (element.getType() == TokenType.EOF) {
			System.out.println("State is eof");
		} else {
			System.out.println("Fail!");
		}

		lexer = new SmartScriptLexer(
				"This is text {$   = i \"abcd\n  \\\" efg\" -84 85.56$} Second text \\{$");
		element = lexer.nextElement();
		if (element.getType() == TokenType.TEXT) {
			System.out.println("State is text");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.TAG_NAME) {
			System.out.println("State is tag name");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.VARIABLE) {
			System.out.println("State is variable");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.STRING) {
			System.out.println("State is string");
			System.out.println("Value of string is\n" + element.asText());
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element instanceof ElementConstantInteger) {
			System.out.println("Number is integer");
			System.out.println("Value of integer is\n" + element.asText());
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element instanceof ElementConstantDouble) {
			System.out.println("Number is double");
			System.out.println("Value of double is\n" + element.asText());
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.TEXT) {
			System.out.println("text is: " + element.asText());
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.EOF) {
			System.out.println("State is eof");
		} else {
			System.out.println("Fail!");
		}

		System.out.println();
		System.out.println();

		lexer = new SmartScriptLexer("{$   For i    \"abc\" \"cde\" $} Text");
		element = lexer.nextElement();
		if (element.getType() == TokenType.TAG_NAME) {
			System.out.println("State is tag name");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.VARIABLE) {
			System.out.println("State is variable");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.STRING) {
			System.out.println("State is string");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.STRING) {
			System.out.println("State is string");
		} else {
			System.out.println("Fail!");
		}
		element = lexer.nextElement();
		if (element.getType() == TokenType.TEXT) {
			System.out.println("State is text");
		} else {
			System.out.println("Fail!");
		}
	}

}
