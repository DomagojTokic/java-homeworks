package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine for executing Smart Script code. Result of operations are sent to
 * output defined in instance of {@link RequestContext}.
 * <p>
 * Supported arithmetic operations are +, -, * and /.
 * </p>
 * <p>
 * Functions are correctly defined in Smart Script syntax by defining arguments
 * before function:</br>
 * example of function pparamSet(value, name) in echo node: {$= value
 * name @pparamSet $}
 * </p>
 * <p>
 * Supported functions are:</br>
 * sin(x) - calculates sinus from given argument and stores the result back to
 * stack.</br>
 * decfmt(x,f) - formats decimal number using given format f which is compatible
 * with {@link DecimalFormat}; produces a string.</br>
 * dup() - duplicates current top value from stack.</br>
 * swap() - replaces the order of two topmost items on stack.</br>
 * setMimeType(x) - takes string x and calls requestContext.setMimeType(x).</br>
 * paramGet(name, defValue) - Obtains from requestContext parameters map a value
 * mapped for name and pushes it onto stack. If there is no such mapping, it
 * pushes instead defValue onto stack.</br>
 * pparamGet(name, defValue) - same as paramGet but reads from requestContext
 * persistent parameters map.</br>
 * pparamSet(value, name) - stores a value into requestContext persistent
 * parameters map.</br>
 * pparamDel(name) - removes association for name from requestContext
 * persistentParameters map.</br>
 * tparamGet(name, defValue) - same as paramGet but reads from requestContext
 * temporaryParameters map.</br>
 * tparamSet(value, name) - stores a value into requestContext
 * temporaryParameters map.</br>
 * tparamDel(name) - removes association for name from requestContext
 * temporaryParameters map.</br>
 * </p>
 * 
 * @author Domagoj
 *
 */
public class SmartScriptEngine {

	/**
	 * Document node of script which will be executed.
	 */
	private DocumentNode documentNode;

	/**
	 * Instance of {@link RequestContext} which defines output format.
	 */
	private RequestContext requestContext;

	/**
	 * Stack for keeping of variable values.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Node visitor which executes operations defined in all nodes sends results
	 * to requestContext's output.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("Failed to write " + node.getText() + " to context!");
				System.exit(1);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().getName();
			multistack.push(variableName, new ValueWrapper(node.getStartExpression().asText()));
			while (multistack.peek(variableName)
					.numCompare(node.getEndExpression().asText()) <= 0) {
				visitChildren(node);
				multistack.peek(variableName).increment(node.getStepExpression().asText());
			}
			multistack.pop(variableName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectStack temp = new ObjectStack();
			for (Element element : node.getElements()) {
				if (element instanceof ElementVariable) {
					temp.push(multistack.peek(element.asText()).getValue());
				} else if (element instanceof ElementOperator) {
					executeOperator(element.asText(), temp);
				} else if (element instanceof ElementFunction) {
					executeFunction(element.asText(), temp);
				} else {
					temp.push(element.asText());
				}
			}
			String echo = "";
			while (!temp.isEmpty()) {
				echo = temp.pop().toString() + echo;
			}
			try {
				requestContext.write(echo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}

		private void visitChildren(Node node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Creates new instance of {@link SmartScriptEngine}
	 * 
	 * @param documentNode Document node of script which will be executed.
	 * @param requestContext Instance of {@link RequestContext} which defines
	 *            output format.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Start method of {@link SmartScriptEngine}.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Executes basic arithmetic operations: +, -. * and /.
	 * 
	 * @param symbol Arithmetic operator symbol.
	 * @param temp Stack which stores operands.
	 */
	private void executeOperator(String symbol, ObjectStack temp) {
		ValueWrapper firstNum = new ValueWrapper(temp.pop());
		Object secondNum = temp.pop();
		switch (symbol) {
		case "+":
			firstNum.increment(secondNum);
			break;
		case "-":
			firstNum.decrement(secondNum);
			break;
		case "*":
			firstNum.multiply(secondNum);
			break;
		case "/":
			firstNum.divide(secondNum);
			break;
		default:
			throw new RuntimeException("Unable to execute operation " + symbol + "!");
		}
		temp.push(firstNum.getValue().toString());
	}

	/**
	 * 
	 * @param function
	 * @param temp
	 */
	private void executeFunction(String function, ObjectStack temp) {
		switch (function) {
		case "sin":
			Double number = Double.parseDouble(temp.pop().toString());
			temp.push(new Double(Math.sin(number)).toString());
			break;
		case "decfmt":
			String format = temp.pop().toString();
			DecimalFormat decimalFormat = new DecimalFormat(format);
			temp.push(decimalFormat.format(Double.parseDouble(temp.pop().toString())));
			break;
		case "dup":
			Object object = temp.pop();
			temp.push(object);
			temp.push(object);
			break;
		case "swap":
			Object first = temp.pop();
			Object second = temp.pop();
			temp.push(first);
			temp.push(second);
			break;
		case "setMimeType":
			requestContext.setMimeType(temp.pop().toString());
			break;
		case "paramGet":
			Object defValue1 = temp.pop();
			Object name1 = temp.pop();
			String value1 = requestContext.getParameter(name1.toString());
			temp.push(value1 == null ? defValue1 : value1);
			break;
		case "pparamGet":
			Object defValue2 = temp.pop();
			Object name2 = temp.pop();
			String value2 = requestContext.getPersistentParameter(name2.toString());
			temp.push(value2 == null ? defValue2 : value2);
			break;
		case "pparamSet":
			Object name3 = temp.pop();
			Object value3 = temp.pop();
			requestContext.setPersistentParameter(name3.toString(), value3.toString());
			break;
		case "tparamGet":
			Object defValue4 = temp.pop();
			Object name4 = temp.pop();
			String value4 = requestContext.getTemporaryParameter(name4.toString());
			temp.push(value4 == null ? defValue4 : value4);
			break;
		case "tparamSet":
			Object name5 = temp.pop();
			Object value5 = temp.pop();
			requestContext.setTemporaryParameter(name5.toString(), value5.toString());
			break;
		case "tparamDel":
			Object delName = temp.pop();
			requestContext.removeTemporaryParameter(delName.toString());
			break;
		default:
			throw new RuntimeException("Invalid function " + function + "!");
		}
	}

}
