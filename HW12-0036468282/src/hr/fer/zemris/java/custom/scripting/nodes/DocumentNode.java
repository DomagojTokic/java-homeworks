package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Root node of document
 * @author Domagoj Tokić
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
