package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 * Example of creating and visualizing bar chart by using
 * {@link BarChartComponent} with data stored in file.
 * 
 * @author Domagoj
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Defult serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new instance of <code>BarChartDemo</code>
	 * 
	 * @param chartFile File path
	 */
	public BarChartDemo(String chartFile) {
		super();
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(1000, 700);
		setLocation(100, 100);
		BarChart chart = generateBarChart(chartFile);
		BarChartComponent component = new BarChartComponent(chart);
		Border test = BorderFactory.createMatteBorder(50, 100, 50, 100, Color.red);
		component.setBorder(test);
		JLabel label = new JLabel(chartFile);
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label, BorderLayout.NORTH);
		add(component, BorderLayout.CENTER);
	}

	/**
	 * Generates instance of {@link BarChart} from file.
	 * 
	 * @param chartFile File path
	 * @return instance of <code>BarChart</code>
	 */
	private BarChart generateBarChart(String chartFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(chartFile))) {
			ArrayList<XYValue> bars = new ArrayList<>();
			int yMin = 0;
			int yMax = 0;
			int step = 0;
			String xName = reader.readLine();
			String yName = reader.readLine();
			String barsLine = reader.readLine();
			String[] barArray = barsLine.split(" ");
			String[] splitted;
			for (int i = 0; i < barArray.length; i++) {
				splitted = barArray[i].split(",");
				if (splitted.length != 2) {
					System.err.println(
							"Third line must contain one or multiple whole number pairs 'x,y'");
					System.exit(1);
				}
				try {
					bars.add(new XYValue(Integer.parseInt(splitted[0]),
							Integer.parseInt(splitted[1])));
				} catch (NumberFormatException e) {
					System.err.println(
							"Third line must contain one or multiple whole number pairs 'x,y'");
					System.exit(1);
				}
			}
			try {
				yMin = Integer.parseInt(reader.readLine().trim());
				yMax = Integer.parseInt(reader.readLine().trim());
				step = Integer.parseInt(reader.readLine().trim());
			} catch (NumberFormatException e) {
				System.err.println("yMin, yMan and step must be whole numbers!");
				System.exit(1);
			}

			return new BarChart(bars, xName, yName, yMin, yMax, step);
		} catch (IOException e) {
			System.err.println("Unable to read from file " + chartFile);
			System.exit(1);
		}
		return null;
	}

	/**
	 * Entrance into {@link BarChartDemo} program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Please provide path to chart definition file!");
			System.exit(1);
		}
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(args[0]).setVisible(true);
		});
	}

}
