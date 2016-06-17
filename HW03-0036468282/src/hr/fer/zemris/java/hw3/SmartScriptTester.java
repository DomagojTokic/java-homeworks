package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class for testing SmartScriptParser class. It parses input text (code) and
 * prints out parsed version of text Input for parser is provided by writing
 * path to text file (content to be parsed) as command line argument.
 * @author Domagoj Tokić
 *
 */
public class SmartScriptTester {

	/**
	 * Entrance into program
	 * @param args
	 */
	public static void main(String[] args) {
		String filepath = args[0];
		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8);
			SmartScriptParser parser = null;
			parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = createOriginalDocumentBody(document);
			SmartScriptParser parser2 = new SmartScriptParser(
					originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();
			String originalDocumentBody2 = createOriginalDocumentBody(document2);
			System.out.println(originalDocumentBody2); // should write something
														// like original
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (IOException e1) {
			System.err.println("Unable to read given filepath!");
		} catch (Exception e) {
			System.out
					.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
	}

	/**
	 * Creates String which represents original document body
	 * @param document Document node
	 * @return Parsed text of input (document body)
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		String docBody = "";

		return appendChildrenText(document, docBody);
	}

	/**
	 * Appends children node texts in order to form original document body.
	 * @param node Next node (text of node) to be appended to original document
	 *            body
	 * @param docBody String which is used as storage for text
	 * @return Final document body
	 */
	private static String appendChildrenText(Node node, String docBody) {
		for (int i = 0; i < node.numberOfChildren(); i++) {

			if (node.getChild(i) instanceof TextNode) {
				docBody += node.getChild(i).asText();
			} else {
				docBody += SmartScriptLexer.startOfTag
						+ node.getChild(i).asText()
						+ SmartScriptLexer.endOfTag;
			}

			docBody = appendChildrenText(node.getChild(i), docBody);
		}
		if (node instanceof ForLoopNode) {
			docBody += SmartScriptLexer.startOfTag + "END"
					+ SmartScriptLexer.endOfTag;
		}
		return docBody;
	}
}
