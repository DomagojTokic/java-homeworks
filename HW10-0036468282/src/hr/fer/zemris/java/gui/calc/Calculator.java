package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * <p>
 * Simple calculator providing arithmetic and trigonometric operations. Program
 * works with double precision numbers. There are two types of operations -
 * operations which deal with one operand which is currently on screen and
 * operations which deal with to operands. If user selects operation before
 * pressing at least one number, operation is ignored. Program will
 * automatically calculate operation which has only one operand and when user
 * selects new two number operation after existing one without explicitly using
 * operation "=".
 * </p>
 * <p>
 * Pressing "=" multiple times after two number operation will replace first
 * operand with result and do the same operation again.
 * Number currently displayed on screen can be any time pushed on stack and at
 * any time (except when calculator is blocked) popped to replace currently
 * displayed number.
 * </p>
 * <p>
 * Supported two number operations: +, -, *, /, x^n and n-th root of x.
 * Supported unary operations: sin, arcsin, cos, arccos, tan, arctan, ctg,
 * arcctg, 1/x, log, 10^n, ln, e^n.
 * </p>
 * <p>
 * Calculator uses check box "Inv" to invert operations:
 * sin -> arcsin,
 * cos -> arccos,
 * tan -> arctan,
 * ctg -> arcctg,
 * log -> 10^n,
 * ln -> e^n,
 * x^n -> n'th root of x.
 * </p>
 * 
 * @author Domagoj
 *
 */
public class Calculator extends JFrame {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** Maximum button (and check box) size */
	public static final Dimension MAX_BUTTON_SIZE = new Dimension(500, 500);

	/** Last operand which user has input or popped from stack. */
	private String currentNumber;

	/** Number which has been input before last two number operator. */
	private Double previousNumber;

	/** Calculator stack for storing numbers currently displayed on screen. */
	private Stack<String> stack;

	/** Stores value of Inv (invert operation) check box */
	private boolean invert;

	/**
	 * Tells if input of next number should be added to currentNumber or should
	 * calculator reset operations and numbers and start writing a new number.
	 * If it's true, reset operations and numbers will happen.
	 */
	private boolean operationEnd;

	/**
	 * Tells if calculator is blocked from using arithmetic operations and
	 * pushing number. It's true when result of operation is Infinity or NaN.
	 * This variable is set from true to false when user enters new number
	 */
	private boolean blocked;

	/** Operation which was last pressed by user. */
	private BinaryOperator<Double> operation;

	/**
	 * Copy of last executed operation which will be executed if user presses
	 * button "=" multiple times.
	 */
	private BinaryOperator<Double> recursiveOperation;

	/**
	 * Creates new instance of {@link Calculator}.
	 */
	public Calculator() {
		super();
		currentNumber = null;
		previousNumber = null;
		stack = new Stack<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
		setMinimumSize(new Dimension(400, 300));
		setSize(600, 450);
		initGUI();
	}

	/**
	 * Initializes {@link Calculator} GUI. Sets up calculator screen, delegates
	 * generating buttons to other methods and sets maximum button size.
	 */
	private void initGUI() {

		Container cp = getContentPane();
		getContentPane().setLayout(new CalcLayout(5));

		JTextField screen = new JTextField("0");
		screen.setAlignmentY(CENTER_ALIGNMENT);
		screen.setHorizontalAlignment(JTextField.RIGHT);
		screen.setFont(new Font("SansSerif Bold", Font.PLAIN, 26));
		screen.setEditable(false);
		screen.setBackground(Color.YELLOW);
		cp.add(screen, "1,1");

		setDigits(cp, screen);
		setUnaryOperations(cp, screen);
		setDualOperations(cp, screen);
		setOperators(cp, screen);

		for (Component component : cp.getComponents()) {
			if (component instanceof JButton) {
				component.setMaximumSize(MAX_BUTTON_SIZE);
			}
		}
	}

