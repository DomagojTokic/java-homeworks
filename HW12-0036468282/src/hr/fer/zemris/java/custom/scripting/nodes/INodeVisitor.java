package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class implementing Visitor design pattern. It's used when we want to add
 * functionalities to nodes with keeping single responsibility principle.
 * 
 * @author Domagoj
 *
 */
public interface INodeVisitor {

	/**
	 * {@link TextNode} visit method.
	 * 
	 * @param node instance of text node.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * {@link ForLoopNode} visit method.
	 * 
	 * @param node instance of for loop node.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * {@link EchoNode} visit method.
	 * 
	 * @param node instance of echo node.
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * {@link DocumentNode} visit method.
	 * 
	 * @param node instance of document node.
	 */
	public void visitDocumentNode(DocumentNode node);

}
