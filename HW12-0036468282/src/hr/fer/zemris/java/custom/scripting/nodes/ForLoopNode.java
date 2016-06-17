package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node for storing for loop function.
 * @author Domagoj Tokić
 */
public class ForLoopNode extends Node {

	/**
	 * Variable for storing for loop iteration value
	 */
	ElementVariable variable;

	/**
	 * Start expression of for loop variable
	 */
	Element startExpression;

	/**
	 * Value with which variable is compared to
	 */
	Element endExpression;

	/**
	 * Value by which is variable changed every iteration
	 */
	Element stepExpression;

	/**
	 * Creates instance of ForLoopNode. Step expression can be null.
	 * @param variable For loop variable
	 * @param startExpression For loop start expression
	 * @param endExpression For loop end expression
	 * @param stepExpression For loop step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		if (variable == null || startExpression == null
				|| endExpression == null) {
			throw new IllegalArgumentException("Illegal null parameter!");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}



	/**
	 * Returns for loop variable
	 * @return for loop variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns for loop start expression
	 * @return for loop start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns for loop end expression
	 * @return for loop end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns for loop step expression
	 * @return for loop step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