	/**
	 * Sets up all calculator operators except arithmetic and trigonometric
	 * operations: equals, push, pop, clear, reset and invert check box.
	 * 
	 * @param cp Main calculator container.
	 * @param screen Calculator screen component.
	 */
	private void setOperators(Container cp, JTextField screen) {
		ActionListener equalsAction = a -> {
			if (blocked) {
				showErrorMessage();
				return;
			}			
			if (previousNumber != null && currentNumber != null && recursiveOperation != null) {
				operation = null;
				previousNumber = recursiveOperation.apply(previousNumber,
						Double.parseDouble(currentNumber));
				finishOperation(previousNumber, screen);
			} else {
				currentNumber = null;
			}
		};

		ActionListener clearAction = a -> {
			if (blocked) {
				unBlock();
			}
			currentNumber = null;
			screen.setText("0");
		};

		ActionListener resetAction = a -> {
			unBlock();
			invert = false;
			stack.clear();
			finishOperation(0.0, screen);
		};

		ActionListener pushAction = a -> {
			if (blocked) {
				showErrorMessage();
				return;
			}
			stack.push(screen.getText());
		};

		ActionListener popAction = a -> {
			if (blocked) {
				showErrorMessage();
				return;
			}
			if (!stack.isEmpty()) {
				currentNumber = stack.pop();
				screen.setText(currentNumber);
			}
		};

		ItemListener invertOperAction = c -> {
			JCheckBox checkBox = (JCheckBox) c.getItem();
			if (checkBox.isSelected()) {
				invert = true;
			} else {
				invert = false;
			}
		};

		JButton equals = new JButton("=");
		JButton clear = new JButton("clr");
		JButton reset = new JButton("res");
		JButton push = new JButton("push");
		JButton pop = new JButton("pop");
		JCheckBox invertOper = new JCheckBox("Inv");
		invertOper.setMaximumSize(MAX_BUTTON_SIZE);

		equals.addActionListener(equalsAction);
		clear.addActionListener(clearAction);
		reset.addActionListener(resetAction);
		push.addActionListener(pushAction);
		pop.addActionListener(popAction);
		invertOper.addItemListener(invertOperAction);

		cp.add(equals, "1,6");
		cp.add(clear, "1,7");
		cp.add(reset, "2,7");
		cp.add(push, "3,7");
		cp.add(pop, "4,7");
		cp.add(invertOper, "5,7");
	}

	/**
	 * Sets up two number operation buttons on calculator: +, -, *, / and x^n.
	 * 
	 * @param cp Main calculator container.
	 * @param screen Calculator screen component.
	 */
	private void setDualOperations(Container cp, JTextField screen) {

		ActionListener dualAction = a -> {
			executeDualAction(a, screen);
		};

		OperationStorageButton plus = new OperationStorageButton(DualOperation.PLUS,
				DualOperation.PLUS, "+");
		OperationStorageButton minus = new OperationStorageButton(DualOperation.MINUS,
				DualOperation.MINUS, "-");
		OperationStorageButton times = new OperationStorageButton(DualOperation.MULTIPLY,
				DualOperation.MULTIPLY, "*");
		OperationStorageButton divine = new OperationStorageButton(DualOperation.DIVIDE,
				DualOperation.DIVIDE, "/");
		OperationStorageButton power = new OperationStorageButton(DualOperation.POWER,
				DualOperation.ROOT, "x^n");

		plus.addActionListener(dualAction);
		minus.addActionListener(dualAction);
		times.addActionListener(dualAction);
		divine.addActionListener(dualAction);
		power.addActionListener(dualAction);

		cp.add(plus, "5,6");
		cp.add(minus, "4,6");
		cp.add(times, "3,6");
		cp.add(divine, "2,6");
		cp.add(power, "5,1");
	}

