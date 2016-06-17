package hr.fer.zemris.java.hw13.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Generates PNG image of pie chart. The image is the same as represented on
 * http://www.vogella.com/tutorials/JFreeChart/article.html
 * 
 * @author Domagoj
 *
 */
public class PieChartServlet extends HttpServlet {

	/** Serial verion UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Which operating system are you using?");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(chartPanel.getPreferredSize());
		BufferedImage image = createImage(chartPanel);
		byte[] imageBytes = getImageBytes(image);
		resp.setContentType("image/png");
		resp.setContentLength(imageBytes.length);
		resp.getOutputStream().write(imageBytes);
	}

	/**
	 * Creates dataset represented on pie chart.
	 * 
	 * @return dataset for representation on pie chart.
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;
	}

	/**
	 * Creates new {@link JFreeChart} object out of given dataset and chart
	 * title.
	 * 
	 * @param dataset Data shown on chart.
	 * @param title Title of chart.
	 * @return new JFreeChart object out of given dataset and chart title.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * Creates image out of given {@link JPanel} object. In this case, it's
	 * used to create picture of pie chart.
	 * 
	 * @param panel Panel containing object(s) for getting image.
	 * @return Image of given JPanel object
	 */
	private static BufferedImage createImage(JPanel panel) {
		int w = panel.getWidth();
		int h = panel.getHeight();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		panel.paint(g);
		return bi;
	}

	/**
	 * Returns image in byte array representation.
	 * 
	 * @param image Image for conversion into byte array.
	 * @return byte array representation.
	 */
	private byte[] getImageBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", baos);
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

}
