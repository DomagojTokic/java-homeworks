package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Parses Smart Script code and reconstruct it by visiting each child of
 * constructed tree. Root of tree is document node.
 * 
 * @author Domagoj Tokić
 *
 */
public class TreeWriter {

	/**
	 * Class which uses Visitor design pattern to go through all nodes created
	 * by parsing Smart Script code and reconstructs it on standard output.
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText().replace("{$", "\\{$"));
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String text = "";
			text += "FOR " + node.getVariable().asText() + " " + node.getStartExpression().asText()
					+ " " + node.getEndExpression().asText() + " ";
			if (node.getStepExpression() != null) {
				text += node.getStepExpression().asText() + " ";
			}
			System.out.print(SmartScriptLexer.startOfTag + text + SmartScriptLexer.endOfTag);
			visitChildren(node);
			System.out.print(SmartScriptLexer.startOfTag + SmartScriptParser.endOfForLoop
					+ SmartScriptLexer.endOfTag);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			String text = "=";
			for (Element element : node.getElements()) {
				if (element instanceof ElementString) {
					String stringText = element.asText().replace("\n", "\\n").replace("\r", "\\r")
							.replace("\t", "\\t");
					text += " \"" + stringText + "\"";
				} else if (element instanceof ElementFunction) {
					text += " " + SmartScriptLexer.functionSymbol + element.asText();
				} else {
					text += " " + element.asText();
				}
			}
			System.out.print(SmartScriptLexer.startOfTag + text + SmartScriptLexer.endOfTag);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}

		/**
		 * Method which sends visitor to children of given node.
		 * 
		 * @param node Node whose children should be visited.
		 */
		private void visitChildren(Node node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	}

	/**
	 * Entrance into {@link TreeWriter} class.
	 * 
	 * @param args Path to file with Smart Script code.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Program must accept one argument - file path!");
			System.exit(1);
		}

		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
			INodeVisitor visitor = new WriterVisitor();
			SmartScriptParser parser = new SmartScriptParser(docBody);
			parser.getDocumentNode().accept(visitor);
		} catch (IOException e) {
			System.err.println("Invalid file path argument!");
			System.exit(1);
		}
	}

}