	/**
	 * Stores two number operation which will execute after user enters second
	 * number and presses "=" or another two number operation. Every two number
	 * operation is stored as recursive in case of user pressing "=" multiple
	 * times.
	 * 
	 * @param a Action generated from operation button.
	 * @param screen Calculator screen component.
	 */
	@SuppressWarnings("unchecked")
	private void executeDualAction(ActionEvent a, JTextField screen) {
		if (blocked) {
			showErrorMessage();
			return;
		}

		if (operation != null && currentNumber != null) {
			Double result = operation.apply(previousNumber, Double.parseDouble(currentNumber));
			currentNumber = result.toString();
			finishOperation(result, screen);
		}

		previousNumber = Double.parseDouble(screen.getText());

		if (!invert) {
			operation = (BinaryOperator<Double>) ((OperationStorageButton) a.getSource())
					.getMainOperation();
		} else {
			operation = (BinaryOperator<Double>) ((OperationStorageButton) a.getSource())
					.getInvertedOperation();
		}

		recursiveOperation = operation;
		operationEnd = false;
		currentNumber = null;
	}

	/**
	 * Sets up unary operation buttons on calculator: sin, cos, tan, ctg, log,
	 * ln and 1/x.
	 * 
	 * @param cp Main calculator container.
	 * @param screen Calculator screen component.
	 */
	private void setUnaryOperations(Container cp, JTextField screen) {
		ActionListener unaryAction = a -> {
			executeUnaryAction(a, screen);
		};

		OperationStorageButton sin = new OperationStorageButton(UnaryOperation.SIN,
				UnaryOperation.ARCSIN, "sin");
		OperationStorageButton invert = new OperationStorageButton(UnaryOperation.INVERT,
				UnaryOperation.INVERT, "1/x");
		OperationStorageButton log = new OperationStorageButton(UnaryOperation.LOG,
				UnaryOperation.POW10, "log");
		OperationStorageButton cos = new OperationStorageButton(UnaryOperation.COS,
				UnaryOperation.ARCCOS, "cos");
		OperationStorageButton ln = new OperationStorageButton(UnaryOperation.LN,
				UnaryOperation.POW_E, "ln");
		OperationStorageButton tan = new OperationStorageButton(UnaryOperation.TAN,
				UnaryOperation.ARCTAN, "tan");
		OperationStorageButton ctg = new OperationStorageButton(UnaryOperation.CTG,
				UnaryOperation.ARCCTG, "ctg");

		sin.addActionListener(unaryAction);
		invert.addActionListener(unaryAction);
		log.addActionListener(unaryAction);
		cos.addActionListener(unaryAction);
		ln.addActionListener(unaryAction);
		tan.addActionListener(unaryAction);
		ctg.addActionListener(unaryAction);

		cp.add(sin, "2,2");
		cp.add(invert, "2,1");
		cp.add(log, "3,1");
		cp.add(cos, "3,2");
		cp.add(ln, "4,1");
		cp.add(tan, "4,2");
		cp.add(ctg, "5,2");
	}

	/**
	 * Executes unary (one operand) operation, if calculator isn't blocked, on
	 * number currently displayed on
	 * screen.
	 * 
	 * @param a Action generated from operation button.
	 * @param screen Calculator screen component.
	 */
	@SuppressWarnings("unchecked")
	private void executeUnaryAction(ActionEvent a, JTextField screen) {
		if (blocked) {
			showErrorMessage();
			return;
		}
		if (currentNumber == null) {
			return;
		}

		OperationStorageButton button = (OperationStorageButton) a.getSource();
		UnaryOperator<Double> operation;
		if (!invert) {
			operation = (UnaryOperator<Double>) button.getMainOperation();
		} else {
			operation = (UnaryOperator<Double>) button.getInvertedOperation();
		}

		Double result = operation.apply(Double.parseDouble(screen.getText()));
		finishOperation(result, screen);
		currentNumber = screen.getText();
	}

