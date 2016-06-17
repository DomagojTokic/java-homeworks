package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program adds new prime number on two parallel lists when user presses button
 * "next".
 * 
 * @author Domagoj
 *
 */
public class PrimDemo extends JFrame {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	
	/** Generator of primary numbers. */
	PrimListModel primListModel;

	/**
	 * Creates new instance of PrimDemo.
	 */
	public PrimDemo() {
		primListModel = new PrimListModel();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLocation(200, 200);
		setMinimumSize(new Dimension(200, 300));
		setSize(300, 400);
		initGUI();
	}

	/**
	 * Initializes {@link PrimDemo} GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		JList<Integer> leftList = new JList<Integer>();
		JList<Integer> rightList = new JList<Integer>();

		JScrollPane leftPanel = new JScrollPane(leftList);
		JScrollPane rightPanel = new JScrollPane(rightList);

		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(leftPanel);
		panel.add(rightPanel);

		JButton button = new JButton("next");

		ActionListener next = a -> {
			primListModel.next();
		};

		button.addActionListener(next);

		leftList.setModel(primListModel);
		rightList.setModel(primListModel);

		add(panel, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
	}

	/**
	 * Entrance into <code>PrimDemo</code>
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});

	}

}