	/**
	 * Sets up basic decimal digits 0-9, decimal dot, and operation for changing
	 * sign +/- or number currently displayed on screen.
	 * 
	 * @param cp Main calculator container.
	 * @param screen Calculator screen component.
	 */
	private void setDigits(Container cp, JTextField screen) {
		ActionListener addDigit = a -> {
			executeAddDigit(a, screen);
		};

		JButton one = new JButton("1");
		JButton two = new JButton("2");
		JButton three = new JButton("3");
		JButton four = new JButton("4");
		JButton five = new JButton("5");
		JButton six = new JButton("6");
		JButton seven = new JButton("7");
		JButton eight = new JButton("8");
		JButton nine = new JButton("9");
		JButton zero = new JButton("0");
		JButton dot = new JButton(".");

		one.addActionListener(addDigit);
		two.addActionListener(addDigit);
		three.addActionListener(addDigit);
		four.addActionListener(addDigit);
		five.addActionListener(addDigit);
		six.addActionListener(addDigit);
		seven.addActionListener(addDigit);
		eight.addActionListener(addDigit);
		nine.addActionListener(addDigit);
		zero.addActionListener(addDigit);
		dot.addActionListener(addDigit);

		cp.add(one, "4,3");
		cp.add(two, "4,4");
		cp.add(three, "4,5");
		cp.add(four, "3,3");
		cp.add(five, "3,4");
		cp.add(six, "3,5");
		cp.add(seven, "2,3");
		cp.add(eight, "2,4");
		cp.add(nine, "2,5");
		cp.add(zero, "5,3");
		cp.add(dot, "5,5");

		ActionListener changePosNeg = a -> {
			executeChangePosNeg(screen);
		};

		JButton posNeg = new JButton("+/-");
		posNeg.addActionListener(changePosNeg);
		cp.add(posNeg, "5,4");

	}

	/**
	 * Adds new digit current number. If previous input was operation, end of
	 * operation or calculator was blocked, method will start with creation of
	 * new number and forget about previous numbers and operation.
	 * 
	 * @param a
	 * @param screen
	 */
	private void executeAddDigit(ActionEvent a, JTextField screen) {
		if (blocked) {
			unBlock();
		}
		if (operationEnd) {
			currentNumber = null;
			operationEnd = false;
			operation = null;
			recursiveOperation = null;
		}

		String input = ((JButton) a.getSource()).getText();

		if (currentNumber == null && !input.equals(".")) {
			currentNumber = input;
		} else if (currentNumber == null && input.equals(".")) {
			currentNumber = "0.";
		} else if (currentNumber.contains(".") && input.equals(".")) {
			return;
		} else if (currentNumber.equals("0")) {
			currentNumber = input;
		} else {
			currentNumber += input;
		}
		screen.setText(currentNumber);
	}

	/**
	 * Changes sign of currently displayed number. If currently displayed number
	 * is result of operation, previously entered number and operation is reset.
	 * 
	 * @param screen Calculator screen component.
	 */
	private void executeChangePosNeg(JTextField screen) {
		if (blocked) {
			showErrorMessage();
			return;
		}
		String text = screen.getText();
		if (!text.equals("0")) {
			if (text.startsWith("-")) {
				screen.setText(text.substring(1, text.length()));
			} else {
				screen.setText("-" + text);
			}
			currentNumber = screen.getText();
			if (operationEnd) {
				previousNumber = null;
				operation = null;
			}
		}
	}

	/**
	 * Finishes operation by displaying result on calculator screen. If result
	 * is Infinity or NaN (Not a Number), it blocks user from using arithmetic
	 * operations until reset is pressed or new number has been input.
	 * Unblocking resets previous and current number and previously stored
	 * operations.
	 * 
	 * @param result Calculation result for displaying on screen.
	 * @param screen Calculator screen component.
	 */
	private void finishOperation(Double result, JTextField screen) {
		String output = null;
		operationEnd = true;
		if (result.isInfinite() || result.isNaN()) {
			blocked = true;
			output = result.toString();
		} else {
			if ((int) result.doubleValue() == result) {
				output = String.format("%d", result.intValue());
			} else {
				output = result.toString().replace(",", ".");
			}
		}
		screen.setText(output);
	}

	/**
	 * Unblocks calculator and deletes previously inserted numbers and
	 * operation.
	 */
	private void unBlock() {
		blocked = false;
		currentNumber = null;
		previousNumber = null;
		operation = null;
	}

	/**
	 * Shows error message if calculator is blocked and user tried to enter
	 * arithmetic or trigonometric operation.
	 */
	private void showErrorMessage() {
		JOptionPane.showMessageDialog(this,
				"Invalid calculation argument! Please reset, clear or insert new number.",
				"Invalid Operator", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Start of calculator application.
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

}
